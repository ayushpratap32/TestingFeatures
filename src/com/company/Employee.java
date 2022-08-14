package com.company;

import java.util.Objects;

public class Employee  {

    private String name;
    private String designation;
    private int experience;
    private String city;
    private long salary;

    public Employee(String name, String designation, int experience, String city, long salary) {
        this.name = name;
        this.designation = designation;
        this.experience = experience;
        this.city = city;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return experience == employee.experience && salary == employee.salary && name.equals(employee.name) && designation.equals(employee.designation) && city.equals(employee.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, designation, experience, city, salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", experience=" + experience +
                ", city='" + city + '\'' +
                ", salary=" + salary +
                '}';
    }
}
