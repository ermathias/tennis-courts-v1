package com.tenniscourts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class ReservationError implements Serializable {

    private String message;

}
