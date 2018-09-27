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
 * 解答表pojo
 *
 * @author lindaixi
 * @create 2018-09-19-16:53
 */
@Alias("answer")
@ApiModel("解答表")
@Data
public class Answer implements Serializable {
    private static final long serialVersionUID = 1736040398236111132L;

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
     * 答案主体
     */
    @ApiModelProperty("答案主体")
    private String content = "";

    /**
     * 是否正确
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("是否正确")
    private Integer isCorrect = 1;

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
