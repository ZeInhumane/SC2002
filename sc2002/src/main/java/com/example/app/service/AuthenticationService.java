package com.example.app.service;

import com.example.app.models.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class AuthenticationService {
    private final Map<String, String> applicantCreds = new HashMap<>();
    private final Map<String, String> officerCreds = new HashMap<>();
    private final Map<String, String> managerCreds = new HashMap<>();

    private static final UserManagementService userMgmt = new UserManagementService();
    private static final ProjectService projectService = new ProjectService();

    public AuthenticationService() {
        try {
            loadUsersFromExcel("ApplicantList.xlsx", Role.APPLICANT, applicantCreds);
            loadUsersFromExcel("OfficerList.xlsx", Role.OFFICER, officerCreds);
            loadUsersFromExcel("ManagerList.xlsx", Role.MANAGER, managerCreds);
            System.out.println(userMgmt.listUsers());
            loadProjectsFromExcel("ProjectList.xlsx");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load initial data", e);
        }
    }

    private void loadUsersFromExcel(String filename, Role role, Map<String, String> credMap) throws Exception {
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(filename);
                Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue;

                String name = row.getCell(0).getStringCellValue().trim();
                String nric = row.getCell(1).getStringCellValue().trim();
                int age = (int) row.getCell(2).getNumericCellValue();
                MaritalStatus ms = MaritalStatus.valueOf(row.getCell(3).getStringCellValue().trim().toUpperCase());
                String password = row.getCell(4).getStringCellValue().trim();
                String email = "placeholder@example.com";

                User user = switch (role) {
                    case APPLICANT -> new Applicant(name, password, email, role, nric, age, ms);
                    case OFFICER -> new Officer(name, password, email, role, nric, age, ms);
                    case MANAGER -> new Manager(name, password, email, role, nric, age, ms);
                };

                userMgmt.createUser(user);
                credMap.put(nric, password);
            }
        }
    }

    private void loadProjectsFromExcel(String filename) throws Exception {
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(filename);
                Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheetAt(0);
            boolean skipHeader = true;

            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String projectName = row.getCell(0).getStringCellValue().trim();
                String neighborhood = row.getCell(1).getStringCellValue().trim();
                String type1Str = row.getCell(2).getStringCellValue().trim();
                int type1Qty = (int) row.getCell(3).getNumericCellValue();
                String type2Str = row.getCell(5).getStringCellValue().trim();
                int type2Qty = (int) row.getCell(6).getNumericCellValue();
                int managerId = (int) row.getCell(10).getNumericCellValue();

                Date openDate = row.getCell(8).getDateCellValue();
                Date closeDate = row.getCell(9).getDateCellValue();

                int officerSlots = (int) row.getCell(11).getNumericCellValue(); // Not used yet

                MaritalStatus group = MaritalStatus.SINGLE;

                Map<FlatType, Integer> flatMap = new HashMap<>();
                flatMap.put(FlatType.valueOf(normalizeFlat(type1Str)), type1Qty);
                flatMap.put(FlatType.valueOf(normalizeFlat(type2Str)), type2Qty);

                projectService.createProject(
                        projectName,
                        openDate,
                        closeDate,
                        neighborhood,
                        group,
                        flatMap,
                        managerId // managerId placeholder
                );
            }

            System.out.println("Project list loaded.");
            System.out.println(projectService.findAll());

        } catch (Exception e) {
            System.err.println("Failed to load project list: " + e.getMessage());
            throw e;
        }
    }

    private String normalizeFlat(String s) {
        return "_" + s.toUpperCase().replace("-", "").replace(" ", "");
    }

    private Date parseDate(String dateStr) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
    }

    private String getCellAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public boolean authenticateApplicant(String nric, String pw) {
        return pw.equals(applicantCreds.get(nric));
    }

    public boolean authenticateOfficer(String nric, String pw) {
        System.out.println("CHECK THIS");
        System.out.println(officerCreds);
        System.out.println(nric);
        System.out.println(pw);
        return pw.equals(officerCreds.get(nric));
    }

    public boolean authenticateManager(String nric, String pw) {
        return pw.equals(managerCreds.get(nric));
    }
}
