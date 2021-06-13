package com.tenniscourts.repository;

import com.tenniscourts.entity.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
    List<TennisCourt> findByName(String tennisCourtName);
}
