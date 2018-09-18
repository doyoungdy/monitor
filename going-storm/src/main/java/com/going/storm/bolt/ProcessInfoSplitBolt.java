package com.going.storm.bolt;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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
	private static final String CONST_SPLIT_PROC_INFO = "\\|";
	private static final String CONST_SPLIT_PROC_KV = ":";
	public static final String[] DATE_TIME_FORMAT = new String[] { "yyyy-MM-dd HH:mm" };

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
			if (!StringUtils.isEmpty(body)) {
				String[] processInfoArray = body.split("\\|");
				System.out.println("");
				if (processInfoArray.length < 6) {
					return;
				}
				String appType = getPairValue(processInfoArray[2]);
				jedisCommands = getInstance();

				String storeBody = jedisCommands.hget(REDIS_KEY_PREFIX + tags, appType);
				if (storeBody == null || "".equals(storeBody.trim())) {
					jedisCommands.hset(REDIS_KEY_PREFIX + tags, appType, body);
				} else {
					Date storeDate = getProcessInfoDate(storeBody);
					Date tupleDate = getProcessInfoDate(body);
					if (storeDate != null && tupleDate != null && tupleDate.after(storeDate))
						jedisCommands.hset(REDIS_KEY_PREFIX + tags, appType, body);
				}

			}
		} finally {
			if (jedisCommands != null) {
				returnInstance(jedisCommands);
			}
			this.collector.ack(tuple);
		}
	}

	private Date getProcessInfoDate(String storeBody) {
		Map<String, String> processInfoMap = getProcessInfoMap(storeBody);
		String storeStasticDate = processInfoMap.get("date_stastic");
		Date date = null;
		try {
			date = DateUtils.parseDate(storeStasticDate, DATE_TIME_FORMAT);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private String getPairValue(String splitStr) {
		int index = splitStr.indexOf(":");
		if (index > 0) {
			return splitStr.substring(index + 1, splitStr.length());
		} else {
			return "";
		}
	}

	/**
	 * 该进程信息转换为Map
	 * 
	 * <pre>
	 * 原始信息为：|date_stastic:2018-08-29 11:30|app_type:CMS&CMS-API_1|pid:26382|total_memory(MB):3617m|memory_used(MB):639m|cpu_use_rate(%):0.0|memory_use_rate(%):8.1
	 * 通过”|“符号识别参数
	 * 通过”:“符号识别参数的key和value
	 * </pre>
	 * 
	 * @param processInfo
	 * @return
	 */
	private Map<String, String> getProcessInfoMap(String processInfo) {
		String[] processInfoArray = processInfo.split(CONST_SPLIT_PROC_INFO);
		Map<String, String> processInfoMap = new HashMap<String, String>();

		for (String processInf : processInfoArray) {
			if ("".equals(processInf.trim()))
				continue;
			if (processInf.contains(CONST_SPLIT_PROC_KV)) {
				processInfoMap.put(processInf.substring(0, processInf.indexOf(CONST_SPLIT_PROC_KV)), //
						processInf.substring(processInf.indexOf(CONST_SPLIT_PROC_KV) + 1, processInf.length()));
			}
		}
		return processInfoMap;
	}
}
