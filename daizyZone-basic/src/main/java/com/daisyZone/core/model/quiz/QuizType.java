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
 * 题型表pojo
 *
 * @author lindaixi
 * @create 2018-09-19-13:37
 */
@Alias("quizType")
@ApiModel(value = "题型表")
@Data
public class QuizType implements Serializable {
    private static final long serialVersionUID = 8100225899196224513L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long pid = 0L;

    /**
     * 题型名称
     */
    @ApiModelProperty("题型名称")
    private String name = "";

    /**
     * 题型描述
     */
    @ApiModelProperty("题型描述")
    private String description = "";

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date utime;
}
