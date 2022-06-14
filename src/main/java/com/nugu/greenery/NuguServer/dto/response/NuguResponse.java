package com.nugu.greenery.NuguServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NuguResponse {
    private String version;
    private String resultCode;
    private Map<String, Object> output;
}
