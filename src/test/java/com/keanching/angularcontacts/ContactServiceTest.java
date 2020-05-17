package com.keanching.angularcontacts;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keanching.angularcontacts.domain.Contact;
import com.keanching.angularcontacts.dto.SearchCriteria;
import com.keanching.angularcontacts.service.ContactService;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class ContactServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceTest.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private ContactService contactService;

    private static ObjectMapper objectMapper = new ObjectMapper();
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        objectMapper.setDateFormat(sdf);
    }
    
    @Test
    public void testSearchFirstName() {
        List<Contact> contactList = null;
        
        try {
            SearchCriteria searchCriteria = new SearchCriteria.Builder().setFirstName("jeremy").build();
            logger.debug("Search criteria: {}", objectMapper.writer().writeValueAsString(searchCriteria));
            
            contactList = this.contactService.search(searchCriteria);
            
            logger.debug("Results:");
            for (Contact contact : contactList) {
                logger.debug(objectMapper.writer().writeValueAsString(contact));
            }
            
            logger.debug("Results found: {}", contactList.size());
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(contactList != null && contactList.size() == 6);
    }
    
    @Test
    public void testSearchLastName() {
        List<Contact> contactList = null;
        
        try {
            SearchCriteria searchCriteria = new SearchCriteria.Builder().setLastName("smith").build();
            logger.debug("Search criteria: {}", objectMapper.writer().writeValueAsString(searchCriteria));
            
            contactList = this.contactService.search(searchCriteria);
            
            logger.debug("Results:");
            for (Contact contact : contactList) {
                logger.debug(objectMapper.writer().writeValueAsString(contact));
            }
            
            logger.debug("Results found: {}", contactList.size());
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(contactList != null && contactList.size() == 3);
    }
    
    @Test
    public void testSearchFirstNameLastName() {
        List<Contact> contactList = null;
        
        try {
            SearchCriteria searchCriteria =
                new SearchCriteria.Builder()
                    .setFirstName("Bobby")
                    .setLastName("Baker")
                    .build();
            logger.debug("Search criteria: {}", objectMapper.writer().writeValueAsString(searchCriteria));
            
            contactList = this.contactService.search(searchCriteria);
            
            logger.debug("Results:");
            for (Contact contact : contactList) {
                logger.debug(objectMapper.writer().writeValueAsString(contact));
            }
            
            logger.debug("Results found: {}", contactList.size());
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(contactList != null && contactList.size() == 1);
    }
    
    @Test
    public void testSearchFirstNameLastNameUserIdEmailDOB() {
        List<Contact> contactList = null;
        
        try {
            SearchCriteria searchCriteria =
                new SearchCriteria.Builder()
                    .setFirstName("Bobby")
                    .setLastName("Baker")
                    .setUserId("bbakerr5")
                    .setEmail("bbakerr5@livejournal.com")
                    .setDobStart(sdf.parse("1950-12-11 00:00:00"))
                    .setDobEnd(sdf.parse("1950-12-11 23:59:59"))
                    .build();
            logger.debug("Search criteria: {}", objectMapper.writer().writeValueAsString(searchCriteria));
            
            contactList = this.contactService.search(searchCriteria);
            
            logger.debug("Results:");
            for (Contact contact : contactList) {
                logger.debug(objectMapper.writer().writeValueAsString(contact));
            }
            
            logger.debug("Results found: {}", contactList.size());
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(contactList != null && contactList.size() == 1);
    }
    
    @Test
    public void testSearchAnyName() {
        List<Contact> contactList = null;
        
        try {
            SearchCriteria searchCriteria = new SearchCriteria.Builder().setAnyName(new String[] {"tam", "mc"}).build();
            logger.debug("Search criteria: {}", objectMapper.writer().writeValueAsString(searchCriteria));
            
            contactList = this.contactService.search(searchCriteria);
            
            logger.debug("Results:");
            for (Contact contact : contactList) {
                logger.debug(objectMapper.writer().writeValueAsString(contact));
            }
            
            logger.debug("Results found: {}", contactList.size());
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(contactList != null && contactList.size() == 11);
    }
    
    @Test
    public void testDoesUserIdExist() {
        String userId = "amatthewsrq";
        int excludedContactId = 1001; // This is kching
        boolean found = false;
        
        try {
            found = this.contactService.doesUserIdExist(userId, excludedContactId);
            logger.debug("User id '{}' found: {}", userId, found);
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(found);
    }
    
    @Test
    public void testAddContact() {
        SearchCriteria searchCriteria = new SearchCriteria.Builder().setAnyName(new String[] {"zzz"}).build();
        Contact contact = new Contact();
        contact.setFirstName("Zzz123");
        contact.setLastName("Doe");
        Contact contact2 = new Contact();
        contact2.setFirstName("Zzz456");
        contact2.setLastName("Doe");
        List<Contact> resultsList = null;
        
        try {
            this.contactService.add(contact);
            this.contactService.add(contact2);
            logger.debug("Added new contacts");
            
            resultsList = this.contactService.search(searchCriteria);
            
            logger.debug("Results:");
            for (Contact c: resultsList) {
                logger.debug(objectMapper.writer().writeValueAsString(c));
            }
            
            logger.debug("Results found: {}", resultsList.size());            
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(resultsList != null && resultsList.size() == 2);
    }
    
    @Test
    public void testDeleteContact() {
        int countAfterDelete = 0;
        
        try {
            this.contactService.delete(1);
            countAfterDelete = this.contactService.getTotalCount();
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(countAfterDelete == 1000);
    }
    
    @Test
    public void testUpdateContact() {
        Contact updatedContact = null;
        String newEmail = "asdf@asdf.com";
        
        try {
            Contact contact = this.contactService.getById(1001);
            contact.setFirstName("K");
            contact.setLastName("C");
            contact.setEmail(newEmail);
            this.contactService.update(contact);
            updatedContact = this.contactService.getById(1001);
            logger.debug("Updated contact: {}", objectMapper.writer().writeValueAsString(updatedContact));
        }
        catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        
        Assert.assertTrue(updatedContact != null && updatedContact.getEmail().equals(newEmail));
    }
}
