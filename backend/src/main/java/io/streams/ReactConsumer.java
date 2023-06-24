package io.streams;


import java.util.Properties;
import java.time.Duration;
import java.util.Arrays;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.streams.CoinOuterClass.Coin;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;


public class ReactConsumer {
   public static void main(String[] args) throws Exception {
      
     
      Properties props = new Properties();

      props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

      KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);

      consumer.subscribe(Arrays.asList("market_data7"));
      
      
      while (true) {
         ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(0));
         for (ConsumerRecord<String, byte[]> record : records)
            
            System.out.println(Coin.parseFrom(record.value()));

      }

   }
}