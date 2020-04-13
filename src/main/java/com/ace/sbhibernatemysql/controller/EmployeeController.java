package com.ace.sbhibernatemysql.controller;

import java.math.BigDecimal;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ace.sbhibernatemysql.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	//example of using requestparam
	//http://localhost:8080/test?emp_id=10
	  @GetMapping("/test") public ResponseEntity<Object> test(@RequestParam
	  BigInteger emp_id) {
	  
	  return employeeService.findByEmpId(emp_id);
	  
	  }
	 
	  //example of PathVariable
	
	//http://localhost:8080/test/1
	/*
	 * @GetMapping("/test/{emp_id}") public ResponseEntity<Object>
	 * test(@PathVariable BigInteger emp_id) {
	 * 
	 * return employeeService.findByEmpId(emp_id);
	 * 
	 * }
	 */
	
}
