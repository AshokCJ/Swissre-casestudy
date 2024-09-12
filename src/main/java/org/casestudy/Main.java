package org.casestudy;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initializing... ");
        EmployeeAnalysis employeeAnalysis = new EmployeeAnalysis();
        String fileName =args[0];
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        try {
            employeeAnalysis.readEmployeeData(fileName);
            employeeAnalysis.analyzeReportingLineLength();
            employeeAnalysis.analyzeSalary();
        }
        catch (IOException ex) {
            System.out.println("Exception occurred while reading the csv file " + ex);
        }
    }
}
