package com.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JobStatusEnum {

    NORMAL(1),

    PAUSE(0);

    private final Integer value;

}

