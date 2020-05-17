package com.keanching.angularcontacts.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.keanching.angularcontacts.domain.Contact;
import com.keanching.angularcontacts.dto.SearchCriteria;
import com.keanching.angularcontacts.dto.UiResponse;
import com.keanching.angularcontacts.dto.UiResponseStatus;
import com.keanching.angularcontacts.service.ContactService;
import com.keanching.angularcontacts.util.CommonUtils;
import com.keanching.angularcontacts.util.Constants;

@RestController
@RequestMapping(Constants.REST_URL_PREFIX)
public class ContactsController {
    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);
    
    @Value("${simulate.task.length.millis}")
    private int simulateLongRunningTaskMillis;
    
    @Autowired
    private ContactService contactService;
    
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public UiResponse searchContacts(@RequestBody SearchCriteria searchCriteria) throws Exception {
        logger.debug("[Enter] MainController.searchContacts");
        
        logger.debug("Search criteria submitted: {}", CommonUtils.toJsonString(searchCriteria));
        CommonUtils.simulateLongRunningTask(this.simulateLongRunningTaskMillis);
        
        List<Contact> retList = this.contactService.search(searchCriteria);
        logger.debug("Search results found: {}", retList.size());
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("contacts", retList);
        
        logger.debug("[Exit] MainController.searchContacts");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public UiResponse getAllContacts() throws Exception {
        logger.debug("[Enter] MainController.getAllContacts");
        
        logger.debug("Getting all contacts");
        List<Contact> retList = this.contactService.getAll();
        logger.debug("Search results found: {}", retList.size());
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("contacts", retList);
        
        logger.debug("[Exit] MainController.getAllContacts");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)
    public UiResponse getContactById(@PathVariable int contactId) throws Exception {
        logger.debug("[Enter] MainController.getContactById");
        
        logger.debug("Search by id: {}", contactId);
        CommonUtils.simulateLongRunningTask(this.simulateLongRunningTaskMillis);
        
        Contact retObj = this.contactService.getById(contactId);
        logger.debug("Returning: {}", CommonUtils.toJsonString(retObj));
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("contact", retObj);
        
        logger.debug("[Exit] MainController.getContactById");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
    
    /**
     * Set excludedContactId to -1 to skip all contacts.
     */
    @RequestMapping(value = "/userIdExists", method = RequestMethod.GET)
    public UiResponse doesUserIdExist(@RequestParam String submittedUserId, @RequestParam int excludedContactId) throws Exception {
        logger.debug("[Enter] MainController.doesUserIdExist");
        logger.debug("submittedUserId: {}, excludedContactId: {}", submittedUserId, excludedContactId);
        CommonUtils.simulateLongRunningTask(this.simulateLongRunningTaskMillis);
        String errorMsg = null;
        
        if (StringUtils.isBlank(submittedUserId)) {
            errorMsg = "1001:User ID cannot be empty";
        }
        else if (submittedUserId.length() < 4) {
            errorMsg = "1002:User ID must be greater than 3 characters";
        }
        else if (submittedUserId.length() > 20) {
            errorMsg = "1003:User ID cannot be greater than 20 characters";
        }
        
        if (errorMsg != null) {
            logger.debug("error: {}", errorMsg);
            logger.debug("[Exit] MainController.doesUserIdExist");
            return new UiResponse(null, UiResponseStatus.ERROR, Lists.newArrayList(errorMsg));
        }
        
        boolean found = this.contactService.doesUserIdExist(submittedUserId, excludedContactId);
        logger.debug("User id '{}' {}, skipping {}", submittedUserId, found ? "exists" : "does not exist", excludedContactId);
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("userIdExists", found);
        
        logger.debug("[Exit] MainController.doesUserIdExist");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/updateContact", method = RequestMethod.POST)
    public UiResponse updateContact(@RequestBody Contact contact) throws Exception {
        logger.debug("[Enter] MainController.updateContact");
        logger.debug("Updating contact: {}", CommonUtils.toJsonString(contact));
        
        try {
            Thread.sleep(2500);
        }
        catch (Exception e) {
            logger.error("", e);
        }
        
        this.contactService.update(contact);
        
        logger.debug("[Exit] MainController.updateContact");
        return new UiResponse(null, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/addContact", method = RequestMethod.POST)
    public UiResponse addContact(@RequestBody Contact contact) throws Exception {
        logger.debug("[Enter] MainController.addContact");
        logger.debug("Adding new contact: {}", CommonUtils.toJsonString(contact));
        
        try {
            Thread.sleep(2500);
        }
        catch (Exception e) {
            logger.error("", e);
        }
        
        contact.setDateOfBirth(new Date()); // Just set to current date for now
        this.contactService.add(contact);
        logger.debug("New contact added: {}", contact.getId());
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("newContactId", contact.getId());
        
        logger.debug("[Exit] MainController.addContact");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
}
