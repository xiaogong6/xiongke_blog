package com.api.response;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一的返回数据
 *
 * @author xiongke
 */
@Getter
public class ServerResponseEntity<T> implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ServerResponseEntity.class);

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return Objects.equals(ResponseEnum.OK.value(), this.code);
    }

    @Override
    public String toString() {
        return "ServerResponseEntity{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }

    public static <T> ServerResponseEntity<T> success(T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.OK.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success() {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.OK.value());
        serverResponseEntity.setMsg(ResponseEnum.OK.getMsg());
        return serverResponseEntity;
    }

    /**
     * 前端显示失败消息
     *
     * @param msg 失败消息
     * @return
     */
    public static <T> ServerResponseEntity<T> showFailMsg(String msg) {
        log.error(msg);
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setCode(ResponseEnum.SHOW_FAIL.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum) {
        log.error(responseEnum.toString());
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(responseEnum.getMsg());
        serverResponseEntity.setCode(responseEnum.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(String failMsg) {
        log.error(failMsg);
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(failMsg);
        serverResponseEntity.setCode(ResponseEnum.EXCEPTION.value());
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
        log.error(responseEnum.toString());
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(responseEnum.getMsg());
        serverResponseEntity.setCode(responseEnum.value());
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> transform(ServerResponseEntity<?> oldServerResponseEntity) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setMsg(oldServerResponseEntity.getMsg());
        serverResponseEntity.setCode(oldServerResponseEntity.getCode());
        log.error(serverResponseEntity.toString());
        return serverResponseEntity;
    }

}
