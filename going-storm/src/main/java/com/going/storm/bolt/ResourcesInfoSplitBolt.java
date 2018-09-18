package com.going.storm.bolt;

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
public class ResourcesInfoSplitBolt extends AbstractRedisBolt {
  
	private static final String REDIS_KEY_PREFIX = "RESOURCE_INFO_";
	
	public ResourcesInfoSplitBolt(JedisPoolConfig config) {
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
			
			jedisCommands = getInstance();
			jedisCommands.lpush(REDIS_KEY_PREFIX+tags, body);

		} finally {
			if (jedisCommands != null) {
				returnInstance(jedisCommands);
			}
			this.collector.ack(tuple);
		}
	}
	

}
