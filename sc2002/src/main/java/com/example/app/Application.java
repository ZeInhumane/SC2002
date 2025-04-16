package com.example.app;

import com.example.app.controller.ApplicationController;
import com.example.app.controller.EnquiryController;
import com.example.app.controller.RegistrationController;
import com.example.app.controller.UserController;
import com.example.app.controller.ProjectController;
import com.example.app.models.Applicant;
import com.example.app.models.ApplicationStatus;
import com.example.app.models.Enquiry;
import com.example.app.models.Manager;
import com.example.app.models.Officer;
import com.example.app.models.Project;
// import com.example.app.models.ReStatus;
import com.example.app.models.Registration;
import com.example.app.models.User;
import com.example.app.repository.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Application {

    // All controller beans are autowired at the top.
    // @Autowired private UserController userController;
    // @Autowired private ManagerController managerController;
    // @Autowired private OfficerController officerController;
    // @Autowired private ApplicantController applicantController;
    // @Autowired private ProjectController projectController;
    // @Autowired private RegistrationController registrationController;
    // @Autowired private ApplicationController applicationController;
    // @Autowired private EnquiryController enquiryController;

    public static void main(String[] args) {
        System.out.println("App started!");
    }

    /**
     * The run() method starts a CLI loop where the user selects the entity to work with.
     * This version makes direct calls to the controller methods.
     */
    public void run(String... args) throws Exception {


        // Scanner scanner = new Scanner(System.in);
        // boolean exit = false;
        // while (!exit) {
        //     System.out.println("\n==== Main Menu ====");
        //     System.out.println("1. Manage Users");
        //     System.out.println("2. Manage Managers");
        //     System.out.println("3. Manage Officers");
        //     System.out.println("4. Manage Applicants");
        //     System.out.println("5. Manage Projects");
        //     System.out.println("6. Manage Registrations");
        //     System.out.println("7. Manage Applications");
        //     System.out.println("8. Manage Enquiries");
        //     System.out.println("0. Exit");
        //     System.out.print("Enter your option: ");
        //     int option = Integer.parseInt(scanner.nextLine());
        //     switch (option) {
        //         case 1: manageUsers(scanner); break;
        //         case 2: manageManagers(scanner); break;
        //         case 3: manageOfficers(scanner); break;
        //         case 4: manageApplicants(scanner); break;
        //         case 5: manageProjects(scanner); break;
        //         case 6: manageRegistrations(scanner); break;
        //         case 7: manageApplications(scanner); break;
        //         case 8: manageEnquiries(scanner); break;
        //         case 0: exit = true; break;
        //         default: System.out.println("Invalid option. Please try again.");
        //     }
        // }
        // System.out.println("Exiting CLI application.");
        // scanner.close();
    }
    
    // // -------------------------------
    // // User Management Submenu (via Controller)
    // // -------------------------------
    // private void manageUsers(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Users ---");
    //         System.out.println("1. List All Users");
    //         System.out.println("2. Create a User");
    //         System.out.println("3. Get User by ID");
    //         System.out.println("4. Delete User");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<User> users = userController.getAllUsers();
    //                 for (User user : users) {
    //                     System.out.println("ID: " + user.getId() +
    //                             ", Username: " + user.getUsername() +
    //                             ", Email: " + user.getEmail());
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter username: ");
    //                 String username = scanner.nextLine();
    //                 System.out.print("Enter password: ");
    //                 String password = scanner.nextLine();
    //                 System.out.print("Enter email: ");
    //                 String email = scanner.nextLine();
    //                 // For simplicity, we pass null for Role.
    //                 User newUser = new User(username, password, email, null);
    //                 User createdUser = userController.createUser(newUser);
    //                 System.out.println("User created with ID: " + createdUser.getId());
    //                 break;
    //             case 3:
    //                 System.out.print("Enter user ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 User user = userController.getUser(id);
    //                 if (user != null) {
    //                     System.out.println("User Details - ID: " + user.getId() +
    //                             ", Username: " + user.getUsername() +
    //                             ", Email: " + user.getEmail());
    //                 } else {
    //                     System.out.println("User not found!");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter user ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 userController.deleteUser(delId);
    //                 System.out.println("User deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option. Please try again.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Manager Management Submenu (via Controller)
    // // -------------------------------
    // private void manageManagers(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Managers ---");
    //         System.out.println("1. List All Managers");
    //         System.out.println("2. Create a Manager");
    //         System.out.println("3. Get Manager by ID");
    //         System.out.println("4. Delete Manager");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<Manager> managers = managerController.getAllManagers();
    //                 for (Manager m : managers) {
    //                     System.out.println("ID: " + m.getId() +
    //                             ", Username: " + m.getUsername() +
    //                             ", Department: " + m.getDepartmentName());
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter username: ");
    //                 String username = scanner.nextLine();
    //                 System.out.print("Enter password: ");
    //                 String password = scanner.nextLine();
    //                 System.out.print("Enter email: ");
    //                 String email = scanner.nextLine();
    //                 System.out.print("Enter department name: ");
    //                 String dept = scanner.nextLine();
    //                 Manager newManager = new Manager(username, password, email, null, dept);
    //                 Manager createdManager = managerController.createManager(newManager);
    //                 System.out.println("Manager created with ID: " + createdManager.getId());
    //                 break;
    //             case 3:
    //                 System.out.print("Enter manager ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 Manager m = managerController.getManager(id);
    //                 if (m != null) {
    //                     System.out.println("Manager ID: " + m.getId() +
    //                             ", Username: " + m.getUsername() +
    //                             ", Department: " + m.getDepartmentName());
    //                 } else {
    //                     System.out.println("Manager not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter manager ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 managerController.deleteManager(delId);
    //                 System.out.println("Manager deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Officer Management Submenu (via Controller)
    // // -------------------------------
    // private void manageOfficers(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Officers ---");
    //         System.out.println("1. List All Officers");
    //         System.out.println("2. Create an Officer");
    //         System.out.println("3. Get Officer by ID");
    //         System.out.println("4. Delete Officer");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<Officer> officers = officerController.getAllOfficers();
    //                 for (Officer o : officers) {
    //                     System.out.println("ID: " + o.getId() +
    //                             ", Username: " + o.getUsername() +
    //                             ", Rank: " + o.getRank());
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter username: ");
    //                 String username = scanner.nextLine();
    //                 System.out.print("Enter password: ");
    //                 String password = scanner.nextLine();
    //                 System.out.print("Enter email: ");
    //                 String email = scanner.nextLine();
    //                 System.out.print("Enter rank: ");
    //                 String rank = scanner.nextLine();
    //                 Officer newOfficer = new Officer(username, password, email, null, rank);
    //                 Officer createdOfficer = officerController.createOfficer(newOfficer);
    //                 System.out.println("Officer created with ID: " + createdOfficer.getId());
    //                 break;
    //             case 3:
    //                 System.out.print("Enter officer ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 Officer o = officerController.getOfficer(id);
    //                 if (o != null) {
    //                     System.out.println("Officer ID: " + o.getId() +
    //                             ", Username: " + o.getUsername() +
    //                             ", Rank: " + o.getRank());
    //                 } else {
    //                     System.out.println("Officer not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter officer ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 officerController.deleteOfficer(delId);
    //                 System.out.println("Officer deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Applicant Management Submenu (via Controller)
    // // -------------------------------
    // private void manageApplicants(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Applicants ---");
    //         System.out.println("1. List All Applicants");
    //         System.out.println("2. Create an Applicant");
    //         System.out.println("3. Get Applicant by ID");
    //         System.out.println("4. Delete Applicant");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<Applicant> applicants = applicantController.getAllApplicants();
    //                 for (Applicant a : applicants) {
    //                     System.out.println("ID: " + a.getId() +
    //                             ", Username: " + a.getUsername() +
    //                             ", Resume: " + a.getResumeLink());
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter username: ");
    //                 String username = scanner.nextLine();
    //                 System.out.print("Enter password: ");
    //                 String password = scanner.nextLine();
    //                 System.out.print("Enter email: ");
    //                 String email = scanner.nextLine();
    //                 System.out.print("Enter resume link: ");
    //                 String resume = scanner.nextLine();
    //                 Applicant newApplicant = new Applicant(username, password, email, null, resume);
    //                 Applicant createdApplicant = applicantController.createApplicant(newApplicant);
    //                 System.out.println("Applicant created with ID: " + createdApplicant.getId());
    //                 break;
    //             case 3:
    //                 System.out.print("Enter applicant ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 Applicant a = applicantController.getApplicant(id);
    //                 if (a != null) {
    //                     System.out.println("Applicant ID: " + a.getId() +
    //                             ", Username: " + a.getUsername() +
    //                             ", Resume: " + a.getResumeLink());
    //                 } else {
    //                     System.out.println("Applicant not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter applicant ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 applicantController.deleteApplicant(delId);
    //                 System.out.println("Applicant deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Project Management Submenu (via Controller)
    // // -------------------------------
    // private void manageProjects(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Projects ---");
    //         System.out.println("1. List All Projects");
    //         System.out.println("2. Create a Project");
    //         System.out.println("3. Get Project by ID");
    //         System.out.println("4. Delete Project");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<Project> projects = projectController.getAllProjects();
    //                 for (Project p : projects) {
    //                     System.out.println("ID: " + p.getId() +
    //                             ", Name: " + p.getProjectName() +
    //                             ", Status: " + p.getProjectStatus());
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter project name: ");
    //                 String projectName = scanner.nextLine();
    //                 System.out.print("Enter application open date (yyyy-MM-dd): ");
    //                 String openDateStr = scanner.nextLine();
    //                 System.out.print("Enter application close date (yyyy-MM-dd): ");
    //                 String closeDateStr = scanner.nextLine();
    //                 System.out.print("Enter visibility (true/false): ");
    //                 Boolean visibility = Boolean.parseBoolean(scanner.nextLine());
    //                 System.out.print("Enter project status (PENDING, BOOKED, SUCCESSFUL, UNSUCCESSFUL): ");
    //                 String statusStr = scanner.nextLine().trim().toUpperCase();
    //                 try {
    //                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //                     Date openDate = sdf.parse(openDateStr);
    //                     Date closeDate = sdf.parse(closeDateStr);
    //                     ProjectStatus status = ProjectStatus.valueOf(statusStr);
    //                     // For simplicity, manager and officer are left as null.
    //                     Project newProject = new Project(projectName, openDate, closeDate, visibility, status, null, null);
    //                     Project createdProject = projectController.createProject(newProject);
    //                     System.out.println("Project created with ID: " + createdProject.getId());
    //                 } catch (Exception e) {
    //                     System.out.println("Invalid input for dates or status. " + e.getMessage());
    //                 }
    //                 break;
    //             case 3:
    //                 System.out.print("Enter project ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 Project p = projectController.getProject(id);
    //                 if (p != null) {
    //                     System.out.println("Project ID: " + p.getId() +
    //                             ", Name: " + p.getProjectName() +
    //                             ", Status: " + p.getProjectStatus());
    //                 } else {
    //                     System.out.println("Project not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter project ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 projectController.deleteProject(delId);
    //                 System.out.println("Project deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Registration Management Submenu (via Controller)
    // // -------------------------------
    // private void manageRegistrations(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Registrations ---");
    //         System.out.println("1. List All Registrations");
    //         System.out.println("2. Create a Registration");
    //         System.out.println("3. Get Registration by ID");
    //         System.out.println("4. Delete Registration");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<Registration> regs = registrationController.getAllRegistrations();
    //                 for (Registration r : regs) {
    //                     System.out.println("ID: " + r.getId() +
    //                             ", User ID: " + (r.getUser() != null ? r.getUser().getId() : "null") +
    //                             ", Project ID: " + (r.getProject() != null ? r.getProject().getId() : "null"));
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter user ID: ");
    //                 Long userId = Long.parseLong(scanner.nextLine());
    //                 System.out.print("Enter project ID: ");
    //                 Long projId = Long.parseLong(scanner.nextLine());
    //                 Date now = new Date();
    //                 User user = userController.getUser(userId);
    //                 Project project = projectController.getProject(projId);
    //                 Registration newReg = new Registration(user, project, now);
    //                 Registration createdReg = registrationController.createRegistration(newReg);
    //                 System.out.println("Registration created with ID: " + createdReg.getId());
    //                 break;
    //             case 3:
    //                 System.out.print("Enter registration ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 Registration r = registrationController.getRegistration(id);
    //                 if (r != null) {
    //                     System.out.println("Registration ID: " + r.getId() +
    //                             ", User ID: " + (r.getUser() != null ? r.getUser().getId() : "null") +
    //                             ", Project ID: " + (r.getProject() != null ? r.getProject().getId() : "null"));
    //                 } else {
    //                     System.out.println("Registration not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter registration ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 registrationController.deleteRegistration(delId);
    //                 System.out.println("Registration deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Application Management Submenu (via Controller)
    // // -------------------------------
    // private void manageApplications(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Applications ---");
    //         System.out.println("1. List All Applications");
    //         System.out.println("2. Create an Application");
    //         System.out.println("3. Get Application by ID");
    //         System.out.println("4. Delete Application");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<com.example.app.models.Application> apps = applicationController.getAllApplications();
    //                 for (com.example.app.models.Application a : apps) {
    //                     System.out.println("ID: " + a.getId() +
    //                             ", Applicant ID: " + (a.getApplicant() != null ? a.getApplicant().getId() : "null") +
    //                             ", Project ID: " + (a.getProject() != null ? a.getProject().getId() : "null") +
    //                             ", Status: " + a.getStatus());
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter applicant ID: ");
    //                 Long applicantId = Long.parseLong(scanner.nextLine());
    //                 System.out.print("Enter project ID: ");
    //                 Long projId = Long.parseLong(scanner.nextLine());
    //                 System.out.print("Enter application status (FOUND, BOOKED, SUCCESSFUL, UNSUCCESSFUL): ");
    //                 String appStatusStr = scanner.nextLine().trim().toUpperCase();
    //                 try {
    //                     // Since we're using the string directly in the constructor, we don't need to convert to enum
    //                     Applicant applicant = applicantController.getApplicant(applicantId);
    //                     Project project = projectController.getProject(projId);
    //                     // Convert string to enum before creating the application
    //                     ApplicationStatus status = ApplicationStatus.valueOf(appStatusStr);
    //                     com.example.app.models.Application newApp = new com.example.app.models.Application(applicant, project, status);
    //                     com.example.app.models.Application createdApp = applicationController.createApplication(newApp);
    //                     System.out.println("Application created with ID: " + createdApp.getId());
    //                 } catch (Exception e) {
    //                     System.out.println("Invalid status input.");
    //                 }
    //                 break;
    //             case 3:
    //                 System.out.print("Enter application ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 com.example.app.models.Application a = applicationController.getApplication(id);
    //                 if (a != null) {
    //                     System.out.println("Application ID: " + a.getId() +
    //                             ", Applicant ID: " + (a.getApplicant() != null ? a.getApplicant().getId() : "null") +
    //                             ", Project ID: " + (a.getProject() != null ? a.getProject().getId() : "null") +
    //                             ", Status: " + a.getStatus());
    //                 } else {
    //                     System.out.println("Application not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter application ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 applicationController.deleteApplication(delId);
    //                 System.out.println("Application deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
    //     }
    // }

    // // -------------------------------
    // // Enquiry Management Submenu (via Controller)
    // // -------------------------------
    // private void manageEnquiries(Scanner scanner) {
    //     boolean back = false;
    //     while (!back) {
    //         System.out.println("\n--- Manage Enquiries ---");
    //         System.out.println("1. List All Enquiries");
    //         System.out.println("2. Create an Enquiry");
    //         System.out.println("3. Get Enquiry by ID");
    //         System.out.println("4. Delete Enquiry");
    //         System.out.println("0. Back to Main Menu");
    //         System.out.print("Enter option: ");
    //         int option = Integer.parseInt(scanner.nextLine());
    //         switch (option) {
    //             case 1:
    //                 List<Enquiry> enqs = enquiryController.getAllEnquiries();
    //                 for (Enquiry enq : enqs) {
    //                     System.out.println("ID: " + enq.getId() +
    //                             ", Question: " + enq.getQuestion() +
    //                             ", Posted By User ID: " + (enq.getPostedBy() != null ? enq.getPostedBy().getId() : "null"));
    //                 }
    //                 break;
    //             case 2:
    //                 System.out.print("Enter question: ");
    //                 String question = scanner.nextLine();
    //                 System.out.print("Enter response: ");
    //                 String response = scanner.nextLine();
    //                 System.out.print("Enter posted by user ID: ");
    //                 Long userId = Long.parseLong(scanner.nextLine());
    //                 User postedBy = userController.getUser(userId);
    //                 Enquiry newEnq = new Enquiry(question, response, postedBy);
    //                 Enquiry createdEnq = enquiryController.createEnquiry(newEnq);
    //                 System.out.println("Enquiry created with ID: " + createdEnq.getId());
    //                 break;
    //             case 3:
    //                 System.out.print("Enter enquiry ID: ");
    //                 Long id = Long.parseLong(scanner.nextLine());
    //                 Enquiry enq = enquiryController.getEnquiry(id);
    //                 if (enq != null) {
    //                     System.out.println("Enquiry ID: " + enq.getId() +
    //                             ", Question: " + enq.getQuestion() +
    //                             ", Response: " + enq.getResponse());
    //                 } else {
    //                     System.out.println("Enquiry not found.");
    //                 }
    //                 break;
    //             case 4:
    //                 System.out.print("Enter enquiry ID to delete: ");
    //                 Long delId = Long.parseLong(scanner.nextLine());
    //                 enquiryController.deleteEnquiry(delId);
    //                 System.out.println("Enquiry deleted (if existed).");
    //                 break;
    //             case 0:
    //                 back = true;
    //                 break;
    //             default:
    //                 System.out.println("Invalid option.");
    //         }
        // }
    // }
}
