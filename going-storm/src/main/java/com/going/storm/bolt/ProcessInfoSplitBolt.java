package com.going.storm.bolt;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.redis.bolt.AbstractRedisBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.rocketmq.spout.scheme.DefaultMessageScheme;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import redis.clients.jedis.JedisCommands;

/**
 * 获取终端发来的资源利用信息， 将新获取的资源信息替换旧的资源信息。
 * 
 * @author Administrator
 *
 */
public class ProcessInfoSplitBolt extends AbstractRedisBolt {

	private static final String REDIS_KEY_PREFIX = "PROCESS_INFO_";
	
	public ProcessInfoSplitBolt(JedisPoolConfig config) {
		super(config);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}

	@Override
	protected void process(Tuple tuple) {
		JedisCommands jedisCommands = null;
		try {
			String body = tuple.getStringByField(DefaultMessageScheme.FIELD_BODY);
			String tags = tuple.getStringByField(DefaultMessageScheme.FIELD_TAGS).trim();
			if(!StringUtils.isEmpty(body)) {
				String[] processInfoArray = body.split("\\|");
				if(processInfoArray.length < 6) {
					return ;
				}
				String appType = getPairValue(processInfoArray[2]);
				jedisCommands = getInstance();
				jedisCommands.hset(REDIS_KEY_PREFIX+tags, appType, body);
			}
		} finally {
			if (jedisCommands != null) {
				returnInstance(jedisCommands);
			}
			this.collector.ack(tuple);
		}
	}
	
	private String getPairValue(String splitStr) {
		int index = splitStr.indexOf(":");
		if (index > 0) {
			return splitStr.substring(index+1, splitStr.length());
		} else {
			return "";
		}
	}
	
//	public static void main(String[] args) {
//		String str = "|date_stastic:2018-08-22 14:30|app_type:Ftp-server|pid:18919|total_memory(MB):|memory_used(MB):|cpu_use_rate(%):|memory_use_rate(%):";
//		String[] processInfoArray = str.split("\\|");
//		for(String processInfo : processInfoArray) {
//			System.out.println(processInfo);
//			int index = processInfo.indexOf(":");
//			System.out.println(processInfo.substring(index+1, processInfo.length()));
//		}
//	}
}
