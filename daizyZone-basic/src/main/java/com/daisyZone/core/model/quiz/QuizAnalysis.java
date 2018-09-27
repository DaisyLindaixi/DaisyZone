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
 * 题目分析表pojo
 *
 * @author lindaixi
 * @create 2018-09-19-16:25
 */
@Alias("analysis")
@ApiModel("题目分析表")
@Data
public class QuizAnalysis implements Serializable {
    private static final long serialVersionUID = 8355516548034878286L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long pid = 0L;

    /**
     * 关联题目Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("关联题目Id")
    private Long quizId = 0L;

    /**
     * 解析内容
     */
    @ApiModelProperty("解析内容")
    private String content = "";

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
