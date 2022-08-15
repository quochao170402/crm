package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserService userService = null;

    private UserServiceImpl() {

    }

    public static UserService getService() {
        if (userService == null) userService = new UserServiceImpl();
        return userService;
    }

    @Override
    public List<UserModel> findAll() {
        return null;
    }
}
