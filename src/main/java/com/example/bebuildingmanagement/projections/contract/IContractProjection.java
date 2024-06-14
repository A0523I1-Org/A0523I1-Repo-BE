package com.example.bebuildingmanagement.projections.contract;

public interface IContractProjection {
    Long getId();
    String getStartDate();
    String getEndDate();
    String getCustomerName();
    String getLandingCode();
}
