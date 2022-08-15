package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.UserModel;

import java.util.List;

public interface UserRepository {

    List<UserModel> findAll();
}
