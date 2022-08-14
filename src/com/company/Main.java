package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

    List<Employee> employeeList=new ArrayList<>();
                employeeList.add(new Employee("John","se" ,4, "Zurich",300));
                employeeList.add(new Employee("Ace","ase" ,2, "Amsterdam",200));
                employeeList.add(new Employee("Keith", "ita",5, "Barbados",400));
                employeeList.add(new Employee("Mohammad","ta" ,14, "Zurich",500));
                employeeList.add(new Employee("Austin","md" ,22, "Florida",700));
                employeeList.add(new Employee("Stephen", "ceo",25, "Citadel",950));


        System.out.println("-----------employee sort via city");
                employeeList.stream().sorted(Comparator.comparing(Employee::getCity)).forEach(System.out::println);


        System.out.println("-----------employee salary sum");

        System.out.println(employeeList.stream().map(Employee::getSalary).reduce(Long::sum).get());

        //min salary

        System.out.println("-----------employee min salary");
        System.out.println(employeeList.stream().min(Comparator.comparing(Employee::getSalary)).get());


        //max salary

        System.out.println("-----------employee max salary");
        System.out.println(employeeList.stream().max(Comparator.comparing(Employee::getSalary)).get());


        //top 2 min salary


        System.out.println("-----------employee top 2 min salary");
        employeeList.stream().sorted(Comparator.comparing(Employee::getSalary)).limit(2).forEach(System.out::println);

        //top 2 max salary

        System.out.println("-----------employee top 2 max salary");
        employeeList.stream().sorted(Comparator.comparing(Employee::getSalary)).skip(employeeList.size()-2).limit(2).forEach(System.out::println);


        //count employee

        System.out.println("-----------employee count-----------");

        System.out.println(employeeList.stream().count());

        //find first
        System.out.println("-------------------find first---------------------");
        System.out.println(employeeList.stream().findFirst().get());

        //find any
                System.out.println("-------------------find any---------------------");
                System.out.println(employeeList.stream().findAny().get());
    }
}
