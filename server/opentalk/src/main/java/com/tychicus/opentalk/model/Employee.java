package com.tychicus.opentalk.model;
import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
import jakarta.persistence.*;
import jdk.jshell.spi.SPIResolutionException;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "employee")
@AllArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "FULL_NAME")
    private String fullName;

    private String email;

    private String password;

    @Column(name = "GOOGLE_ID")
    private String googleId;

    private boolean enabled;

    @Column(name= "NUMBER_OF_JOINED_OPEN_TALK")
    private int numberOfJoinedOpenTalk;

    @ManyToOne(
            fetch = FetchType.LAZY,
//            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH, CascadeType.MERGE,
                 CascadeType.REFRESH
            })
    private CompanyBranch companyBranch;

    // relationship mapping represent one user can have multiple role
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, mappedBy = "employee")
    Set<OpenTalkIsHosted> openTalkIsHosted = new HashSet<>(0);

    // relationship mapping represent one user can join many open talk
    @ManyToMany (
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            },
            targetEntity = OpenTalk.class
    )
    @JoinTable(
            name = "PARTICIPANTS",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "OPEN_TALK_ID")
    )

    Set<OpenTalk> joinOpenTalkList = new HashSet<>();

    // constructor
    public Employee(String username, String fullName, String email, String password, String googleId, boolean enabled) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.googleId = googleId;
        this.enabled = enabled;
    }

    // register host for open talk
    public void registerHost(OpenTalk openTalk) {
        this.joinOpenTalkList.add(openTalk);
        openTalk.setHost(this);
        this.numberOfJoinedOpenTalk = this.joinOpenTalkList.size();
    }

    // register joined open talk
    public void registerJoinedOpenTalk(OpenTalk openTalk) {
        this.joinOpenTalkList.add(openTalk);
        this.numberOfJoinedOpenTalk = this.joinOpenTalkList.size();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", enabled=" + enabled +
                ", companyBranch=" + companyBranch +
                '}';
    }
}
