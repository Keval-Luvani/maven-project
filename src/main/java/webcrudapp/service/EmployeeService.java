package webcrudapp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webcrudapp.model.Employee;

public interface EmployeeService {

	Employee fetchData(int employeeId);

	void deleteEmployee(int employeeId);

	List<Employee> viewEmployee();
	
	void createEmployee(Employee employee);
	
	void updateEmployee(Employee employee);
	
}
