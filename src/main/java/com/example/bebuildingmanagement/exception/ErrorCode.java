package com.example.bebuildingmanagement.exception;

import lombok.*;


@Getter
public enum ErrorCode {
    CODE_LANDING_BLANK(1000,"Vui lòng nhập mã mặt bằng"),
    CODE_LANDING_FORMAT(1001,"Mã mặt bằng phải đúng định dạng MBxxx"),
    CODE_LANDING_SPECIAL_CHARACTERS (1003,"Mã mặt bằng phải là số và không được có ký tự đặc biệt"),
    CODE_LANDING_AT_LEAST(1004,"Mã mặt bằng phải có ít nhất 5 ký tự"),
    CODE_LANDING_MAX(1005,"Mã mặt bằng phải có tối nhất 25 ký tự"),
    CODE_LANDING_AVAILABLE(1006 , "Mã mặt bằng đã tồn tại."),

    AREA_LANDING_BLANK(7,"Vui lòng nhập diện tích."),
    AREA_LANDING_REAL_NUMBER(8,"Vui lòng nhập diện tích lớn hơn 0."),
    AREA_LANDING_SPECIAL_CHARACTERS(9,"Diện tích phải là số và không được có ký tự đặc biệt."),

    ;

    private int code;
    private String message;

    ErrorCode() {
    }

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}