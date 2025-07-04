package com.example.balatro.domain.effects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.List;
import java.util.Map;

public class JokerEffectUtil {
    public static List<Map<String, Object>> parseParamList(String jsonString) {
        if (jsonString == null || jsonString.isBlank()) return List.of();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonString);
            if (root.isArray()) {
                return mapper.readValue(jsonString, new TypeReference<>() {});
            } else if (root.isObject()) {
                Map<String, Object> single = mapper.convertValue(root, new TypeReference<>() {});
                return List.of(single);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
