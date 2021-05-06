public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
}
package com.tenniscourts.tenniscourts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt, Long> {
	
	public List<TennisCourt> findByname(String name);
}
