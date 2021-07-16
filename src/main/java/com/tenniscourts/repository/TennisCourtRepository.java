package com.tenniscourts.repository;

import com.tenniscourts.model.tennis.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
}
