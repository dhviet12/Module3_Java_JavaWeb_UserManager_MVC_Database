package service;

import model.User;

public interface IUserService extends IService<User>{
     boolean deleteUser(int id);
     void createUser(User user);
     User findByCountry(String country);

}
