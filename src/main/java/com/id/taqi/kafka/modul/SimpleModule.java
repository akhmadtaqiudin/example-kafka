package com.id.taqi.kafka.modul;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
public class SimpleModule implements Serializable {
    private String field1;
    private String field2;
}
