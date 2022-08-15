package cybersoft.javabackend.java18.crm.repository.impl;

import cybersoft.javabackend.java18.crm.model.UserModel;
import cybersoft.javabackend.java18.crm.repository.AbstractRepository;
import cybersoft.javabackend.java18.crm.repository.UserRepository;

import java.sql.PreparedStatement;
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

            return userModels;

        });
    }
}
