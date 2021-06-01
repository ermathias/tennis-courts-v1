package com.tenniscourts.reservations;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CreateReservationRequestDTO {

    @ApiModelProperty(name = "guestId", required = true)
    @NotNull
    private Long guestId;

    @ApiModelProperty(name = "scheduleIds", required = true, value = "[1, 2, 3]", notes = "The schedule ids for reservations. It can only be one id per " +
            "tennis " +
            "court")
    @NotNull(message = "Schedule ids must not be null")
    private List<Long> scheduleIds;

}
