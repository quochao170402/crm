package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.RoleRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl extends AbstractRepository<RoleModel> implements RoleRepository {

    private static RoleRepository roleRepository = null;

    private RoleRepositoryImpl() {

    }

    public static RoleRepository getRepository() {
        if (roleRepository == null) roleRepository = new RoleRepositoryImpl();
        return roleRepository;
    }


    @Override
    public List<RoleModel> findAll() {
        return executeQuery(connection -> {
            String query = """
                    select id, name, description
                    from roles
                    """;

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet results = statement.executeQuery();

            List<RoleModel> roles = new ArrayList<>();
            while (results.next()) {
                RoleModel role = new RoleModel();
                role.setId(results.getInt("id"));
                role.setName(results.getString("name"));
                role.setDescription(results.getString("description"));
                roles.add(role);
            }
            return roles;
        });
    }

    @Override
    public boolean insert(RoleModel roleModel) {
        return executeUpdate(connection -> {
            String query = """
                    insert into roles (name, description)
                    values (?, ?);
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());

            return statement.executeUpdate();
        }) != 0;

    }

    @Override
    public boolean update(RoleModel roleModel) {
        return executeUpdate(connection -> {
            String query = """
                    update roles
                    set name = ?,
                    description = ?
                    where id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleModel.getName());
            statement.setString(2, roleModel.getDescription());
            statement.setInt(3, roleModel.getId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public boolean deleteById(int id) {
        return executeUpdate(connection -> {
            String query = """
                    delete from roles
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public RoleModel findById(int id) {
        return executeQuerySingle(connection -> {
            String query = """
                    select id, name, description
                    from roles
                    where id = ?
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                RoleModel role = new RoleModel();
                role.setId(results.getInt("id"));
                role.setName(results.getString("name"));
                role.setDescription(results.getString("description"));
                return role;
            }
            return null;
        });
    }

    @Override
    public List<RoleModel> findAll(int pageSize, int currentPage) {
        return executeQuery(connection -> {
            String query = """
                    select id, name, description
                    from roles
                    limit ? offset ?
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pageSize);
            statement.setInt(2, (currentPage - 1) * pageSize);
            ResultSet results = statement.executeQuery();

            List<RoleModel> roles = new ArrayList<>();
            while (results.next()) {
                RoleModel role = new RoleModel();
                role.setId(results.getInt("id"));
                role.setName(results.getString("name"));
                role.setDescription(results.getString("description"));
                roles.add(role);
            }
            return roles;
        });
    }
}
