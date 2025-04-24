# SC2002 CLI HDB Flat Booking Application

## Overview

This is a Java-based, command-line interface (CLI) application simulating the end-to-end process of applying for and managing HDB flat bookings. It supports three user roles:

- **Applicant**: Browse projects, submit and manage flat applications, view and withdraw applications, and submit enquiries.
- **Officer**: View applicants’ booking applications, register and book flats on behalf of applicants, manage and reply to enquiries, and generate booking receipts.
- **Manager**: (Planned) Oversee projects, approvals, and officer workflows (to be implemented).

The application stores data in plain-text files (development/production modes) and uses a service–repository pattern with customizable serializers.

---

## Key Features

- **Authentication & Authorization**: Login by NRIC/password, role-based menus
- **Project Browsing & Application**: View available projects, apply for flats, track application status
- **Enquiry Management**: Create, edit, delete enquiries about projects
- **Officer Booking Workflow**: Officers can register for new projects, view pending/confirmed bookings, process applications, and generate receipts
- **Flexible Storage**: Reads/writes to `resources/db/{development|production}/*.txt` with pluggable serializers
- **Build & Test**: Maven-based build, JUnit test suite
- **UML Diagrams**: Detailed sequence, class, and package diagrams under `uml-diagrams/`

---

## Getting Started

### Prerequisites

- **Java JDK 11** or higher
- **Maven 3.6+**

### Build & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/your-org/sc2002.git
   cd sc2002
   ```

2. Build with Maven:
   ```bash
   mvn clean package
   ```

3. Run in development mode:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.app.cli.Main" -Dexec.args="--mode=development"
   ```

4. Switch to production mode by changing `--mode=production` and using the production files under `resources/db/production/`.

### Running Tests

Execute all unit tests:
```bash
mvn test
```

---

## Project Structure

```
sc2002/
├─ src/main/java/com/example/app/
│  ├─ cli/            # Command-line UI components
│  │   └─ utils/      # CLI helper classes (Helper, Readers)
│  ├─ control/        # Controllers orchestrating UI ↔ services
│  ├─ service/        # Service interfaces
│  ├─ service/impl/   # Business logic implementations
│  ├─ repository/     # Data access; serializers & repositories
│  ├─ models/         # Domain entities (Applicant, Officer, Project, etc.)
│  ├─ enums/          # Status, Role, FlatType definitions
│  ├─ exceptions/     # Custom exception types
│  └─ utils/          # Helpers for I/O, runtime configuration
├─ src/main/resources/db/
│  ├─ development/    # Text files for dev data
│  └─ production/     # Text files for live data
├─ src/test/java      # Unit tests
├─ uml-diagrams/      # PlantUML & Draw.io diagrams
└─ pom.xml            # Maven configuration
```

---

## Configuration & Data Files

- **resources/db/development/**: sample data sets (applicants, projects, registrations, enquiries, users).
- **resources/db/production/**: separate files for production mode.
- **Excel reference lists**: under `resources/` for bulk imports (ApplicantList.xlsx, ProjectList.xlsx, etc.).

Modify `Settings.java` for file paths and application mode.

---

## UML Diagrams

See the `uml-diagrams/` folder for:
- **Class Diagrams**: Domain model and package structure
- **Sequence Diagrams**: Login flow, Applicant flows, Officer enquiry/bookings workflows

---
