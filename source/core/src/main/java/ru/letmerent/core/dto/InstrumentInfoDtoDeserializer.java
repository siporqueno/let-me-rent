package ru.letmerent.core.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
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
        InstrumentInfoDto instrum = new InstrumentInfoDto();
        if (node.get("id") != null) {
            instrum.setId(node.get("id").longValue());
        }
        instrum.setTitle(node.get("title").toString().replace("\"", ""));
        instrum.setBrandName(node.get("brandName").toString().replace("\"", ""));
        instrum.setFee(node.get("fee").decimalValue());
        instrum.setPrice(node.get("price").decimalValue());
        if (node.get("ownerUsername") != null) {
            instrum.setOwnerUsername(node.get("ownerUsername").toString().replace("\"", ""));
        }
        instrum.setCategoryName(node.get("categoryName").toString().replace("\"", ""));
        if (node.get("ownerId") != null) {
            instrum.setOwnerId(node.get("ownerId").longValue());
        }
        if (node.get("ownerFirstName") != null) {
            instrum.setOwnerFirstName(node.get("ownerFirstName").toString().replace("\"", ""));
        }
        if (node.get("ownerSecondName") != null) {
            instrum.setOwnerSecondName(node.get("ownerSecondName").toString().replace("\"", ""));
        }
        if (node.get("ownerLastName") != null) {
            instrum.setOwnerLastName(node.get("ownerLastName").toString().replace("\"", ""));
        }
        if (node.get("ownerEmail") != null) {
            instrum.setOwnerEmail(node.get("ownerEmail").toString().replace("\"", ""));
        }
        instrum.setDescription(node.get("description").toString().replace("\"", ""));
        if (node.get("intervals") != null) {
            JsonNode jsonIntervals = node.get("intervals");
            List<IntervalDto> intervals = new ArrayList<>();
            if (jsonIntervals.isArray()) {
                for (JsonNode jsonInterval : jsonIntervals) {
                    LocalDateTime dateStart = convertStringDateToLocalDateTime(jsonInterval.get("dateStart").toString().replace("\"", ""));
                    LocalDateTime dateFinish = convertStringDateToLocalDateTime(jsonInterval.get("dateFinish").toString().replace("\"", ""));
                    IntervalDto intervalDto = new IntervalDto(dateStart, dateFinish);
                    intervals.add(intervalDto);
                }
            }
            instrum.setIntervals(intervals);
        }

        Collection<String> pictureUrls = new ArrayList<>();
        if (node.get("pictureUrls") != null) {
            JsonNode jsonPictureUrls = node.get("pictureUrls");
            if (jsonPictureUrls.isArray()) {
                for (JsonNode jsonUrl : jsonPictureUrls) {
                    pictureUrls.add(jsonUrl.toString().replace("\"", ""));
                }
            }
        }
        instrum.setPictureUrls(pictureUrls);


        if (node.get("startDate") != null) {
            instrum.setStartDate(convertStringDateToLocalDateTime(node.get("startDate").toString().replace("\"", "")));
        }
        if (node.get("endDate") != null) {
            instrum.setEndDate(convertStringDateToLocalDateTime(node.get("endDate").toString().replace("\"", "")));
        }

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
