package com.unionclass.reviewservice.common.config;

import com.unionclass.reviewservice.common.kafka.event.MemberCreatedEvent;
import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Bean
    public Map<String, Object> consumerConfigs(Class<?> valueType) {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "review-aggregate-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, valueType);

        return config;
    }

    @Bean
    public ConsumerFactory<String, ReviewCreatedEvent> reviewConsumerFactory() {

        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(ReviewCreatedEvent.class),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ReviewCreatedEvent.class, false)));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReviewCreatedEvent> reviewCreatedEventListener() {
        ConcurrentKafkaListenerContainerFactory<String, ReviewCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, MemberCreatedEvent> memberCreatedEventConsumerFactory() {

        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(MemberCreatedEvent.class),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(MemberCreatedEvent.class, false)));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MemberCreatedEvent> memberCreatedEventListener() {
        ConcurrentKafkaListenerContainerFactory<String, MemberCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(memberCreatedEventConsumerFactory());

        return factory;
    }
}
