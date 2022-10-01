package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.UserModel;

public interface AuthService {
    UserModel login(String email, String password);
    UserModel register(UserModel userModel);
}
