package com.company;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	    String base_url = "http://numbersapi.com/";
        String number = "10";
        String type = "/trivia";
        String def = "Boring number. No fact.";
        String url = base_url + number + type + "?json";

        HttpURLConnection connection;
        URL u = new URL(url);

        connection =(HttpURLConnection)u.openConnection();
        connection.setConnectTimeout(10000);
        connection.connect();

        int status = connection.getResponseCode();
        System.out.println(status);

        ArrayList<String> lines = new ArrayList<>();

        if (status == 200) {
            Scanner sc = new Scanner(connection.getInputStream());
            while (sc.hasNext()) {
                lines.add(sc.nextLine());
            }
        }

        String savePath = "D:\\Projects\\IntelliJProjects\\API\\result";
        Path path = Path.of(savePath);
        Files.write(path, lines);

        ArrayList<String> readlines = (ArrayList<String>) Files.readAllLines(path);
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < readlines.size(); i++) {
            stringBuilder.append(readlines.get(i));
        }
        Gson gson = new Gson();

        Result result = gson.fromJson(stringBuilder.toString(), Result.class);

        System.out.println(number + ": " + result.getText());
    }
}
