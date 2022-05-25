package com.example.studit.config.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseException {
    private BaseResponseStatus status;
}
