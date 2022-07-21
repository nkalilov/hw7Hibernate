package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        try(Connection connect = Util.connection();
            Statement statement = connect.createStatement()) {
            statement.executeUpdate("create table if not exists users(id serial primary key," +
                    "name varchar not null," +
                    "last_name varchar not null," +
                    "age smallint not null)");
            System.out.println("Table created...");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate("drop table if exists users");
            System.out.println("Table dropped...");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users(name, last_name, age) values(?,?,?)";
        try(Connection connection = Util.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User " + name + " added");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try(Connection connection = Util.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("User successfully deleted...");
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "select * from users";
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "delete from users";
        try(Connection connection = Util.connection();
            Statement statement = connection.createStatement()) {
            System.out.println("All users deleted...");
            statement.executeQuery(sql);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}