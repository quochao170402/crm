package cybersoft.javabackend.java18.crm.repository;

import cybersoft.javabackend.java18.crm.model.StatusModel;

import java.util.List;

public interface StatusRepository {
    StatusModel findById(int id);

    List<StatusModel> findAll(int pageSize, int currentPage);

    boolean insert(StatusModel statusModel);

    boolean update(StatusModel statusModel);

    boolean delete(int id);
}
