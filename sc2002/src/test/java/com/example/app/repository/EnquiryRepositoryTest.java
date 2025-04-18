package com.example.app.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.app.models.Enquiry;

// public class EnquiryRepositoryTest {

//     private EnquiryRepository enquiryRepo;

//     @BeforeEach
//     public void setup() {
//         enquiryRepo = new EnquiryRepository();

//         // Populate with sample data
//         enquiryRepo.save(new Enquiry("Q1", "R1", 101, 1, 10));
//         enquiryRepo.save(new Enquiry("Q2", "R2", 102, 2, 10));
//         enquiryRepo.save(new Enquiry("Q3", "R3", 101, 3, 11));
//         enquiryRepo.save(new Enquiry("Q4", "R4", 103, 1, 12));
//     }

//     @Test
//     public void testFindByEnquirerId() {
//         List<Enquiry> result = enquiryRepo.findByEnquirerId(1);
//         assertEquals(2, result.size());
//         assertTrue(result.stream().allMatch(e -> e.getEnquirerId() == 1));
//     }

//     @Test
//     public void testFindByReplierId() {
//         List<Enquiry> result = enquiryRepo.findByReplierId(10);
//         assertEquals(2, result.size());
//         assertTrue(result.stream().allMatch(e -> e.getReplierId() == 10));
//     }

//     @Test
//     public void testFindByProjectId() {
//         List<Enquiry> result = enquiryRepo.findByProjectId(101);
//         assertEquals(2, result.size());
//         assertTrue(result.stream().allMatch(e -> e.getProjectId() == 101));
//     }

//     @Test
//     public void testEnquiryConstructorAndGetters() {
//         Enquiry enquiry = new Enquiry("When is the launch?", "Next month", 105, 5, 6);

//         assertEquals("When is the launch?", enquiry.getQuestion());
//         assertEquals("Next month", enquiry.getResponse());
//         assertEquals(105, enquiry.getProjectId());
//         assertEquals(5, enquiry.getEnquirerId());
//         assertEquals(6, enquiry.getReplierId());
//         assertTrue(enquiry.getId() > 0); // ID should be auto-generated
//     }

//     @Test
//     public void testSetters() {
//         Enquiry enquiry = new Enquiry();

//         enquiry.setQuestion("Updated question?");
//         enquiry.setResponse("Updated response.");
//         enquiry.setProjectId(200);
//         enquiry.setEnquirerId(3);
//         enquiry.setReplierId(4);

//         assertEquals("Updated question?", enquiry.getQuestion());
//         assertEquals("Updated response.", enquiry.getResponse());
//         assertEquals(200, enquiry.getProjectId());
//         assertEquals(3, enquiry.getEnquirerId());
//         assertEquals(4, enquiry.getReplierId());
//     }

//     @Test
//     public void testAutoIncrementedId() {
//         Enquiry e1 = new Enquiry();
//         Enquiry e2 = new Enquiry();

//         assertTrue(e2.getId() > e1.getId());
//     }
// }
