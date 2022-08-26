package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.ProjectModel;

import java.util.List;

public interface ProjectService {
    List<ProjectModel> findAll(int pageSize, int currentPage);

    ProjectModel findById(int id);

    boolean insert(ProjectModel projectModel);

    boolean update(ProjectModel projectModel);

    boolean delete(int id);
}
