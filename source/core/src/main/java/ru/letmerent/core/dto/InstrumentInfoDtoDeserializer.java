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
        String title = node.get("title").toString().replace("\"", "");
        String brandName = node.get("brandName").toString().replace("\"", "");
        BigDecimal fee = node.get("fee").decimalValue();
        BigDecimal price = node.get("price").decimalValue();
        String ownerUsername = node.get("ownerUsername").toString().replace("\"", "");
        String categoryName = node.get("categoryName").toString().replace("\"", "");
        Long ownerId = node.get("ownerId").longValue();
        String ownerFirstName = node.get("ownerFirstName").toString().replace("\"", "");
        String ownerSecondName = node.get("ownerSecondName").toString().replace("\"", "");
        String ownerLastName = node.get("ownerLastName").toString().replace("\"", "");
        String ownerEmail = node.get("ownerEmail").toString().replace("\"", "");
        String description = node.get("description").toString().replace("\"", "");

        List<IntervalDto> intervals = new ArrayList<>();
        JsonNode jsonIntervals = node.get("intervals");
        if (jsonIntervals.isArray()) {
            for (JsonNode jsonInterval : jsonIntervals) {
                LocalDateTime dateStart = convertStringDateToLocalDateTime(jsonInterval.get("dateStart").toString().replace("\"", ""));
                LocalDateTime dateFinish = convertStringDateToLocalDateTime(jsonInterval.get("dateFinish").toString().replace("\"", ""));
                IntervalDto intervalDto = new IntervalDto(dateStart, dateFinish);
                intervals.add(intervalDto);
            }
        }

        Collection<String> pictureUrls = new ArrayList<>();
        JsonNode jsonPictureUrls = node.get("pictureUrls");
        if (jsonPictureUrls.isArray()) {
            for (JsonNode jsonUrl : jsonPictureUrls) {
                pictureUrls.add(jsonUrl.toString().replace("\"", ""));
            }
        }

        LocalDateTime startDate = convertStringDateToLocalDateTime(node.get("startDate").toString().replace("\"", ""));
        LocalDateTime endDate = convertStringDateToLocalDateTime(node.get("endDate").toString().replace("\"", ""));

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
