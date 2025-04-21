package com.example.app.repository;

import com.example.app.models.Enquiry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnquiryRepositoryTest extends GeneralRepositoryTestBase<Enquiry> {

    private final EnquiryRepository enquiryRepository = new EnquiryRepository();

    @Override
    protected GeneralRepository<Enquiry> getRepository() {
        return enquiryRepository;
    }

    @Override
    protected List<Enquiry> createSampleEntities() {
        return List.of(
                new Enquiry(null, "When is the project open? I have heard they are open next month, but I am not quite sure,,,", 101, 1),
                new Enquiry(null, "How many units left?", 101, 2),
                new Enquiry(null, "Is this project near MRT?", 102, 1)
        );
    }

    @Override
    protected List<Enquiry> saveSampleEntities() throws IOException {
        List<Enquiry> enquiries = createSampleEntities();
        for (Enquiry e : enquiries) {
            enquiryRepository.save(e);
        }

        // Simulate replies (optional)
        enquiries.get(0).setResponse("It opens next month.");
        enquiries.get(0).setReplierId(10);
        enquiryRepository.save(enquiries.get(0));

        enquiries.get(1).setResponse("Only 5 left.");
        enquiries.get(1).setReplierId(10);
        enquiryRepository.save(enquiries.get(1));

        return enquiries;
    }

    @Test
    public void testFindByEnquirerId() throws IOException {
        saveSampleEntities();

        List<Enquiry> byUser1 = enquiryRepository.findByEnquirerId(1);
        assertEquals(2, byUser1.size());

        List<Enquiry> byUser2 = enquiryRepository.findByEnquirerId(2);
        assertEquals(1, byUser2.size());

        List<Enquiry> notFound = enquiryRepository.findByEnquirerId(999);
        assertTrue(notFound.isEmpty());
    }

    @Test
    public void testFindByReplierId() throws IOException {
        saveSampleEntities();

        List<Enquiry> byReplier10 = enquiryRepository.findByReplierId(10);
        assertEquals(2, byReplier10.size());

        List<Enquiry> byReplier999 = enquiryRepository.findByReplierId(999);
        assertTrue(byReplier999.isEmpty());
    }

    @Test
    public void testFindByProjectId() throws IOException {
        saveSampleEntities();

        List<Enquiry> forProject101 = enquiryRepository.findByProjectId(101);
        assertEquals(2, forProject101.size());

        List<Enquiry> forProject102 = enquiryRepository.findByProjectId(102);
        assertEquals(1, forProject102.size());

        List<Enquiry> forProject999 = enquiryRepository.findByProjectId(999);
        assertTrue(forProject999.isEmpty());
    }
}