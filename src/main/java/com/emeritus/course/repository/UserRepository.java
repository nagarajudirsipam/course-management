package com.emeritus.course.repository;

import com.emeritus.course.model.EmeritusUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<EmeritusUser, String> {
    Iterable<EmeritusUser> findAllByRole(String role);

}
