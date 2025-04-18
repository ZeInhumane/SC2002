package com.example.app;

// import cli.ApplicantCLI;
// import cli.OfficerCLI;
// import cli.ManagerCLI;
import com.example.app.service.AuthenticationService;
import com.example.app.service.ApplicantService;
import com.example.app.service.OfficerService;
import com.example.app.service.UserManagementService;
import com.example.app.service.ManagerService;
import com.example.app.cli.ApplicantCLI;
import com.example.app.cli.ManagerCLI;
import com.example.app.cli.OfficerCLI;
import com.example.app.models.Applicant;
import com.example.app.models.Officer;
import com.example.app.models.Manager;
// import com.example.app.service.dummy.DummyOfficerService;
// import com.example.app.service.dummy.DummyManagerService;
import com.example.app.utils.Console;

public class Main {
    public static void main(String[] args) {
        // 1) Load Excel‑based credentials
        AuthenticationService auth = new AuthenticationService();
        UserManagementService userMgmt = new UserManagementService();
        // OfficerManagementService offMgmt = new OfficerService();


        // 2) Hook up your real or dummy service implementations
        // ApplicantCLI appSvc = new ApplicantCLI(null);
        // OfficerService   offSvc = new DummyOfficerService();
        // ManagerService   mgrSvc = new DummyManagerService();

        // 3) CLI Loop
        while (true) {
            System.out.println("\n=== HDB BTO CLI ===");
            System.out.println("1) Login as Applicant");
            System.out.println("2) Login as HDB Officer");
            System.out.println("3) Login as HDB Manager");
            System.out.println("0) Exit");
            int choice = Console.readInt("Enter choice: ");

            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }

            String id = Console.readLine("User ID: ");
            String pw = Console.readLine("Password: ");
            

            switch (choice) {
                case 1:
                    if (auth.authenticateApplicant(id, pw)) {
                        System.out.println("You're logged in");
                        Applicant user = (Applicant) userMgmt.findByNric(id);
                        ApplicantService appSvc = new ApplicantService(user);
                        new ApplicantCLI(appSvc).run();
                    } else {
                        System.out.println("Invalid Applicant credentials.");
                    }
                    break;
                case 2:
                    if (auth.authenticateOfficer(id, pw)) {
                        Officer user = (Officer) userMgmt.findByNric(id);
                        OfficerService appSvc = new OfficerService(user);
                        new OfficerCLI(appSvc).run();
                    } else {
                        System.out.println("Invalid Officer credentials.");
                    }
                    break;
                case 3:
                    if (auth.authenticateManager(id, pw)) {
                        System.out.println("You're logged in");
                        Manager user = (Manager) userMgmt.findByNric(id);
                        ManagerService appSvc = new ManagerService(user);
                        new ManagerCLI(appSvc).run();
                    } else {
                        System.out.println("Invalid Manager credentials.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
