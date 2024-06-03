package com.example.bebuildingmanagement.service.implement.contract;
import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import com.example.bebuildingmanagement.dto.response.mail.DataMailDTO;
import com.example.bebuildingmanagement.entity.*;
import com.example.bebuildingmanagement.exception.ResourceNotFoundException;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.projections.contract.IContractProjection;
import com.example.bebuildingmanagement.projections.contract.IEmployeeProjection;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.contract.IContractRepository;
import com.example.bebuildingmanagement.repository.ICustomerRepository;
import com.example.bebuildingmanagement.repository.IEmployeeRepository;
import com.example.bebuildingmanagement.service.interfaces.contract.IContractService;
import com.example.bebuildingmanagement.service.interfaces.mail.IMailService;
import com.example.bebuildingmanagement.utils.Const;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractServiceImpl implements IContractService {
    IContractRepository iContractRepository;
    IAccountRepository iAccountRepository;
    IEmployeeRepository iEmployeeRepository;
    IMailService iMailService;
    ICustomerRepository iCustomerRepository;

    //anh lq
    @Override
    public ContractDetailsProjection contractById(Long id) {
        return iContractRepository.contractById(id).orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
    }


    @Override
    public void updateContractById(ContractRequestDTO contractDTO, Long id) {
        iContractRepository.contractById(id).orElseThrow(() -> new RuntimeException("Contract not found"));

        iContractRepository.updateContractById(contractDTO.getContent(),
                contractDTO.getDeposit(),contractDTO.getDescription(),contractDTO.getStartDate(),contractDTO.getEndDate(),
                contractDTO.getFirebaseUrl(), contractDTO.getTaxCode(),contractDTO.getTerm(),id);

    }
//anh lq
    @Override
    public void deleteContractById(Long id) {
        iContractRepository.contractById(id).orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        iContractRepository.deleteContractById(id);
    }

        public Page<ContractResponseDTO> getContracts (Optional<Integer> page) {
            // lấy username đang đăng nhập :
            String username = "TranThiB";
            // lay thong tin account:
            Account account = iAccountRepository.findByUsername(username);
            Pageable pageable = PageRequest.of(page.orElse(0), 3);
            Page<IContractProjection> contractProjections = null ;
            Page<ContractResponseDTO> contractResponseDTOS ;

            // check role :
            for (Role role: account.getRoles()
                 ) {
                if (role.getName().equals("ADMIN")){
                    // lấy tất cả danh sách hợp đồng
                    contractProjections = iContractRepository.getContracts(pageable);
                    break;

                }else {
                    // chỉ lấy ra danh sách nhân viên đã tạo hợp đồng :
                    contractProjections = iContractRepository.getContractByEmployeeUsername(pageable,username);

                }
            }
            //convert về DTO:
            contractResponseDTOS = contractProjections.map(
                    contractProjection -> {
                        ContractResponseDTO contractResponseDTO = ContractResponseDTO.builder()
                                .startDate(contractProjection.getStartDate())
                                .endDate(contractProjection.getEndDate())
                                .customerName(contractProjection.getCustomerName())
                                .landingCode(contractProjection.getLandingCode())
                                .build();
                        return contractResponseDTO;
                    });


            return contractResponseDTOS;

        }

    @Override
    public void createContract(ContractNewRequestDTO contractNewRequestDTO) {
        // Kiểm tra xem mặt bang đã làm hợp đồng chưa .
        if (iContractRepository.existsByLandingId(contractNewRequestDTO.getLandingId())){
            throw new  RuntimeException("Mặt bằng này đã làm hợp đồng,  chọn mặt bằng khác !");
        }
        // lấy username đang đăng nhập
        String username = "TranThiB";
        // lấy 1 số dữ liệu employee để send mail và insert employeeId vào contract;
        IEmployeeProjection employee = iEmployeeRepository.getEmployeeByUsername(username) ;

        // đẩy dữ liệu lên repo để thực hiện query insert :
         iContractRepository.createContract(
                contractNewRequestDTO.getTerm(),contractNewRequestDTO.getStartDate(),
                contractNewRequestDTO.getEndDate(),contractNewRequestDTO.getTaxCode(),
                contractNewRequestDTO.getCurrentFee(),contractNewRequestDTO.getDescription(),
                contractNewRequestDTO.getDeposit(),contractNewRequestDTO.getFirebaseUrl(),
                contractNewRequestDTO.getContent(),contractNewRequestDTO.getLandingId(),
                contractNewRequestDTO.getCustomerId(),employee.getId()
        ) ;

        // gui mail :

            Customer customer = iCustomerRepository.findById(contractNewRequestDTO.getCustomerId())
                    .orElseThrow(()-> new RuntimeException("Không tìm thấy khách hàng !"));
            DataMailDTO dataMail = new DataMailDTO();
            dataMail.setToEmail(customer.getEmail());
            dataMail.setSubject(Const.SEND_MAIL_SUBJECT.CLIENT_REGISTER);
            // trả dữ liệu ve thymelef để hiên thị
            Map<String,Object> props = new HashMap<>();
            props.put("customerName",customer.getName());
            props.put("startDate",contractNewRequestDTO.getStartDate());
            props.put("endDate",contractNewRequestDTO.getEndDate());
            props.put("term",contractNewRequestDTO.getTerm());
            props.put("employeeName",employee.getName());
            props.put("phone",employee.getPhone());
            props.put("email",employee.getEmail());


            dataMail.setProps(props);
        try {
            iMailService.sendMail(dataMail,Const.TEMPLATE_FILE_NAME.CLIENT_REGISTER);
        } catch (MessagingException e) {
            throw new RuntimeException("Gửi mail thất bại !");
        }


    }
}

