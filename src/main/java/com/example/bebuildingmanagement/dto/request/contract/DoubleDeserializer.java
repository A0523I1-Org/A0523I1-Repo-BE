package com.example.bebuildingmanagement.dto.request.contract;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;


public class DoubleDeserializer extends JsonDeserializer<Double> {
    //để xử lý việc phân tích cú pháp (deserialize) dữ liệu kiểu Double từ JSON.
    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        nó lấy giá trị chuỗi từ JsonParser p bằng p.getValueAsString().
        String value = p.getValueAsString();

        // Xử lý trường hợp có nhiều dấu chấm trong chuỗi
        int numDecimalPoints = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            //value.charAt(i): Phương thức này trả về ký tự tại vị trí i trong chuỗi value
            char c = value.charAt(i);
            //Nếu chuỗi có dấu phẩy (,), nó sẽ được thay thế bằng dấu chấm (.)
            if (c == ',') {
                c = '.';
            } else if (c == '.') {
                //Số lượng dấu chấm (.) trong chuỗi được đếm bằng biến numDecimalPoints.
                numDecimalPoints++;
                //Nếu có nhiều hơn 1 dấu chấm, các dấu chấm thừa sẽ bị bỏ qua.
                if (numDecimalPoints >= 1) {
                   //Nếu có nhiều hơn 1 dấu chấm, các dấu chấm thừa sẽ bị bỏ qua.
                    continue;
                }
            }
            //sb là một biến kiểu StringBuilder được sử dụng để xây dựng lại chuỗi sau khi xử lý.
            //append(c) là một phương thức của StringBuilder dùng để thêm ký tự c vào cuối chuỗi hiện tại của sb.
            sb.append(c);
        }

        return Double.parseDouble(sb.toString());
    }
}
