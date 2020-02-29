package com.fangzy.springboot_rocketmq_producer.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PublicityVo {
    private int id;
    private String name;
}
