package com.tenniscourts.guests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CreateGuestRequestDTO {

    private String name;

}
