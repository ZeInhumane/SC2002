package com.example.app.repository;

/**
 * RepositoryDependency is a singleton class that provides access to various repository instances.
 * It ensures that only one instance of each repository is created and reused throughout the application.
 *
 */
public class RepositoryDependency {

    private static final UserRepository userRepository = new UserRepository();
    private static final RegistrationRepository registrationRepository = new RegistrationRepository();
    private static final ProjectRepository projectRepository = new ProjectRepository();
    private static final EnquiryRepository enquiryRepository = new EnquiryRepository();
    private static final ApplicationRepository applicationRepository = new ApplicationRepository();

    /**
     * Returns the singleton instance of the UserRepository.
     * @return The singleton instance of UserRepository.
     */
    public static UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Returns the singleton instance of the RegistrationRepository.
     * @return The singleton instance of RegistrationRepository.
     */
    public static RegistrationRepository getRegistrationRepository() {
        return registrationRepository;
    }

    /**
     * Returns the singleton instance of the ProjectRepository.
     * @return The singleton instance of ProjectRepository.
     */
    public static ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    /**
     * Returns the singleton instance of the EnquiryRepository.
     * @return The singleton instance of EnquiryRepository.
     */
    public static EnquiryRepository getEnquiryRepository() {
        return enquiryRepository;
    }

    /**
     * Returns the singleton instance of the ApplicationRepository.
     * @return The singleton instance of ApplicationRepository.
     */
    public static ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }
}
