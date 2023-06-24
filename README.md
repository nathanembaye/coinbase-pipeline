### coinbase-realtime-data-pipeline

1. Ingesting coinbase data via web-sockets
2. Producer writes data to kafka broker in Java
3. Data is serialized with Google Protobuf
3. Consumer is spark structured streaming for distributed processing
4. Persisted data in cassandra db via CQL



#start zookeeper
#bin/zookeeper-server-start.sh config/zookeeper.properties

#start kafka
#bin/kafka-server-start.sh config/server.properties

#start spark: http://localhost:8080/
#./sbin/start-master.sh
#./sbin/stop-master.sh
#usr/local/Cellar/apache-spark/3.4.0/libexec/


#start cassandra
#cassandra -f for daemon
#cqlsh for UI


#protoc
#protoc --java_out=../java/ coin.proto
#protoc --include_imports --descriptor_set_out=coin.desc coin.proto