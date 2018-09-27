package com.daisyZone.core.model.quiz;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 题目题型关联表pojo
 *
 * @author lindaixi
 * @create 2018-09-20-9:22
 */
@Alias("quizTypeRelation")
@ApiModel("题目题型关联表")
@Data
public class QuizTypeRelation implements Serializable {
    private static final long serialVersionUID = -953395466369564329L;

    /**
     * 题目Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("题目Id")
    private Long quizId = 0L;

    /**
     * 题型Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("题型Id")
    private Long typeId = 0L;
}
