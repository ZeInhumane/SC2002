package com.example.app.serializer;

/**
 * SerializerDependency is a utility class that provides access to various serializers used in the application. It
 * follows the Singleton design pattern to ensure that only one instance of each serializer is created and reused
 * throughout the application.
 */
public class SerializerDependency {

    private static final StringSerializer stringSerializer = new StringSerializer();
    private static final UserSerializer userSerializer = new UserSerializer();
    private static final RegistrationSerializer registrationSerializer = new RegistrationSerializer();
    private static final ProjectSerializer projectSerializer = new ProjectSerializer();
    private static final EnquirySerializer enquirySerializer = new EnquirySerializer();
    private static final ApplicationSerializer applicationSerializer = new ApplicationSerializer();

    /**
     * Returns the singleton instance of the UserSerializer.
     * 
     * @return The singleton instance of UserSerializer.
     */
    public static UserSerializer getUserSerializer() {
        return userSerializer;
    }

    /**
     * Returns the singleton instance of the RegistrationSerializer.
     * 
     * @return The singleton instance of RegistrationSerializer.
     */
    public static RegistrationSerializer getRegistrationSerializer() {
        return registrationSerializer;
    }

    /**
     * Returns the singleton instance of the ProjectSerializer.
     * 
     * @return The singleton instance of ProjectSerializer.
     */
    public static ProjectSerializer getProjectSerializer() {
        return projectSerializer;
    }

    /**
     * Returns the singleton instance of the EnquirySerializer.
     * 
     * @return The singleton instance of EnquirySerializer.
     */
    public static EnquirySerializer getEnquirySerializer() {
        return enquirySerializer;
    }

    /**
     * Returns the singleton instance of the ApplicationSerializer.
     * 
     * @return The singleton instance of ApplicationSerializer.
     */
    public static ApplicationSerializer getApplicationSerializer() {
        return applicationSerializer;
    }

    /**
     * Returns the singleton instance of the StringSerializer.
     * 
     * @return The singleton instance of StringSerializer.
     */
    public static StringSerializer getStringSerializer() {
        return stringSerializer;
    }
}
