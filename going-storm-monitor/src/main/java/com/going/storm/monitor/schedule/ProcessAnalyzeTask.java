package com.going.storm.monitor.schedule;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.going.storm.monitor.service.MailService;
import com.going.storm.monitor.service.ProcessAnalyzeService;

@Component
public class ProcessAnalyzeTask {
	
	private Logger logger = LoggerFactory.getLogger(ProcessAnalyzeTask.class);
	
	@Autowired
	private ProcessAnalyzeService processAnalyzeService;

	@Autowired
	private MailService mailService;
	
	/**
	 * 每个小时的5分和35分执行一次
	 * @throws Exception
	 */
	@Scheduled(cron ="* 5,35 * * * *")
	public void execute() throws Exception {
		logger.info("进程分析开始...");
		String problemReport = processAnalyzeService.getProcessProblemInfo();
		if(problemReport == null || "".equals(problemReport.trim()))
			return;
		mailService.sendSimpleMail("doyoungdy@sina.com", null, "ab-internet预警["+DateFormatUtils.format(new Date(), "yyyy-mm-dd hh:mm")+"]", problemReport);
		
		logger.info("进程分析结束...");
	}

}
