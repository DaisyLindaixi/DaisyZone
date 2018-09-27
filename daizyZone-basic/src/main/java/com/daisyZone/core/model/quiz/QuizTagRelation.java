package com.daisyZone.core.model.quiz;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 题目标签关联表pojo
 *
 * @author lindaixi
 * @create 2018-09-20-9:16
 */
@Alias("quizTagRelation")
@ApiModel("题目标签关联表")
@Data
public class QuizTagRelation implements Serializable {
    private static final long serialVersionUID = -3302645527321082662L;

    /**
     * 题目Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("题目Id")
    private Long quizId = 0L;

    /**
     * 标签Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("标签Id")
    private Long tagId = 0L;
}
