# Programming Language Interpreter

## Overview

This project implements an interpreter for a simple programming language that supports variable assignment, scoped operations, and printing variables. The interpreter is designed with a backend that validates the input using a Spring-based server and a frontend interface where users can write, compile, and execute code in this language.

## Features

### Supported Operations
1. **Variable Assignment to Integers:**
   ```
   x = 99
   ```
   Assigns the variable `x` to the integer value `99`.

2. **Variable Assignment to Other Variables:**
   ```
   x = y
   ```
   Assigns the value of variable `y` to `x`.

3. **Scoped Operations:**
    - Open a new scope:
      ```
      scope {
      ```
    - Close the most recently opened scope:
      ```
      }
      ```
   Variables declared inside a scope are destroyed when the scope ends.

4. **Printing Variables:**
   ```
   print x
   ```
   Prints the value of variable `x`. If `x` is not defined in the current or any enclosing scope, it prints `null`.

5. **Careful to spaces:**
   The interpreter is sensitive to spaces and newlines. Make sure to follow the syntax rules.
    ```
    x=1
    ```
    is not the same as
    ```
    x = 1
    ```   

### Example Program

Given the following code:
```
 x = 1
 print x
 scope {
   x = 2
   print x
   scope {
     x = 3
     y = x
     print x
     print y
   }
   print x
   print y
 }
 print x
```

The output will be:
```
1
2
3
3
2
null
1
```

## Components

### Backend (Spring Boot)
- **Input Validation:** Ensures the program syntax is correct.
- **Execution Engine:** Parses the input code and executes the operations following the rules of the language.
- **API Endpoints:**
    - `/interpret`: Validates and executes the input code.

### Frontend
- **Code Editor:** A web page where users can write their programs.
- **Compile Button:** Sends the program to the backend for validation and execution.
- **Output Display:** Displays the output of the program.

## Installation

### Prerequisites
1. Java 11 or later
2. Maven
3. Node.js and npm

### Backend Setup
1. Clone the repository.
2. Navigate to the `backend` directory.
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Frontend Setup
1. Navigate to the `frontend` directory.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```

The frontend will be available at `http://localhost:3000`.

## Usage
1. Open the frontend in your browser.
2. Write your code in the provided editor.
3. Click the **Compile** button to validate and execute the program.
4. View the output in the results section.

## Code Structure

### Backend
- **Controller:** Handles HTTP requests.
- **Service:** Processes the input code and executes the interpreter logic.
- **Model:** Defines data structures for variables and scopes.

### Frontend
- **Components:**
    - Code Editor: Enables users to write their programs.
    - Output Display: Shows the results of the program execution.


