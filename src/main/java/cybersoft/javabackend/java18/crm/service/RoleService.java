package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.RoleModel;

import java.util.List;

public interface RoleService {
    List<RoleModel> findAll();

    boolean insert(RoleModel roleModel);

    boolean update(int id, RoleModel roleModel);

    boolean delete(int id);
}
