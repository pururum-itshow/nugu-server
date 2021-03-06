package com.nugu.greenery.NuguServer.controller;

import com.nugu.greenery.NuguServer.dto.response.NuguResponse;
import com.nugu.greenery.NuguServer.service.NuguService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NuguController {
    private final NuguService nuguService;

    @GetMapping(value="/health")
    public String checkHealth(){
        return "OK";
    }

    @GetMapping(value="/answer.humidity", produces= MediaType.APPLICATION_JSON_VALUE)
    public NuguResponse requestHumidity(){
        return nuguService.requestHumidity();
    }
    //@RequestBody NuguRequest nuguRequest

    @GetMapping(value="/weather", produces= MediaType.APPLICATION_JSON_VALUE)
    public NuguResponse requestWeather(){
        return nuguService.requestWeather();
    }

}
