package com.tenniscourts.guests;


import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestDTO {

    private Long id;
    private String name;
}
