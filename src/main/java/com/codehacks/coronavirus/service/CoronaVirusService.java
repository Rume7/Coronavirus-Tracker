package com.codehacks.coronavirus.service;

import com.codehacks.coronavirus.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusService {

    private List<LocationStats> allStats = new ArrayList<>();

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/UID_ISO_FIPS_LookUp_Table.csv";
    private static String VIRUS_DATA_URL2 = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL2))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        Reader csvReader = new StringReader(httpResponse.body());
        CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().build();
        List<CSVRecord> records = new CSVParser(csvReader, csvFormat).getRecords();

        List<LocationStats> newStats = new ArrayList<>();

        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            locationStats.setLatitude(record.get("Lat"));
            locationStats.setLongitude(record.get("Long"));
            //locationStats.setLatestStats(Integer.parseInt(record.get(record.size() - 1)));

            int currentCases = Integer.parseInt(record.get("12/31/20"));
            int prevDayCases = Integer.parseInt(record.get("12/30/20"));
            locationStats.setLatestTotalStats(currentCases);
            locationStats.setDiffFromPrevDay(currentCases - prevDayCases);
            newStats.add(locationStats);
        }
        this.allStats = newStats;
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }
}

