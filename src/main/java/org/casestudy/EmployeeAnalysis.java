package org.casestudy;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * EmployeeAnalysis class for processing and analyzing employee data.
 * This class reads employee information from a CSV file, analyzes reporting line lengths,
 * and evaluates salary ranges based on subordinates' average salaries.
 */
public class EmployeeAnalysis {

    private final Map<Integer, Employee> employees = new HashMap<>();

    /**
     * Reads employee data from a CSV file and populates the employees map.
     * @param fileName The name of the CSV file containing employee data.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public void readEmployeeData(String fileName) throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            var employeeList = new CsvToBeanBuilder<Employee>(reader)
                    .withType(Employee.class)
                    .build()
                    .parse();

            for (Employee employee : employeeList) {
                employees.put(employee.getId(), employee);
            }
        }
    }

    /**
     * Analyzes the reporting line length for each employee and prints out
     * employees with reporting lines longer than 4 levels.
     */
    public void analyzeReportingLineLength() {
        for (Employee employee : employees.values()) {
            if(employee == null) {
                System.out.println("employee info is not available.");
                continue;
            }
            if (employee.getManagerId() != null) {

                Employee manager = employees.get(employee.getManagerId());
                if (manager != null) {
                    manager.getSubordinates().add(employee);
                }

                int reportingLength = getReportingLength(employee);
                employee.setReportingLineLength(reportingLength);
                if (employee.getReportingLineLength() > 4) {
                    System.out.println(employee.getFirstName() + " " + employee.getLastName() +
                            " has a reporting line that is long by: " + (employee.getReportingLineLength() - 4));
                }
            }
        }
    }

    /**
     * Calculates the reporting line length for a given employee.
     * @param employee The employee to calculate the reporting line length for.
     * @return The length of the reporting line.
     * @throws IllegalStateException If a manager is not found for an employee.
     */
    private int getReportingLength(Employee employee) {
        Integer currentManagerId = employee.getManagerId();
        int length = 0;
        while (currentManagerId != null) {
            length++;
            Employee manager = employees.get(currentManagerId);
            if (manager == null) {
                throw new IllegalStateException("Manager not found for employee: " + employee.getId());
            }
            currentManagerId = manager.getManagerId();
        }
        return length;
    }

    /**
     * Analyzes the salary of each employee with subordinates, comparing it to the
     * average salary of their subordinates. Prints out employees whose salaries
     * are outside the expected range (1.2 to 1.5 times the average of subordinates).
     */
    public void analyzeSalary() {
        for (Employee employee : employees.values()) {
            //Salary is not analyzed if subordinates are not present for an employee
            if (employee.getManagerId() != null && !employee.getSubordinates().isEmpty()) {
                var subordinates = employee.getSubordinates();

                BigDecimal avgSalOfSubordinates = subordinates.stream()
                        .map(Employee::getSalary)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(subordinates.size()), 2, RoundingMode.HALF_UP);

                BigDecimal minSalRange = avgSalOfSubordinates.multiply(new BigDecimal("1.2"));
                BigDecimal maxSalRange = avgSalOfSubordinates.multiply(new BigDecimal("1.5"));

                if (employee.getSalary().compareTo(minSalRange) < 0) {
                    System.out.println("Employee " + employee.getFirstName() + " " + employee.getLastName()
                            + " is earning  less than they should, by: " + minSalRange.subtract(employee.getSalary()));
                }
                if (employee.getSalary().compareTo(maxSalRange) > 0) {
                    System.out.println("Employee " + employee.getFirstName() + " " + employee.getLastName()
                            + " is earning more than they should, by: " + employee.getSalary().subtract(maxSalRange));
                }
            }
        }
    }
}