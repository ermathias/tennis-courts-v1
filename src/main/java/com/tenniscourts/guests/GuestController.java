package com.tenniscourts.guests;

import com.tenniscourts.reservations.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class GuestController {

    @Autowired
    GuestServImpl gServ;

    @GetMapping("/")
    @ResponseBody
    public String index(){
        return "ok";
    }

    @GetMapping("/findGuestbyId/{id}")
    @ResponseBody
    public String findGuestbyId(@PathVariable(value="id") Long id) throws IOException, ParseException{
        Guest g = gServ.findGuestById(id);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(g);

        return jsonInString;
    }

    @GetMapping("/findGuestbyName/{name}")
    @ResponseBody
    public String findGuestbyName(@PathVariable(value="name") String name) throws IOException, ParseException{
        Guest g = gServ.findGuestByName(name);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(g);

        return jsonInString;
    }

    @GetMapping("/findAllGuest")
    @ResponseBody
    public String findAllGuest() throws IOException, ParseException{
        List<Guest> g = gServ.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(g);

        return jsonInString;
    }

    @PostMapping("/addGuest")
    @Transactional
    public String addGuest(@RequestBody String guestJSon) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Guest guest = mapper.readValue(guestJSon, Guest.class);
        gServ.save(guest);
        return "redirect:/";
    }

    @DeleteMapping("/deleteGuestbyId/{id}")
    @ResponseBody
    public void deleteGuestbyId(@PathVariable(value="id") Long id) throws JsonProcessingException, ParseException{
        Guest g = gServ.findGuestById(id);
        gServ.delete(g);
    }

    @PutMapping("/updateGuest")
    @Transactional
    public String updateGuest(@RequestBody String guestJSon) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.s");
        mapper.setDateFormat(format);
        Guest guest = mapper.readValue(guestJSon, Guest.class);
        gServ.updateGuest(guest.getId(), guest.getDateCreate(), guest.getDateUpdate(), guest.getIpNumberCreate(), guest.getIpNumberUpdate(), guest.getUserCreate(), guest.getUserUpdate(), guest.getName());
        return "redirect:/";
    }
}
