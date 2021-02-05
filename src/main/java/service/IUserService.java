package service;

import model.User;

import java.util.List;

public interface IUserService extends IService<User>{
     boolean deleteUser(int id);
     void createUser(User user);
     List<User> findByCountry(String country);

}
