package com.example.BAS.repositories;

import com.example.BAS.models.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    Optional<Policy> getPolicyByPolicyNumberIgnoreCase(String policyNumber);

    Optional<List<Policy>> getPoliciesByReminderDatePskEquals(LocalDate date);

    Optional<List<Policy>> getPoliciesByAmountIsNotNullAndReceiveDatePskIsNull();
}
