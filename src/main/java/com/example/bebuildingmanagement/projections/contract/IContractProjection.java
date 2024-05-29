package com.example.bebuildingmanagement.projections.contract;

import java.util.Date;

public interface IContractProjection {
    Date getStartDate();
    Date getEndDate();
    String getCustomerName();
    String getLandingCode();
}
