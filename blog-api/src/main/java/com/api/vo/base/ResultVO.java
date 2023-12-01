package com.api.vo.base;

import com.api.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class ResultVO<T> {

    private Boolean flag;

    private Integer code;

    private String message;

    private T data;

    public static <T> ResultVO<T> ok() {
        return resultVO(true, StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> ResultVO<T> ok(T data) {
        return resultVO(true, StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> ResultVO<T> ok(T data, String message) {
        return resultVO(true, StatusCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> ResultVO<T> fail() {
        return resultVO(false, StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getDesc(), null);
    }

    public static <T> ResultVO<T> fail(StatusCodeEnum statusCodeEnum) {
        return resultVO(false, statusCodeEnum.getCode(), statusCodeEnum.getDesc(), null);
    }

    public static <T> ResultVO<T> fail(String message) {
        return resultVO(false, message);
    }

    public static <T> ResultVO<T> fail(T data) {
        return resultVO(false, StatusCodeEnum.FAIL.getCode(), StatusCodeEnum.FAIL.getDesc(), data);
    }

    public static <T> ResultVO<T> fail(T data, String message) {
        return resultVO(false, StatusCodeEnum.FAIL.getCode(), message, data);
    }

    public static <T> ResultVO<T> fail(Integer code, String message) {
        return resultVO(false, code, message, null);
    }

    private static <T> ResultVO<T> resultVO(Boolean flag, String message) {
        return ResultVO.<T>builder()
                .flag(flag)
                .code(flag ? StatusCodeEnum.SUCCESS.getCode() : StatusCodeEnum.FAIL.getCode())
                .message(message).build();
    }

    private static <T> ResultVO<T> resultVO(Boolean flag, Integer code, String message, T data) {
        return ResultVO.<T>builder()
                .flag(flag)
                .code(code)
                .message(message)
                .data(data).build();
    }

}
