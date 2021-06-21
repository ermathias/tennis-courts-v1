package com.tenniscourts.repository;

import com.tenniscourts.model.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
}
