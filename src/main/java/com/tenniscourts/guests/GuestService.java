package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.reservations.Reservation;
import com.tenniscourts.reservations.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    private final ReservationRepository reservationRepository;

    private final GuestMapper guestMapper;

    public List<GuestDTO> handleFindAllGuests() {
        return this.guestMapper.map(this.guestRepository.findAll());
    }

    public GuestDTO handleFindGuestById(Long guestId) {
        return this.guestMapper.map(this.guestRepository.findById(guestId).orElseThrow(() ->
                new EntityNotFoundException("Guest with provided id has not been found.")));
    }

    public List<GuestDTO> handleFindGuestsByName(String guestName)
    {
        return this.guestMapper.map(this.guestRepository.findAllByNameLike(guestName));
    }

    public GuestDTO handleCreateGuest(CreateGuestRequestDTO createGuestRequestDTO)
    {
        Guest guest = Guest.builder().name(createGuestRequestDTO.getName()).build();

        return this.guestMapper.map(this.guestRepository.save(guest));
    }

    public GuestDTO handleUpdateGuest(Long guestId, String guestName)
    {
        Guest guest = this.guestRepository.findById(guestId).orElseThrow(() ->
                new EntityNotFoundException("Guest with provided id has not been found."));

        guest.setName(guestName);

        return this.guestMapper.map(this.guestRepository.save(guest));
    }

    public Long handleDeleteGuest(Long guestId)
    {
        Guest guest = this.guestRepository.findById(guestId).orElseThrow(() ->
                new EntityNotFoundException("Guest with provided id has not been found."));

        // We need to delete the reservations made by the guest
        List<Reservation> reservations = this.reservationRepository.findByGuest_Id(guestId);

        this.reservationRepository.deleteAll(reservations);

        // Now we can safely delete the guest
        this.guestRepository.delete(guest);

        return guestId;
    }
}
