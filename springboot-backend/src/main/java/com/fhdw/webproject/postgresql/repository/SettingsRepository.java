package com.fhdw.webproject.postgresql.repository;

import com.fhdw.webproject.postgresql.model.SettingsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<SettingsDTO, Long> {
}
