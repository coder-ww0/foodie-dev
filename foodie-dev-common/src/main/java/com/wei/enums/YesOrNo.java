package com.wei.enums;

import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author www
 * @date 2022/3/24 11:26
 * @description: TODO
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
