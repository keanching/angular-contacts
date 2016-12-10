package com.intecore.angularcontacts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl implements FavoritesService {
    private static final Logger logger = LoggerFactory.getLogger(FavoritesServiceImpl.class);

    Set<Integer> favSet = new TreeSet<Integer>();
    
    @PostConstruct
    public void init() {
        // Hard code some favorites
        try {
            addFavorite(11);
            addFavorite(998);
            addFavorite(999);
            addFavorite(1000);
            addFavorite(1001);
        }
        catch (Exception e) {
            logger.error("", e);
        }
    }
    
    @Override
    public void addFavorite(int contactId) throws Exception {
        favSet.add(new Integer(contactId));
    }
    
    @Override
    public void removeFavorite(int contactId) throws Exception {
        favSet.remove(new Integer(contactId));
    }
    
    @Override
    public List<Integer> getFavorites() throws Exception {
        return new ArrayList<Integer>(this.favSet);
    }
}
