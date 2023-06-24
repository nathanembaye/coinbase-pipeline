package io.streams;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.protobuf.functions.*;



class SparkConsumer {
    public static void main(String[] args) {

     
        SparkSession spark = SparkSession
                            .builder()
                            .appName("Nathans Spark Cluster")
                            .config("spark.master", "local")
                            .getOrCreate();


        Dataset<Row> df = spark
            .readStream()
            .format("kafka")
            .option("kafka.bootstrap.servers", "localhost:9092")
            .option("subscribe", "market_data7")
            .load();
    
        
        df = df.select(from_protobuf(col("value"), "Coin", "/Users/nathan/Desktop/projects/coinbase-realtime-data-pipeline/backend/src/main/protobuf/coin.desc").alias("bitcoin"));
        df = df.select("bitcoin.*");
        

        try {    
           
            df.writeStream().format("org.apache.spark.sql.cassandra")
                            .option("checkpointLocation", "checkpoint")
                            .option("keyspace", "market") 
                            .option("table", "bitcoin")
                            .outputMode("append")
                            .start()
                            .awaitTermination();


        }


        catch (Exception e) {
            System.out.print("Exception: " + e);
        }


        finally {
            System.out.print("Finished");
        }

       
        
    }
}
