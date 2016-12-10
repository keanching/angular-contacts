package com.intecore.angularcontacts.service;

import java.util.List;

import com.intecore.angularcontacts.domain.Contact;
import com.intecore.angularcontacts.dto.SearchCriteria;

public interface ContactService {
    public List<Contact> getAll() throws Exception;
    public Contact getById(int contactId) throws Exception;
    public List<Contact> search(SearchCriteria searchCriteria) throws Exception;
    public void add(Contact contact) throws Exception;
    public void update(Contact contact) throws Exception;
    public void delete(int contactId) throws Exception;
    public boolean doesUserIdExist(String submittedUserId, int excludedContactId) throws Exception;
    public int getTotalCount() throws Exception;
}
