package com.example.app.serializer;

import com.example.app.exceptions.DataParsingException;
import com.example.app.models.Enquiry;

import java.util.LinkedList;
import java.util.Optional;

public class EnquirySerializer implements Serializer<Enquiry> {

    private StringSerializer stringSerializer = SerializerDependency.getStringSerializer();

    @Override
    public String serialize(Enquiry enquiry) {

        Integer responseCommas = enquiry.getResponse() == null ? 0 : enquiry.getResponse().split(",", -1).length;
        return String.format("%d,%d,%d,%s,%s,%s",
                enquiry.getId(),
                enquiry.getEnquirerId(),
                enquiry.getProjectId(),
                stringSerializer.serialize(enquiry.getQuestion()),
                enquiry.getReplierId() == null ? "" : enquiry.getReplierId().toString(),
                stringSerializer.serialize(enquiry.getResponse())
        );
    }

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
