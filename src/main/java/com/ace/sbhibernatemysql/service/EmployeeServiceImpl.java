package com.ace.sbhibernatemysql.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ace.sbhibernatemysql.Repo.EmployeeRepository;
import com.ace.sbhibernatemysql.entity.Employee;


@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public ResponseEntity<Object> findByEmpId(BigInteger bigInteger) {
	Employee employee=	employeeRepository.findByEmpId(bigInteger);
	if (employee!=null) {
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
		return  new ResponseEntity<>("employee not found", HttpStatus.OK);
	}

}
