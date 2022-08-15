package cybersoft.javabackend.java18.crm.service;

import cybersoft.javabackend.java18.crm.model.RoleModel;
import cybersoft.javabackend.java18.crm.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleServiceTest {

    @Test
    void shouldInsertWorkCorrectly() {
        RoleModel roleModel = new RoleModel();
        roleModel.setName("HELL");
        roleModel.setDescription("O");
        assertTrue(RoleServiceImpl.getService().insert(roleModel));
    }
}
