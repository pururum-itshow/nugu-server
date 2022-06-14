package com.nugu.greenery.NuguServer.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class WeatherResponseDto {
    private Response response;
    @Data
    public static class Response{
        private Header header;
        private Body body;
    }
    @Data
    public static class Header{
        private String resultCode;
        private String resultMsg;
    }
    @Data
    public static class Body{
        private String dataType;
        private Items items;
        private int pageNo;
        private int numOfRows;
        private int totalCount;
    }

    @Data
    public static class Items{
        private List<Item> item;
    }

    @Data
    public static class Item{
        private String baseDate;
        private String baseTime;
        private String category;
        private int nx;
        private int xy;
        private String obsrValue;
    }
}

