package com.nugu.greenery.NuguServer.service;

import com.nugu.greenery.NuguServer.dto.response.NuguResponse;
import com.nugu.greenery.NuguServer.dto.response.WeatherResponseDto;
import com.nugu.greenery.NuguServer.entity.Humidity;
import com.nugu.greenery.NuguServer.entity.repository.HumidityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NuguService {
    public final HumidityRepository humidityRepository;

    private final String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    @Value("${spring.sendgrid.api-key}")
    private String apiKey;

    @Transactional
    public NuguResponse requestHumidity() {
        Map<String, Object> output = new HashMap<>();

        Humidity humidity = humidityRepository.findLastestHumidity();
        int value = humidity.getValue();

        //상태 계산
        String status;
        if (value < 20) {
            status = "매우 마름";
        } else if (value < 40) {
            status = "마름";
        } else if(value<70){
            status = "";
        }else if (value < 80) {
            status = "촉촉함";
        } else {
            status = "축축함";
        }

        LocalDate now = LocalDate.now();
        output.put("month", Integer.toString(now.getMonthValue()));   //달
        output.put("day", Integer.toString(now.getDayOfMonth()));     //일
        output.put("humidity", Integer.toString(value));      //습도
        output.put("status", status);       //상태

        return NuguResponse.builder()
                .version("2.0")
                .resultCode("OK")
                .output(output)
                .build();
    }

    public NuguResponse requestWeather() {
        LocalDateTime current = LocalDateTime.now().minusHours(1);

        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = current.format(formatDate);

        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH00");
        String time = current.format(formatTime);

        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=10");
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=1");
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=59");
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=125");
        } catch (Exception e) {
            return NuguResponse.builder()
                    .version("2.0")
                    .resultCode("500")
                    .output(null)
                    .build();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        WeatherResponseDto response = restTemplate.getForObject(URI.create(urlBuilder.toString()), WeatherResponseDto.class);

        List<WeatherResponseDto.Item> items = response.getResponse().getBody().getItems().getItem();
        Map<String, Object> output = new HashMap<>();
        String status = "";

        for (WeatherResponseDto.Item item : items) {
            //강수형태
            if (item.getCategory().equals("PTY")) {
                switch (Integer.parseInt(item.getObsrValue())) {
                    case 1:
                    case 5:
                    case 6:
                        status = "rain";
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 7:
                        status = "snow";
                        break;
                }
            }

            // 풍속
            else if (item.getCategory().equals("WSD")) {
                double windSpeed = Double.parseDouble(item.getObsrValue());
                if (windSpeed > 14) {
                    status = "wind";
                }
            }
            //습도
            else if (item.getCategory().equals("REH")) {
                int REH = Integer.parseInt(item.getObsrValue());
                if (REH <= 40) {
                    status = "dry";
                }
            }
            //기온
            else if (item.getCategory().equals("T1H")) {
                double tempature = Double.parseDouble(item.getObsrValue());
                if (tempature >= 33) {
                    status = "hot";
                } else if (tempature <= 0) {
                    status = "cold";
                }
                output.put("tempature", item.getObsrValue());
            }

        }

        output.put("state", status);

        return NuguResponse.builder()
                .version("2.0")
                .resultCode("OK")
                .output(output)
                .build();
    }

}
