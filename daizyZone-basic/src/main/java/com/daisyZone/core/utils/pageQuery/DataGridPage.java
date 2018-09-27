package com.daisyZone.core.utils.pageQuery;

import java.util.List;

/**
 * 查询出来的分页结果 转换 成grid格式的数据
 * 
 * @Description
 * @author jiaqi
 * @date 2016年6月17日 下午1:00:36
 * @version V1.0
 * @param <T>
 */
public class DataGridPage<T> {
  /**
   * 总记录数
   */
  private final int total;

  /**
   * 查询结果集
   */
  private final List<T> rows;

  public DataGridPage(int total, List<T> rows) {
    super();
    this.total = total;
    this.rows = rows;
  }

  /**
   * 将PageData<T> page 数据格式化
   * 
   * @Description
   * @author jiaqi
   * @param page
   * @return
   */
  public static <T> DataGridPage<T> create(PageData<T> page) {
    if (page == null) {
      return null;
    } else {
      return new DataGridPage<T>(page.getTotal(), page.getRecords());
    }
  }

  /**
   * 获取每页数据
   * 
   * @Description
   * @author jiaqi
   * @return
   */
  public List<T> getRows() {
    return this.rows;
  }

  /**
   * 得到总记录数
   * 
   * @Description
   * @author jiaqi
   * @return
   */
  public int getTotal() {
    return total;
  }

}
