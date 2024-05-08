//package com.tychicus.opentalk.testCascade;
//import com.tychicus.opentalk.model.CompanyBranch;
//import com.tychicus.opentalk.model.OpenTalk;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySources;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class TestCascade {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void testPersist() {
//        // set value for companyBranch and openTalk
////        CompanyBranch companyBranch = new CompanyBranch();
////        companyBranch.setName("Branch 1");
//        OpenTalk openTalk = new OpenTalk();
//        openTalk.setTopic("Topic 1");
////        openTalk.setCompanyBranch(companyBranch);
////        entityManager.persist(companyBranch);
//        entityManager.flush();
//        entityManager.persist(openTalk);
//        entityManager.flush();
////        assertNotNull(companyBranch.getId());
//        assertNotNull(openTalk.getId());
//
//    }
//
//    // test merge
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void testMerge() {
//        // set value for companyBranch and openTalk
//        CompanyBranch companyBranch = new CompanyBranch();
//        companyBranch.setName("Branch 2");
//        OpenTalk openTalk = new OpenTalk();
//        openTalk.setTopic("Topic 2");
//        openTalk.setCompanyBranch(companyBranch);
//        entityManager.persist(openTalk);
//        entityManager.flush();
//
//        // merge companyBranch
//        companyBranch.setName("Branch 3");
//        entityManager.merge(companyBranch);
//        entityManager.flush();
//
//        assertEquals("Branch 3", openTalk.getCompanyBranch().getName());
//    }
//
//    // test remove
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void testRemove() {
//        // set value for companyBranch and openTalk
//        CompanyBranch companyBranch = new CompanyBranch();
//        companyBranch.setName("Branch 4");
//
//        OpenTalk openTalk = new OpenTalk();
//        openTalk.setCompanyBranch(companyBranch); // Set the companyBranch field of the OpenTalk instance
//
//        companyBranch.getOpenTalks().add(openTalk); // Add the OpenTalk instance to the openTalks set of the CompanyBranch instance
//
//        entityManager.persist(companyBranch);
//        entityManager.flush();
//
//        // remove companyBranch
//        entityManager.remove(companyBranch);
//        entityManager.flush();
//
//        assertNull(entityManager.find(CompanyBranch.class, companyBranch.getId()));
//        assertNull(entityManager.find(OpenTalk.class, openTalk.getId())); // Now this assertion should pass
//    }
//
//    // test refresh
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void testRefresh() {
//        // set value for companyBranch and openTalk
//        CompanyBranch companyBranch = new CompanyBranch();
//        companyBranch.setName("Branch 5");
//        OpenTalk openTalk = new OpenTalk();
//        openTalk.setTopic("Topic 5");
//        openTalk.setCompanyBranch(companyBranch);
//        entityManager.persist(openTalk);
//        entityManager.flush();
//
//        companyBranch.setName("Branch 6");
//        openTalk.setTopic("Topic 6");
//
//        // refresh companyBranch
//        entityManager.refresh(companyBranch);
//        entityManager.flush();
//        // refresh openTalk
////        entityManager.refresh(openTalk);
////        entityManager.flush();
//
//        assertEquals("Branch 5", companyBranch.getName());
//    }
//
//    // test Detach
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void testDetach() {
//        // set value for companyBranch and openTalk
//        CompanyBranch companyBranch = new CompanyBranch();
//        companyBranch.setName("Branch 4");
//
//        OpenTalk openTalk = new OpenTalk();
//        openTalk.setCompanyBranch(companyBranch); // Set the companyBranch field of the OpenTalk instance
//
//        companyBranch.getOpenTalks().add(openTalk); // Add the OpenTalk instance to the openTalks set of the CompanyBranch instance
//
//        entityManager.persist(companyBranch);
//        entityManager.flush();
//
//        entityManager.detach(companyBranch);
//
////        entityManager.detach(openTalk);
////        entityManager.flush();
//
//        assertFalse(entityManager.contains(companyBranch));
//        assertFalse(entityManager.contains(openTalk));
//    }
//
//}
