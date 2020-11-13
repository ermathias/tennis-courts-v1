package com.tenniscourts.guests;

import com.tenniscourts.config.persistence.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

  @Column
  @NotNull
  private String name;

  /**
   * true is Tennis Court Admin
   * false is ordinary User
   */
  @Column
  @NotNull
  private boolean admin;

}
