package com.daisyZone.core.model.basic;

import com.daisyZone.core.enumeration.ResponseDataEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 包装返回对象
 * @author jiaqi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {

    /**
     * 响应码  （200 成功；其他 失败）
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 请求URL
     */
    private String uri;

    /**
     * 返回object内容
     */
    private Object body;


    /**
     * 构造方法
     *
     * @param code 响应码  （200 成功；其他 失败）
     */
    public ResponseData(int code) {
        super();
        this.code = code;
    }

    /**
     * 构造方法
     *
     * @param code 响应码  （200 成功；其他 失败）
     * @param msg  响应信息
     */
    public ResponseData(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造方法
     *
     * @param code 响应码  （200 成功；其他 失败）
     * @param msg  响应信息
     * @param body 返回内容
     */
    public ResponseData(int code, String msg, Object body) {
        super();
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    /**
     * 构造方法
     *
     * @param ResponseDataEnum 系统响应枚举
     */
    public ResponseData(ResponseDataEnum ResponseDataEnum) {
        this.setResponseDataEnum(ResponseDataEnum);
    }

    /**
     * 构造方法
     *
     * @param result 绑定效验结果
     */
    public ResponseData(BindingResult result) {
        this.setBindingResult(result);
    }

    /**
     * 根据数据响应枚举,设置数据响应
     *
     * @param ResponseDataEnum 数据响应枚举
     */
    public void setResponseDataEnum(ResponseDataEnum ResponseDataEnum) {
        this.code = ResponseDataEnum.getCode();
        this.msg = ResponseDataEnum.getMsg();
    }

    /**
     * 根据数据响应枚举,设置数据响应
     *
     * @param ResponseDataEnum 数据响应枚举
     * @param body             返回内容
     */
    public void setResponseDataEnum(ResponseDataEnum ResponseDataEnum, Object body) {
        this.code = ResponseDataEnum.getCode();
        this.msg = ResponseDataEnum.getMsg();
        this.body = body;
    }

    /**
     * 根据绑定结果,设置数据响应
     *
     * @param result 绑定效验结果
     */
    public void setBindingResult(BindingResult result) {
        ResponseDataEnum illegalargument = ResponseDataEnum.ILLEGALARGUMENT;
        this.code = illegalargument.getCode();

        if (result == null || !result.hasErrors()) {
            this.msg = illegalargument.getMsg();
        } else {
            List<FieldError> fieldErrors = result.getFieldErrors();
            StringBuffer sb = new StringBuffer(32);
            for (int i = 0; i < fieldErrors.size(); i++) {
                FieldError error = fieldErrors.get(i);
                sb.append(error.getDefaultMessage());
                if (i != (fieldErrors.size() - 1)) {
                    sb.append("<br>");
                }
            }

            this.msg = sb.toString();
        }
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", body=" + body +
                '}';
    }
}
