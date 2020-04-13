package com.ace.sbhibernatemysql.Repo;

import java.math.BigInteger;

import com.ace.sbhibernatemysql.entity.Employee;

public interface EmployeeRepository {

	public Employee findByEmpId(BigInteger empId) ;
}
