# BIG COMPANY Organizational Analysis

## Project Overview

This Java application analyzes the organizational structure of COMPANY based on employee data provided in a CSV file. 

## Features

The application performs the following analyses:
1. Identifies managers earning less than 20% above the average salary of their direct subordinates.
2. Identifies managers earning more than 50% above the average salary of their direct subordinates.
3. Identifies employees with more than 4 managers between them and the CEO.

## Prerequisites

- Java SE (version 22)
- Maven
- JUnit (for running tests)

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/AshokCJ/Swissre-casestudy.git
   ```
2. Navigate to the project directory:
   ```
   cd Swissre-casestudy
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```

## Usage

1. Prepare your CSV file with employee data. The file should have the following structure:
   ```
   Id,firstName,lastName,salary,managerId
   123,Joe,Doe,60000,
   124,Martin,Chekov,45000,123
   ...
   ```

2. Run the application:
   ```
   java -jar .\target\Swissre-casestudy-1.0-SNAPSHOT-jar-with-dependencies.jar path/to/your/employee_data.csv
   ```

3. The analysis results will be printed to the console.

## Assumptions

Following are assumptions made on the case study : 

- Each employee has a unique ID.
- First name and last name are assumed to be present in all rows.
- There are no circular references in the management structure. 
      Eg- It shouldn't be like Employee A manager is Employee B and Employee B manager is Employee A
- There is exactly one CEO in the dataset.
- The reporting line length is calculated as the number of managers between an employee and the CEO, not including the employee or the CEO.
- Only direct subordinates are considered when calculating average salaries for comparison.

## Testing

Run the tests using Maven:
```
mvn test
```
