package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.StatusModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.StatusRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusRepositoryImpl extends AbstractRepository<StatusModel> implements StatusRepository {
    private static StatusRepository statusRepository;

    private StatusRepositoryImpl() {

    }

    public static StatusRepository getStatusRepository() {
        if (statusRepository == null) statusRepository = new StatusRepositoryImpl();
        return statusRepository;
    }

    @Override
    public StatusModel findById(int id) {
        return executeQuerySingle(connection -> {
            String query = """
                    select id, name
                    from status
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(results.getInt("id"));
                statusModel.setName(results.getString("name"));
                return statusModel;
            }
            return null;
        });
    }

    @Override
    public List<StatusModel> findAll(int pageSize, int currentPage) {
        return executeQuery(connection -> {
            String query = """
                    select id, name
                    from status
                    limit ? offset ?
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pageSize);
            statement.setInt(2, (currentPage - 1) * pageSize);

            ResultSet results = statement.executeQuery();
            List<StatusModel> statusModels = new ArrayList<>();
            while (results.next()) {
                StatusModel statusModel = new StatusModel();
                statusModel.setId(results.getInt("id"));
                statusModel.setName(results.getString("name"));
                statusModels.add(statusModel);
            }
            return statusModels;
        });
    }

    @Override
    public boolean insert(StatusModel statusModel) {
        return executeUpdate(connection -> {
            String query = """
                    insert into status (name)
                    values (?);
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, statusModel.getName());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean update(StatusModel statusModel) {
        return executeUpdate(connection -> {
            String query = """
                    update status
                    set name = ?
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, statusModel.getName());
            statement.setInt(2, statusModel.getId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean delete(int id) {
        return executeUpdate(connection -> {
            String query = """
                    delete from status
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            return statement.executeUpdate();
        }) != 0;
    }
}
