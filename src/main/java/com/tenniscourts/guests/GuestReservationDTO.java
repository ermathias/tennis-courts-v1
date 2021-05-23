package com.tenniscourts.guests;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.tenniscourts.guests.GuestDTO.GuestDTOBuilder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class GuestReservationDTO {

	@NotNull
 @ApiModelProperty(value = "guestId", name = "guestId", dataType = "Long")

	private Long guestId;
	 @ApiModelProperty(value = "guestId", name = "guestId", dataType = "List<GuestDTO>")

	private List<GuestDTO> guestDTOList;
}
