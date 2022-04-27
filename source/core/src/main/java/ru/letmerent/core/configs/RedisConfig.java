package ru.letmerent.core.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.letmerent.core.dto.Cart;
import ru.letmerent.core.dto.CartDeserializer;
import ru.letmerent.core.dto.OrderItemDto;
import ru.letmerent.core.dto.OrderItemDtoDeserializer;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
                .addModule(new SimpleModule()
//                        .addDeserializer(Cart.class, new CartDeserializer())
//                        .addDeserializer(OrderItemDto.class, new OrderItemDtoDeserializer())
//                        .addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
                        .addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                )
                .build();
//        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        return mapper;
    }
}
