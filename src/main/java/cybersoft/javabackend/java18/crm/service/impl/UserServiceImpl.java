package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.UserRepository;
import cybersoft.javabackend.java18.crm.repository.impl.UserRepositoryImpl;
import cybersoft.javabackend.java18.crm.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserService userService = null;
    private final UserRepository userRepository;

    private UserServiceImpl() {
        userRepository = UserRepositoryImpl.getInstance();
    }

    public static UserService getService() {
        if (userService == null) userService = new UserServiceImpl();
        return userService;
    }

    @Override
    public List<UserModel> findAll(int pageSize, int currentPage) {
        return userRepository.findAll(pageSize, currentPage);
    }

    @Override
    public boolean insert(UserModel user) {
        if (user == null) return false;
        return userRepository.insert(user);
    }

    @Override
    public UserModel findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean update(UserModel user) {
        if (user == null) return false;
        return userRepository.update(user);
    }

    @Override
    public boolean delete(int id) {
        return userRepository.delete(id);
    }
}
