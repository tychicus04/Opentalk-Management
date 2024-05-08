package com.tychicus.opentalk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "OPEN_TALK")
public class OpenTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    private Employee host;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "START_TIME")
    private LocalDateTime startTime;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "END_TIME")
    private LocalDateTime endTime;
    @Column(name = "MEETING_LINK")
    private String meetingLink;
    private boolean enabled;
    private String slide;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "COMPANY_BRANCH_ID", referencedColumnName = "ID")
    private CompanyBranch companyBranch;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "PARTICIPANTS",
            joinColumns = @JoinColumn(name = "OPEN_TALK_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    private Set<Employee> participants = new HashSet<>();
    public OpenTalk(Long id) {
        this.id = id;
    }
    public OpenTalk(String topic, LocalDateTime startTime, LocalDateTime endTime, String meetingLink) {
        this.topic = topic;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingLink = meetingLink;
    }
    @Override
    public String toString() {
        return "OpenTalk{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", meetingLink='" + meetingLink + '\'' +
                '}';
    }
}
