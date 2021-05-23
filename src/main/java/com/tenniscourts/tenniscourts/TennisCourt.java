package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.persistence.BaseEntity;
import com.tenniscourts.schedules.Schedule;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

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

}
