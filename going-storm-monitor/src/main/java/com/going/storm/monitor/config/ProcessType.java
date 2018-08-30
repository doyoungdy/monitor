package com.going.storm.monitor.config;

public enum ProcessType {
	
	WEB_SITE_CMS("CMS&CMS-API", "官网cms&cms-api"),
	JDT_CMS("CMS&CMS-API", "经代通cms&cms-api"),
	CHANDAO("ChanDao", "弹道"),
	FTP_SERVER("Ftp-server", "Ftp服务"),
	DUBBO_MONITOR("Dubbo-Monitor", "Dubbo监控"),
	
	ZOOKEEPER("Zookeeper","Zookeeper"),
	REDIS("Redis","Redis服务"),
	JENKINS("Jenkins","Redis服务"),
	ACTIVEMQ("ActiveMq","ActiveMq服务"),
	WEBSITE("website","官网"),
	NEXUS("nexus-oss","MAVEN私服"),
	MICRO_SERVICE("haioums","微服务"),
	
	JDT("jdt","经代通服务"),
	MINI_PROGRAME("miniprogram","小程序")
	;
	
	private String code;
	private String descript;
	
	private ProcessType(String code, String descript) {
		this.code = code;
		this.descript = descript;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	
	public static String getDescByCode(String code) {
		ProcessType processType = getProcessTypeByCode(code);
		if(processType == null) return null;
		return processType.getDescript();
	}
	
	public static ProcessType getProcessTypeByCode(String code) {
		ProcessType[] processTypes = ProcessType.values();
		for(ProcessType processType : processTypes) {
			if(processType.getCode().equals(code))
				return processType;
		}
		return null;
	}
	

}
