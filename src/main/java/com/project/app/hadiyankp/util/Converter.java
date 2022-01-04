package com.project.app.hadiyankp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class Converter extends AbstractJackson2HttpMessageConverter {
    public Converter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }
    @Override
    public boolean canWrite(Class<?>clazz,MediaType mediaType){
        return false;
    }
    @Override
    public boolean canWrite(MediaType mediaType){
        return false;
    }
    @Override
    public boolean canWrite(Type type, Class<?>clazz, MediaType mediaType){
        return false;
    }
}
