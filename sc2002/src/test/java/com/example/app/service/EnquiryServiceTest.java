package com.example.app.service;

import com.example.app.models.Enquiry;
import com.example.app.repository.RepositoryDependency;
import com.example.app.service.impl.EnquiryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnquiryServiceTest {
    private EnquiryService service;

    @BeforeEach
    void setUp() throws IOException {
        RepositoryDependency.getEnquiryRepository().deleteAll();
        service = new EnquiryServiceImpl();
    }

    @Test
    void submitAndFindEnquiry() throws IOException {
        Enquiry enquiry = service.submitEnquiry("When is launch?", 1, 101);
        assertNotNull(enquiry.getId());
        assertEquals("When is launch?", enquiry.getQuestion());

        Enquiry found = service.findById(enquiry.getId());
        assertNotNull(found);
        assertEquals(enquiry.getId(), found.getId());
    }

    @Test
    void updateEnquiryQuestion_success() throws IOException {
        Enquiry e = service.submitEnquiry("Old question", 2, 201);
        Enquiry updated = service.updateEnquiryQuestion(e.getId(), "Updated question");
        assertEquals("Updated question", updated.getQuestion());
    }

    @Test
    void replyEnquiry_success() throws IOException {
        Enquiry e = service.submitEnquiry("Any updates?", 3, 301);
        Enquiry replied = service.replyEnquiry(e.getId(), 999, "We will announce soon.");
        assertEquals("We will announce soon.", replied.getResponse());
        assertEquals(999, replied.getReplierId());
    }

    @Test
    void deleteEnquiry_success() throws IOException {
        Enquiry e = service.submitEnquiry("To be deleted", 4, 401);
        service.deleteEnquiry(e.getId());
        assertNull(service.findById(e.getId()));
    }

    @Test
    void findByEnquirerAndReplier() throws IOException {
        Enquiry e1 = service.submitEnquiry("Q1", 5, 501);
        Enquiry e2 = service.submitEnquiry("Q2", 5, 502);
        service.replyEnquiry(e2.getId(), 777, "Reply to Q2");

        List<Enquiry> enquirerList = service.findByEnquirerId(501);
        assertEquals(1, enquirerList.size());
        assertEquals("Q1", enquirerList.get(0).getQuestion());

        List<Enquiry> replierList = service.findByReplierId(777);
        assertEquals(1, replierList.size());
        assertEquals("Reply to Q2", replierList.get(0).getResponse());
    }
}
