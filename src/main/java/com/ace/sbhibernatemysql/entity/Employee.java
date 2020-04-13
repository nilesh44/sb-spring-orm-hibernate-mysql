package com.ace.sbhibernatemysql.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="Employee")
@Setter
@Getter
public class Employee {
	
	@Column(name ="emp_id")
	private BigInteger empId;
	
	@Column(name="emp_fn")
	private String empFirstName;
	
	@Column(name="emp_ln")
	private String empLastName;

}
