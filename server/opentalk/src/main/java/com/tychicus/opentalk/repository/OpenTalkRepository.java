package com.tychicus.opentalk.repository;

import com.tychicus.opentalk.dto.opentalk.IOpenTalkCount;
import com.tychicus.opentalk.dto.opentalk.OpenTalkCount;
import com.tychicus.opentalk.model.OpenTalk;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.tychicus.opentalk.dto.opentalk.OpenTalkCount;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface OpenTalkRepository extends JpaRepository<OpenTalk, Long> {


    @Cacheable("openTalks")
    @Query("SELECT DISTINCT o FROM OpenTalk o " +
            "JOIN o.participants p " +
            "WHERE p.id = :employeeId " +
            "AND (CAST(:startTime AS LocalDateTime) IS NULL OR CAST(o.startTime AS LocalDateTime) >= :startTime) " +
            "AND (CAST(:endTime AS LocalDateTime) IS NULL OR CAST(o.endTime AS LocalDateTime) <= :endTime)")
    Page<OpenTalk> findByEmployeeId(@Param("employeeId") Long employeeId,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime,
                                    Pageable pageable);

    @Query("SELECT DISTINCT o FROM OpenTalk o " +
            "WHERE o.endTime < :now " +
            "AND o.companyBranch.name LIKE CONCAT('%',:branch,'%') " +
            "AND o.host.username LIKE CONCAT('%',:username)" +
            "AND (CAST(:startTime AS LocalDateTime) IS NULL OR CAST(o.startTime AS LocalDateTime) >= :startTime) " +
            "AND (CAST(:endTime AS LocalDateTime) IS NULL OR CAST(o.endTime AS LocalDateTime) <= :endTime)"
    )
    Page<OpenTalk> findByAlreadyFinishedOpenTalk(@Param("branch") String branch,
                                                 @Param("now") LocalDateTime now,
                                                 @Param("username") String username,
                                                 @Param("startTime") LocalDateTime startTime,
                                                 @Param("endTime") LocalDateTime endTime,
                                                 Pageable pageable);

    @Query("SELECT DISTINCT o FROM OpenTalk o " +
            "WHERE o.startTime > :now AND o.endTime <= :estimateTime " +
            "AND o.companyBranch.name LIKE CONCAT('%',:branch,'%') " +
            "AND o.host.username LIKE CONCAT('%',:username)" +
            "AND (CAST(:startTime AS LocalDateTime) IS NULL OR CAST(o.startTime AS LocalDateTime) >= :startTime) " +
            "AND (CAST(:endTime AS LocalDateTime) IS NULL OR CAST(o.endTime AS LocalDateTime) <= :endTime)")
    Page<OpenTalk> findByIsComingOpenTalk(@Param("branch") String branch,
                                          @Param("username") String username,
                                          @Param("now") LocalDateTime now,
                                          @Param("estimateTime") LocalDateTime estimateTime,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime,
                                          Pageable pageable);

    @Query("SELECT DISTINCT o FROM OpenTalk o " +
            "JOIN o.companyBranch b " +
            "WHERE CAST(o.startTime AS LocalDate) = :localDate " +
            "AND b.name LIKE %:branchName%")
    OpenTalk findOpenTalkForSendEmail(LocalDate localDate, String branchName);


    // count total open talk by year class
    @Query("SELECT new com.tychicus.opentalk.dto.opentalk.OpenTalkCount(YEAR(o.startTime), COUNT(o)) " +
            "FROM OpenTalk o " +
            "GROUP BY YEAR(o.startTime)")
    List<OpenTalkCount> countTotalOpenTalksByYearClass();


//    // count total open talk by year interface
//    @Query("SELECT YEAR(o.startTime) as year, COUNT(o.id) as total " +
//            "FROM OpenTalk o " +
//            "GROUP BY YEAR(o.startTime)")
//    List<IOpenTalkCount> countTotalOpenTalksByYearInterface();
}
