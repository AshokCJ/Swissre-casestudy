package org.casestudy;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the organization.
 * This class is designed to be compatible with CSV parsing using OpenCSV.
 */
public class Employee {

    @CsvBindByName(column = "Id")
    private int id;

    @CsvBindByName
    private String firstName;

    @CsvBindByName
    private String lastName;

    @CsvBindByName
    private BigDecimal salary;

    @CsvBindByName
    private Integer managerId;

    @CsvIgnore
    private List<Employee> subordinates = new ArrayList<>();

    @CsvIgnore
    private int reportingLineLength = 0;

    // No-argument constructor
    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, BigDecimal salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public int getReportingLineLength() {
        return reportingLineLength;
    }

    public void setReportingLineLength(int reportingLineLength) {
        this.reportingLineLength = reportingLineLength;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", managerId=" + managerId +
                ", subordinates=" + subordinates +
                ", reportingLineLength=" + reportingLineLength +
                '}';
    }
}
