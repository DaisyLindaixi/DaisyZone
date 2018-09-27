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
 * 解答记录表pojo
 *
 * @author lindaixi
 * @create 2018-09-19-17:05
 */
@Alias("quizLog")
@ApiModel("解答记录表")
@Data
public class QuizLog implements Serializable {
    private static final long serialVersionUID = 6707666082138111059L;

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
     * 解答。可为空。为空则题目没有做完
     */
    @ApiModelProperty("解答")
    private String answer = "";

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
