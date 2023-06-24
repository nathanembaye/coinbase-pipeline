package io.streams;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletionStage;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import com.google.gson.Gson;
import com.google.protobuf.Timestamp;
import io.streams.CoinOuterClass.Coin;



public class Producer {

    public static void main(String[] args) throws Exception {
        
        //connection payload
        String payload = "{\"type\":\""+"subscribe"+
                         "\", \"product_ids\":"+Arrays.asList("\"BTC-USD\"") + 
                         ", \"channels\":" + Arrays.asList("\"matches\"") + "}";  
        


        //create websocket to listen to feed
        WebSocket ws = HttpClient
                .newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create("wss://ws-feed.exchange.coinbase.com"), new WebSocketClient())
                .join();
        
        


        //open websocket connection
        while (true){
            ws.sendText(payload, true);
            Thread.sleep(1000);
        }
        
        

    }



    private static class WebSocketClient implements WebSocket.Listener {


        public WebSocketClient() { }
        

        public void onOpen(WebSocket webSocket) {
            WebSocket.Listener.super.onOpen(webSocket);
            System.out.println("WebSocket is open...........");
        }



        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        

            if (data.toString().contains("price")) {
                System.out.println("WebSocket received data........." + data + "\n");
                sendToKafka(data.toString());
            }
            
            return WebSocket.Listener.super.onText(webSocket, data, last);
        
        }




        public void onError(WebSocket webSocket, Throwable error) {
            WebSocket.Listener.super.onError(webSocket, error);
            System.out.println("WebSocket raised an error........" + error);
        }

        


    }


    

     
     static void sendToKafka(String response) {

        Properties props = new Properties();
      
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
      
        

        Coin bitcoin = getCoin(response);
        
        KafkaProducer<String, byte[]> producer = new KafkaProducer <String, byte[]>(props);
        
        
        try {
            producer.send(new ProducerRecord<String, byte[]>("market_data7", bitcoin.toByteArray()));
            producer.close();
        }

        catch (Error e) {
            System.out.println("Error from Producer: " + e);
        }
         

   
      }  

      
      static Coin getCoin(String data) {
            
            //set response data to GSON 
            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(data, Map.class);
            
            
            //access GSON to build Coin proto 
            Coin coin = Coin.newBuilder()
                            .setMakerOrderId(map.get("maker_order_id").toString()) 
                            .setTakerOrderId(map.get("taker_order_id").toString()) 
                            .setSide(map.get("side").toString()) 
                            .setProductId(map.get("product_id").toString()) 
                            .setPrice(Double.parseDouble(map.get("price").toString())) 
                            .setSize(Double.parseDouble(map.get("size").toString())) 
                            .setTime(Timestamp.newBuilder()
                                            .setSeconds(OffsetDateTime.parse(map.get("time").toString()).toInstant().getEpochSecond())
                                            .setNanos(OffsetDateTime.parse(map.get("time").toString()).toInstant().getNano())
                                            .build()) 
                            .setTradeId(Double.parseDouble(map.get("trade_id").toString())) 
                            .build();

            return coin;

      }

      


  }