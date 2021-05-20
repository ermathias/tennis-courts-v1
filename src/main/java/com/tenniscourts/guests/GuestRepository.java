package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query(value ="Select * from guest where name = ?1 ",nativeQuery = true)
    Optional<List<Guest>> findByName(String name);

    @Modifying
    @Query(value = "Update guest g set g.name = ?2 where g.id = ?1 ",nativeQuery = true)
    void update(Long id, String name);

}
