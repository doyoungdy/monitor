package com.going.storm.monitor.config;

public enum Host {
	IP_101_217("dev","10.10.101.217"),
	IP_102_58("dev","10.10.102.58"),
	IP_108_114("dev","10.10.108.114"),
	IP_108_204("dev","10.10.108.204"),
	IP_108_205("dev","10.10.108.205"),
	IP_21_57("prd","10.4.21.57"),
	IP_21_39("prd","10.4.21.39"),
	IP_62_11("prd","10.5.62.11"),
	IP_82_57("prd","10.5.82.57"),
	IP_82_58("prd","10.5.82.58"),
	;
	
	private String env;
	private String ip;
	
	private Host(String env, String ip) {
		this.env = env;
		this.ip = ip;
	}
	public String getenv() {
		return env;
	}
	public void setenv(String env) {
		this.env = env;
	}
	public String getip() {
		return ip;
	}
	public void setip(String ip) {
		this.ip = ip;
	}
	
}
