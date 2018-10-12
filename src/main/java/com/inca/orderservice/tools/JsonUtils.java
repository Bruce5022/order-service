package com.inca.orderservice.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static ObjectMapper om = new ObjectMapper();

    public static JsonNode str2Json(String str) throws Exception {
        return om.readTree(str);
    }
}
