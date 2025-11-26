## Learn4Immigrants – Technical Documentation

### 1. Overview

Learn4Immigrants is a **Java 17 + Spring Boot** web application that helps immigrants and refugees discover educational resources (ESL, job training, college prep, citizenship support, etc.) based on their profile (age, immigration category, location).

The app lets users:
- **Sign up / log in** or browse as a **guest**
- See **recommended** and **eligible** resources
- **Save** resources (for registered users)

---

### 2. Technologies Used (What & Why)

- **Java 17**
  - What: Main programming language.
  - Why: Modern LTS Java version with good performance, strong type system, and full support from Spring Boot 3.x.

- **Spring Boot 3 (Spring MVC, embedded Tomcat)**
  - What: Framework that turns our Java code into a full web server.
  - Why:
    - Auto-configures an embedded **Tomcat** server → no need to deploy to a separate app server.
    - Simple main class (`Learn4ImmigrantsApplication`) to start everything.
    - Clear MVC structure (`controller`, `service`, domain model).

- **Spring Web (`spring-boot-starter-web`)**
  - What: REST and MVC web stack.
  - Why:
    - Easy to create controllers (`WebController`, `ApiController`) that map URLs like `/login`, `/dashboard`, `/api/save-resource`.
    - Handles HTTP, routing, JSON, and form submissions.

- **Thymeleaf (`spring-boot-starter-thymeleaf`)**
  - What: Server-side HTML templating engine.
  - Why:
    - Lets us write normal HTML (`index.html`, `login.html`, `signup.html`, `dashboard.html`, etc.) with small `th:*` attributes for dynamic data.
    - Very simple to bind data from controllers into the UI (e.g., `${user.name}`, loops for resources).
    - Great for educational/demo projects, no heavy frontend framework required.

- **Maven**
  - What: Build and dependency management tool.
  - Why:
    - `pom.xml` declares all dependencies and plugins.
    - One command (`mvn spring-boot:run`) builds and runs the app.
    - Easy to share and reproduce the project across machines.

- **Static Assets (CSS, Images)**
  - What:
    - Global styles in `src/main/resources/static/css/styles.css`
    - Images in `src/main/resources/static/images/`
  - Why:
    - Centralized styling gives the whole site a consistent, modern look (colors, buttons, nav bar, layouts).
    - Spring Boot automatically serves static files from `/static`.

- **(Optional) PostgreSQL Driver**
  - What: JDBC driver dependency in `pom.xml`.
  - Why:
    - Prepares the project for future persistence if we decide to store users/resources in a real database.
    - Currently the business logic is focused on in-memory/domain classes; the driver is there for easy expansion.

---

### 3. High-Level Architecture (How It’s Implemented)

- **Entry Point**
  - `classes.Learn4ImmigrantsApplication`
  - Annotated with `@SpringBootApplication`.
  - `SpringApplication.run(...)` starts the whole web server (Tomcat) on port `8080`.

- **Controllers**
  - `classes.controller.WebController`
    - Handles page routing:
      - `/` → home page (`index.html`)
      - `/login` (GET/POST)
      - `/signup` (GET/POST)
      - `/guest` (GET/POST)
      - `/dashboard`
      - `/guest-dashboard`
      - `/logout`
    - Coordinates with `UserService` and `ResourceService`.
  - `classes.controller.ApiController`
    - JSON endpoints under `/api`:
      - `/api/save-resource` – saves a resource for the logged-in user.
      - `/api/resources/recommended` – recommended resources for the user.
      - `/api/resources/eligible` – all eligible resources for the user.
      - `/api/resources/saved` – user’s saved list.

- **Services**
  - `classes.service.UserService`
    - Creates accounts, guest users, and handles login.
    - Encapsulates user-related business logic.
  - `classes.service.ResourceService`
    - Chooses recommended and eligible resources based on user info.
    - Central place to plug in more complex eligibility logic later.

- **Domain / Model Classes**
  - `User`, `EducationalResource`, `Provider`, `ImmigrationCategory`, `ResourceCategory`, `Requirements`, etc.
  - These represent the **business concepts** (students, providers, programs, eligibility requirements).

- **Views (Thymeleaf Templates)**
  - Located in `src/main/resources/templates/`:
    - `index.html` – main landing page.
    - `login.html`, `signup.html`, `guest.html` – forms for different flows.
    - `dashboard.html`, `guest-dashboard.html` – resource views after login/guest entry.
  - Shared elements:
    - **Top navigation bar** (Home/Login/Sign Up/Guest)
    - Consistent color palette and buttons.

- **Configuration**
  - `src/main/resources/application.properties`
    - `server.port=8080`
    - Thymeleaf and static resource paths.

---

### 4. How to Run the Application

#### Prerequisites

- **Java**: Version 17 or higher.
- **Maven**: Installed and on your PATH.

Check:

```bash
java -version
mvn -v
```

#### Run in Development Mode

From the project root:

```bash
cd /Users/ermi/Desktop/Learn4ImmigrantsGUI   # or your local path
mvn spring-boot:run
```

Then open your browser and visit:

- Home: `http://localhost:8080/`
- Login: `http://localhost:8080/login`
- Sign Up: `http://localhost:8080/signup`
- Guest: `http://localhost:8080/guest`

#### Build a JAR and Run

```bash
cd /Users/ermi/Desktop/Learn4ImmigrantsGUI
mvn clean package
java -jar target/learn4immigrants-1.0-SNAPSHOT.jar
```

Again, browse to `http://localhost:8080/`.

---

### 4.1 How Someone Else Can Run This on Their Own Machine

If another person wants to run Learn4Immigrants on their computer:

1. **Get the project files**
   - Option A: Clone from Git (if hosted):
     - `git clone <your-repo-url>`
   - Option B: Zip & share:
     - Zip the entire project folder (including `pom.xml`, `src/`, and `Documentation.md`), send it to them, and have them unzip it somewhere on their machine.

2. **Install Java and Maven**
   - Install **Java 17+** and **Maven** on their machine.
   - Verify:
     ```bash
     java -version
     mvn -v
     ```

3. **Open a terminal in the project folder**
   - Example:
     ```bash
     cd /path/to/Learn4ImmigrantsGUI
     ```

4. **Download dependencies and start the server**
   - Run:
     ```bash
     mvn spring-boot:run
     ```
   - Maven will:
     - Download all required libraries (Spring Boot, Thymeleaf, etc.) the first time.
     - Compile the code.
     - Start the embedded web server on port `8080`.

5. **Open the website**
   - In a web browser:
     - Home: `http://localhost:8080/`
     - Login: `http://localhost:8080/login`
     - Sign Up: `http://localhost:8080/signup`
     - Guest: `http://localhost:8080/guest`

6. **Stop the server**
   - Go back to the terminal running `mvn spring-boot:run` and press **CTRL + C**.

### 5. How to Expand Resources to Other Cities/States

Right now, resources are tied to **location** and **categories** in the domain model. To grow beyond the current city (e.g., Seattle), we can extend in a structured way:

#### A. Extend the Data Model

- **Provider / EducationalResource**
  - Ensure each resource has:
    - `location` (e.g., `"Seattle, WA"`, `"Portland, OR"`, `"Los Angeles, CA"`)
    - Possibly a more structured `city`, `state`, `zipcode` if needed later.
- Optionally, introduce:
  - `City` or `Region` enum / class if you want to constrain allowed locations.

#### B. Add More Resource Entries

- Add additional `EducationalResource` and `Provider` entries:
  - For each new city/state, create resources with:
    - Appropriate `ResourceCategory`
    - `Provider` (name, location)
    - URL, description, and eligibility `Requirements`.
- This can be done:
  - Initially in code (hard-coded list in a factory or service), or
  - Later in a **database** (PostgreSQL) with JPA if you want admin CRUD UI.

#### C. Update Matching Logic in `ResourceService`

In `ResourceService`, we already filter resources based on the user:
- Match by:
  - `user.location`
  - `user.immigrationCategory`
  - `user.age`, etc.

To support multiple cities/states:
- Ensure the matching logic can:
  - Compare **city/state portions** of the location string, not just exact text.
  - Optionally normalize locations (e.g., `"Seattle, WA"` vs `"Seattle"`).
- You could:
  - Parse `location` into `city` and `state` fields.
  - Filter `EducationalResource` by `city` and `state`.

#### D. (Future) Move to a Database for Scalability

When the resource list gets large:

- Use the existing PostgreSQL dependency and add:
  - JPA entities for `EducationalResource`, `Provider`, `User`, etc.
  - Spring Data repositories (e.g., `EducationalResourceRepository`).
- Benefits:
  - Resources can be added/updated without code changes.
  - Easier to create an **admin interface** for adding cities/states and resources.

---

### 6. Possible Future Enhancements

- **Search & Filters**
  - Let users filter by city, state, resource category, or provider.

- **Multi-language Support**
  - Serve pages in different languages (Spanish, Amharic, Tigrinya, etc.).

- **Admin Dashboard**
  - Web UI to add/edit/delete resources and providers per city/state.

- **Email or SMS Notifications**
  - Notify users when new resources appear in their city.

This architecture and tech stack (Java + Spring Boot + Thymeleaf + Maven) gives a strong, extensible base for growing Learn4Immigrants from one city to many regions while keeping the code understandable and maintainable. 


