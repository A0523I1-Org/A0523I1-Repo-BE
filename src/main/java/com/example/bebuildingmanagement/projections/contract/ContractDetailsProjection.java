package com.example.bebuildingmanagement.projections.contract;

import java.util.Date;

public interface ContractDetailsProjection {
    Long getId();
    String getCode();
    String getCustomerName();
    String getEmployeeName();
    String getContent();
    double getDeposit();
    String getDescription();
    Date getStartDate();
    double getFeePerMouth();
    Date getEndDate();
    String getFireBaseUrl();
    String getTaxCode();
    int getTerm();

}
