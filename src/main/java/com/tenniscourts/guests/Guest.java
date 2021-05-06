package com.tenniscourts.guests;

import com.tenniscourts.config.persistence.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class Guest extends BaseEntity<Long> {

  @Column
  @NotNull
  @Id
  private String name;

}
