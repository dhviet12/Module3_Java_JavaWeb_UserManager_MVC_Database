package service;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService{
    protected Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanager",
                    "root",
                    "121097");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public List<User> findAll() {
       List<User> userList = new ArrayList<>();
       Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                userList.add(new User(id,name,email,country));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    @Override
    public User findByID(int id) {
        User user= null;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                user = new User(id,name,email,country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        Connection connection = getConnection();
        boolean check = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name= ?, email= ?, country= ? where id= ?");
            preparedStatement.setInt(4,user.getId());
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3,user.getCountry());
            check = preparedStatement.executeUpdate() >0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return check;
    }

    @Override
    public boolean deleteUser(int id) {
        boolean delete= false;
        Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement= connection.prepareStatement("delete from users where id= ?");
            preparedStatement.setInt(1,id);
            delete = preparedStatement.executeUpdate()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return delete;
    }

    @Override
    public void createUser(User user) {
        Connection connection = getConnection();
        try {
            // cach 1:
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" + "  (name, email, country) VALUES " + " (?, ?, ?);");
//            preparedStatement.setString(1,user.getName());
//            preparedStatement.setString(2,user.getEmail());
//            preparedStatement.setString(3,user.getCountry());
//            preparedStatement.executeUpdate();

            // cach 2:
            CallableStatement callableStatement = connection.prepareCall("{call add_user(?,?,?)}");
            callableStatement.setString(1, user.getName());
            callableStatement.setString(2, user.getEmail());
            callableStatement.setString(3, user.getCountry());
            callableStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> findByCountry(String country) {
        Connection connection = getConnection();
        List<User> userCountry = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where country= ?;");
            preparedStatement.setString(1,country);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                userCountry.add(new User(name,email,country));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userCountry;
    }

    @Override
    public void addUserTransaction(User user, int[] permision) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            // insert user
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" + "(name, email, country) VALUES " + " (?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            int rowAffected = preparedStatement.executeUpdate();
            // get user id
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int userId = 0;
            if(resultSet.next()){
                resultSet.getInt(1);
            }
            // assign permission to user
            if(rowAffected ==1){
                PreparedStatement preparedStatement1 =connection.prepareStatement("INSERT INTO user_permission(user_id,permission_id) "+ "VALUES(?,?)");
                for (int permissionId : permision){
                    preparedStatement1.setInt(1,userId);
                    preparedStatement1.setInt(2,permissionId);
                    preparedStatement1.executeUpdate();
                }
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
