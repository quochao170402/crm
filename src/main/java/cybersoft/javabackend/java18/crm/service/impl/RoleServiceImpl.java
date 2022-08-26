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
    public boolean update(RoleModel roleModel) {
        return roleRepository.update(roleModel);
    }

    @Override
    public boolean delete(int id) {
        return roleRepository.deleteById(id);
    }

    @Override
    public RoleModel findById(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public List<RoleModel> findAll(int pageSize, int currentPage) {
        return roleRepository.findAll(pageSize, currentPage);
    }
}
