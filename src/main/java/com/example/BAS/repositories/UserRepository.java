package com.example.BAS.repositories;


import com.example.BAS.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<List<User>> findUsersByOfficeNumber(Long officeNumber);

    Optional<User> findUserByAdvisorNumber(Long advisorNumber);
}
