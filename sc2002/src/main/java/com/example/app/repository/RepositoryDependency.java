package com.example.app.repository;

public class RepositoryDependency {
    private static final UserRepository userRepository = new UserRepository();
    private static final RegistrationRepository registrationRepository = new RegistrationRepository();
    private static final ProjectRepository projectRepository = new ProjectRepository();
    private static final EnquiryRepository enquiryRepository = new EnquiryRepository();
    private static final ApplicationRepository applicationRepository = new ApplicationRepository();

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static RegistrationRepository getRegistrationRepository() {
        return registrationRepository;
    }

    public static ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public static EnquiryRepository getEnquiryRepository() {
        return enquiryRepository;
    }

    public static ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }
}
