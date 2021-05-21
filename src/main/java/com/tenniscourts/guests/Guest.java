package com.tenniscourts.guests;

import com.tenniscourts.config.persistence.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.prefs.Preferences;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Guest extends BaseEntity<Long> {


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column
  @NotNull
  private String name;

  @Column
  @NotNull
  private long id;


  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }
}
