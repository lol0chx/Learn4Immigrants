# How to Run the Spring Boot Web Application

## Option 1: Run from IntelliJ IDEA (Easiest)

1. **Open the project** in IntelliJ IDEA
2. **Find the main class**: `src/main/java/classes/Learn4ImmigrantsApplication.java`
3. **Right-click** on `Learn4ImmigrantsApplication.java`
4. **Select "Run 'Learn4ImmigrantsApplication.main()'"**
5. **Wait for the application to start** - you'll see output like:
   ```
   Started Learn4ImmigrantsApplication in X.XXX seconds
   ```
6. **Open your browser** and go to: **http://localhost:8080**

## Option 2: Install Maven and Run from Terminal

### Install Maven:
```bash
# On macOS, install Homebrew first (if not installed):
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Then install Maven:
brew install maven
```

### Run the application:
```bash
cd /Users/ermi/Desktop/Learn4Immigrants
mvn spring-boot:run
```

Then visit: **http://localhost:8080**

## Option 3: Build and Run JAR

```bash
# Build the application
mvn clean package

# Run the JAR
java -jar target/learn4immigrants-1.0-SNAPSHOT.jar
```

## Troubleshooting

- **Port 8080 already in use?** 
  - Change the port in `src/main/resources/application.properties`:
    ```
    server.port=8081
    ```
  - Then visit http://localhost:8081

- **Database connection errors?**
  - Make sure PostgreSQL is running
  - Check database credentials in `EducationalResourceRepository.java`

- **Application won't start?**
  - Check that Java 17+ is installed: `java -version`
  - Make sure all dependencies are downloaded (IntelliJ should do this automatically)

