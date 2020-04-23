package com.example.springbootzuulgatwayproxystudentservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootZuulgatwayproxyStudentServiceApplication {

	@RequestMapping(value = "/echoStudentName/{name}")
	public String echoStudentName(@PathVariable(name = "name") String name) {
		return "Hello  " + name + " Responsed on : " + new Date();
	}

	@RequestMapping(value = "/getStudentDetails/{name}")
	public Student getStudentDetails(@PathVariable(name = "name") String name) {
		return new Student(name, "Pune", "MCA");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootZuulgatwayproxyStudentServiceApplication.class, args);
	}
}

class Student {
	String name;
	String address;
	String cls;

	public Student(String name, String address, String cls) {
		super();
		this.name = name;
		this.address = address;
		this.cls = cls;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCls() {
		return cls;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("2");

		List<String> list1 = new LinkedList<String>();
		list1.add("4");
	}

}
