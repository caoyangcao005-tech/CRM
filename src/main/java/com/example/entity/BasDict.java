package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasDict {
    private Integer dictId;
    private String dictType;
    private String dictItem;
    private String dictValue;
    private Integer dictIsEditable;
}
