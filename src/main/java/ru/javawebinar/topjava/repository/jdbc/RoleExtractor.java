package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class RoleExtractor implements ResultSetExtractor<Map<Integer, List<Role>>> {
    @Override
    public Map<Integer, List<Role>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        Map<Integer, List<Role>> data = new HashMap<>();
        while (rs.next()) {
            Integer user_id = rs.getInt("user_id");
            data.putIfAbsent(user_id, new ArrayList<>());
            String role = rs.getString("role");
            data.get(user_id).add(Role.valueOf(role));
        }
        return data;
    }
}

