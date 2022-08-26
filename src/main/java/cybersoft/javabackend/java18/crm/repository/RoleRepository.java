package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.RoleModel;

import java.util.List;

public interface RoleRepository {

    List<RoleModel> findAll();

    RoleModel findById(int id);

    boolean insert(RoleModel roleModel);

    boolean update(RoleModel roleModel);

    boolean deleteById(int id);

    List<RoleModel> findAll(int pageSize, int currentPage);
}
