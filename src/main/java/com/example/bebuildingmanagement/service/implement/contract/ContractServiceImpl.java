package com.example.bebuildingmanagement.service.implement.contract;
import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import com.example.bebuildingmanagement.dto.response.mail.DataMailDTO;
import com.example.bebuildingmanagement.entity.*;
import com.example.bebuildingmanagement.exception.ResourceNotFoundException;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.projections.contract.IContractProjection;
import com.example.bebuildingmanagement.projections.employee.IEmployeeInfoProjection;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.landing.ILandingRepository;
import com.example.bebuildingmanagement.repository.contract.IContractRepository;
import com.example.bebuildingmanagement.repository.customer.ICustomerRepository;
import com.example.bebuildingmanagement.repository.employee.IEmployeeRepository;

import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import com.example.bebuildingmanagement.service.interfaces.contract.IContractService;

import com.example.bebuildingmanagement.service.interfaces.mail.IMailService;
import com.example.bebuildingmanagement.constants.ContractConst;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractServiceImpl implements IContractService {
    IContractRepository iContractRepository;
    IAccountRepository iAccountRepository;
    IEmployeeRepository iEmployeeRepository;
    IMailService iMailService;
    ICustomerRepository iCustomerRepository;
    ILandingRepository iLandingRepository;
    IAccountService iAccountService;

    //anh lq
    @Override
    public ContractDetailsProjection contractById(Long id) {
      return  iContractRepository.contractById(id).orElseThrow(() -> new ResourceNotFoundException("Hợp đồng " + id+ " không tồn tại "));
    }
    @Override
    public void updateContractById(ContractRequestDTO contractDTO, Long id) {
        iContractRepository.contractById(id).orElseThrow(() -> new ResourceNotFoundException("Hợp đồng " + id+ " không tồn tại "));
        iContractRepository.updateContractById(
                contractDTO.getContent(), contractDTO.getDeposit(), contractDTO.getStartDate(),contractDTO.getEndDate(),
                contractDTO.getFireBaseUrl(), contractDTO.getTaxCode(),contractDTO.getTerm(),contractDTO.getCurrentFee(),id);
    }

    @Override
    public void deleteContractById(Long id) {
        iContractRepository.contractById(id).orElseThrow(() -> new ResourceNotFoundException("Hợp đồng " + id+ " không tồn tại "));
        Long landingId =  iContractRepository.queryLandingId(id);
        iContractRepository.deleteContractById(id,landingId);
        // set mb ve null
        iContractRepository.updateContractByLanding(id);
    }
    //anh lq
// Danh Sách Hợp Đồng : (Hoài NT)
    @Override
        public Page<ContractResponseDTO> getContracts(Optional<Integer> page, String customerName, String landingCode, String startDate, String endDate,String fieldSort) {
            // lấy username đang đăng nhập :
            String username = iAccountService.getCurrentAccount().getUsername();
            // lay thong tin account:
            Account account = iAccountRepository.findByUsername(username).orElseThrow();
            Pageable pageable;
         // sắp xếp :
        if (fieldSort.trim().equals("") || fieldSort == null){
            // khi không có field thì mặc định sắp xếp theo id mới nhất :
            pageable = PageRequest.of(page.orElse(0), 4,Sort.by(Sort.Direction.DESC,"id"));
        }else {
            // khi có field thì sắp xếp theo field :
                pageable = PageRequest.of(page.orElse(0), 4,Sort.by(Sort.Direction.ASC,fieldSort));
        }

            Page<IContractProjection> contractProjections = null ;
            Page<ContractResponseDTO> contractResponseDTOS ;
            // set lại date để thực hiện search khi date k có giá trị :
            if (startDate==""){
                startDate = null;
            }else if (endDate == ""){
                endDate = null;
            }
            if ((startDate == null && endDate == "") || (startDate == "" && endDate == null)){
                startDate = null;
                endDate = null;

            }
            // check role :
            for (Role role: account.getRoles()
                 ) {
                if (role.getName().equals("ADMIN")){
                    // nếu ADMIN  lấy tất cả danh sách hợp đồng :
                        contractProjections = iContractRepository.getContracts(pageable,"%"+customerName.trim()+"%","%"+landingCode.trim()+"%",startDate,endDate);
                        break;
                }else {
                    // nếu USER chỉ lấy ra danh sách mà nhân viên đó đã tạo hợp đồng :
                    contractProjections = iContractRepository.getContractsByEmployeeUsername(pageable,username,"%"+customerName.trim()+"%","%"+landingCode.trim()+"%",startDate,endDate);
                }
            }
            //convert về DTO:
            contractResponseDTOS = contractProjections.map(
                    contractProjection -> {
                        ContractResponseDTO contractResponseDTO = ContractResponseDTO.builder()
                                .id(contractProjection.getId())
                                .startDate(contractProjection.getStartDate())
                                .endDate(contractProjection.getEndDate())
                                .customerName(contractProjection.getCustomerName())
                                .landingCode(contractProjection.getLandingCode())
                                .build();
                        return contractResponseDTO;
                    });
            return contractResponseDTOS;
        }
    // Thêm Mới Hợp Đồng : (Hoài NT)
    @Override
    public void createContract(ContractNewRequestDTO contractNewRequestDTO) {
        // Kiểm tra xem mặt bằng đã làm hợp đồng chưa  :
        if (iContractRepository.existsByLandingId(contractNewRequestDTO.getLandingId())){
            throw new  RuntimeException(ContractConst.ERROR_MESSAGE.LANDING_ALREADY_EXIST);
        }
        // lấy username đang đăng nhập :
        String username = iAccountService.getCurrentAccount().getUsername();
        // lấy dữ liệu employee để insert employeeId vào contract;
        IEmployeeInfoProjection employee = iEmployeeRepository.getEmployeeByUsername(username) ;

        // đẩy dữ liệu lên repo để thực hiện query insert :
         iContractRepository.createContract(
                contractNewRequestDTO.getTerm(),contractNewRequestDTO.getStartDate(),
                contractNewRequestDTO.getEndDate(),contractNewRequestDTO.getTaxCode(),
                contractNewRequestDTO.getCurrentFee(),
                contractNewRequestDTO.getDeposit(),contractNewRequestDTO.getFirebaseUrl(),
                contractNewRequestDTO.getContent(),contractNewRequestDTO.getLandingId(),
                contractNewRequestDTO.getCustomerId(),employee.getId()
        ) ;
         // set lại mặt bằng  đã tạo hợp đồng thành đã thuê sau khi thêm thành công :
            iLandingRepository.setLandingIsAvailableFalse(contractNewRequestDTO.getLandingId());
    }
    // gửi mail (Hoài NT)
    @Override
    public void sendMailToCustomer(ContractNewRequestDTO contract) {
        // format date để hiển thị cho khách hàng
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String startDateFM = df.format(contract.getStartDate());
        String endDateFM = df.format(contract.getEndDate());

        // lay username đang đăng nhập
        String username = iAccountService.getCurrentAccount().getUsername();
        // lấy 1 số dữ liệu của employee để gửi mail cho khách :
        IEmployeeInfoProjection employee = iEmployeeRepository.getEmployeeByUsername(username) ;
        // lấy 1 số thông tin của khách hàng để gửi mail :
        Customer customer = iCustomerRepository.findById(contract.getCustomerId())
                .orElseThrow(()-> new RuntimeException(ContractConst.ERROR_MESSAGE.CUSTOMER_NOT_FOUNT));
        DataMailDTO dataMail = new DataMailDTO();
        dataMail.setToEmail(customer.getEmail());
        dataMail.setSubject(ContractConst.SEND_MAIL_SUBJECT.CLIENT_REGISTER);
        // trả dữ liệu ve thymelef để hiên thị :
        Map<String,Object> props = new HashMap<>();
        props.put("customerName",customer.getName());
        props.put("startDate",startDateFM);
        props.put("endDate",endDateFM);
        props.put("term",contract.getTerm());
        props.put("employeeName",employee.getName());
        props.put("phone",employee.getPhone());
        props.put("email",employee.getEmail());
        dataMail.setProps(props);
        try {
            iMailService.sendMail(dataMail, ContractConst.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
        } catch (MessagingException e) {
            throw new RuntimeException(ContractConst.ERROR_MESSAGE.MAIL_SENDING_FAILED);
        }
    }
}

