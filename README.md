# BIG COMPANY Organizational Analysis

## Project Overview

This Java application analyzes the organizational structure of COMPANY based on employee data provided in a CSV file. 

## Features

The application performs the following analyses:
1. Identifies managers earning less than 20% above the average salary of their direct subordinates.
2. Identifies managers earning more than 50% above the average salary of their direct subordinates.
3. Identifies employees with more than 4 managers between them and the CEO.

## Prerequisites

- Java SE (any version)
- Maven
- JUnit (for running tests)

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/your-username/big-company-analysis.git
   ```
2. Navigate to the project directory:
   ```
   cd big-company-analysis
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
   java -jar target/big-company-analysis-1.0-SNAPSHOT.jar path/to/your/employee_data.csv
   ```

3. The analysis results will be printed to the console.

## Assumptions

1. Data Integrity:
    - The CSV file is well-formed and contains no corrupted data.
    - Each employee has a unique ID.
    - There are no circular references in the management structure.

2. CEO Identification:
    - The CEO is the only employee with no manager ID specified.
    - There is exactly one CEO in the dataset.

3. Salary:
    - All salaries are positive numbers in the same currency.

4. Management Structure:
    - Every employee (except the CEO) has exactly one manager.
    - An employee can have zero to many direct subordinates.

5. Reporting Line:
    - The reporting line length is calculated as the number of managers between an employee and the CEO, not including the employee or the CEO.

6. Salary Comparisons:
    - Only direct subordinates are considered when calculating average salaries for comparison.

7. Performance:
    - The application assumes the dataset (up to 1000 employees) can fit in memory.

For a full list of assumptions, please refer to the `ASSUMPTIONS.md` file.

## Testing

Run the tests using Maven:
```
mvn test
```

## Contributing

Please read `CONTRIBUTING.md` for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details.