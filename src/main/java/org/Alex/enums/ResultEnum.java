package org.Alex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    RESULT_SUCCESS(200, "操作成功"),
    RESULT_DATA_NOT_FIND(501, "数据不存在"),
    RESULT_UPLOAD_FAIL(502, "文件上传失败"),
    RESULT_MAIL_FAIL(503, "不能用管理员邮箱进行评论"),
    RESULT_ERROR(500, "系统错误");
    private final Integer code;
    private final String message;
}
