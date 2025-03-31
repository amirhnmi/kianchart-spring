package com.kianchart.kianchart.core.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseEntity<T> extends ResponseEntity<Map<String,Object>> {

    private CustomResponseEntity(Map<String, Object> body, HttpStatus status) {
        super(body, status);
    }

    public static <T> CustomResponseEntity<T> showList(T items, Long totalCount) {
        Map<String, Object> data = new HashMap<>();
        data.put("items", items);
        data.put("message", "OK");
        data.put("source", "database");
        data.put("code", HttpStatus.OK.value());

        if (totalCount != null) {
            data.put("total", totalCount);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        return new CustomResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T> CustomResponseEntity<T> showDetail(T item) {
        Map<String, Object> data = new HashMap<>();
        data.put("item", item);
        data.put("message", "OK");
        data.put("source", "database");
        data.put("code", HttpStatus.OK.value());

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        return new CustomResponseEntity<>(response, HttpStatus.OK);
    }
}
