package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.TaskModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.TaskRepository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImpl extends AbstractRepository<TaskModel> implements TaskRepository {
    private static TaskRepository taskRepository;

    private TaskRepositoryImpl() {

    }

    public static TaskRepository getRepository() {
        if (taskRepository == null) taskRepository = new TaskRepositoryImpl();
        return taskRepository;
    }

    @Override
    public boolean deleteById(int id) {
        return executeUpdate(connection -> {
            String query = """
                    delete from tasks
                    where id = ?
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean update(TaskModel taskModel) {
        return executeUpdate(connection -> {
            String query = """
                    update tasks
                    set name = ?,
                    start_date = ?,
                    end_date = ?,
                    user_id = ?,
                    job_id = ?,
                    status_id = ?
                    where id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskModel.getName());
            statement.setDate(2, Date.valueOf(taskModel.getStartDate()));
            statement.setDate(3, Date.valueOf(taskModel.getEndDate()));
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getProjectId());
            statement.setInt(6, taskModel.getStatusId());
            statement.setInt(7, taskModel.getId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean insert(TaskModel taskModel) {
        return executeUpdate(connection -> {
            String query = """
                    insert into tasks (name, start_date, end_date, user_id, job_id, status_id)
                    values (?,?,?,?,?,?);
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, taskModel.getName());
            statement.setDate(2, Date.valueOf(taskModel.getStartDate()));
            statement.setDate(3, Date.valueOf(taskModel.getEndDate()));
            statement.setInt(4, taskModel.getUserId());
            statement.setInt(5, taskModel.getProjectId());
            statement.setInt(6, taskModel.getStatusId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public List<TaskModel> findAll(int pageSize, int currentPage) {
        return executeQuery(connection -> {
            String query = """
                    select id, name, start_date, end_date, user_id, job_id, status_id
                    from tasks
                    limit ? offset ?
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pageSize);
            statement.setInt(2, (currentPage - 1) * pageSize);

            ResultSet results = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (results.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(results.getInt("id"));
                taskModel.setName(results.getString("name"));
                taskModel.setStartDate(results.getString("start_date"));
                taskModel.setEndDate(results.getString("end_date"));
                taskModel.setUserId(results.getInt("user_id"));
                taskModel.setProjectId(results.getInt("job_id"));
                taskModel.setStatusId(results.getInt("status_id"));
                taskModels.add(taskModel);
            }

            return taskModels;
        });
    }

    @Override
    public TaskModel findById(int id) {
        return executeQuerySingle(connection -> {
            String query = """
                    select id, name, start_date, end_date, user_id, job_id, status_id
                    from tasks
                    where id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(results.getInt("id"));
                taskModel.setName(results.getString("name"));
                taskModel.setStartDate(results.getString("start_date"));
                taskModel.setEndDate(results.getString("end_date"));
                taskModel.setUserId(results.getInt("user_id"));
                taskModel.setProjectId(results.getInt("job_id"));
                taskModel.setStatusId(results.getInt("status_id"));
                return taskModel;
            }

            return null;
        });
    }

    @Override
    public List<TaskModel> findByUserId(int id) {
        return executeQuery(connection -> {
            String query = """
                    select id, name, start_date, end_date, user_id, job_id, status_id
                    from tasks
                    where user_id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();
            List<TaskModel> taskModels = new ArrayList<>();
            while (results.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setId(results.getInt("id"));
                taskModel.setName(results.getString("name"));
                taskModel.setStartDate(results.getString("start_date"));
                taskModel.setEndDate(results.getString("end_date"));
                taskModel.setUserId(results.getInt("user_id"));
                taskModel.setProjectId(results.getInt("job_id"));
                taskModel.setStatusId(results.getInt("status_id"));
                taskModels.add(taskModel);
            }

            return taskModels;
        });
    }
}
