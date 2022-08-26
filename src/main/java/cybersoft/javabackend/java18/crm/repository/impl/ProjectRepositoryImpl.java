package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.ProjectModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.ProjectRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryImpl extends AbstractRepository<ProjectModel> implements ProjectRepository {
    private static ProjectRepository projectRepository;

    private ProjectRepositoryImpl() {

    }

    public static ProjectRepository getRepository() {
        if (projectRepository == null) projectRepository = new ProjectRepositoryImpl();
        return projectRepository;
    }

    @Override
    public List<ProjectModel> findAll(int pageSize, int currentPage) {
        return executeQuery(connection -> {
            String query = """
                    select id, name, start_date, end_date
                    from jobs
                    limit ? offset ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pageSize);
            statement.setInt(2, (currentPage - 1) * pageSize);
            List<ProjectModel> projectModels = new ArrayList<>();
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                ProjectModel projectModel = new ProjectModel();
                projectModel.setId(results.getInt("id"));
                projectModel.setName(results.getString("name"));

                projectModel.setStartDate(results.getString("start_date"));
                projectModel.setEndDate(results.getString("end_date"));
                projectModels.add(projectModel);
            }
            return projectModels;
        });
    }

    @Override
    public ProjectModel findById(int id) {
        return executeQuerySingle(connection -> {
            String query = """
                    select id, name, start_date, end_date
                    from jobs
                    where id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                ProjectModel projectModel = new ProjectModel();
                projectModel.setId(results.getInt("id"));
                projectModel.setName(results.getString("name"));
                projectModel.setStartDate(results.getString("start_date"));
                projectModel.setEndDate(results.getString("end_date"));
                return projectModel;
            }
            return null;
        });
    }

    @Override
    public boolean insert(ProjectModel projectModel) {
        return executeUpdate(connection -> {
            String query = """
                    insert into jobs (name, start_date, end_date)
                    values (?, ?, ?);
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectModel.getName());
            statement.setDate(2, java.sql.Date.valueOf(projectModel.getStartDate()));
            statement.setDate(3, java.sql.Date.valueOf(projectModel.getEndDate()));

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean update(ProjectModel projectModel) {
        return executeUpdate(connection -> {
            String query = """
                    update jobs
                    set name = ?,
                    start_date = ?,
                    end_date = ?
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectModel.getName());
            statement.setDate(2, java.sql.Date.valueOf(projectModel.getStartDate()));
            statement.setDate(3, java.sql.Date.valueOf(projectModel.getEndDate()));
            statement.setInt(4, projectModel.getId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean delete(int id) {
        return executeUpdate(connection -> {
            String query = """
                    delete from jobs
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            return statement.executeUpdate();
        }) != 0;
    }
}