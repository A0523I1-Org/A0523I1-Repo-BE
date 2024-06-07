package com.example.bebuildingmanagement.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomCodeGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "OE-";
        String query = "SELECT MAX(id) FROM employee";
        Connection connection;
        try {
            connection = session.getJdbcConnectionAccess().obtainConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String lastId = rs.getString(1);
                if (lastId != null) {
                    // Chỉ lấy phần số từ ID cuối cùng
                    long number = Long.parseLong(lastId.substring(prefix.length())) + 1;
                    // Đảm bảo rằng số ID có 4 chữ số
                    String paddedNumber = String.format("%04d", number);
                    return prefix + paddedNumber;
                }
            }
        } catch (SQLException e) {
            throw new HibernateException("Unable to generate ID", e);
        }

        // Nếu không có ID nào tồn tại, trả về giá trị mặc định OE-0100
        return prefix + "0100";
    }
}
