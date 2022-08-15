package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.RoleModel;

import java.util.List;

public interface RoleRepository {

    List<RoleModel> findAll();

    boolean insert(RoleModel roleModel);

    boolean update(int id, RoleModel roleModel);

    boolean delete(int id);
}
