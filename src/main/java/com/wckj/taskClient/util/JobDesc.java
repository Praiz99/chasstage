package com.wckj.taskClient.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义注解
 * @author xutaotao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JobDesc {
	String description();
}
