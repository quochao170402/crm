package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.StatusModel;
import cybersoft.javabackend.java18.crm.repository.StatusRepository;
import cybersoft.javabackend.java18.crm.repository.impl.StatusRepositoryImpl;
import cybersoft.javabackend.java18.crm.service.StatusService;

import java.util.List;

public class StatusServiceImpl implements StatusService {
    private static StatusService service;

    private final StatusRepository statusRepository;

    private StatusServiceImpl() {
        statusRepository = StatusRepositoryImpl.getStatusRepository();

    }

    public static StatusService getService() {
        if (service == null) service = new StatusServiceImpl();
        return service;
    }

    @Override
    public StatusModel findById(int id) {
        return statusRepository.findById(id);
    }

    @Override
    public List<StatusModel> findAll(int pageSize, int currentPage) {
        return statusRepository.findAll(pageSize, currentPage);
    }

    @Override
    public boolean insert(StatusModel statusModel) {
        if (statusModel == null) return false;
        return statusRepository.insert(statusModel);
    }

    @Override
    public boolean update(StatusModel statusModel) {
        if (statusModel == null) return false;
        return statusRepository.update(statusModel);
    }

    @Override
    public boolean delete(int id) {
        return statusRepository.delete(id);
    }
}
