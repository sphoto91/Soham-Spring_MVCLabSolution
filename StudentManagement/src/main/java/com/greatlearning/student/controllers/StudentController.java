package com.greatlearning.student.controllers;

import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.student.models.Student;
import com.greatlearning.student.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/student")
public class StudentController {
	private StudentService studentService;
	@RequestMapping("/student-list")
	
	public String getStudents(Model theModel){
		List<Student> students = studentService.findAll();
		System.out.println(students);
		theModel.addAttribute("ListOfStudents", students);
		return "list-student";
	}

	@RequestMapping("/update")
	public String showFormforUpdate(@RequestParam("student_id") int theId, Model theModel) {
		Student students = studentService.findById(theId);
		theModel.addAttribute("ListOfStudents", students);
		return "student-form";
	
	}
	@RequestMapping("/delete")
	public String deleteStudent(@RequestParam("student_id") int theId, Model theModel) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		studentService.deleteById(theId);
		return "redirect/student/student-list";
	}
	@PostMapping("/save")
	public String addStudent(@RequestParam("id") int theId, @RequestParam("name") String name,
			@RequestParam("department") String department, @RequestParam("country") String country) throws SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
		System.out.println(theId);
		Student theStudent;
		if (theId!=0) {
			theStudent = studentService.findById(theId);
			theStudent.setName(name);
			theStudent.setDepartment(department);
			theStudent.setCountry(country);
		}
		/*else {
			theStudent = new Student();
			studentService.save(theStudent);
		}*/
		return "redirect/student/student-list";
	}
}
