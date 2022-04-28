package ru.letmerent.core.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemDtoDeserializer extends StdDeserializer<OrderItemDto> {

    public OrderItemDtoDeserializer() {
        this(null);
    }

    public OrderItemDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderItemDto deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jp.getCodec().readTree(jp);
        Long id = node.get("id").longValue();
        LocalDateTime startDate = convertStringDateToLocalDateTime(node.get("startDate").toString().replace("\"",""));
        LocalDateTime endDate = convertStringDateToLocalDateTime(node.get("endDate").toString().replace("\"",""));
        BigDecimal rentPrice = node.get("rentPrice").decimalValue();
        Long rentLength = node.get("rentLength").longValue();
        JsonNode nodeInstrument = node.get("instrument");
        Long instrumentId = nodeInstrument.get("id").longValue();
        String title = nodeInstrument.get("title").toString().replace("\"","");
        String brandName = nodeInstrument.get("brandName").toString().replace("\"","");
        BigDecimal fee = nodeInstrument.get("fee").decimalValue();
        BigDecimal price = nodeInstrument.get("price").decimalValue();
        String ownerUsername = nodeInstrument.get("ownerUsername").toString().replace("\"","");
        String categoryName = nodeInstrument.get("categoryName").toString().replace("\"","");
        String avatarPictureUrl=nodeInstrument.get("avatarPictureUrl").toString().replace("\"","");

        InstrumentForListDto instrument = new InstrumentForListDto();
        instrument.setId(instrumentId);
        instrument.setTitle(title);
        instrument.setBrandName(brandName);
        instrument.setFee(fee);
        instrument.setPrice(price);
        instrument.setOwnerUsername(ownerUsername);
        instrument.setCategoryName(categoryName);
        instrument.setAvatarPictureUrl(avatarPictureUrl);

        OrderItemDto oi = new OrderItemDto();
        oi.setId(id);
        oi.setStartDate(startDate);
        oi.setEndDate(endDate);
        oi.setRentPrice(rentPrice);
        oi.setRentLength(rentLength);
        oi.setInstrument(instrument);

        return oi;
    }

    private LocalDateTime convertStringDateToLocalDateTime(String date) {
        String[] dates = date.split("-", 3);
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);
        return LocalDateTime.of(year, month, day, 00, 00);
    }
}
