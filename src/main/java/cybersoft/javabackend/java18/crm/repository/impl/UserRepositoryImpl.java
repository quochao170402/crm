package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<UserModel> implements UserRepository {

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
}
