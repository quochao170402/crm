package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.UserRepository;
import cybersoft.javabackend.java18.crm.repository.impl.UserRepositoryImpl;
import cybersoft.javabackend.java18.crm.service.AuthService;

public class AuthServiceImpl implements AuthService {
    private static AuthService instance;
    private final UserRepository userRepository;

    private AuthServiceImpl(){
        userRepository = UserRepositoryImpl.getInstance();
    }

    public static AuthService getInstance(){
        if (instance == null) instance = new AuthServiceImpl();
        return instance;
    }

    @Override
    public UserModel login(String email, String password) {
        UserModel userModel = userRepository.findByEmail(email);
        if (userModel.getPassword().equals(password)) return userModel;
        return null;
    }

    @Override
    public UserModel register(UserModel userModel) {
        if (userRepository.insert(userModel)) return userRepository.findByEmail(userModel.getEmail());
        return null;
    }
}
