package com.daisyZone.core.utils.pageQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果封装类
 * 
 * @Description
 * @author jiaqi
 * @date 2016年6月24日 下午1:17:50
 * @version V1.0
 * @param <T>
 */
public class PageData<T> implements Serializable {

  /** @Fields serialVersionUID: */

  private static final long serialVersionUID = 3346734648891873135L;

  /**
   * 起始行
   */
  protected int start = 0;

  /**
   * 每行记录数
   */
  protected int limit;

  /**
   * 总数据条数
   */
  protected int total = -1;

  /**
   * 具体每页的数据
   */
  protected List<T> records = null;

  public PageData(int paramInt1, int paramInt2, List<T> paramList, int paramInt3) {
    this.start = paramInt1;
    this.limit = paramInt2;
    this.records = paramList;
    this.total = paramInt3;
  }

  public boolean isStartEnabled() {
    return this.start >= 0;
  }

  public int getStart() {
    return this.start;
  }

  public int getLimit() {
    return this.limit;
  }

  public int getTotal() {
    return this.total;
  }

  public List<T> getRecords() {
    return this.records == null ? new ArrayList<>() : this.records;
  }

  public int getPageNo() {
    if (this.limit == 0) {
      return -1;
    }
    return this.start / this.limit + 1;
  }

  public int getTotalPages() {
    if ((this.total < 0) || (this.limit <= 0)) {
      return -1;
    }
    return (this.total - 1) / this.limit + 1;
  }
}
