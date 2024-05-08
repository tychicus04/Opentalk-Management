package com.tychicus.opentalk.dto.opentalk;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tychicus.opentalk.dto.employee.EmployeeDTOForOpenTalk;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OpenTalkDTO {
    private Long id;
    private String topic;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    private String linkMeeting;
    private EmployeeDTOForOpenTalk host;
    private String companyBranchName;
    private Set<EmployeeDTOForOpenTalk> participants;
    private String slide;
}
