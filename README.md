### coinbase-realtime-data-pipeline

1. Ingesting coinbase data via web-sockets
2. Producer writes data to kafka broker in Java
3. Data is serialized with Google Protobuf
3. Consumer is spark structured streaming for distributed processing
4. Persisted data in cassandra db via CQL