package cybersoft.javabackend.java18.crm.service.impl;

import cybersoft.javabackend.java18.crm.model.ProjectModel;
import cybersoft.javabackend.java18.crm.repository.ProjectRepository;
import cybersoft.javabackend.java18.crm.repository.impl.ProjectRepositoryImpl;
import cybersoft.javabackend.java18.crm.service.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    private static ProjectService projectService = null;
    private final ProjectRepository projectRepository;

    private ProjectServiceImpl() {
        projectRepository = ProjectRepositoryImpl.getRepository();
    }

    public static ProjectService getService() {
        if (projectService == null) projectService = new ProjectServiceImpl();
        return projectService;
    }

    @Override
    public List<ProjectModel> findAll(int pageSize, int currentPage) {
        return projectRepository.findAll(pageSize, currentPage);
    }

    @Override
    public ProjectModel findById(int id) {
        return projectRepository.findById(id);
    }

    @Override
    public boolean insert(ProjectModel projectModel) {
        return projectRepository.insert(projectModel);
    }

    @Override
    public boolean update(ProjectModel projectModel) {
        return projectRepository.update(projectModel);
    }

    @Override
    public boolean delete(int id) {
        return projectRepository.delete(id);
    }
}


