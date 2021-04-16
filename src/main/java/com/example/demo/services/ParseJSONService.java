package com.example.demo.services;

import com.example.demo.entity.InfoJSON;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;

public class ParseJSONService {

    public InfoJSON getEntity() throws IOException, ParseException {
        InfoJSON infoJSON = new InfoJSON();
        FileReader reader = new FileReader("src/main/java/courier.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        infoJSON.setSpeed(getLong(jsonObject, "speed"));

        infoJSON.setLongitude(getDouble(jsonObject, "longitude"));
        infoJSON.setLatitude(getDouble(jsonObject, "latitude"));


        infoJSON.setStart(getTime(jsonObject, "start"));
        infoJSON.setEnd(getTime(jsonObject, "end"));
        infoJSON.setTime_to_give(getTime(jsonObject, "time_to_give"));
        return infoJSON;
    }

    public Long getLong(JSONObject jsonObject, String value) {
        return (Long) jsonObject.get(value);
    }

    public Double getDouble(JSONObject jsonObject, String value) {
        return (Double) jsonObject.get(value);
    }

    public LocalTime getTime(JSONObject jsonObject, String value) {
        String timeString = (String) jsonObject.get(value);
        return LocalTime.parse(timeString);
    }

}
