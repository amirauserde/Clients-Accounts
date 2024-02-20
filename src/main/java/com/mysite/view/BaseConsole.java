package com.mysite.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mysite.util.ScannerWrapper;

public class BaseConsole {

    ScannerWrapper scannerWrapper;
    ObjectMapper objectMapper;

    public BaseConsole(){
        scannerWrapper  = ScannerWrapper.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
}
