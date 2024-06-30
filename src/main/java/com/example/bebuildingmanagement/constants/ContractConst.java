package com.example.bebuildingmanagement.constants;

public class ContractConst {
    public final static  class SEND_MAIL_SUBJECT {
        public  final static  String CLIENT_REGISTER =
                "XÁC NHẬN TẠO HỢP ĐỒNG";
    }

    public final static class  TEMPLATE_FILE_NAME  {
        public final static  String CLIENT_REGISTER = "sendMail";

    }

    public final static class QUERY {
        public final static String SELECT_CONTRACTS_BY_EMPLOYEE_USERNAME = "select " +
                " DATE_FORMAT(start_date, '%d/%m/%Y') as startDate,"+
                " DATE_FORMAT(end_date, '%d/%m/%Y') as endDate,"+
                " c.id as id, " +
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
                "where c.is_deleted = 0 " +
                " and ac.username = ?1  " +
                "and cus.name like ?2 " +
                "and l.code like ?3 " +
                "and  (?4 IS NULL OR c.start_date >= ?4) " +
                "and  (?5 IS NULL OR c.end_date <= ?5) " ;

        public final static String COUNT_ALL_CONTRACT = "select " +
                " count(*)" +
                "from contract as c " +
                "left join customer as cus "+
                "on c.customer_id = cus.id "+
                "left join landing as l "+
                "on c.landing_id = l.id "+
                "left join employee as e "+
                "on c.employee_id = e.id "+
                "where c.is_deleted = 0 " +
                "and cus.name like ?1 " +
                "and l.code like ?2 " +
                "and  (?3 IS NULL OR c.start_date >= ?3) " +
                "and  (?4 IS NULL OR c.end_date <= ?4)" ;
        public final static String COUNT_CONTRACT_BY_USERNAME =
                        "select " +
                        "count(*) " +
                        "from contract as c " +
                        " join customer as cus "+
                        "on c.customer_id = cus.id "+
                        " join landing as l "+
                        "on c.landing_id = l.id "+
                        " join employee as e "+
                        "on c.employee_id = e.id "+
                        " join account as ac " +
                        "on e.account_id = ac.id " +
                        "where c.is_deleted = 0 " +
                        " and ac.username = ?1  " +
                        "and cus.name like ?2 " +
                        "and l.code like ?3 " +
                        "and  (?4 IS NULL OR c.start_date >= ?4) " +
                        "and  (?5 IS NULL OR c.end_date <= ?5) ";
        public final static String SELECT_ALL_CONTRACT = "select " +
                " DATE_FORMAT(start_date, '%d/%m/%Y') as startDate,"+
                " DATE_FORMAT(end_date, '%d/%m/%Y') as endDate,"+
                " c.id as id, " +
                "cus.name as customerName,"+
                "l.code as landingCode "+
                "from contract as c " +
                "left join customer as cus "+
                "on c.customer_id = cus.id "+
                "left join landing as l "+
                "on c.landing_id = l.id "+
                "left join employee as e "+
                "on c.employee_id = e.id "+
                "where c.is_deleted = 0 " +
                "and cus.name like ?1 " +
                "and l.code like ?2 " +
                "and  (?3 IS NULL OR c.start_date >= ?3) " +
                "and  (?4 IS NULL OR c.end_date <= ?4) " ;

        public final static String INSERT_CONTRACT = "INSERT INTO contract( " +
                " term, start_date, end_date ," +
                " tax_code,current_fee," +
                " deposit,firebase_url,content," +
                " landing_id, customer_id, employee_id )" +
                " VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11)";
        public final static String UPDATE_CONTRACT = " UPDATE contract SET content = :content, deposit = :deposit, start_date = :startDate,end_date = :endDate, firebase_url =  :firebaseUrl,tax_code = :taxCode,term = :term, current_fee = :currentFee WHERE id = :id";
        public final static String DELETE_CONTRACT =  " UPDATE contract c " +
                " INNER JOIN landing l ON c.landing_id = l.id " +
                " SET c.is_deleted = 1, l.is_available = 1 " +
                " WHERE c.id = :contractId AND l.id =  :landingId ;";
        public final static String LANDING_ID =  "SELECT landing_id FROM contract where id = :contractId";
        public final static String SELECT_CONTRACT_BY_ID = "SELECT cont.id,land.code as code,cus.name as customerName,emp.name as employeeName,cont.content as content,cont.deposit as deposit,cont.start_date as startDate, cont.current_fee as FeePerMouth,cont.end_date as endDate,cont.firebase_url as firebaseUrl,cont.tax_code as taxCode,cont.term  as term" +
                " FROM Contract cont " +
                " join landing land on cont.landing_id = land.id " +
                " join customer cus on cont.customer_id = cus.id " +
                " join employee emp on cont.employee_id = emp.id " +
                " where cont.id = ?1 ";
        public static final String SELECT_CONTRACT_BY_LANDING_ID =
                            " select start_date as startDate,"  +
                                " end_date as endDate," +
                                " term as term " +
                                " from contract " +
                                 " where landing_id = ?1";
        public static final String UPDATE_CONTRAC_IS_LANDING =
                            " update contract " +
                           " set landing_id = NULL " +
                            " where id = ?1 ";
    }


    public final static class ERROR_MESSAGE {
        public final static String TERM_NOT_BLANK = "Vui lòng nhập kì hạn ! " ;
        public final static String TERM_MIN = "Kì hạn thuê tối thiểu 1 tháng !" ;
        public final static String TERM_MAX = "Kì hạn thuê tối đa không quá 10 năm !" ;
        public final static String TAX_CODE_NOT_BLANK = "Vui lòng nhập mã số thuế !";
        public final static String TAX_CODE_FORMAT = "Vui lòng nhập đúng định dạng mã số thuế ! (10 chữ số)";
        public final static String CURRENT_FEE_NOT_BLANK = "Phí hiện tại của mặt bằng không xác định !";
        public final static String CURRENT_FEE_ILLEGAL = "Phí hiện tại của mặt bằng không hợp lệ !";
        public final static String DEPOSIT_NOT_BLANK = "Vui lòng nhập tiền đặt cọc ! ";
        public final static String DEPOSIT_ILLEGAL = "Tiền đặt cọc không hợp lệ";
        public final static String DEPOSIT_MIN = "Tiền đặt cọc tối thiểu bằng 10% so với phí hiện tại ! ";
        public final static String FIREBASE_NOT_BLANK = "Vui lòng cung cấp ảnh H/Đ !";
        public final static String FIREBASE_FORMAT = "Vui lòng cung cấp url ảnh đúng định dạng (https://xxx/xx/xx) !";
        public final static String CONTENT_NOT_BLANK = "Vui lòng nhập nội dung H/Đ !";
        public final static String CONTENT_MIN = "Vui lòng nhập nội dung tối thiểu 50 kí tự !";
        public final static String LANDING_NOT_BLANK = "Vui lòng cung cấp mặt bằng !";
        public final static String LANDING_ILLEGAL = "Mặt bằng không hợp lệ ! ";
        public final static String CUSTOMER_NOT_BLANK = "Vui lòng chọn khách hàng !";
        public static final String CUSTOMER_NOT_FOUND = "Không tìm thấy khách hàng ! ";
        public static final String START_DATE_NOT_BLANK ="Vui lòng chọn ngày bắt đầu !" ;

        public static final String START_DATE_AFTER = "Ngày bắt đầu phải sau hoặc bằng ngày hiện tại (ngày tương lai) !";
        public static final String START_DATE_FORMAT = "Vui lòng nhập ngày bắt đầu đúng định dạng (yyyy-MM-dd)!";
        public static final String END_DATE_NOT_BLANK = "Vui lòng chọn ngày kết thúc !";
        public static final String END_DATE_COMPARED_TO_TERM = "Ngày kết thúc phải sau ngày bắt đầu bằng đúng số tháng của kì hạn !";
        public static final String END_DATE_FORMAT = "Vui lòng nhập ngày kết thúc đúng định dạng (yyyy-MM-dd)!";
        public static final String LANDING_ALREADY_EXIST = "Mặt bằng này đã làm hợp đồng  !";
        public static final String CUSTOMER_NOT_FOUNT = "Không tìm thấy khách hàng !";
        public static final String MAIL_SENDING_FAILED = "Gửi mail thất bại !";
        public static final String CONFIRM_PASSWORD_FALSE = "Mật khẩu không chính xác !";
        public static final String PAGE_IS_EMPTY = "Vui lòng nhập page !";
    }

    public final static class SUCCESS_MESSAGE {
        public static final String ADD_NEW_CONTRACT = "Thêm mới hợp đồng thành công !";


        
        public static final String PAGE_NOT_NEGATIVE = "Giá trị page không được nhỏ hơn 0 !";
        public static final String SEND_MAIL = "Gửi Mail thành công !";
    }
}


