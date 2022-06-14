package com.nugu.greenery.NuguServer.controller;

import com.nugu.greenery.NuguServer.dto.request.NuguRequest;
import com.nugu.greenery.NuguServer.dto.response.NuguResponse;
import com.nugu.greenery.NuguServer.service.NuguService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NuguController {
    private final NuguService nuguService;

    @GetMapping("/health")
    public HttpStatus checkHealth(){
        return HttpStatus.OK;
    }

    @PostMapping("/answer.humidity")
    public void requestHumidity(){
        nuguService.requestHumidity();
    }

    @GetMapping(value="/weather", produces= MediaType.APPLICATION_JSON_VALUE)
    public NuguResponse requestWeather(@RequestBody NuguRequest nuguRequest){
        return nuguService.requestWeather();
    }

}
