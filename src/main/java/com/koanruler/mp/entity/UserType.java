package com.koanruler.mp.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserType {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer type;
	private String name;

	// Constructors

	/** default constructor */
	public UserType() {
	}

	/** full constructor */
	public UserType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}