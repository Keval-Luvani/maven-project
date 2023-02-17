package webcrudapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import webcrudapp.model.Employee;
import webcrudapp.model.Skills;

public class EmployeeDaoImpl implements EmployeeDao{
	Connection connection;
	public  static Connection initializeDatabase() throws SQLException, ClassNotFoundException
    {
        String databaseDriver = "com.mysql.jdbc.Driver";
        String databaseURL = "jdbc:mysql:// localhost:3306/";
        String databaseName = "employee";
        String databaseUsername = "root";
        String databasePassword = "root";
  
        Class.forName(databaseDriver);
        Connection connection = DriverManager.getConnection(databaseURL + databaseName, databaseUsername, databasePassword);
        return connection;
    }
	
	public List<Employee> getEmployees() {
		try {
			 List<Employee> employeeList = new ArrayList<Employee>();
			 connection = initializeDatabase();
		     String query = "select * from employee";
		     PreparedStatement preparedStatement = connection.prepareStatement(query);
		     ResultSet resultset = preparedStatement.executeQuery();
		     while(resultset.next()) {
					Employee employee = new Employee();
					employee.setEmployeeId(resultset.getInt(1));
					employee.setName(resultset.getString(2));
					employee.setAge(resultset.getInt(3));
					employee.setSalary(resultset.getFloat(4));
					employee.setJoiningDate(resultset.getString(5));
					employee.setSkillList(getSkills(resultset.getInt(1)));
					employeeList.add(employee);
				}
		     return employeeList;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Employee getEmployee(int employeeId) {
		
		try {
			System.out.println("entered get empyoee");
			connection = initializeDatabase();
			String query = "select * from employee where employee_id=?";
	        
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1,employeeId);
	        Employee employee = new Employee();
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()) {
	        	employee.setEmployeeId(resultSet.getInt(1));
	        	employee.setName(resultSet.getString(2));
	        	employee.setAge(resultSet.getInt(3));
	        	employee.setSalary(resultSet.getFloat(4));
	        	employee.setJoiningDate(resultSet.getString(5));
	        	employee.setSkillList(getSkills(employeeId));
	        }
	        System.out.println("*****"+employee);
	        return employee;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
		
		return null;
	}

	public void addEmployee(Employee employee) {
		
		try {
			connection = initializeDatabase();
			String query = "insert into employee (name,age,salary,joining_date) values(?,?,?,?)";
	        
	        PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, employee.getName());
	        preparedStatement.setInt(2,employee.getAge());
	        preparedStatement.setFloat(3, employee.getSalary());
	        preparedStatement.setString(4, employee.getJoiningDate());
	        preparedStatement.execute();
	        ResultSet resultsetid = preparedStatement.getGeneratedKeys();
	        
	        int id=0;
	        while(resultsetid.next()) {
	        	id = resultsetid.getInt(1);
	        }
	        
	        for(String skill:employee.getSkillList()) {
				addSkill(id,skill);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
		
	}

	public void updateEmployee(Employee employee) {
		 try {
	        connection = initializeDatabase();
	        String query = "update employee set name=?,age=?,salary=?,joining_date=? where employee_id = ?";
	        
	        PreparedStatement prepareStatement = connection.prepareStatement(query);
	        prepareStatement.setString(1, employee.getName());
	        prepareStatement.setInt(2,employee.getAge());
	        prepareStatement.setFloat(3, employee.getSalary());
	        prepareStatement.setString(4, employee.getJoiningDate());
	        prepareStatement.setInt(5,employee.getEmployeeId());
	        prepareStatement.executeUpdate();
	       
	        }catch(SQLException e){
	        	e.printStackTrace();
	        }catch (ClassNotFoundException e) {
				e.printStackTrace();
	        }
	}

	public void deleteEmployee(int employeeId) {
		try {
	        connection = initializeDatabase();
	        String query = "delete from employee where employee_id=?";
	        
	        PreparedStatement prepareStatement = connection.prepareStatement(query);
	        prepareStatement.setInt(1, employeeId);
	        prepareStatement.executeUpdate();
	    }catch(SQLException e){
	    	e.printStackTrace();
	    }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void addSkill(int employeeId,String skill) {
		
        try{
        	connection = initializeDatabase();
            String query = "insert into skills (employee_id,skill) values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,employeeId);
            preparedStatement.setString(2,skill);
	    	preparedStatement.executeUpdate();
	    }catch(Exception ex){
	    	  ex.printStackTrace();
	    }
		
	}
	
	public void deleteSkill(int employeeId,String skill) {
        try{
        	Connection connection = initializeDatabase();
            String query = "delete from skills where employee_id=? AND skill=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,employeeId);
            preparedStatement.setString(2,skill);
	    	preparedStatement.executeUpdate();
        }catch(SQLException e){
        	e.printStackTrace();
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
        }
	}

	public List<String> getSkills(int employeeId) {
		
        try{
        	Connection connection = initializeDatabase();
            String query = "select skill from skills where employee_id=?";
            List<String> skillList = new ArrayList<String>();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,employeeId);
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	while(resultSet.next()){
	    		  skillList.add(resultSet.getString(1));
	        }
	    	return skillList;
	    }catch(Exception ex){
	    	  ex.printStackTrace();
	    }
		return null;
	}

	

}
