package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Role;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Epic("Repository Test")
@Feature("Role Repository Test")
class RoleRepoTest {
  @Autowired private RoleRepo roleRepo;

  @BeforeEach
  void setup() {
    Role role = Role.builder().name("ROLE_TEST").build();
    roleRepo.save(role);
  }

  @Test
  @Description("User tries to save correct Role")
  void saveRole() {
    Role role = Role.builder().name("ROLE_TEST_2").build();

    roleRepo.save(role);

    Assertions.assertThat(role.getId()).isGreaterThan(-1);
  }

  @Test
  @Description("User tries to get Role by name")
  void getByName() {
    String roleName = "ROLE_TEST";

    Role role = roleRepo.findByName(roleName);

    Assertions.assertThat(role.getName()).isEqualTo(roleName);
  }

  @Test
  @Description("User tries to update Role")
  void updateRole() {
    String roleName = "ROLE_TEST";

    Role role = roleRepo.findByName(roleName);

    role.setName("ROLE_NEW");
    Role updatedRole = roleRepo.save(role);

    Assertions.assertThat(updatedRole.getName()).isEqualTo("ROLE_NEW");
  }

  @Test
  @Description("User tries delete Role by name")
  void deleteRole() {
    String roleName = "ROLE_TEST";

    Role role = roleRepo.findByName(roleName);

    roleRepo.delete(role);

    Role roleDeleted = roleRepo.findByName(roleName);

    Assertions.assertThat(roleDeleted).isEqualTo(null);
  }
}
