package webcrudapp.service;

import java.util.List;

import webcrudapp.dao.EmployeeDao;
import webcrudapp.dao.EmployeeDaoImpl;
import webcrudapp.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {
	EmployeeDao employeeDao = new EmployeeDaoImpl();
	public Employee fetchData(int employeeId) {
		Employee employee = employeeDao.getEmployee(employeeId);
		return employee;
	}

	public void deleteEmployee(int employeeId) {
		employeeDao.deleteEmployee(employeeId);
		return ; 
	}

	public List<Employee> viewEmployee() {
		List<Employee> employeeList = employeeDao.getEmployees();
		return employeeList;
	}

	public void createEmployee(Employee employee) {
		employeeDao.addEmployee(employee);
		return;	
	}

	public void updateEmployee(Employee employee) {
		employeeDao.updateEmployee(employee);
		List<String> updatedSkillList = employee.getSkillList();
		List<String> databaseSkillList = employeeDao.getSkills(employee.getEmployeeId());
		for(String skill : updatedSkillList) {
			if(!databaseSkillList.contains(skill)){
				employeeDao.addSkill(employee.getEmployeeId(),skill);
			}
		}
		for(String skill : databaseSkillList) {
			if(!updatedSkillList.contains(skill)){
				employeeDao.deleteSkill(employee.getEmployeeId(),skill);
			}
		}
		return;
	}

}
