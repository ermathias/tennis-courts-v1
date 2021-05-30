package com.tenniscourts.tenniscourts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
    @Query(value = "select t from TennisCourt t where t.name like %?1%")
    List <TennisCourt> findByName(String name);
}
