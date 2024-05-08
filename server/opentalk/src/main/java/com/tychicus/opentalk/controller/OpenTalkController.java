package com.tychicus.opentalk.controller;

import com.tychicus.opentalk.dto.opentalk.OpenTalkCount;
import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
import com.tychicus.opentalk.service.IOpenTalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/open-talk")
@RequiredArgsConstructor
public class OpenTalkController {
    private final IOpenTalkService openTalkService;

    // Get open talk by id
    @GetMapping("/{openTalkId}")
    public ResponseEntity<OpenTalkDTO> getOpenTalkById(
            @PathVariable Long openTalkId
    ) {
        OpenTalkDTO response = openTalkService.getOpenTalkById(openTalkId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all open talks
    @GetMapping("/all-open-talk")
    public ResponseEntity<List<OpenTalkDTO>> getAllOpenTalks(){
        return ResponseEntity.ok().body(openTalkService.getAllOpenTalks());
    }

    // Add new open talk
    @PostMapping("/add")
    public ResponseEntity<OpenTalkDTO> addNewOpenTalk(
            @RequestBody OpenTalkDTO openTalkDTO
    ) {
        openTalkDTO.setId(0L);
        OpenTalkDTO response = openTalkService.saveOpenTalk(openTalkDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update open talk
    @PutMapping("/update")
    public ResponseEntity<OpenTalkDTO> updateOpenTalk(@RequestBody OpenTalkDTO openTalkDTO
    ) {
        OpenTalkDTO response = openTalkService.saveOpenTalk(openTalkDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete open talk
    @DeleteMapping("/delete/{openTalkId}")
    public ResponseEntity<Void> cancelOpenTalk(
            @PathVariable Long openTalkId
    ) {
        openTalkService.cancelOpenTalk(openTalkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get open talk already finished
    @GetMapping("/already-finished")
    public ResponseEntity<Page<OpenTalkDTO>> getOpenTalkAlreadyFinished(
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {
        Page<OpenTalkDTO> response = openTalkService.findAlreadyFinishedOpenTalks(
                pageNo, pageSize,
                startTime, endTime,
                branch, username, LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get open talk is coming
    @GetMapping("/coming")
    public ResponseEntity<Page<OpenTalkDTO>> getOpenTalkIsComing(
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {
        Page<OpenTalkDTO> response = openTalkService.findIsComingOpenTalks(
                pageNo, pageSize,
                startTime, endTime,
                branch, username,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get open talk by employee id
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Page<OpenTalkDTO>> getOpenTalkByEmployeeId(
            @PathVariable Long employeeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {
        Page<OpenTalkDTO> response = openTalkService.findByEmployeeId(
                employeeId, pageNo,
                pageSize, startTime, endTime
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Count open talks has already finished this year class
    @GetMapping("/count-already-finished")
    public ResponseEntity<List<OpenTalkCount>> countOpenTalksHasAlreadyFinishedThisYearClass() {
        List<OpenTalkCount> response = openTalkService.countOpenTalksHasAlreadyFinishedThisYearClass();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
