package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.persistence.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class TennisCourt extends BaseEntity<Long> {

	@Column
	@NotNull
	@Id
	private Long Id;
	  
    @Column
    @NotNull
    private String name;
}
