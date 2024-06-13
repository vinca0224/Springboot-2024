package com.vinca.spring02.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 해당 패키지에 Mapper로 인식하라
@MapperScan(basePackages = {"com.vinca.spring02.mapper"})
public class MyBatisConfig {
    
}
