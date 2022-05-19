package ru.letmerent.core.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InstrumentInfoDtoDeserializer extends StdDeserializer<InstrumentInfoDto> {

    public InstrumentInfoDtoDeserializer() {
        this(null);
    }

    public InstrumentInfoDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public InstrumentInfoDto deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jp.getCodec().readTree(jp);
        Long id = node.get("id").longValue();
        System.out.println(id);
        String title = node.get("title").toString().replace("\"", "");
        System.out.println(title);
        String brandName = node.get("brandName").toString().replace("\"", "");
        System.out.println(brandName);
        BigDecimal fee = node.get("fee").decimalValue();
        System.out.println(fee);
        BigDecimal price = node.get("price").decimalValue();
        System.out.println(price);
        String ownerUsername = node.get("ownerUsername").toString().replace("\"", "");
        System.out.println(ownerUsername);
        String categoryName = node.get("categoryName").toString().replace("\"", "");
        System.out.println(categoryName);
        Long ownerId = node.get("ownerId").longValue();
        System.out.println(ownerId);
        String ownerFirstName = node.get("ownerFirstName").toString().replace("\"", "");
        System.out.println(ownerFirstName);
        String ownerSecondName = node.get("ownerSecondName").toString().replace("\"", "");
        System.out.println(ownerSecondName);
        String ownerLastName = node.get("ownerLastName").toString().replace("\"", "");
        System.out.println(ownerLastName);
        String ownerEmail = node.get("ownerEmail").toString().replace("\"", "");
        System.out.println(ownerEmail);
        String description = node.get("description").toString().replace("\"", "");
        System.out.println(description);
        List<IntervalDto> intervals = new ArrayList<>();
        Collection<String> pictureUrls = new ArrayList<>();
        LocalDateTime startDate = convertStringDateToLocalDateTime(node.get("startDate").toString().replace("\"", ""));
        System.out.println(startDate);
        LocalDateTime endDate = convertStringDateToLocalDateTime(node.get("endDate").toString().replace("\"", ""));
        System.out.println(endDate);

        InstrumentInfoDto instrum = new InstrumentInfoDto();
        instrum.setId(id);
        instrum.setTitle(title);
        instrum.setBrandName(brandName);
        instrum.setPrice(price);
        instrum.setFee(fee);
        instrum.setOwnerUsername(ownerUsername);
        instrum.setCategoryName(categoryName);
        instrum.setOwnerId(ownerId);
        instrum.setOwnerFirstName(ownerFirstName);
        instrum.setOwnerSecondName(ownerSecondName);
        instrum.setOwnerLastName(ownerLastName);
        instrum.setOwnerEmail(ownerEmail);
        instrum.setDescription(description);
        instrum.setPictureUrls(pictureUrls);
        instrum.setIntervals(intervals);
        instrum.setStartDate(startDate);
        instrum.setEndDate(endDate);

        return instrum;
    }

    private LocalDateTime convertStringDateToLocalDateTime(String date) {
        String[] dates = date.split("-", 3);
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);
        return LocalDateTime.of(year, month, day, 00, 00);
    }
}
