package org.casestudy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to cover basic sanity positive scenarios
 */
class EmployeeAnalysisPositiveTest {

    private EmployeeAnalysis employeeAnalysis;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        employeeAnalysis = new EmployeeAnalysis();
    }

    @Test
    void testReadEmployeeData() throws IOException {
        // Create a temporary CSV file
        File csvFile = tempDir.resolve("test_employees.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.write("id,firstName,lastName,managerId,salary\n");
            writer.write("1,John,Doe,,100000\n");
            writer.write("2,Jane,Smith,1,80000\n");
        }

        employeeAnalysis.readEmployeeData(csvFile.getAbsolutePath());

        // Use reflection to access the private employees map
        Map<Integer, Employee> employees = getEmployeesMap();

        assertEquals(2, employees.size());
        assertTrue(employees.containsKey(1));
        assertTrue(employees.containsKey(2));

        Employee john = employees.get(1);
        assertEquals("John", john.getFirstName());
        assertEquals("Doe", john.getLastName());
        assertNull(john.getManagerId());
        assertEquals(100000, john.getSalary().doubleValue(), 0.01);

        Employee jane = employees.get(2);
        assertEquals("Jane", jane.getFirstName());
        assertEquals("Smith", jane.getLastName());
        assertEquals(Integer.valueOf(1), jane.getManagerId());
        assertEquals(80000, jane.getSalary().doubleValue(), 0.01);
    }

    @Test
    void testAnalyzeReportingLineLength() {
        // Set up test data
        Map<Integer, Employee> employees = new HashMap<>();
        employees.put(1, new Employee(1, "John", "Doe", BigDecimal.valueOf(100000.0),null));
        employees.put(2, new Employee(2, "Jane", "Smith", BigDecimal.valueOf(80000.0),1));
        employees.put(3, new Employee(3, "Bob", "Johnson", BigDecimal.valueOf(60000.0),2));
        employees.put(4, new Employee(4, "Alice", "Brown", BigDecimal.valueOf(50000.0),3));
        setEmployeesMap(employees);

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        employeeAnalysis.analyzeReportingLineLength();

        // Reset System.out
        System.setOut(System.out);

        String output = outContent.toString();
        assertFalse(output.contains("has a reporting line that is long"));

        // Verify reporting line lengths
        assertEquals(0, employees.get(1).getReportingLineLength());
        assertEquals(1, employees.get(2).getReportingLineLength());
        assertEquals(2, employees.get(3).getReportingLineLength());
        assertEquals(3, employees.get(4).getReportingLineLength());
    }

    @Test
    void testAnalyzeSalary() {
        // Set up test data
        Map<Integer, Employee> employees = new HashMap<>();
        Employee ceo = new Employee(1, "John", "Doe", BigDecimal.valueOf(200000.00), null);
        Employee manager1 = new Employee(2, "Jane", "Smith", BigDecimal.valueOf(116000.00), 1);  // Modified
        Employee manager2 = new Employee(3, "Bob", "Johnson", BigDecimal.valueOf(98000.00), 1);  // Modified
        Employee emp1 = new Employee(4, "Alice", "Brown", BigDecimal.valueOf(80000.00), 2);
        Employee emp2 = new Employee(5, "Charlie", "Davis", BigDecimal.valueOf(75000.00), 2);
        Employee emp4 = new Employee(5, "Michael", "Jackson", BigDecimal.valueOf(750000.00), 2);
        Employee emp3 = new Employee(6, "Eve", "Wilson", BigDecimal.valueOf(70000.00), 3);


        ceo.getSubordinates().add(manager1);
        ceo.getSubordinates().add(manager2);
        manager1.getSubordinates().add(emp1);
        manager1.getSubordinates().add(emp2);
        manager2.getSubordinates().add(emp3);

        employees.put(1, ceo);
        employees.put(2, manager1);
        employees.put(3, manager2);
        employees.put(4, emp1);
        employees.put(5, emp2);
        employees.put(6, emp3);

        setEmployeesMap(employees);

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        employeeAnalysis.analyzeSalary();

        // Reset System.out
        System.setOut(System.out);

        String output = outContent.toString();
        assertFalse(output.contains("is earning less than they should"));
        assertFalse(output.contains("is earning more than they should"));
    }

    private Map<Integer, Employee> getEmployeesMap() {
        try {
            java.lang.reflect.Field employeesField = EmployeeAnalysis.class.getDeclaredField("employees");
            employeesField.setAccessible(true);
            return (Map<Integer, Employee>) employeesField.get(employeeAnalysis);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to access employees map: " + e.getMessage());
            return null;
        }
    }

    private void setEmployeesMap(Map<Integer, Employee> employees) {
        try {
            java.lang.reflect.Field employeesField = EmployeeAnalysis.class.getDeclaredField("employees");
            employeesField.setAccessible(true);
            employeesField.set(employeeAnalysis, employees);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set employees map: " + e.getMessage());
        }
    }
}