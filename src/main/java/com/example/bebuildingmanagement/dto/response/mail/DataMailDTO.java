package com.example.bebuildingmanagement.dto.response.mail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
    // DTO gửi mail  : (Hoài NT)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataMailDTO {
    private String toEmail;
    private String subject;
    private String content;
    private Map<String,Object> props;
}
