package com.example.bebuildingmanagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeReqDTO implements Validator {
    //    @Size(min = 5, max = 45, message = "Enter only 5 to 45 characters")
    //    private String firstname;
    //    @Size(min = 5, max = 45, message = "Enter only 5 to 45 characters")
    //    private String lastname;
    //    @Pattern(regexp = "^0\\d{9,10}$")
    //    private String phoneNumber;
    //    @Min(value = 18)
    //    private int age;
    //    @Pattern(regexp = "^[A-Za-z0-9]+@gmail.com$")
    //    private String email;


    //    @PostMapping("/add")
    //    public ModelAndView add(@Validated @ModelAttribute User user, BindingResult bindingResult) {
    //        if (bindingResult.hasErrors()) {
    //            return new ModelAndView("formCreate", "user", user);
    //        }
    String code;
    String name;
    Date dob;
    String gender;
    String address;
    String phone;
    String email;
    Date workDate;
    String firebaseUrl;
    Long department;
    Long salaryRank;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
    //    @Override
    //    public void validate(Object target, Errors errors) {
    //        StudentDTO studentDTO = (StudentDTO) target;
    //        String email = studentDTO.getEmail();
    //        if ("".equals(email)) {
    //            errors.rejectValue("email", "", "Can not be empty");
    //        } else if (email.length() < 5) {
    //            errors.rejectValue("email", "email.min");//email.min = > 5 characters
    //        }
    //    }


    //    @PostMapping("/add")
    //    public String addNewStudent(@Valid @ModelAttribute("student") StudentDTO student,
    //                                BindingResult bindingResult){
    //        new StudentDTO().validate(student,bindingResult);
    //        if(bindingResult.hasErrors()){
    //            return "/create";
    //        }
}
