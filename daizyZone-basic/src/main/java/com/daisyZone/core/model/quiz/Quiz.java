package com.daisyZone.core.model.quiz;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目表pojo
 *
 * @author lindaixi
 * @create 2018-09-19-13:20
 */
@Alias("quiz")
@ApiModel(value="题目表")
@Data
public class Quiz implements Serializable {
    private static final long serialVersionUID = -7170957885389161945L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="主键")
    private Long pid = 0L;

    /**
     * 题干
     */
    @ApiModelProperty(value="题干")
    private String title = "";

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark = "";

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value="修改时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date utime;
}
