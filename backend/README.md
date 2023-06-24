#start kafka
#bin/zookeeper-server-start.sh config/zookeeper.properties
#bin/kafka-server-start.sh config/server.properties
#bin/schema-registry-start ./etc/schema-registry/schema-registry.properties


#protoc
#protoc --java_out=../java/ coin.proto
#protoc --include_imports --descriptor_set_out=coin.desc coin.proto


#spark structured streaming
#usr/local/Cellar/apache-spark/3.4.0/libexec/
#./sbin/start-master.sh
#./sbin/stop-master.sh


#cassandra
#cassandra -f for daemon
#cqlsh for UI