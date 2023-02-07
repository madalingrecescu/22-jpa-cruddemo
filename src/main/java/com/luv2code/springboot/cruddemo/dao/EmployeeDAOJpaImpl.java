package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {

	
	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public List<Employee> findAll() {
		
		//create a query
		Query theQuery = entityManager.createQuery("from Employee");
		//execute query and get result list
		List<Employee> employees = theQuery.getResultList();
		//return the results
		return employees;
	}

	@Override
	public Employee findById(int theId) {
		
		//get the employee
		Employee employee = entityManager.find(Employee.class, theId);
		
		//return the employee
		return employee;
	}

	@Override
	public void save(Employee thEmployee) {
		
		Employee dbEmployee = entityManager.merge(thEmployee);
		
		//update with id from db ... so e can get generated id for save/insert
		thEmployee.setId(dbEmployee.getId());
	}

	@Override
	public void deleteById(int theId) {
		
		Employee employee = entityManager.find(Employee.class, theId);
		
		Query theQuery = entityManager.createQuery("delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", theId);
		theQuery.executeUpdate();

	}

}
