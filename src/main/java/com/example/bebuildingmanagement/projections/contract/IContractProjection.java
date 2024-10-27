package com.example.bebuildingmanagement.projections.contract;
//  projection hứng dữ liệu để lấy info hợp đồng : (Hoài NT)
public interface IContractProjection {
    Long getId();
    String getStartDate();
    String getEndDate();
    String getCustomerName();
    String getLandingCode();
}
