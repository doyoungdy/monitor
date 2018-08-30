package com.going.storm.monitor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@ComponentScan(basePackages="com.going.storm.monitor.schedule")
public class ScheduleConfig {

}
