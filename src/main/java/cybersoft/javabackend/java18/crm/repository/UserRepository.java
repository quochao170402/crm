package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.UserModel;

import java.util.List;

public interface UserRepository {

    List<UserModel> findAll();

    boolean insert(UserModel user);

    UserModel findById(int id);

    boolean update(UserModel user);

    List<UserModel> findAll(int pageSize, int currentPage);

    boolean delete(int id);

    List<UserModel> findByRole(int id);

    UserModel findByEmail(String email);
}
