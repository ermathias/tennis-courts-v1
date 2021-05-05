package com.tenniscourts.tenniscourts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
	List<TennisCourtDTO> findByname(String name);

	void save(TennisCourtDTO tennisCourt);
}
