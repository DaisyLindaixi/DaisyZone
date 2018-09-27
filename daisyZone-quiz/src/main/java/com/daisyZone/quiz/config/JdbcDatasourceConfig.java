package com.daisyZone.quiz.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.daisyZone.core.common.mapper.SqlMapper;
import com.daisyZone.core.utils.pageQuery.PageDataWrapInterceptor;
import com.daisyZone.core.utils.pageQuery.PageInterceptor;
import com.daisyZone.core.utils.pageQuery.PageQuery;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据源配置
 * @author Luo Liang
 */
@Configuration
@MapperScan(basePackages = "com.daisyZone.*.mapper", markerInterface = SqlMapper.class, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class JdbcDatasourceConfig {
    /**
     * 别名pojo所在包,可以通过逗号分隔
     */
    public static final String TYPE_ALIASES_PACKAGE = "com.daisyZone.core.model";

    /**
     * mybatis的mapper.xml所在位置
     */
    public static final String MAPPER_LOCATION = "classpath*:public/db/**/*Mapper.xml";


    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource(Environment env) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(env.getProperty("spring.datasource.url"));
        druidDataSource.setUsername(env.getProperty("spring.datasource.username"));
        druidDataSource.setPassword(env.getProperty("spring.datasource.password"));
        druidDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return druidDataSource;
    }

    /**
     * mybatis的配置,相当于之前的configuration.xml
     */
    private org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 配置缓存
        configuration.setCacheEnabled(false);
        // 对于未知的SQL查询，允许返回不同的结果集以达到通用的效果
        configuration.setMultipleResultSetsEnabled(true);
        // 配置默认的执行器。SIMPLE 执行器没有什么特别之处。REUSE 执行器重用预处理语句。BATCH 执行器重用语句和批量更新
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        // 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载
        configuration.setLazyLoadingEnabled(false);
        configuration.setAggressiveLazyLoading(true);
        // 设置超时时间，它决定驱动等待一个数据库响应的时间
        configuration.setDefaultStatementTimeout(25000);

        // 增加分页别名
        configuration.getTypeAliasRegistry().registerAlias("pageQuery", PageQuery.class);

        // 增加分页拦截器以及分页数据包装拦截器
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setDialect("mysql");
        configuration.addInterceptor(pageInterceptor);
        configuration.addInterceptor(new PageDataWrapInterceptor());

        return configuration;
    }

    /**
     * 使用SqlSessionFactoryBean工厂产生SqlSession对象,mybatis核心对象
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        // 配置mapper.xml文件位置
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] mapperLocations = resolver.getResources(MAPPER_LOCATION);

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        sqlSessionFactoryBean.setConfiguration(configuration());
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 系统的基础数据源事务管理器   对于多数据源的事务管理，需指定相应的事务管理器
     * @param dataSource 数据源
     * @return PlatformTransactionManager事务管理
     */
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
