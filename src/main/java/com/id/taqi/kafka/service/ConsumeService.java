package com.id.taqi.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id.taqi.kafka.assembler.MappingData;
import com.id.taqi.kafka.modul.FpMasukanDto;
import com.id.taqi.kafka.modul.InputReq;
import com.id.taqi.kafka.modul.ListFpMasukanDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
public class ConsumeService {
    @Autowired
    private KafkaTemplate template;

    @Autowired
    private MappingData mappingData;
    private final ObjectMapper mapper = new ObjectMapper();
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "fpmasukan")
    public void consumData(@Payload String message,
                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws IOException {
        log.info("{} in partition {}, consume {}", topic, partition, message);
        latch.countDown();

        InputReq[] req = mapper.readValue(message,InputReq[].class);

        log.info("Sending to topic {}, with body {}", "kafka-test-produce", req);
        template.send("kafka-test-produce",req);
    }
}
