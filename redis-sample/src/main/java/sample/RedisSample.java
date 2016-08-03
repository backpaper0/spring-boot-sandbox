package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.core.convert.CustomConversions;
import org.springframework.data.redis.core.convert.MappingRedisConverter;
import org.springframework.data.redis.core.convert.RedisConverter;

@SpringBootApplication
public class RedisSample {
    public static void main(String[] args) {
        SpringApplication.run(RedisSample.class, args);
    }

    @Bean
    RedisConverter redisConverter() {
        MappingRedisConverter redisConverter = new MappingRedisConverter(null, null, null);
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new Converter<UUID, byte[]>() {
            @Override
            public byte[] convert(UUID source) {
                return source.toString().getBytes();
            }
        });
        converters.add(new Converter<byte[], UUID>() {
            @Override
            public UUID convert(byte[] source) {
                return UUID.fromString(new String(source));
            }
        });
        redisConverter.setCustomConversions(new CustomConversions(converters));
        return redisConverter;
    }
}
