package com.fhdw.webproject.postgresql.repository;

import com.fhdw.webproject.postgresql.model.TimeEntryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntryDTO, Long> {
}
