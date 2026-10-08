package com.violencedetection.backend.repository;

import com.violencedetection.backend.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
