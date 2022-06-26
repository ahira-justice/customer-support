package com.ahirajustice.customersupport.common.utils;

import com.ahirajustice.customersupport.common.exceptions.SystemErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class ObjectMapperUtil {

    public static String serialize(ObjectMapper objectMapper, Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            throw new SystemErrorException("Could not serialize object of class: " + object.getClass().getSimpleName());
        }
    }

    public static <TResult> TResult deserialize(ObjectMapper objectMapper, String resultJson, Class<TResult> resultClass) {
        if (StringUtils.isBlank(resultJson))
            return null;

        try {
            return objectMapper.readValue(resultJson, resultClass);
        } catch (JsonProcessingException ex) {
            throw new SystemErrorException("Could not deserialize json to class: " + resultClass.getSimpleName());
        }
    }

    public static <TResult> TResult deserialize(ObjectMapper objectMapper, String resultJson, TypeReference<TResult> typeReference) {
        if (StringUtils.isBlank(resultJson))
            return null;

        try {
            return objectMapper.readValue(resultJson, typeReference);
        } catch (JsonProcessingException ex) {
            throw new SystemErrorException("Could not deserialize json to class: " + typeReference.getType().getTypeName());
        }
    }

}