package com.going.storm.monitor.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessInfo {
	
	private static final String CONST_STR_0 ="_0";
	private static final String CONST_STR_1 ="_1";
	private static final String CONST_STR_2 ="_2";
	private static final String CONST_STR_3 ="_3";
	private static final String CONST_STR_4 ="_4";
	private static final String CONST_STR_5 ="_5";

	private static final Map<String, List<String>> PROCESS_INFO = new HashMap<String, List<String>>();

//	private static final List<String> HOST_INFO = new ArrayList<String>();

	static {
//		for (Host host : Host.values()) {
//			if (host.getenv().equals("dev")) {
//				HOST_INFO.add(host.getip());
//			}
//		}
		List<String> processList = new ArrayList<String>();

		processList.add( ProcessType.WEB_SITE_CMS.getCode()+CONST_STR_0);
		processList.add( ProcessType.WEB_SITE_CMS.getCode()+CONST_STR_1);
		processList.add( ProcessType.CHANDAO.getCode()+CONST_STR_0);
		processList.add( ProcessType.FTP_SERVER.getCode()+CONST_STR_0);
		PROCESS_INFO.put(Host.IP_101_217.getip(), processList);
		
		processList = new ArrayList<String>();
		processList.add(ProcessType.ACTIVEMQ.getCode()+CONST_STR_0);
		processList.add(ProcessType.DUBBO_MONITOR.getCode()+CONST_STR_0);
		processList.add(ProcessType.DUBBO_MONITOR.getCode()+CONST_STR_1);
		processList.add(ProcessType.DUBBO_MONITOR.getCode()+CONST_STR_2);
		processList.add(ProcessType.DUBBO_MONITOR.getCode()+CONST_STR_3);
		processList.add(ProcessType.JENKINS.getCode()+CONST_STR_0);
		processList.add(ProcessType.REDIS.getCode()+CONST_STR_0);
		processList.add(ProcessType.ZOOKEEPER.getCode()+CONST_STR_0);
		processList.add(ProcessType.ZOOKEEPER.getCode()+CONST_STR_1);
		processList.add(ProcessType.ZOOKEEPER.getCode()+CONST_STR_2);
		processList.add(ProcessType.ZOOKEEPER.getCode()+CONST_STR_3);
		PROCESS_INFO.put(Host.IP_102_58.getip(), processList);

		processList = new ArrayList<String>();
		processList.add( ProcessType.MINI_PROGRAME.getCode()+CONST_STR_0);
		processList.add( ProcessType.MINI_PROGRAME.getCode()+CONST_STR_1);
		processList.add( ProcessType.NEXUS.getCode()+CONST_STR_0);
		processList.add( ProcessType.WEBSITE.getCode()+CONST_STR_0);
		processList.add( ProcessType.WEBSITE.getCode()+CONST_STR_1);
		processList.add( ProcessType.WEBSITE.getCode()+CONST_STR_2);
		processList.add( ProcessType.WEBSITE.getCode()+CONST_STR_3);
		PROCESS_INFO.put(Host.IP_108_114.getip(), processList);
		
		processList = new ArrayList<String>();
		processList.add( ProcessType.JDT_CMS.getCode()+CONST_STR_0);
		processList.add( ProcessType.JDT_CMS.getCode()+CONST_STR_1);
		processList.add( ProcessType.JDT_CMS.getCode()+CONST_STR_2);
		processList.add( ProcessType.JDT_CMS.getCode()+CONST_STR_3);
		PROCESS_INFO.put(Host.IP_108_204.getip(), processList);

		processList = new ArrayList<String>();
		processList.add( ProcessType.MICRO_SERVICE.getCode()+CONST_STR_0);
		processList.add( ProcessType.MICRO_SERVICE.getCode()+CONST_STR_1);
		processList.add( ProcessType.MICRO_SERVICE.getCode()+CONST_STR_2);
		processList.add( ProcessType.MICRO_SERVICE.getCode()+CONST_STR_3);
		processList.add( ProcessType.JDT.getCode()+CONST_STR_0);
		processList.add( ProcessType.JDT.getCode()+CONST_STR_1);
		processList.add( ProcessType.JDT.getCode()+CONST_STR_2);
		processList.add( ProcessType.JDT.getCode()+CONST_STR_3);
		PROCESS_INFO.put(Host.IP_108_205.getip(), processList);
	}
	
	public static List<String> getProcessInfos(String hostIp) {
		return PROCESS_INFO.get(hostIp);
	}
	
	public static Set<String> getHostInfos() {
		return PROCESS_INFO.keySet();
	}

}
