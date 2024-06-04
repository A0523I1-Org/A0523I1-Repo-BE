package com.example.bebuildingmanagement.dto.request.contract;

import com.example.bebuildingmanagement.utils.Const;
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
               errors.rejectValue("term", "", Const.ERROR_MESSAGE.TERM_NOT_BLANK);
          } else if (term < 1) {
               errors.rejectValue("term", "", Const.ERROR_MESSAGE.TERM_MIN);
          } else if (term > 120) {
               errors.rejectValue("term", "", Const.ERROR_MESSAGE.TERM_MAX);
          }
          // check taxCode :
          if (taxCode == null || taxCode.trim().equals("")) {
               errors.rejectValue("taxCode", "", Const.ERROR_MESSAGE.TAX_CODE_NOT_BLANK);
          } else if (!taxCode.matches("(^[0-9]{10}$)")) {
               errors.rejectValue("taxCode", "", Const.ERROR_MESSAGE.TAX_CODE_FORMAT);
          }
          // check currentFee  (giá tiền mặt bằng tại thời điểm làm hợp đồng) :
          if (currentFee == 0.0) {
               errors.rejectValue("currentFee", "", Const.ERROR_MESSAGE.CURRENT_FEE_NOT_BLANK);
          } else if (currentFee < 0) {
               errors.rejectValue("currentFee", "", Const.ERROR_MESSAGE.CURRENT_FEE_ILLEGAL);
          }


          // check deposit :
          if (deposit == 0) {
               errors.rejectValue("deposit", "", Const.ERROR_MESSAGE.DEPOSIT_NOT_BLANK);
          } else if (deposit < 0) {
               errors.rejectValue("deposit", "", Const.ERROR_MESSAGE.DEPOSIT_ILLEGAL);
          } else if (deposit < (term * currentFee * 10) / 100) {
               errors.rejectValue("deposit", "", Const.ERROR_MESSAGE.DEPOSIT_MIN);
          }
          // check firebaseUrl :
          if (firebaseUrl == null || firebaseUrl.trim().equals("")) {
               errors.rejectValue("firebaseUrl", "", Const.ERROR_MESSAGE.FIREBASE_NOT_BLANK);
          } else if (!firebaseUrl.matches("(^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png)$)")) {
               errors.rejectValue("firebaseUrl", "",Const.ERROR_MESSAGE.FIREBASE_FORMAT );
          }

          //check content :
          if (content == null || content.trim().equals("")) {
               errors.rejectValue("content", "", Const.ERROR_MESSAGE.CONTENT_NOT_BLANK);
          } else if (content.length() < 50) {
               errors.rejectValue("content", "", Const.ERROR_MESSAGE.CONTENT_MIN);
          }

          //check id landing and employee :
          if (landingId == null) {
               errors.rejectValue("landingId", "", Const.ERROR_MESSAGE.LANDING_NOT_BLANK);
          } else if (landingId <= 0) {
               errors.rejectValue("landingId", "", Const.ERROR_MESSAGE.LANDING_ILLEGAL);
          }

          if (customerId == null) {
               errors.rejectValue("customerId", "", Const.ERROR_MESSAGE.CUSTOMER_NOT_BLANK);
          } else if (customerId <= 0) {
               errors.rejectValue("customerId", "", Const.ERROR_MESSAGE.CUSTOMER_NOT_FOUND);
          }

          // check startDate :

          Date date = new Date();

          if (startDate == null) {
               errors.rejectValue("startDate", "", Const.ERROR_MESSAGE.START_DATE_NOT_BLANK);
          } else if (date.after(startDate) && !(date.getDay() == startDate.getDay() && date.getMonth() == startDate.getMonth() && date.getYear() == startDate.getYear())) {
               errors.rejectValue("startDate", "", Const.ERROR_MESSAGE.START_DATE_AFTER);
          }else if (!strStartDate.matches("(^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$)")){
               errors.rejectValue("startDate", "", Const.ERROR_MESSAGE.START_DATE_FORMAT);
          }

          //check endDate :

          LocalDate localDateEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
          LocalDate endDateNew = LocalDate.parse(strStartDate).plusMonths(term);
          if (endDate == null) {
               errors.rejectValue("endDate", "", Const.ERROR_MESSAGE.END_DATE_NOT_BLANK);
          } else if ((localDateEndDate.getDayOfMonth() != endDateNew.getDayOfMonth())
                  || (localDateEndDate.getMonthValue() != endDateNew.getMonthValue())
                  || (localDateEndDate.getYear() != endDateNew.getYear())) {
               errors.rejectValue("endDate", "", Const.ERROR_MESSAGE.END_DATE_COMPARED_TO_TERM);

          }else if (!strEndDate.matches("(^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$)")){
               errors.rejectValue("endDate", "", Const.ERROR_MESSAGE.END_DATE_FORMAT);
          }
     }
}


