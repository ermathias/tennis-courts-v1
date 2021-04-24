package com.tenniscourts.guests;

public class FixtureGuest {

    public static Guest fixtureGuest(){
        var guest = new Guest();
        guest.setName("Nadal");
        guest.setId(1L);
        return guest;
    }

    public static Guest fixtureGuestBuilder(){
        return Guest.builder().name("Nadal").build();
    }

    public static Guest fixtureOtherGuest(){
        var guest = new Guest();
        guest.setName("Federer");
        guest.setId(1L);
        return guest;
    }

    public static GuestDTO fixtureGuestDTO(){
        var guestDTO = new GuestDTO();
        guestDTO.setName("Nadal");
        return guestDTO;
    }

    public static GuestDTO fixtureOtherGuestDTO(){
        var guestDTO = new GuestDTO();
        guestDTO.setName("Federer");
        return guestDTO;
    }
}
