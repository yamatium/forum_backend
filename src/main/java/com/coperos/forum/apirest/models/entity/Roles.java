package com.coperos.forum.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="roles")
public class Roles implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	private String role;
	
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="edited_at")
	@Temporal(TemporalType.DATE)
	private Date editedAt;
	
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getEditedAt() {
		return editedAt;
	}

	public void setEditedAt(Date editedAt) {
		this.editedAt = editedAt;
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
