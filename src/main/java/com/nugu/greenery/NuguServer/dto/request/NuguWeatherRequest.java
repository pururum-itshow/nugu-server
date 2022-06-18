package com.nugu.greenery.NuguServer.dto.request;

import java.util.Map;

public class NuguWeatherRequest {
    private String version;
    private Action action;
    private Event event;
    private Context context;

    public static class Action{
        private String actionName;
        private Parameters parameters;
    }

    public static class Event{
        private String type;
    }

    public static class Context{
        private Session session;
        private Device device;
        private SupportedInterfaces supportedInterfaces;
        private PrivatePlay privatePlay;
    }

    public static class Parameters{
        private MapClass tempature;
        private MapClass state;
    }

    public static class MapClass{
        private String type;
        private String value;
    }

    public static class Session{
        private String accessToken;
        //private String id;
        //private boolean isNew;
        //private String accessToken;
        //private boolean isPlayBuilderRequest;
    }

    public static class Device{
        private String type;
        private Map<String, Object> state;
    }

    public static class SupportedInterfaces{
        private AudioPlayer audioPlayer;
    }

    public static class AudioPlayer{
//        private String playerActivity;
//        private String token;
//        private int offsetInsilliseconds = 10000;
    }

    public static class PrivatePlay{

    }
}



