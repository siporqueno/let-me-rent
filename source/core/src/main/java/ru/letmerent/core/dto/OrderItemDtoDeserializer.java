package ru.letmerent.core.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class OrderItemDtoDeserializer extends StdDeserializer<OrderItemDto> {

    public OrderItemDtoDeserializer() {
        this(null);
    }

    public OrderItemDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderItemDto deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {

        return new OrderItemDto();
    }
}
