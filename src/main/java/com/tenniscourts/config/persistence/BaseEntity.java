package com.tenniscourts.config.persistence;

import com.tenniscourts.audit.CustomAuditEntityListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners(CustomAuditEntityListener.class)
public class BaseEntity<ID> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Column
    private String ipNumberUpdate;

    @Column
    private Long userCreate;

    @Column
    private Long userUpdate;

    @Column
    @LastModifiedDate
    private LocalDateTime dateUpdate;

    @Column
    private String ipNumberCreate;

    @Column
    @CreatedDate
    private LocalDateTime dateCreate;

	/**
	 * @return the id
	 */
	public ID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ID id) {
		this.id = id;
	}

	/**
	 * @return the ipNumberUpdate
	 */
	public String getIpNumberUpdate() {
		return ipNumberUpdate;
	}

	/**
	 * @param ipNumberUpdate the ipNumberUpdate to set
	 */
	public void setIpNumberUpdate(String ipNumberUpdate) {
		this.ipNumberUpdate = ipNumberUpdate;
	}

	/**
	 * @return the userCreate
	 */
	public Long getUserCreate() {
		return userCreate;
	}

	/**
	 * @param userCreate the userCreate to set
	 */
	public void setUserCreate(Long userCreate) {
		this.userCreate = userCreate;
	}

	/**
	 * @return the userUpdate
	 */
	public Long getUserUpdate() {
		return userUpdate;
	}

	/**
	 * @param userUpdate the userUpdate to set
	 */
	public void setUserUpdate(Long userUpdate) {
		this.userUpdate = userUpdate;
	}

	/**
	 * @return the dateUpdate
	 */
	public LocalDateTime getDateUpdate() {
		return dateUpdate;
	}

	/**
	 * @param dateUpdate the dateUpdate to set
	 */
	public void setDateUpdate(LocalDateTime dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	/**
	 * @return the ipNumberCreate
	 */
	public String getIpNumberCreate() {
		return ipNumberCreate;
	}

	/**
	 * @param ipNumberCreate the ipNumberCreate to set
	 */
	public void setIpNumberCreate(String ipNumberCreate) {
		this.ipNumberCreate = ipNumberCreate;
	}

	/**
	 * @return the dateCreate
	 */
	public LocalDateTime getDateCreate() {
		return dateCreate;
	}

	/**
	 * @param dateCreate the dateCreate to set
	 */
	public void setDateCreate(LocalDateTime dateCreate) {
		this.dateCreate = dateCreate;
	}

}
