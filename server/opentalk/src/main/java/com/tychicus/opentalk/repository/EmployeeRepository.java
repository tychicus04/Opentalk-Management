package com.tychicus.opentalk.repository;

import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee,  Long> {

    // find by type of class T in collection
    <T> Collection<T> findBy(Class<T> type);
    // find all employees by company branch name
    @Query("SELECT e FROM Employee e WHERE e.enabled = true AND e.companyBranch.name = ?1")
    List<Employee> findEmployeeByEnabledAndCompanyBranch(String branch);

    // find all employees by email
    Optional<Employee> findByEmail(String email);

    // find employee by username
    Optional<Employee> findByUsername(String username);

    // find all employees has not hosted this year
    @Query("SELECT DISTINCT e FROM Employee e " +
            "JOIN e.openTalkIsHosted o " +
            "WHERE YEAR(o.endTime) < :year " +
            "AND e.enabled = true")
    List<Employee> findEmployeesByHasNotHostedThisYear(@Param("year") Integer year);

    // find all employees by some filter
    @Query("SELECT DISTINCT e FROM Employee e " +
            "WHERE (:enabled IS NULL OR e.enabled = :enabled) " +
            "AND (:branch IS NULL OR e.companyBranch.name LIKE CONCAT('%',:branch,'%')) " +
            "AND (:username IS NULL OR e.username LIKE CONCAT('%',:username,'%'))")
    Page<Employee> findEmployeeBySomeFilter(@Param("enabled") Boolean enabled,
                                            @Param("branch") String branch,
                                            @Param("username") String username,
                                            Pageable pageable);

    // find all employees by sort by no of open talk
    @Query("SELECT DISTINCT e FROM Employee e " +
            "WHERE (:enabled IS NULL OR e.enabled = :enabled) " +
            "AND (:branch IS NULL OR e.companyBranch.name LIKE CONCAT('%',:branch,'%')) " +
            "AND (:username IS NULL OR e.username LIKE CONCAT('%',:username,'%'))")
    Page<Employee> findEmployeeBySortByNoOfOpenTalk(@Param("enabled") Boolean enabled,
                                                    @Param("branch") String branch,
                                                    @Param("username") String username, Pageable pageable);

    // find all employees by company branch name to send email
    @Query(value ="SELECT DISTINCT e.* FROM employee e " +
          "JOIN company_branch c ON e.company_branch_id = c.id " +
            "WHERE c.name = :branchName", nativeQuery = true)
    List<Employee> findEmployeeForSendEmail(@Param("branchName") String branchName);


        @Query("SELECT e.email FROM Employee e")
        List<String> findAllEmails();

}
