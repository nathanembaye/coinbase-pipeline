### streaming-processing-pipeline

<img width="2211" alt="diagram" src="https://github.com/nathanembaye/coinbase-pipeline/assets/62483081/295e72d4-8372-4f2c-89bf-dd706909fbd5">

1. Ingesting coinbase data via web-sockets
2. Producer writes data to kafka broker in Java
3. Data is serialized with Google Protobuf
3. Consumer is spark structured streaming for distributed processing
4. Persisted data in cassandra db via CQL


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
