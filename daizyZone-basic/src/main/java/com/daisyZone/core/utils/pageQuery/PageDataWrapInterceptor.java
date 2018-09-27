package com.daisyZone.core.utils.pageQuery;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * mybatics查询结果返回拦截器，将查询的结果封装成PageData,返回回去
 * 
 * @ClassName: PageDataWrapInterceptor
 * @author: fuyinan
 * @date: 2016年6月12日 下午3:26:12
 */
@Intercepts({@org.apache.ibatis.plugin.Signature(method = "handleResultSets",
    type = org.apache.ibatis.executor.resultset.ResultSetHandler.class,
    args = {java.sql.Statement.class})})
public class PageDataWrapInterceptor implements Interceptor {

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Object intercept(Invocation paramInvocation) throws Throwable {
    Object param = paramInvocation.proceed();
    PageBounds localPageBounds = (PageBounds) PageInterceptor.MARK_DATA_PAGE_BOUNDS.get();
    if ((localPageBounds != null) && (!(param instanceof PageData)) && ((param instanceof List))) {
      int i = PageInterceptor.getTotalCount().intValue();
      List<PageData> localArrayList = new ArrayList<PageData>();
      if (i < 0) {
        PageData pageData = new PageData(0, localPageBounds.getLimit(), new ArrayList(), 0);
        localArrayList.add(pageData);
      } else {
        PageData pageData =
            new PageData(localPageBounds.getOffset(), localPageBounds.getLimit(), (List) param, i);
        localArrayList.add(pageData);
      }

      param = localArrayList;
    }
    return param;
  }

  @Override
  public Object plugin(Object paramObject) {
    return Plugin.wrap(paramObject, this);
  }

  @Override
  public void setProperties(Properties properties) {
    // TODO Auto-generated method stub

  }

}
