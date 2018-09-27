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
 * 标签表pojo
 *
 * @author lindaixi
 * @create 2018-09-20-9:25
 */
@Alias("quizTag")
@ApiModel("标签表")
@Data
public class QuizTag implements Serializable {
    private static final long serialVersionUID = 1482220772123604336L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long pid = 0L;

    /**
     * 标签名称
     */
    @ApiModelProperty("标签名称")
    private String name = "";

    /**
     * 标签描述
     */
    @ApiModelProperty("标签描述")
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
