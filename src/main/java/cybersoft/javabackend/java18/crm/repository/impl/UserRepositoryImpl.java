package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<UserModel> implements UserRepository {
    private static UserRepository userRepository = null;

    private UserRepositoryImpl() {

    }

    public static UserRepository getInstance() {
        if (userRepository == null) userRepository = new UserRepositoryImpl();
        return userRepository;
    }

    @Override
    public List<UserModel> findAll() {
        return executeQuery(connection -> {
            String query = """
                    select id, email, password, fullname, avatar, role_id
                    from users
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            List<UserModel> userModels = new ArrayList<>();

            ResultSet results = statement.executeQuery();
            while (results.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(results.getInt("id"));
                userModel.setEmail(results.getString("email"));
                userModel.setPassword(results.getString("password"));
                userModel.setFullname(results.getString("fullname"));
                userModel.setAvatar(results.getString("avatar"));
                userModel.setRoleId(results.getInt("role_id"));

                userModels.add(userModel);
            }
            return userModels;

        });
    }

    @Override
    public boolean insert(UserModel user) {
        return executeUpdate(connection -> {
            String query = """
                    insert into users (email, password, fullname, avatar, role_id)
                    values (?, ?, ?, ?, ?);
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullname());
            statement.setString(4, user.getAvatar());
            statement.setInt(5, user.getRoleId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public UserModel findById(int id) {
        return executeQuerySingle(connection -> {
            String query = """
                    select id, email, password, fullname, avatar, role_id
                    from users
                    where id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(results.getInt("id"));
                userModel.setEmail(results.getString("email"));
                userModel.setPassword(results.getString("password"));
                userModel.setFullname(results.getString("fullname"));
                userModel.setAvatar(results.getString("avatar"));
                userModel.setRoleId(results.getInt("role_id"));

                return userModel;
            } else
                return null;
        });
    }

    @Override
    public boolean update(UserModel user) {
        return executeUpdate(connection -> {
            String query = """
                    update users
                    set email = ?, password = ?, fullname = ?, avatar = ?, role_id = ?
                    where id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullname());
            statement.setString(4, user.getAvatar());
            statement.setInt(5, user.getRoleId());
            statement.setInt(6, user.getId());

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public List<UserModel> findAll(int pageSize, int currentPage) {
        return executeQuery(connection -> {
            String query = """
                    select id, email, password, fullname, avatar, role_id
                    from users
                    limit ? offset ?
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pageSize);
            statement.setInt(2, (currentPage - 1) * pageSize);

            ResultSet results = statement.executeQuery();
            List<UserModel> userModels = new ArrayList<>();
            while (results.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(results.getInt("id"));
                userModel.setEmail(results.getString("email"));
                userModel.setPassword(results.getString("password"));
                userModel.setFullname(results.getString("fullname"));
                userModel.setAvatar(results.getString("avatar"));
                userModel.setRoleId(results.getInt("role_id"));

                userModels.add(userModel);
            }
            return userModels;
        });
    }

    @Override
    public boolean delete(int id) {
        return executeUpdate(connection -> {
            String query = """
                    delete from users
                    where id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            return statement.executeUpdate();
        }) != 0;
    }

    @Override
    public List<UserModel> findByRole(int roleId) {
        return executeQuery(connection -> {
            String query = """
                    select id, email, password, fullname, avatar, role_id
                    from users
                    where role_id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roleId);

            List<UserModel> userModels = new ArrayList<>();
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(results.getInt("id"));
                userModel.setEmail(results.getString("email"));
                userModel.setPassword(results.getString("password"));
                userModel.setFullname(results.getString("fullname"));
                userModel.setAvatar(results.getString("avatar"));
                userModel.setRoleId(results.getInt("role_id"));

                userModels.add(userModel);
            }
            return userModels;

        });
    }

    @Override
    public UserModel findByEmail(String email) {
        return executeQuerySingle(connection -> {
            String query = """
                    select id, email, password, fullname, avatar, role_id
                    from users
                    where  email = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                UserModel userModel = new UserModel();
                userModel.setId(results.getInt("id"));
                userModel.setEmail(results.getString("email"));
                userModel.setPassword(results.getString("password"));
                userModel.setFullname(results.getString("fullname"));
                userModel.setAvatar(results.getString("avatar"));
                userModel.setRoleId(results.getInt("role_id"));
                return userModel;
            }
            return null;
        });
    }
}
