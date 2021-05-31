package com.tenniscourts.guests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface GuestRepository extends JpaRepository<Guest, Long>{

    @Override
    List<Guest> findAll();

    @Query(value= "select * from guest where id=:idGuest", nativeQuery = true)
    Guest findGuestById(@Param("idGuest") Long idGuest);

    @Query(value= "select * from guest where name=:nameGuest", nativeQuery = true)
    Guest findGuestByName(@Param("nameGuest") String nameGuest);

    @Modifying
    @Query (value= "update guest set date_create=:dcGuest ,date_update=:duGuest, ip_number_create=:incGuest , ip_number_update=:inuGuest , user_create=:ucGuest ,user_update=:uuGuest ,name=:nameGuest where id=:idGuest", nativeQuery = true)
    void updateGuest(@Param("idGuest") Long idGuest, @Param("dcGuest") LocalDateTime dcGuest, @Param("duGuest") LocalDateTime duGuest, @Param("incGuest") String incGuest, @Param("inuGuest") String inuGuest, @Param("ucGuest") Long ucGuest, @Param("uuGuest") Long uuGuest, @Param("nameGuest") String nameGuest);
}
