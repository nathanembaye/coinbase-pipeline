<h1>streaming-processing-pipeline</h1>

![design](https://github.com/nathanembaye/coinbase-pipeline/assets/62483081/f9d6e545-0050-411b-868c-316e49545eb6)


1. Connect to Coinbase WebsSocket via Java Producer App
2. Serialize data with Protocol Buffer, Google Protobuf
3. Producer writes data to Kafka broker
4. Consumer is Spark Structured Streaming engine for distributed processing
5. Persisted data in cassandra db via CQL




<br/>
<br/>
<br/>




#start zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

#start kafka
bin/kafka-server-start.sh config/server.properties

#start spark: http://localhost:8080/ --> usr/local/Cellar/apache-spark/3.4.0/libexec/
./sbin/start-master.sh
./sbin/stop-master.sh


#start cassandra
cassandra -f for daemon
cqlsh


#protoc
#protoc --java_out=../java/ coin.proto
#protoc --include_imports --descriptor_set_out=coin.desc coin.proto
