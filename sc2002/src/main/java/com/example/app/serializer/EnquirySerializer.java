package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Enquiry;

import java.util.LinkedList;

/**
 *
 * Serializer for Enquiry objects. This class implements the Serializer interface and provides methods to serialize and
 * deserialize Enquiry objects.
 *
 * @see Serializer
 * @see Enquiry
 */
public class EnquirySerializer implements Serializer<Enquiry> {

    private StringSerializer stringSerializer = SerializerDependency.getStringSerializer();

    /**
     * Serializes an Enquiry object into a string format.
     * 
     * @param enquiry
     *            The Enquiry object to serialize.
     * @return The serialized string representation of the Enquiry object.
     */
    @Override
    public String serialize(Enquiry enquiry) {
        Integer responseCommas = enquiry.getResponse() == null ? 0 : enquiry.getResponse().split(",", -1).length;
        return String.format("%d,%d,%d,%s,%s,%s", enquiry.getId(), enquiry.getEnquirerId(), enquiry.getProjectId(),
                stringSerializer.serialize(enquiry.getQuestion()),
                enquiry.getReplierId() == null ? "" : enquiry.getReplierId().toString(),
                stringSerializer.serialize(enquiry.getResponse()));
    }

    /**
     * Deserializes a string representation of an Enquiry object into an Enquiry object.
     * 
     * @param parts
     *            A LinkedList of strings representing the parts of the Enquiry object.
     * @return The deserialized Enquiry object.
     * @throws DataParsingException
     *             If there is an error during parsing.
     */
    @Override
    public Enquiry deserialize(LinkedList<String> parts) throws DataParsingException {
        Enquiry enquiry = new Enquiry();
        enquiry.setId(Integer.parseInt(parts.removeFirst().trim()));
        enquiry.setEnquirerId(Integer.parseInt(parts.removeFirst().trim()));
        enquiry.setProjectId(Integer.parseInt(parts.removeFirst().trim()));
        enquiry.setQuestion(stringSerializer.deserialize(parts));
        enquiry.setReplierId(parseIntOrNull(parts.removeFirst()));
        enquiry.setResponse(stringSerializer.deserialize(parts));
        return enquiry;
    }

}
