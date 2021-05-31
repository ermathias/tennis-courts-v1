package com.tenniscourts.guests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GuestServImpl implements GuestService{
    @Autowired
    GuestRepository gRep;

    @Override
    public List<Guest> findAll() {
        return gRep.findAll();
    }

    @Override
    public Guest findGuestById(Long idGuest) {
        return gRep.findGuestById(idGuest);
    }

    @Override
    public Guest findGuestByName(String nameGuest) {
        return gRep.findGuestByName(nameGuest);
    }

    @Override
    public void updateGuest(Long idGuest, LocalDateTime dcGuest, LocalDateTime duGuest, String incGuest, String inuGuest, Long ucGuest, Long uuGuest, String nameGuest) {
        gRep.updateGuest(idGuest, dcGuest, duGuest, incGuest, inuGuest, ucGuest, uuGuest, nameGuest);
    }

    @Override
    public void save(Guest g){
        gRep.save(g);
    }

    @Override
    public void delete(Guest g){
        gRep.delete(g);
    }
}
