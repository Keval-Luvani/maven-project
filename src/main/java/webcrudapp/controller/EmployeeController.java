package webcrudapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webcrudapp.model.Employee;
import webcrudapp.service.EmployeeService;
import webcrudapp.service.EmployeeServiceImpl;

public class EmployeeController extends HttpServlet {
	
	public static String getAction(HttpServletRequest request) {
    	String uri = request.getRequestURI();
	    String [] uri_list = uri.split("/");
	    String action = uri_list[uri_list.length-1];
	    return action;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);
		EmployeeService employeeService = new EmployeeServiceImpl();
		Employee employee = new Employee();
		if(action.equals("getdata")){
			System.out.println(Integer.parseInt(request.getParameter("employeeId")));
			employee = employeeService.fetchData(Integer.parseInt(request.getParameter("employeeId")));
			
			request.setAttribute("todayDate", LocalDate.now().toString());
			request.setAttribute("employee",employee);
			
			request.getRequestDispatcher("/views/EmployeeDataEntry.jsp").forward(request, response);
		}else if(action.equals("delete")){
			employeeService.deleteEmployee(Integer.parseInt(request.getParameter("employeeId")));
			response.sendRedirect("/webcrudapp/");
		}else if(action.equals("createpage")){
			request.setAttribute("todayDate", LocalDate.now().toString());
			request.getRequestDispatcher("/views/EmployeeDataEntry.jsp").forward(request, response);
		}else {
			List<Employee> employeeList = employeeService.viewEmployee();
			request.setAttribute("employeeList", employeeList);
			request.getRequestDispatcher("/views/ViewEmployee.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmployeeService employeeService = new EmployeeServiceImpl();
		Employee employee  = new Employee();
		String action = getAction(request);
		if(action.equals("submit")) {
			if(request.getParameter("employeeId").equals("")) {
				String name = request.getParameter("name");
				int age =  Integer.parseInt(request.getParameter("age"));
				String[] skillList = request.getParameterValues("skills");
				float salary = Float.parseFloat(request.getParameter("salary"));
				String joiningDate = request.getParameter("joining_date");
			    
				employee.setName(name);
			    employee.setAge(age);
			    employee.setSkillList(Arrays.asList(skillList));
				employee.setSalary(salary);
				employee.setJoiningDate(joiningDate);
				
		        employeeService.createEmployee(employee);
				
		        response.sendRedirect("/webcrudapp/");
			}else {
				int employeeId = Integer.parseInt(request.getParameter("employeeId"));
				String name = request.getParameter("name");
				int age =  Integer.parseInt(request.getParameter("age"));
				String[] skillList = request.getParameterValues("skills");
				float salary = Float.parseFloat(request.getParameter("salary"));
				String joiningDate = request.getParameter("joining_date");
			    
				employee.setEmployeeId(employeeId);
				employee.setName(name);
			    employee.setAge(age);
			    employee.setSkillList(Arrays.asList(skillList));
				employee.setSalary(salary);
				employee.setJoiningDate(joiningDate);
				employeeService.updateEmployee(employee);
				response.sendRedirect("/webcrudapp/");
			}
		}
	}
}
