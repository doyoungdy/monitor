package com.going.storm.topology;

import java.util.Properties;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.rocketmq.SpoutConfig;
import org.apache.storm.rocketmq.spout.RocketMqSpout;
import org.apache.storm.topology.TopologyBuilder;
import com.going.storm.bolt.ResourcesInfoSplitBolt;

public class ResourceInfosTopology {

	public static void main(String[] args) throws InterruptedException {
		

		Properties properties = new Properties();
		properties.setProperty(SpoutConfig.NAME_SERVER_ADDR, "10.209.8.126:9876");
		properties.setProperty(SpoutConfig.CONSUMER_GROUP, "RESOURCE_INFOS_CONSUMER_GRP");
		properties.setProperty(SpoutConfig.CONSUMER_TOPIC, "RESOURCES_INFO_TOPIC");
		properties.setProperty(SpoutConfig.SCHEME,SpoutConfig.MESSAGE_SCHEME);
		
		properties.setProperty("REDIS_HOST", "10.209.8.126");
		properties.setProperty("REDIS_PORT", "6379");

		// 定义拓扑
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("resouece-info-reader", new RocketMqSpout(properties));
		JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()//
				.setHost(properties.getProperty("REDIS_HOST"))//
				.setPort(Integer.valueOf(properties.getProperty("REDIS_PORT")))//
				.build();
		 builder.setBolt("word-normalizer", new  ResourcesInfoSplitBolt(poolConfig)).shuffleGrouping("resouece-info-reader");
		 
		// builder.setBolt("word-counter", new WordCounter(),2).fieldsGrouping("word-normalizer", new Fields("word"));

		// 配置
		Config conf = new Config();
		// conf.put("wordsFile", args[0]);
		conf.setDebug(false);

		// 运行拓扑
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Resource-Infos-Topologie", conf, builder.createTopology());
		Thread.sleep(1000000000);
		cluster.shutdown();
	}

}
