package com.liuxinchi.hpe.exception;

/**
 * @author 拾荒老冰棍
 */
public class HpeException extends Exception{

    private final Integer code;
    private final String message;

    public HpeException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public HpeException(HpeExceptionEnum ex){
        this(ex.getCode(), ex.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
