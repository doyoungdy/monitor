docker run -d -m 400m --name storm-resources -it -v D:/workspace-monitor/monitor-docker/image/storm/going-storm-0.0.1-SNAPSHOT.jar:/topology.jar ab/storm storm jar /topology.jar com.going.storm.topology.ResourceInfosTopology

docker run -d -m 400m --name storm-process -it -v D:/workspace-monitor/monitor-docker/image/storm/going-storm-0.0.1-SNAPSHOT.jar:/topology.jar ab/storm storm jar /topology.jar com.going.storm.topology.ProcessInfosTopology

docker run -d -m 200m --name storm-monitor -p 8888:8888 -it ab/storm-monitor 

docker run -d  -m 200m --name abinter-monitor-namesrv -p 9876:9876 -v E:/rocketmq/namesrv/master/logs:/opt/logs -v E:/rocketmq/namesrv/master/store:/opt/store going/rocketmq-namesrv:4.2.0

docker run -d -m 1024m --name abinter-monitor-broker -p 10909:10909 -p 10911:10911 -v E:/rocketmq/broker/master-1/logs:/opt/logs -v E:/rocketmq/broker/master-1/store:/opt/store -v E:/rocketmq/broker/master-1/conf:/opt/conf going/rocketmq-broker:4.2.2

docker run -d -m 400m --name abinter-monitor-console -p 8080:8080 -e JAVA_OPTS=" -Drocketmq.config.namesrvAddr=10.209.8.126:9876 " styletang/rocketmq-console-ng:latest

