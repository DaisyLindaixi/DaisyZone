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
 * 笔记表pojo
 *
 * @author lindaixi
 * @create 2018-09-19-17:26
 */
@Alias("quizNotes")
@ApiModel("笔记表")
@Data
public class QuizNotes implements Serializable {
    private static final long serialVersionUID = -7238753648344499179L;

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
     * 笔记内容
     */
    @ApiModelProperty("笔记内容")
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
