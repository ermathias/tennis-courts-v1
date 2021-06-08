package com.tenniscourts.guests;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GuestDTO
{
    private Long id;

    private String name;
}
