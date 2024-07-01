package com.example.bebuildingmanagement.validate.customerValidate.validateCustom;

import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.repository.contract.IContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Component
public class ContractUpdateValidator implements Validator {
    @Autowired
    private IContractRepository iContractRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof ContractRequestDTO)){
            return;
        }
        ContractRequestDTO dto = (ContractRequestDTO) target;

        Long id = dto.getId();
        Integer term = dto.getTerm();
        String taxCode = dto.getTaxCode();
        Double currentFee = dto.getCurrentFee();
        Double deposit = dto.getDeposit();
        String content = dto.getContent();
        String firebaseUrl = dto.getFireBaseUrl();
        Date startDate = dto.getStartDate();
        Date endDate = dto.getEndDate();

        String strStartDate = new SimpleDateFormat(("yyyy-MM-dd"), Locale.getDefault()).format(startDate);
        String strEndDate = new SimpleDateFormat(("yyyy-MM-dd"), Locale.getDefault()).format(endDate);
        Optional<ContractDetailsProjection> contract  = iContractRepository.contractById(id);
        // check term :
        if (term == null) {
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

        //check currentFee:
        if (currentFee == null ){
            errors.rejectValue("currentFee", "", "Phí hiện tại của mặt bằng không xác định ! ");
        }else if (currentFee <= 0) {
            errors.rejectValue("currentFee", "", "Phí hiện tại của mặt bằng phải lớn hơn 0 !");
        }
        //check deposit:
        if (deposit == null) {
            errors.rejectValue("deposit", "", "Vui lòng nhập tiền đặt cọc ! ");
        } else if (deposit <= 0) {
            errors.rejectValue("deposit", "", "Tiền đặt cọc ko bằng được bằng 0");
        }
        else if (deposit < (  currentFee * 10) / 100) {
            errors.rejectValue("deposit", "", "Tiền đặt cọc tối thiểu bằng 10% so với phí hiện tại ! ");
        }
        else if (deposit > (term * currentFee)){
            errors.rejectValue("deposit", "", "Tiền đặt cọc không được nhập lớn hơn tổng tiền  ");

        }
//        else if (deposit < (3 * currentFee)) {
//            errors.rejectValue("deposit", "", "Tiền đặt cọc tối thiểu phải bằng 3 tháng tiền thuê!");
//        }
        //check content:
        if (content == null || content.trim().equals("")) {
            errors.rejectValue("content", "", "Vui lòng nhập nội dung H/Đ !");
        } else if( content.length() > 250){
            errors.rejectValue("content", "", "Vui lòng nhập nội dung tối đa 250 kí tự !");
        }
        //check firebaseUrl
        if (firebaseUrl == null || firebaseUrl.trim().equals("")) {
            errors.rejectValue("fireBaseUrl", "", "Vui lòng cung cấp ảnh H/Đ !");
        }else  if(!firebaseUrl.matches("^(https?://)?(www\\.)?firebasestorage\\.googleapis\\.com(/.+)+$")){
            errors.rejectValue("fireBaseUrl", "", "Chỉ được cung cấp file ảnh đúng định dạng(jpg,gif hoặc png) !");
        }
        // check startDate :
        if (startDate == null) {
            errors.rejectValue("startDate", "", "Vui lòng chọn ngày bắt đầu !");
        }else if (!strStartDate.matches("(^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$)")){
            errors.rejectValue("startDate", "", "Vui lòng nhập ngày bắt đầu đúng định dạng (DD-MM-YYYY)!");
        }else if(startDate.before(contract.get().getStartDate())){
            errors.rejectValue("startDate", "", "Ngày bắt đầu ko dc nhỏ hơn ngày đã tạo hợp đồng");
        }

        //check endDate :
        LocalDate localDateEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateNew = LocalDate.parse(strStartDate).plusMonths(term);
        if (endDate == null) {
            errors.rejectValue("endDate", "", "Vui lòng chọn ngày kết thúc !");
        } else if ((localDateEndDate.getDayOfMonth() != endDateNew.getDayOfMonth())
                || (localDateEndDate.getMonthValue() != endDateNew.getMonthValue())
                || (localDateEndDate.getYear() != endDateNew.getYear())) {
            errors.rejectValue("endDate", "", "Ngày kết thúc tính toán không chính xác !");
        }else if (!strEndDate.matches("(^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$)")){
            errors.rejectValue("endDate", "", "Vui lòng nhập ngày kết thúc đúng định dạng (DD-MM-YYYY)!");
        }
    }

}
