package com.tenniscourts.tenniscourts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
    TennisCourt findByName(String name);


}

