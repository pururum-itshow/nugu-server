package com.nugu.greenery.NuguServer.dto.request;

import java.util.Map;

public class NuguRequest {
    private String version;
    private Action action;
    private Event event;
    private Context context;

    public static class Action{
        private String actionName;
        private Map<String, Object> parameters;
    }

    public static class Event{
        private String type;
    }

    public static class Context{
        private Session session;
        private Device device;
    }

    public static class Session{
        private String id;
        private boolean isNew;
        //private String accessToken;
        //private boolean isPlayBuilderRequest;
    }

    public static class Device{
        private String type;
        //private State state;
        private SupportedInterfaces supportedInterfaces;
    }

    public static class SupportedInterfaces{

    }
}

