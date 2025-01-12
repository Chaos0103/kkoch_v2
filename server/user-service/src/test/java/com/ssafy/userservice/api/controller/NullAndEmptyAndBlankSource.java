package com.ssafy.userservice.api.controller;

import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NullSource
@EmptySource
@ValueSource(strings = {" ", "\t"})
public @interface NullAndEmptyAndBlankSource {
}
