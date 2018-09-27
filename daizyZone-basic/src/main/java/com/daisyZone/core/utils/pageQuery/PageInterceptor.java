package com.daisyZone.core.utils.pageQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * mybaitis分页处理拦截器
 * <p>
 * 将所有的Mapper中方法参数为pageQuery的方法在 执行sql语句之前，对sql语句进行包装，达到分页查询的目的
 *
 * @ClassName: PageInterceptor
 * @author: fuyinan
 * @date: 2016年6月12日 上午8:59:21
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
        args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {

    /**
     * 数据库方言
     */
    private String dialect = "";

    /**
     * 数据库方言 -- oracle
     */
    private static final String ORACLE = "oracle";

    /**
     * 数据库方言 -- mysql
     */
    private static final String MYSQL = "mysql";

    /**
     * 统计条数，在查询结果返回拦截器中使用，设置ThreadLocal()解决多线程并发的问题
     */
    private static final ThreadLocal<Integer> TOTAL_COUNT = new ThreadLocal<>();

    /**
     * 分页查询条件，在查询结果返回拦截器中使用，设置ThreadLocal()同样为解决多线程并发问题
     */
    protected static final ThreadLocal<PageBounds> MARK_DATA_PAGE_BOUNDS =
            new ThreadLocal<PageBounds>();

    /**
     * 拦截器主要方法，用于获取到sql语句，并将sql包装
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 对于StatementHandler其实只有两个实现类，一个是RoutingStatementHandler，另一个是抽象类BaseStatementHandler，
        // BaseStatementHandler有三个子类，分别是SimpleStatementHandler，PreparedStatementHandler和CallableStatementHandler，
        // SimpleStatementHandler是用于处理Statement的，PreparedStatementHandler是处理PreparedStatement的，而CallableStatementHandler是
        // 处理CallableStatement的。Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler，而在RoutingStatementHandler里面拥有一个
        // StatementHandler类型的delegate属性，RoutingStatementHandler会依据Statement的不同建立对应的BaseStatementHandler，即SimpleStatementHandler、
        // PreparedStatementHandler或CallableStatementHandler，在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。
        // 我们在PageInterceptor类上已经用@Signature标记了该Interceptor只拦截StatementHandler接口的prepare方法，又因为Mybatis只有在建立RoutingStatementHandler的时候
        // 是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate =
                    (StatementHandler) ReflectHelper.getFieldValue(statementHandler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
            Object obj = boundSql.getParameterObject();
            if (obj instanceof PageQuery) {
                PageQuery page = (PageQuery) obj;
                PageBounds pageBound = new PageBounds(page.getStart(), page.getRows(), page.getOrders());
                MARK_DATA_PAGE_BOUNDS.set(pageBound);

                // 通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
                MappedStatement mappedStatement =
                        (MappedStatement) ReflectHelper.getFieldValue(delegate, "mappedStatement");
                // 拦截到的prepare方法参数是一个Connection对象
                Connection connection = (Connection) invocation.getArgs()[0];
                // 获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句
                String sql = boundSql.getSql();
                // 给当前的page参数对象设置总记录数
                this.setTotalRecord(page, mappedStatement, connection);
                // 获取分页Sql语句
                String pageSql = this.getPageSql(page, sql);
                // 利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句
                ReflectHelper.setFieldValue(boundSql, "sql", pageSql);
            } else {
                MARK_DATA_PAGE_BOUNDS.set(null);
            }
        }
        return invocation.proceed();
    }

    /**
     * 给当前的参数对象page设置总记录数
     *
     * @param page            Mapper映射语句对应的参数对象
     * @param mappedStatement Mapper映射语句
     * @param connection      当前的数据库连接
     */
    private void setTotalRecord(PageQuery page, MappedStatement mappedStatement,
                                Connection connection) {
        // 获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
        // delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        // 获取到我们自己写在Mapper映射语句中对应的Sql语句
        String sql = boundSql.getSql();
        // 通过查询Sql语句获取到对应的计算总记录数的sql语句
        String countSql = this.getCountSql(sql);
        // 通过BoundSql获取对应的参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
        BoundSql countBoundSql =
                new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
        // 通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
        ParameterHandler parameterHandler =
                new DefaultParameterHandler(mappedStatement, page, countBoundSql);
        // 通过connection建立一个countSql对应的PreparedStatement对象。
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            // 通过parameterHandler给PreparedStatement对象设置参数
            parameterHandler.setParameters(pstmt);
            // 之后就是执行获取总记录数的Sql语句和获取结果了。
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                TOTAL_COUNT.set(Integer.valueOf(totalRecord));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据查询的sql拼出查询总数的sql
     *
     * @param sql 查询sql
     * @return 查询总数的sql
     */
    private String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") o ";
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
     *
     * @param page 分页对象
     * @param sql  原sql语句
     * @return 分页的sql语句
     */
    private String getPageSql(PageQuery page, String sql) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if (StringUtils.isEmpty(dialect) || MYSQL.equalsIgnoreCase(dialect)) {
            return getMysqlPageSql(page, sqlBuffer);
        } else if (ORACLE.equalsIgnoreCase(dialect)) {
            return getOraclePageSql(page, sqlBuffer);
        }
        return sqlBuffer.toString();
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句，--对应mysql
     *
     * @param page      分页对象
     * @param sqlBuffer 原sql语句
     * @return Mysql数据库的分页查询语句
     */
    private String getMysqlPageSql(PageQuery page, StringBuffer sqlBuffer) {
        sqlBuffer.append(" limit ").append(page.getStart()).append(",").append(page.getRows());
        return sqlBuffer.toString();
    }

    /**
     * 获取Oracle数据库的分页查询语句
     *
     * @param page      分页对象
     * @param sqlBuffer 包含原sql语句的String
     * @return Oracle数据库的分页查询语句
     */
    private String getOraclePageSql(PageQuery page, StringBuffer sqlBuffer) {
        // 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
                .append(page.getStart() + page.getRows());
        sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(page.getStart());
        // 上面的Sql语句拼接之后大概是这个样子：
        // select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r
        // >= 16
        return sqlBuffer.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /**
     * org.apache.ibatis.plugin.Interceptor 中封装的设置属性的方法；
     * <p>
     * xml中配置的属性都封装在properties中，通过自己写的 get set 方法无法获取
     */
    @Override
    public void setProperties(Properties properties) {
        // 获取数据库方言配置
        String dialect = properties.getProperty("dialect");
        this.dialect = dialect;
    }

    public static Integer getTotalCount() {
        return TOTAL_COUNT.get();
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

}
