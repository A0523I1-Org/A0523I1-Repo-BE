package com.example.bebuildingmanagement.dto.request.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractRequestDTO {
    @NotBlank(message = "Vui lòng cung cấp nội dung")
    private String content;
    @NotBlank(message = "Vui lòng cung cấp tiền cọc")
    @Size(min = 100)
    private double deposit;
    @NotBlank(message = "Chi tiết không được để trống")
    private String description;
    @NotBlank(message = "Vui lòng cung cấp ngày bắt đầu")
    private Date startDate;
    @NotBlank(message = "Vui lòng cung cấp ngày kết thúc")
    private Date endDate;
    @NotBlank(message = "Vui lòng cung cấp ảnh")
    private String firebaseUrl;
    @NotBlank(message = "Vui lòng cung cấp mã số thuế")
    @Size(min = 10, max = 10, message = "Vui lòng cung cấp mã số thuế hợp lệ gồm 10 chữ số")
    private String taxCode;
    @NotBlank(message = "Vui lòng cung cấp kì hạn")
    @Length(min = 1, max = 120, message = "Vui lòng nhập kì hạn hợp lệ từ 1 đến 120 tháng")
    private int term;


}
