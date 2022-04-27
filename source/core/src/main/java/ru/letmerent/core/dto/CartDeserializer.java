package ru.letmerent.core.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class CartDeserializer extends StdDeserializer<Cart> {

    public CartDeserializer() {
        this(null);
    }

    public CartDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Cart deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        BigDecimal totalFee = node.get("totalFee").decimalValue();
        BigDecimal totalPrice = node.get("totalPrice").decimalValue();
        JsonNode itemsNode = node.get("items");

        return new Cart().setTotalFee(totalFee).setTotalPrice(totalPrice);
    }
}
