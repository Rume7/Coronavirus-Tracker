package com.codehacks.coronavirus.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CoronaVirusService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/UID_ISO_FIPS_LookUp_Table.csv";
    private static String VIRUS_DATA_URL2 = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    @PostConstruct
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL2))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        Reader csvReader = new StringReader(httpResponse.body());
        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().build();
        List<CSVRecord> records = new CSVParser(csvReader, csvFormat).getRecords();

        int i = 0;
        for (CSVRecord record : records) {
            String country = record.get("Country/Region");
            String latitude = record.get("Lat");
            String longitude = record.get("Long");

            System.out.printf("%20s\t|\t%10s\t|\t%10s\n", country, latitude, longitude);
            i++;
            if (i == 10) break;
        }
    }
}

