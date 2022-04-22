package com.liuxinchi.hpe.exception;

/**
 * 描述：     异常枚举
 * @author 拾荒老冰棍
 */
public enum HpeExceptionEnum {
    NEED_LOGIN(10001, "用户未登录"),
    NEED_USER_NAME(10002,"用户名不能为空" ),
    NEED_PASSWORD(10003, "密码不能为空"),
    PASSWORD_TOO_SHORT(10004, "密码长度不能小于8位"),
    NAME_EXISTED(10005, "用户已存在"),
    WRONG_PASSWORD(10006, "用户名或密码错误"),
    SSH_LOGIN_ERROR(10007, "ssh登陆失败"),
    MKDIR_FAILED(10008, "文件夹创建失败"),
    UPLOAD_FAILED(10009, "图片上传失败"),
    IMAGE_FAILED(10010,"不能上传非图片格式文件"),
    VIDEO_FAILED(10011,"不能上传非视频格式文件"),
    INSERT_FAILED(10012, "插入失败"),
    UPDATE_FAILED(10013, "更新失败"),
    DELETE_FAILED(10014, "删除失败"),
    REQUEST_PARAM_ERROR(10015,"请求参数错误"),
    NEED_UPLOAD(10016,"请先上传文件"),
    NEED_ESTIMATE(10017,"请先进行预测"),
    NEED_RECONSTRUCT(10018,"请先进行重建"),
    ESTIMATE_ERROR(10019,"关键点预测失败"),
    RECONSTRUCT(10020,"mesh重建失败"),
    SYSTEM_ERROR(30000, "系统异常，请从控制台或日志中查看具体错误信息");

    /**
     * 异常码
     */
    Integer code;
    /**
     * 异常信息
     */
    String msg;

    HpeExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
