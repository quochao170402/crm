package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.repository.RoleRepository;
import cybersoft.javabackend.java18.crm.repository.impl.RoleRepositoryImpl;
import cybersoft.javabackend.java18.crm.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private static RoleService roleService = null;
    private final RoleRepository roleRepository;

    private RoleServiceImpl() {
        roleRepository = RoleRepositoryImpl.getRepository();
    }

    public static RoleService getService() {
        if (roleService == null) roleService = new RoleServiceImpl();
        return roleService;
    }

    @Override
    public List<RoleModel> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public boolean insert(RoleModel roleModel) {
        return roleRepository.insert(roleModel);
    }

    @Override
    public boolean update(int id, RoleModel roleModel) {
        return roleRepository.update(id, roleModel);
    }

    @Override
    public boolean delete(int id) {
        return roleRepository.delete(id);
    }
}
