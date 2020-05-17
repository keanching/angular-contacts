package com.keanching.angularcontacts.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.keanching.angularcontacts.domain.Contact;
import com.keanching.angularcontacts.dto.SearchCriteria;

@Service
public class ContactServiceImpl implements ContactService {
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Value("${contacts.filename}")
    private String fileName;
    
    /**
     * After loading all contacts, this will be the next id to use when you add a new contact.
     */
    private int nextId;
    
    private Map<Integer, Contact> allContacts = new TreeMap<Integer, Contact>();
    
    @Autowired
    private FavoritesService favoritesService;
    
    @PostConstruct
    public void init() {
        logger.debug("Start loading all objects.");

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream(this.fileName);
            List<Contact> contactList = mapper.readValue(is, new TypeReference<List<Contact>>() {});
            logger.debug("Read file from '{}', total objects: {}", this.fileName, contactList.size());

            for (int i = 0; i < contactList.size(); i++) {
                Contact contact = contactList.get(i);
                this.allContacts.put(new Integer(contact.getId()), contact);
                
                if (i == contactList.size() - 1) {
                    this.nextId = i + 2;
                }
            }

            logger.debug("Finished loading all objects, nextId is {}", this.nextId);
        }
        catch (Exception e) {
            logger.error("", e);
        }
    }

    private void applyFavorites(List<Contact> contactList, List<Integer> favList) {
        for (Contact contact : contactList) {
            contact.setIsFavorite(false); // The contact is initial set to not be a favorite
            applyFavorites(contact, favList);
        }
    }
    
    private void applyFavorites(Contact contact, List<Integer> favList) {
        for (Integer favId : favList) {
            if (contact.getId() == favId.intValue()) {
                contact.setIsFavorite(true);
            }
        }
    }
    
    @Override
    public List<Contact> getAll() throws Exception {
        List<Contact> retList = new ArrayList<Contact>(this.allContacts.values());
        List<Integer> favList = this.favoritesService.getFavorites();
        applyFavorites(retList, favList);
        
        return retList;
    }
    
    @Override
    public Contact getById(int contactId) throws Exception {
        Contact retContact = this.allContacts.get(new Integer(contactId));
        List<Integer> favList = this.favoritesService.getFavorites();
        applyFavorites(retContact, favList);
        
        return retContact;
    }

    /**
     * An anyName search is mutually exclusive with first name or last name.
     */
    @Override
    public List<Contact> search(SearchCriteria searchCriteria) throws Exception {
        List<Predicate<Contact>> predicateList = new ArrayList<Predicate<Contact>>();
        
        // Filter by name, name can be space delimited
        final String[] nameList = searchCriteria.getAnyName();
        boolean doAnyNameSearch = nameList != null && nameList.length > 0;
        
        if (doAnyNameSearch) {
            // Traverse list, each contact that has a first name or last name that matches any of the words submitted will be filtered
            Predicate<Contact> namePredicate = new Predicate<Contact>() {
                @Override
                public boolean apply(Contact contact) {
                    boolean found = false;
                    
                    if (contact != null) {
                        for (String searchWord: nameList) {
                            String contactFirstName = contact.getFirstName();
                            String contactLastName = contact.getLastName();
                            
                            if (contactFirstName != null && contactFirstName.toLowerCase().contains(searchWord.toLowerCase())) {
                                found = true;
                                break;
                            }
                            else if (contactLastName != null && contactLastName.toLowerCase().contains(searchWord.toLowerCase())) {
                                found = true;
                                break;
                            }
                        }
                    }
                    
                    return found;
                }
            };
            
            predicateList.add(namePredicate);
        }
        else { // If not doing an any name search, then attempt to do a first name and last name search
            
            // Filter by first name
            final String firstName = searchCriteria.getFirstName();
            if (!StringUtils.isBlank(firstName)) {
                Predicate<Contact> firstNamePredicate = new Predicate<Contact>() {
                    @Override
                    public boolean apply(Contact input) {
                        if (input != null && input.getFirstName() != null) {
                            return input.getFirstName().equalsIgnoreCase(firstName.toLowerCase());
                        }
                        
                        return false;
                    }
                };
                
                predicateList.add(firstNamePredicate);
            }
            
            // Filter by last name
            final String lastName = searchCriteria.getLastName();
            if (!StringUtils.isBlank(lastName)) {
                Predicate<Contact> lastNamePredicate = new Predicate<Contact>() {
                    @Override
                    public boolean apply(Contact input) {
                        if (input != null && input.getLastName() != null) {
                            return input.getLastName().equalsIgnoreCase(lastName.toLowerCase());
                        }
                        
                        return false;
                    }
                };
                
                predicateList.add(lastNamePredicate);
            }            
        }
        
        // Filter by userId
        final String userId = searchCriteria.getUserId();
        if (!StringUtils.isBlank(userId)) {
            Predicate<Contact> userIdPredicate = new Predicate<Contact>() {
                @Override
                public boolean apply(Contact input) {
                    if (input != null && input.getUserId() != null) {
                        return input.getUserId().equalsIgnoreCase(userId.toLowerCase());
                    }
                    
                    return false;
                }
            };
            
            predicateList.add(userIdPredicate);
        }
        
        // Filter by email
        final String email = searchCriteria.getEmail();
        if (!StringUtils.isBlank(email)) {
            Predicate<Contact> emailPredicate = new Predicate<Contact>() {
                @Override
                public boolean apply(Contact input) {
                    if (input != null && input.getEmail() != null) {
                        return input.getEmail().equalsIgnoreCase(email.toLowerCase());
                    }
                    
                    return false;
                }
            };
            
            predicateList.add(emailPredicate);
        }
        
        // Filter by date of birth
        Date startDate = searchCriteria.getDobStart();
        Date endDate = searchCriteria.getDobEnd();
        if (startDate != null && endDate != null) {
            final DateTime criteriaStartDT = new DateTime(startDate);
            final DateTime criteriaEndDT = new DateTime(endDate);

            Predicate<Contact> dobPredicate = new Predicate<Contact>() {
                @Override
                public boolean apply(Contact input) {
                    if (input != null && input.getDateOfBirth() != null) {
                        DateTime currDT = new DateTime(input.getDateOfBirth());
                        return !currDT.isBefore(criteriaStartDT) && !currDT.isAfter(criteriaEndDT);
                    }
                    
                    return false;
                }
            };

            predicateList.add(dobPredicate);
        }
        
        Collection<Contact> filteredCollection = Collections2.filter(this.allContacts.values(), Predicates.and(predicateList));
        ArrayList<Contact> retList = new ArrayList<Contact>(filteredCollection);
        List<Integer> favList = this.favoritesService.getFavorites();
        applyFavorites(retList, favList);
        
        return retList;
    }

    @Override
    public void add(Contact contact) throws Exception {
        contact.setId(this.nextId);
        this.allContacts.put(new Integer(this.nextId), contact);
        this.nextId++;
    }

    @Override
    public void update(Contact contact) throws Exception {
        this.allContacts.put(new Integer(contact.getId()), contact);
    }

    @Override
    public void delete(int contactId) throws Exception {
        this.allContacts.remove(new Integer(contactId));
    }
    
    @Override
    public boolean doesUserIdExist(String submittedUserId, int excludedContactId) throws Exception {
        if (StringUtils.isBlank(submittedUserId)) {
            return false;
        }
        
        boolean found = false;
        
        for (Contact contact : this.allContacts.values()) {
            if (contact.getId() != excludedContactId && submittedUserId.equalsIgnoreCase(contact.getUserId())) {
                found = true;
                break;
            }
        }
        
        return found;
    }

    @Override
    public int getTotalCount() throws Exception {
        return this.allContacts.size();
    }
}
