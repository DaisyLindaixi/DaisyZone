package com.daisyZone.core.utils.pageQuery;

import org.apache.ibatis.session.RowBounds;

/**
 * 存放分页相关信息
 * 
 * @Description
 * @author jiaqi
 * @date 2016年6月24日 下午1:17:31
 * @version V1.0
 */
public class PageBounds extends RowBounds {

  /**
   * 排序字段
   */
  private String orders;

  public PageBounds(int start, int limit, String orders) {
    super(start, limit);
    this.orders = orders;
  }

  public String getOrders() {
    return this.orders;
  }
}
