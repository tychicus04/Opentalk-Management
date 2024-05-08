package com.tychicus.opentalk.repository;

import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenTalkIsHostedRepository extends JpaRepository<OpenTalkIsHosted, Long> {
    OpenTalkIsHosted findByIdOpenTalk(Long id);
}
