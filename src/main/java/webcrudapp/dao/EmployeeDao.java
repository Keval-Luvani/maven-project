package webcrudapp.dao;

import java.util.List;

import webcrudapp.model.Employee;
import webcrudapp.model.Skills;

public interface EmployeeDao {
	public List<Employee> getEmployees();
	public Employee getEmployee(int id);
	public void addEmployee(Employee employee);
	public void updateEmployee(Employee employee);
	public void deleteEmployee(int id);
	public void addSkill(int id,String skill);
	public void deleteSkill(int id,String Skill);
	public List<String> getSkills(int id);
}
