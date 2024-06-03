package com.example.bebuildingmanagement.utils;

public class Const {
    public final static  class SEND_MAIL_SUBJECT {
        public  final static  String CLIENT_REGISTER = "XÁC NHẬN TẠO HỢP ĐỒNG";
    }

    public final static class  TEMPLATE_FILE_NAME  {
        public final static  String CLIENT_REGISTER = "sendMail";

    }

    public final static class CONTRACT_QUERY {
        public final static String SELECT_CONTRACTS_BY_EMPLOYEE_USERNAME = "select " +
                "c.start_date as startDate,"+
                "c.end_date as endDate,"+
                "cus.name as customerName,"+
                "l.code as landingCode "+
                "from contract as c " +
                " join customer as cus "+
                "on c.customer_id = cus.id "+
                " join landing as l "+
                "on c.landing_id = l.id "+
                " join employee as e "+
                "on c.employee_id = e.id "+
                " join account as ac " +
                "on e.account_id = ac.id " +
                "where c.is_deleted = 0 and ac.username = ?1 ";
        public final static String COUNT_CONTRACT = "select count(*) from contract" ;
        public final static String SELECT_ALL_CONTRACT = "select " +
                "c.start_date as startDate,"+
                "c.end_date as endDate,"+
                "cus.name as customerName,"+
                "l.code as landingCode "+
                "from contract as c " +
                "left join customer as cus "+
                "on c.customer_id = cus.id "+
                "left join landing as l "+
                "on c.landing_id = l.id "+
                "left join employee as e "+
                "on c.employee_id = e.id "+
                "where c.is_deleted = 0 ";
        public final static String INSERT_CONTRACT = "INSERT INTO contract( " +
                " term, start_date, end_date ," +
                " tax_code,current_fee,description," +
                " deposit,firebase_url,content," +
                " landing_id, customer_id, employee_id )" +
                " VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)";
    }

    public final static class EMPLOYEE_QUERY {
        public final static String SELECT_EMPLOYEE_BY_USERNAME = "select e.id ," +
                " e.name  ," +
                " e.phone ," +
                "e.email " +
                " from employee as e " +
                " join account as a " +
                " on e.account_id = a.id " +
                " where username = ?1 ";
    }

}
