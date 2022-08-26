package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.UserModel;

import java.util.List;

public interface UserService {
    List<UserModel> findAll(int pageSize, int currentPage);

    boolean insert(UserModel user);

    UserModel findById(int id);

    boolean update(UserModel user);

    boolean delete(int id);
}
