package com.example.bebuildingmanagement.dto.request.contract;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;


public class DoubleDeserializer extends JsonDeserializer<Double> {
    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();

        // Xử lý trường hợp có nhiều dấu chấm trong chuỗi
        int numDecimalPoints = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == ',') {
                c = '.';
            } else if (c == '.') {
                numDecimalPoints++;
                if (numDecimalPoints >= 1) {
                    // Nếu có nhiều hơn 1 dấu chấm, bỏ qua ký tự này
                    continue;
                }
            }
            sb.append(c);
        }

        return Double.parseDouble(sb.toString());
    }
}
