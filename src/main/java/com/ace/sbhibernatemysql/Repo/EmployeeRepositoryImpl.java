package com.ace.sbhibernatemysql.Repo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ace.sbhibernatemysql.entity.Employee;

@Repository
public class EmployeeRepositoryImpl  implements EmployeeRepository{

	
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
	
	@Override
	 @Transactional
	 
//example of native query in hibernate
	public Employee findByEmpId(BigInteger empId) {
		
		Session session=sessionFactory.getCurrentSession();
	//Transaction transaction=	session.beginTransaction();
	
	SQLQuery<Employee> query = session.createSQLQuery("select * from employee where emp_id="+empId.toString());
	query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	List data = query.list();
	if(!data.isEmpty() && data.size()==1) {
   Employee employee = null;
    for(Object object : data) {
       Map row = (Map)object;
       employee= new Employee();
       employee.setEmpId((BigInteger)row.get("emp_id"));
       employee.setEmpFirstName((String)row.get("emp_fn"));
       employee.setEmpLastName((String)row.get("emp_ln"));
    }
//	transaction.commit();
	if(employee!=null)
	{
	return employee;
		
	}
	
	}
	return null;
	}
}
