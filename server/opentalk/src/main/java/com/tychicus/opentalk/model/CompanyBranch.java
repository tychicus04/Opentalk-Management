package com.tychicus.opentalk.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "COMPANY_BRANCH")
public class CompanyBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "companyBranch",
            cascade =
                    CascadeType.ALL
            )
    private Set<OpenTalk> openTalks = new HashSet<>();

    @OneToMany(
            mappedBy = "companyBranch"
    )
    private Set<Employee> employees;

    public CompanyBranch(Long id) {
        this.id = id;
    }
    public CompanyBranch(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public String toString() {
        return "CompanyBranch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
