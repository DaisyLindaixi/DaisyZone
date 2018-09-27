package com.daisyZone.quiz.endpoints.version1;

import com.daisyZone.core.enumeration.ResponseDataEnum;
import com.daisyZone.core.model.quiz.Quiz;
import com.daisyZone.quiz.service.QuizService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import com.daisyZone.core.model.basic.ResponseData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 题目端点
 *
 * @author lindaixi
 * @date 2018/9/4
 */
@Slf4j
@Api(value = "quiz", tags = {"Version:1.0"})
@RestController
@CrossOrigin
@RequestMapping("v1/quiz")

public class QuizEndpoint {

    @Autowired
    private QuizService quizService;
    /**
     * redis缓存配置
     */
    @Autowired
    private RedisTemplate<String, Object> validControlRedisTemplate;

    /**
     * 根据主键查找app控制配置
     *
     * @return 带有符合条件的app控制配置的请求响应体
     * @author lindaixi
     */
    @GetMapping(value = "config")
    @ApiOperation(value = "测试", notes = "返回测试信息", response = ResponseData.class)
    public ResponseData findById() {
        log.debug("==> \t进入测试端点。");
        ResponseData resp = new ResponseData();
        List<Quiz> quizList = quizService.query(null);
        log.debug("==> \t测试完成。{}。返回：{}", "end of test", quizList);
        resp.setResponseDataEnum(ResponseDataEnum.SUCCESS, quizList);
        return resp;
    }
//    validControlRedisTemplate.opsForValue().set(REDIS_KEY_HATTED_CODE_APP + userId, hattedCode, 180, TimeUnit.SECONDS);

    @GetMapping(value = "putRedis/{key}/{value}")
    @ApiOperation(value = "入redis测试", notes = "返回测试信息", response = ResponseData.class)
    public ResponseData putRedis(@ApiParam(value="redis的key", required = true) @PathVariable("key") String key,
                                 @ApiParam(value="redis的value", required = true) @PathVariable String value) {
        log.debug("==> \t进入redis测试端点。");
        ResponseData resp = new ResponseData();
        validControlRedisTemplate.opsForValue().set(key, value, 180, TimeUnit.SECONDS);
        log.debug("==> \t入redis测试完成。{}。", "end of test");
        resp.setResponseDataEnum(ResponseDataEnum.SUCCESS);
        return resp;
    }

    @GetMapping(value = "getRedis/{key}")
    @ApiOperation(value = "取redis测试", notes = "返回测试信息", response = ResponseData.class)
    public ResponseData getRedis(@ApiParam(value="redis的key", required = true) @PathVariable("key") String key) {
        log.debug("==> \t进入取redis测试端点。");
        ResponseData resp = new ResponseData();
        String result = validControlRedisTemplate.opsForValue().get(key).toString();
        log.debug("==> \t取redis测试完成。{}。返回值：{}", "end of test", result);
        resp.setResponseDataEnum(ResponseDataEnum.SUCCESS, result);
        return resp;
    }

}

