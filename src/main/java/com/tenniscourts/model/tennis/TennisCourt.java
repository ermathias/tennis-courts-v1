package com.tenniscourts.model.tennis;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.dto.TennisCourtDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class TennisCourt extends BaseEntity<Long> {

    @Column
    @NotNull
    private String name;

    public static TennisCourtDTO toDto(TennisCourt tennisCourt) {
        return TennisCourtDTO.builder()
                .name(tennisCourt.getName())
                .build();
    }
}
