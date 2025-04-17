package com.example.app.models;
import java.util.HashSet;
import java.util.Set;



public class Applicant extends User implements FlatTypeAssignable {

    private int applicationId = -1;
    private FlatType flatType;
    private Set<Integer> pastEnquiries = new HashSet<>();

    public Applicant() { }

    public Applicant(String name, String password, String email, Role role, String nric, int age, MaritalStatus maritalStatus) {
        super(name, password, email, role, nric, age, maritalStatus);
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public FlatType getFlatType() {
        return flatType;
    }

    @Override
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public void addToPastEnquiries(int enquiryId) {
        pastEnquiries.add(enquiryId);
    }

    public void removeFromPastEnquiries(int enquiryId) {
        pastEnquiries.remove(enquiryId);
    }

    public Set<Integer> getPastEnquiries() {
        return pastEnquiries;
    }
}
