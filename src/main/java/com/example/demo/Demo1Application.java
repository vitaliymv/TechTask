package com.example.demo;

import com.example.demo.entity.Info;
import com.example.demo.entity.InfoJSON;
import com.example.demo.repository.InfoRepository;
import com.example.demo.services.ParseJSONService;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootApplication
public class Demo1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
    }

    @Autowired
    InfoRepository infoRepository;

    @Override
    public void run(String... args) throws Exception {
        ParseJSONService parseJSONService = new ParseJSONService();
        InfoJSON infoJSON = parseJSONService.getEntity();
        List<Info> infoList = infoRepository.findAll();
        Coordinate lat_c = Coordinate.fromDegrees(infoJSON.getLatitude());
        Coordinate lng_c = Coordinate.fromDegrees(infoJSON.getLongitude());
        Point courier = Point.at(lat_c, lng_c);
        HashMap<Long, Double> hashMap = new HashMap<>();
        double allDistance = 0;
        double time_give = 0;
        long time = ChronoUnit.HOURS.between(infoJSON.getStart(), infoJSON.getEnd());
        double distance = 0;
        for (Info info : infoList) {
            Coordinate lat = Coordinate.fromDegrees(info.getLatitude());
            Coordinate lng = Coordinate.fromDegrees(info.getLongitude());
            Point point = Point.at(lat, lng);
            if (time >= time_give) {
                time_give += distance / infoJSON.getSpeed();
                distance = EarthCalc.haversine.distance(courier, point) / 1000;
                allDistance += distance;
                hashMap.put(info.getId(), distance);
            }

        }
        time_give *= 60;
        Map<Long, Double> minDistance = hashMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new)
                );
        printFirstTask(minDistance, allDistance, time_give);
    }

    void printFirstTask(Map<Long, Double> min, Double fullDistance, double time) {
        System.out.println("Minimal distance. ");
        for (Long key : min.keySet()) {
            System.out.printf("[%o] => ", key);
        }
        System.out.print("| Distance: " + Math.round(fullDistance) + "км.");
        System.out.println(getTime( (int) time));

    }

    private String getTime(int s){
        int hours = s / 60;
        int minutes = s % 60;
        return String.format("Time to way: %d hours %02d minutes", hours, minutes);
    }
}
