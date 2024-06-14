package com.example.bebuildingmanagement.dto.request.contract;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractNewRequestDTO  implements Validator {
     int term;
     Date startDate;
     Date endDate;
     String taxCode;
     double currentFee;
     String description;
     double deposit;
     String firebaseUrl;
     String content;
     Long landingId;
     Long customerId;


     @Override
     public boolean supports(Class<?> clazz) {
          return false;
     }


     @Override
     public void validate(Object target, Errors errors) {
          ContractNewRequestDTO contractNewRequestDTO = (ContractNewRequestDTO) target;
          int term = contractNewRequestDTO.getTerm();
          String taxCode = contractNewRequestDTO.getTaxCode();
          double currentFee = contractNewRequestDTO.getCurrentFee();
          String description = contractNewRequestDTO.getDescription();
          double deposit = contractNewRequestDTO.getDeposit();
          String firebaseUrl = contractNewRequestDTO.getFirebaseUrl();
          String content = contractNewRequestDTO.getContent();
          Long landingId = contractNewRequestDTO.getLandingId();
          Long customerId = contractNewRequestDTO.getCustomerId();
          Date startDate = contractNewRequestDTO.getStartDate();
          Date endDate = contractNewRequestDTO.getEndDate();

          String strStartDate = new SimpleDateFormat(("yyyy-MM-dd"), Locale.getDefault()).format(startDate);
          String strEndDate = new SimpleDateFormat(("yyyy-MM-dd"), Locale.getDefault()).format(endDate);

          // check term :
          if (term == 0) {
               errors.rejectValue("term", "", "Vui lòng nhập kì hạn ! ");
          } else if (term < 1) {
               errors.rejectValue("term", "", "Kì hạn thuê tối thiểu 1 tháng !");
          } else if (term > 120) {
               errors.rejectValue("term", "", "Kì hạn thuê tối đa không quá 10 năm !");
          }
          // check taxCode :
          if (taxCode == null || taxCode.trim().equals("")) {
               errors.rejectValue("taxCode", "", "Vui lòng nhập mã số thuế !");
          } else if (!taxCode.matches("(^[0-9]{10}$)")) {
               errors.rejectValue("taxCode", "", "Vui lòng nhập đúng định dạng mã số thuế ! (10 chữ số)");
          }
          // check currentFee  (giá tiền mặt bằng tại thời điểm làm hợp đồng) :
          if (currentFee == 0.0) {
               errors.rejectValue("currentFee", "", "Phí hiện tại của mặt bằng không xác định !");
          } else if (currentFee < 0) {
               errors.rejectValue("currentFee", "", "Phí hiện tại của mặt bằng không hợp lệ !");
          }

          // check description :
          if (description == null || description.trim().equals("")) {
               errors.rejectValue("description", "", "Vui lòng nhập mô tả (các dịch vụ đã sử dụng) !");
          }

          // check deposit :
          if (deposit == 0) {
               errors.rejectValue("deposit", "", "Vui lòng nhập tiền đặt cọc ! ");
          } else if (deposit < 0) {
               errors.rejectValue("deposit", "", "Tiền đặt cọc không hợp lệ");
          } else if (deposit < (term * currentFee * 10) / 100) {
               errors.rejectValue("deposit", "", "Tiền đặt cọc tối thiểu bằng 10% so với tổng tiền (currentFee*term) ! ");
          }
          // check firebaseUrl :
          if (firebaseUrl == null || firebaseUrl.trim().equals("")) {
               errors.rejectValue("firebaseUrl", "", "Vui lòng cung cấp ảnh H/Đ !");
          } else if (!firebaseUrl.matches("(^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)$)")) {
               errors.rejectValue("firebaseUrl", "", "Chỉ được cung cấp file ảnh đúng định dạng (https://xxx/xx/xx.jpg) (jpg hoặc gif hoặc png) !");
          }

          //check content :
          if (content == null || content.trim().equals("")) {
               errors.rejectValue("content", "", "Vui lòng nhập nội dung H/Đ !");
          } else if (content.length() < 50) {
               errors.rejectValue("content", "", "Vui lòng nhập nội dung tối thiểu 50 kí tự !");
          }

          //check id landing and employee :
          if (landingId == null) {
               errors.rejectValue("landingId", "", "Vui lòng cung cấp mặt bằng");
          } else if (landingId <= 0) {
               errors.rejectValue("landingId", "", "Mặt bằng không hợp lệ ! ");
          }

          if (customerId == null) {
               errors.rejectValue("customerId", "", "Vui lòng chọn khách hàng");
          } else if (customerId <= 0) {
               errors.rejectValue("customerId", "", "Không tìm thấy khách hàng ! ");
          }

          // check startDate :

          Date date = new Date();

          if (startDate == null) {
               errors.rejectValue("startDate", "", "Vui lòng chọn ngày bắt đầu !");
          } else if (date.after(startDate) && !(date.getDay() == startDate.getDay() && date.getMonth() == startDate.getMonth() && date.getYear() == startDate.getYear())) {
               errors.rejectValue("startDate", "", "Ngày bắt đầu phải sau hoặc bằng ngày hiện tại (ngày tương lai) !");
          }else if (!strStartDate.matches("(^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$)")){
               errors.rejectValue("startDate", "", "Vui lòng nhập ngày bắt đầu đúng định dạng (yyyy-MM-dd)!");
          }

          //check endDate :

          LocalDate localDateEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
          LocalDate endDateNew = LocalDate.parse(strStartDate).plusMonths(term);
          if (endDate == null) {
               errors.rejectValue("endDate", "", "Vui lòng chọn ngày kết thúc !");
          } else if ((localDateEndDate.getDayOfMonth() != endDateNew.getDayOfMonth())
                  || (localDateEndDate.getMonthValue() != endDateNew.getMonthValue())
                  || (localDateEndDate.getYear() != endDateNew.getYear())) {
               errors.rejectValue("endDate", "", "Ngày kết thúc phải sau ngày bắt đầu bằng đúng số tháng của kì hạn !");

          }else if (!strEndDate.matches("(^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$)")){
               errors.rejectValue("endDate", "", "Vui lòng nhập ngày kết thúc đúng định dạng (yyyy-MM-dd)!");
          }
     }
}


