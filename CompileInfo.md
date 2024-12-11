
# Compile and Run Instructions for OnlineResourceManagementSys

---

## Prerequisites
1. Ensure **Java 21** (or later) is installed on your system.
    - Verify using the command: `java -version`
    - Example output:
      ```
      java version "21.0.5"
      ```

2. Ensure **Maven** is installed and configured.
    - Verify using the command: `mvn -v`
    - Example output:
      ```
      Apache Maven 3.9.9
      Java version: 21.0.5
      ```

---

## To Compile the Application
1. Navigate to the root directory of the project:
   ```bash
   cd C:\Users\[user]\[dir]\OnlineResourceManagementSys
   ```

   Replace `[user]` with your username and `[dir]` with the parent directory of the project.

2. Run the following Maven command to compile the application:
   ```bash
   mvn compile
   ```

    - This will compile all the source files into `.class` files located in the `target/classes/` directory.

---

## To Package the Application
1. Run the following Maven command to package the application into a JAR file:
   ```bash
   mvn package
   ```

2. This will create a runnable JAR file at:
   ```
   target/OnlineResourceManagementSys-0.0.1-SNAPSHOT.jar
   ```

---

## To Run the Application
1. Navigate to the `target` directory:
   ```bash
   cd target
   ```

2. Run the application using the following command:
   ```bash
   java -jar OnlineResourceManagementSys-0.0.1-SNAPSHOT.jar
   ```

---

## Notes
1. **Data Storage**:
    - User and resource data are stored in `.ser` files under the `data/` directory:
        - Users: `data/users/*.ser`
        - Resources: `data/resources/*.ser`
    - Encryption keys are stored in `data/secretKey.ser`.

2. **Testing**:
    - Unit tests are included in the project and can be run with the following command:
      ```bash
      mvn test
      ```
    - Test results are available in `target/surefire-reports/`.

3. **Dependencies**:
    - External dependencies are bundled into the runnable JAR file. There’s no need to install them separately.

---

## Troubleshooting
1. If the application does not start, ensure the following:
    - Java 21 is correctly installed and configured.
    - The `OnlineResourceManagementSys-0.0.1-SNAPSHOT.jar` file exists in the `target` directory.

2. If issues persist, clean and rebuild the project:
   ```bash
   mvn clean package
   ```

---

## Example Commands
1. **Compile the application**:
   ```bash
   mvn compile
   ```

2. **Package the application**:
   ```bash
   mvn package
   ```

3. **Run the application**:
   ```bash
   java -jar target/OnlineResourceManagementSys-0.0.1-SNAPSHOT.jar
   ```

---

**Prepared by:** Eric Muganga  
**Date:** 22/11/2024
