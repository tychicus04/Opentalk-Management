package com.tychicus.opentalk.service;

import com.tychicus.opentalk.dto.opentalk.OpenTalkCount;
import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
import com.tychicus.opentalk.model.OpenTalk;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IOpenTalkService {

    // CRUD
    // Get all open talks
    List<OpenTalkDTO> getAllOpenTalks();

    // Get open talk by id
    OpenTalkDTO getOpenTalkById(Long openTalkId);

    // Create/Update open talk
    OpenTalkDTO saveOpenTalk(OpenTalkDTO openTalkDTO);

    // Delete open talk by id
    void cancelOpenTalk(Long openTalkId);

    // Find open talk by employee id
    Page<OpenTalkDTO> findByEmployeeId(Long employeeId, Integer pageNo,
                                       Integer pageSize, LocalDateTime startTime,
                                       LocalDateTime endTime);

    // find open talks have already finished
    Page<OpenTalkDTO> findAlreadyFinishedOpenTalks(Integer pageNo, Integer pageSize,
                                                   LocalDateTime startTime, LocalDateTime endTime,
                                                   String branch, String username, LocalDateTime now);

    // find open talks is coming
    Page<OpenTalkDTO> findIsComingOpenTalks(Integer pageNo, Integer pageSize,
                                           LocalDateTime startTime, LocalDateTime endTime,
                                           String branch, String username,
                                            LocalDateTime estimateDate, LocalDateTime now);

//    // get open talks joined by employee id
//    List<OpenTalkDTO> getJoinedOpenTalks(Long employeeId);
//
//    // clear cache of joined open talks
//    void clearJoinedOpenTalksCache(Long employeeId);

    // count open talks has already finished this year class
    List<OpenTalkCount> countOpenTalksHasAlreadyFinishedThisYearClass();
}
