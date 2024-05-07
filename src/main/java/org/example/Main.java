package org.example;

import io.muserver.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        MuServer server = MuServerBuilder.httpServer()
                .withHttpPort(8080)
                .addHandler(Method.GET, "/reservations", (request, response, pathParams) -> {
                    try {
                        logger.log(Level.INFO, "-------------------- Getting all Reservations ------------------------");

                        FileReader fileReader = new FileReader("reservations.json");

                        JSONParser parser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
                        logger.log(Level.INFO, jsonArray.toJSONString());
                        response.write(jsonArray.toJSONString());
                        logger.log(Level.INFO, "----------------------- End of Reservations --------------------------");
                    } catch (Exception e) {
                        System.out.println();
                    }
                    response.write("These are the reservations");
                })
                .addHandler(Method.GET, "/reservations/{date}", (request, response, pathParams) -> {
                    try {
                        logger.log(Level.INFO, "----------------------- Getting reservations from: " + pathParams.get("date") + " -----------------------------");

                        FileReader fileReader = new FileReader("reservations.json");

                        JSONParser parser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) parser.parse(fileReader);

                        List<Object> reservations = new ArrayList<>();

                        for (Object json : jsonArray) {
                            JSONObject jsonItem = (JSONObject) json;
                            String date = jsonItem.get("date").toString();
                            if(date.equals(pathParams.get("date")))
                            reservations.add(json);
                        }

                        response.write(reservations.toString());
                        logger.log(Level.INFO, "----------------------- End of Reservations --------------------------");
                    } catch (Exception e) {
                        System.out.println();
                    }
                })
                .addHandler(Method.POST, "/reservation", (request, response, pathParams) -> {
                    try {
                        logger.log(Level.INFO, "------------------ Creating Reservation ----------------------");

                        FileReader fileReader = new FileReader("reservations.json");

                        JSONParser parser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) parser.parse(fileReader);

                        JSONObject jsonObject = (JSONObject) parser.parse(request.readBodyAsString());

                        List<Object> reservations = new ArrayList<>();

                        for (Object json : jsonArray) {
                            reservations.add(json);
                        }
                        reservations.add(jsonObject);

                        FileWriter file = new FileWriter("reservations.json");
                        file.write(reservations.toString());
                        file.close();
                        logger.log(Level.INFO, "------------------ Reservation Created -----------------------");

                    } catch (Exception e) {
                        logger.log(Level.SEVERE, e.getMessage());
                    }
                })
                .start();
        System.out.println("Started server at " + server.uri());
    }
}