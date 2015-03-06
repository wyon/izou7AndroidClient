package com.izouqi.client.server.webservice;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * ServerApi support cache
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface SupportCache {

}
