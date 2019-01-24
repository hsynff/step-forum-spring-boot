package com.step.forum.spring.repository;

import com.step.forum.spring.model.Role;
import com.step.forum.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String GET_USER_BY_EMAIL_SQL = "select * from user u inner join role r on u.id_role=r.id_role where u.email = ?";
    private final String INSERT_NEW_USER_SQL = "insert into user(email, password, token, status, id_role, first_name, last_name, img) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_STATUS_BY_TOKEN = "update user set token=?, status=? where token=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(INSERT_NEW_USER_SQL, user.getEmail(), user.getPassword(), user.getToken(), user.getStatus(), user.getRole().getId(), user.getFirstName(), user.getLastName(), user.getImagePath());
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_SQL, new Object[]{email}, new RowMapper<User>() {
            @Nullable
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id_user"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setImagePath(rs.getString("img"));
                Role role = new Role();
                role.setId(rs.getInt("id_role"));
                role.setRoleType(rs.getString("role_type"));
                user.setRole(role);
                return user;
            }
        });
    }

    @Override
    public void updateUserStatusByToken(String token) {
        String newToken = UUID.randomUUID().toString();
        int status  = 1;
        int affectedRows = jdbcTemplate.update(UPDATE_USER_STATUS_BY_TOKEN, newToken, status, token);
        if (affectedRows==0){
            throw new IllegalArgumentException("Token is invalid");
        }
    }

}
