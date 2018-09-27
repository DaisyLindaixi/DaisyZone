package com.daisyZone.core.utils.pageQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.Alias;

import java.util.Map;

/**
 * 分页查询参数
 *
 * @Description
 * @author jiaqi
 * @date 2016年6月16日 下午4:57:55
 * @version V1.0
 */
@Alias("pageQuery")
public class PageQuery {

  private static final Integer DEFAULT_LIMIT = 10;

  /**
   * 起始行[算法为(page-1)*limit]
   */
  private int offset;
  /**
   * 每页展示记录数[如不传或为0则默认是20]
   */
  private int limit;

  /**
   * 当前页数,从1开始 [为了app端使用分页方便增加此属性]
   */
  private Integer page;

  /**
   * 搜索框中过滤条件
   */
  private Map<String, String> searchMap;

  /**
   * 搜索框过滤条件连接方式
   * <p>
   * and / or (默认or)
   */
  private String connectType = "or";

  /**
   * 自定义过滤条件
   */
  private Map<String, String> filter;

  /**
   * 用于设置除了分页相关参数以外的参数
   * <p>
   * 分页拦截器只会拦截方法参数只有一个PageQuery的方法
   */
  private Object otherParams;

  /**
   * 排序字段[xml中排序使用此值,sort中的排序也将统一到此属性,具体参看get方法]
   */
  private String orders;

  /**
   * angularjs Table data-sortable为true时的排序列名 wangyong 2017-2-22
   */
  private String sort;
  /**
   * angularjs Table data-sortable为true时的排序的方式 desc asc wangyong 2017-2-22
   */
  private String order;

  public PageQuery() {
    super();
  }


  public PageQuery(int offset, int limit, Map<String, String> searchMap, String connectType,
      Map<String, String> filter, String orders) {
    super();
    this.offset = offset;
    this.limit = limit;
    this.searchMap = searchMap;
    this.connectType = connectType;
    this.filter = filter;
    this.orders = orders;
  }


  /**
   * Gets the 起始行[算法为(page-1)*limit].
   *
   * @return the 起始行[算法为(page-1)*limit]
   */
  public int getOffset() {
    offset = (page != null ? (page - 1) * getLimit() : offset);
    return offset;
  }

  /**
   * Sets the 起始行[算法为(page-1)*limit].
   *
   * @param offset the new 起始行[算法为(page-1)*limit]
   */
  public void setOffset(int offset) {
    this.offset = offset;
  }

  /**
   * Gets the 当前页数,从1开始 [为了app端使用分页方便增加此属性].
   *
   * @return the 当前页数,从1开始 [为了app端使用分页方便增加此属性]
   */
  public int getPage() {
    return page == null ? 0 : page;
  }


  /**
   * Sets the 当前页数,从1开始 [为了app端使用分页方便增加此属性].
   *
   * @param page the new 当前页数,从1开始 [为了app端使用分页方便增加此属性]
   */
  public void setPage(int page) {
    this.page = page;
  }


  /**
   * Gets the 每页展示记录数[如不传或为0则默认是20].
   *
   * @return the 每页展示记录数[如不传或为0则默认是20]
   */
  public int getLimit() {
    return limit < 1 ? DEFAULT_LIMIT : limit;
  }

  /**
   * Sets the 每页展示记录数[如不传或为0则默认是20].
   *
   * @param limit the new 每页展示记录数[如不传或为0则默认是20]
   */
  public void setLimit(int limit) {
    this.limit = limit;
  }

  /**
   * Gets the 搜索框中过滤条件.
   *
   * @return the 搜索框中过滤条件
   */
  public Map<String, String> getSearchMap() {
    return searchMap;
  }

  /**
   * Sets the 搜索框中过滤条件.
   *
   * @param searchMap the new 搜索框中过滤条件
   */
  public void setSearchMap(Map<String, String> searchMap) {
    this.searchMap = searchMap;
  }



  /**
   * Gets the 搜索框过滤条件连接方式 <p> and / or (默认or).
   *
   * @return the 搜索框过滤条件连接方式 <p> and / or (默认or)
   */
  public String getConnectType() {
    return connectType;
  }



  /**
   * Sets the 搜索框过滤条件连接方式 <p> and / or (默认or).
   *
   * @param connectType the new 搜索框过滤条件连接方式 <p> and / or (默认or)
   */
  public void setConnectType(String connectType) {
    this.connectType = connectType;
  }



  /**
   * Gets the 自定义过滤条件.
   *
   * @return the 自定义过滤条件
   */
  public Map<String, String> getFilter() {
    return filter;
  }



  /**
   * Sets the 自定义过滤条件.
   *
   * @param filter the new 自定义过滤条件
   */
  public void setFilter(Map<String, String> filter) {
    this.filter = filter;
  }



  /**
   * Gets the 排序字段,xml中排序使用此值,sort中的排序也将统一到此属性.
   *
   * @return the 排序字段,xml中排序使用此值,sort中的排序也将统一到此属性
   */
  public String getOrders() {
    // 优先使用orders中的排序配置，可配置多项[utime desc,ctime desc]
    if (StringUtils.isNotEmpty(orders)) {
      return orders;
    } else if (StringUtils.isEmpty(orders) && StringUtils.isNotEmpty(sort)) {
      // 若orders为空 sort不为空[前台table框架主要传递这个参数]用sort及order拼出orders使用
      return sort + " " + (order == null ? "" : order);
    }
    // 没有排序配置返回null，xml中可判断orders是否为null来决定是否使用后台sql默认排序
    return null;
  }

  /**
   * Sets the 排序字段,xml中排序使用此值,sort中的排序也将统一到此属性.
   *
   * @param orders the new 排序字段,xml中排序使用此值,sort中的排序也将统一到此属性
   */
  public void setOrders(String orders) {
    this.orders = orders;
  }

  /**
   * Gets the start.
   *
   * @return the start
   */
  public int getStart() {
    return this.getOffset();
  }

  /**
   * Gets the rows.
   *
   * @return the rows
   */
  public int getRows() {
    return this.getLimit();
  }


  /**
   * Gets the 用于设置除了分页相关参数以外的参数 <p> 分页拦截器只会拦截方法参数只有一个PageQuery的方法.
   *
   * @return the 用于设置除了分页相关参数以外的参数 <p> 分页拦截器只会拦截方法参数只有一个PageQuery的方法
   */
  public Object getOtherParams() {
    return otherParams;
  }


  /**
   * Sets the 用于设置除了分页相关参数以外的参数 <p> 分页拦截器只会拦截方法参数只有一个PageQuery的方法.
   *
   * @param otherParams the new 用于设置除了分页相关参数以外的参数 <p> 分页拦截器只会拦截方法参数只有一个PageQuery的方法
   */
  public void setOtherParams(Object otherParams) {
    this.otherParams = otherParams;
  }


  /**
   * Gets the angularjs Table data-sortable为true时的排序列名 wangyong 2017-2-22.
   *
   * @return the angularjs Table data-sortable为true时的排序列名 wangyong 2017-2-22
   */
  public String getSort() {
    return sort;
  }


  /**
   * Sets the angularjs Table data-sortable为true时的排序列名 wangyong 2017-2-22.
   *
   * @param sort the new angularjs Table data-sortable为true时的排序列名 wangyong 2017-2-22
   */
  public void setSort(String sort) {
    this.sort = sort;
  }


  /**
   * Gets the angularjs Table data-sortable为true时的排序的方式 desc asc wangyong 2017-2-22.
   *
   * @return the angularjs Table data-sortable为true时的排序的方式 desc asc wangyong 2017-2-22
   */
  public String getOrder() {
    return order;
  }


  /**
   * Sets the angularjs Table data-sortable为true时的排序的方式 desc asc wangyong 2017-2-22.
   *
   * @param order the new angularjs Table data-sortable为true时的排序的方式 desc asc wangyong 2017-2-22
   */
  public void setOrder(String order) {
    this.order = order;
  }


}
