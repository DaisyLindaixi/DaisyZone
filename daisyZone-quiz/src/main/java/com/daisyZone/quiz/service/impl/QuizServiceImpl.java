package com.daisyZone.quiz.service.impl;

import com.daisyZone.core.model.quiz.Quiz;
import com.daisyZone.quiz.mapper.QuizMapper;
import com.daisyZone.quiz.service.QuizService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 题目表业务层接口实现类
 *
 * @author lindaixi
 * @create 2018-09-20-15:59
 */
@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizMapper quizMapper;

    @Override
    public Quiz add(Quiz data) {
        return null;
    }

    @Override
    public Quiz update(Quiz data) {
        return null;
    }

    @Override
    public void delete(Quiz data) {

    }

    @Override
    public List<Quiz> query(Quiz condition) {
//        return null;
        return quizMapper.query(condition);
    }

    @Override
    public Integer count(Quiz condition) {
        return null;
    }

    @Override
    public Quiz find(Long id) {
        return null;
    }
}
