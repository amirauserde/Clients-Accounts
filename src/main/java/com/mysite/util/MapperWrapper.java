package com.mysite.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MapperWrapper {

    private static final ObjectMapper INSTANCE;

    public static ObjectMapper getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ObjectMapper();
        INSTANCE.enable(SerializationFeature.INDENT_OUTPUT);
        INSTANCE.registerModule(new JavaTimeModule());
    }

    private MapperWrapper() {}

}
