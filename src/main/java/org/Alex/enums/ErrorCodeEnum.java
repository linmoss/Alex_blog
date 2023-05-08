package org.Alex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    ERROR_404(404,"对不起，你请求的页面暂时未找到.<br>它或许已经被迁移至其它页面啦."),
    ERROR_500(500,"您好，服务器暂时出发问题，请稍候访问.<br>或您也可以尝试联系站长."),
    ;
    private final int code;
    private final String msg;
}
