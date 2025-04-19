package com.example.app.serializer;

public class SerializerDependency {

    private static final UserSerializer userSerializer = new UserSerializer();
    private static final RegistrationSerializer registrationSerializer = new RegistrationSerializer();
    private static final ProjectSerializer projectSerializer = new ProjectSerializer();
    private static final EnquirySerializer enquirySerializer = new EnquirySerializer();
    private static final ApplicationSerializer applicationSerializer = new ApplicationSerializer();

    public static UserSerializer getUserSerializer() {
        return userSerializer;
    }

    public static RegistrationSerializer getRegistrationSerializer() {
        return registrationSerializer;
    }

    public static ProjectSerializer getProjectSerializer() {
        return projectSerializer;
    }

    public static EnquirySerializer getEnquirySerializer() {
        return enquirySerializer;
    }

    public static ApplicationSerializer getApplicationSerializer() {
        return applicationSerializer;
    }
}
