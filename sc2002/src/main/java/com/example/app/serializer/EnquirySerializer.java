package com.example.app.serializer;

import com.example.app.models.Enquiry;

public class EnquirySerializer implements Serializer<Enquiry> {

    @Override
    public String serialize(Enquiry enquiry) {

        Integer questionCommas = enquiry.getQuestion().split(",").length;
        Integer responseCommas = enquiry.getResponse() == null ? 0 : enquiry.getResponse().split(",").length;
        return String.format("%d,%d,%d,%d,%s,%s,%d,%s",
                enquiry.getId(),
                enquiry.getEnquirerId(),
                enquiry.getProjectId(),
                questionCommas,
                enquiry.getQuestion(),
                enquiry.getReplierId() == null ? "" : enquiry.getReplierId().toString(),
                responseCommas,
                enquiry.getResponse( ) == null ? "" : enquiry.getResponse()
        );
    }

    @Override
    public Enquiry deserialize(String inputLine) throws RuntimeException {
        String[] parts = inputLine.split(",");
        Enquiry enquiry = new Enquiry();
        enquiry.setId(Integer.parseInt(parts[0]));
        enquiry.setEnquirerId(Integer.parseInt(parts[1]));
        enquiry.setProjectId(Integer.parseInt(parts[2]));
        int questionCommas = Integer.parseInt(parts[3]);
        StringBuilder question = new StringBuilder();
        for (int i = 4; i < 4 + questionCommas; i++) {
            question.append(parts[i]);
            if (i != 4 + questionCommas - 1) {
                question.append(",");
            }
        }
        enquiry.setQuestion(question.toString());
        enquiry.setReplierId(parts[4 + questionCommas].isEmpty() ? null : Integer.parseInt(parts[4 + questionCommas]));
        int responseCommas = Integer.parseInt(parts[5 + questionCommas]);
        StringBuilder response = new StringBuilder();
        for (int i = 6 + questionCommas; i < 6 + questionCommas + responseCommas; i++) {
            response.append(parts[i]);
            if (i != 6 + questionCommas + responseCommas - 1) {
                response.append(",");
            }
        }
        enquiry.setResponse(response.toString());
        return enquiry;
    }

}
