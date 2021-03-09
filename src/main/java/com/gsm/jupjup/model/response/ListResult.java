package com.gsm.jupjup.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ListResult<T> extends CommonResult {
    private List<T> list;
}