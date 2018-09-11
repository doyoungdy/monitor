package com.going.storm.monitor.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.going.storm.monitor.config.ProcessInfo;
import com.going.storm.monitor.schedule.ProcessAnalyzeTask;

@Service
public class ProcessAnalyzeServiceImpl implements ProcessAnalyzeService {

	private Logger logger = LoggerFactory.getLogger(ProcessAnalyzeTask.class);
	
	private static final String CONST_PROCESS_PREFIX = "PROCESS_INFO_";
	private static final String CONST_SPLIT_PROCESS = "_";
	private static final String CONST_SPLIT_PROC_INFO = "\\|";
	private static final String CONST_SPLIT_PROC_KV = ":";
	public static final String[] DATE_TIME_FORMAT = new String[] {"yyyy-MM-dd HH:mm"};
	private static final int PROCESS_UPDATE_FREQ = 1;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public String getProcessProblemInfo() {
		Set<String> hosts = ProcessInfo.getHostInfos();

		Map<String, String> hostAllProcess;
		List<String> hostConfProcess;
		StringBuilder sb = new StringBuilder();
		Iterator<String> hostIterator = hosts.iterator();
		while (hostIterator.hasNext()) {
			String hostIp = hostIterator.next();
			logger.info("开始分析主机{}的信息",hostIp);
			hostConfProcess = ProcessInfo.getProcessInfos(hostIp);
			hostAllProcess = redisTemplate.opsForHash().entries(CONST_PROCESS_PREFIX + hostIp);
			String[] processArray;
			
			for (String processKey : hostConfProcess) {
				logger.info("开始分析主机进程{}的信息",processKey);
				processArray = processKey.split(CONST_SPLIT_PROCESS);
				//处理预计进程不存在的情况
				if (!hostAllProcess.containsKey(processKey)) {
					sb.append("主机：[").append(hostIp).append("]进程[").append(processArray[0]).append("]_").append(processArray[1])//
					.append("不存在。").append("\r\n");
					logger.info("主机进程信息没有获取到。");
				} else {
					handleSuperTimeProcess(sb, hostAllProcess.get(processKey), hostIp, processArray);
				}
				logger.info("结束分析主机进程{}的信息",processKey);
			}
			logger.info("结束分析主机{}的信息",hostIp);
		}
		return sb.toString();
	}
	
	private void handleSuperTimeProcess(StringBuilder sb, String processInfo, String hostIP, String[] processArray ) {
		//处理预计处理一小时没有更新的情况
		logger.info("主机进程信息为{}。", processInfo);
		Map<String, String> processInfoMap = getProcessInfoMap(processInfo);
		String stasticdate = processInfoMap.get("date_stastic");
		Date date = new Date();
		try {
			date = DateUtils.parseDate(stasticdate, DATE_TIME_FORMAT);
			date = DateUtils.addHours(date, PROCESS_UPDATE_FREQ);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date now = new Date();
		if(now.after(date)) {
			sb.append("主机：[").append(hostIP).append("]进程[").append(processArray[0]).append("]_").append(processArray[1])//
			.append("最新更新时间为").append(stasticdate).append(",已经有超过").append(PROCESS_UPDATE_FREQ).append("小时没有获取到进程信息，可能进程已经挂掉了，请关注。")//
			.append("最近一次更新的进程详细信息为[").append(processInfo).append("]")//
			.append("\r\n");
			logger.info("该进程可能超过预定时间没有更新到了。");
		}
	}
	
	/**
	 * 该进程信息转换为Map
	 * <pre>
	 * 原始信息为：|date_stastic:2018-08-29 11:30|app_type:CMS&CMS-API_1|pid:26382|total_memory(MB):3617m|memory_used(MB):639m|cpu_use_rate(%):0.0|memory_use_rate(%):8.1
	 * 通过”|“符号识别参数
	 * 通过”:“符号识别参数的key和value
	 * </pre>
	 * @param processInfo
	 * @return
	 */
	private Map<String, String> getProcessInfoMap(String processInfo) {
		String[] processInfoArray = processInfo.split(CONST_SPLIT_PROC_INFO);
		Map<String, String> processInfoMap = new HashMap<String, String>();
		
		for(String processInf : processInfoArray) {
			if("".equals(processInf.trim()))
				continue;
			if (processInf.contains(CONST_SPLIT_PROC_KV)) {
				processInfoMap.put(processInf.substring(0, processInf.indexOf(CONST_SPLIT_PROC_KV)), //
						processInf.substring(processInf.indexOf(CONST_SPLIT_PROC_KV) + 1, processInf.length()));
			}
		}
		return processInfoMap;
	}

}
