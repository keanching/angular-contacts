package com.intecore.angularcontacts.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intecore.angularcontacts.domain.Contact;
import com.intecore.angularcontacts.dto.UiResponse;
import com.intecore.angularcontacts.dto.UiResponseStatus;
import com.intecore.angularcontacts.service.ContactService;
import com.intecore.angularcontacts.service.FavoritesService;
import com.intecore.angularcontacts.util.CommonUtils;
import com.intecore.angularcontacts.util.Constants;

@RestController
@RequestMapping(Constants.REST_URL_PREFIX)
public class FavoritesController {
    private static final Logger logger = LoggerFactory.getLogger(FavoritesController.class);
    
    @Value("${simulate.task.length.millis}")
    private int simulateLongRunningTaskMillis;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private FavoritesService favoritesService;
    
    @RequestMapping(value = "/favoritesCount", method = RequestMethod.GET)
    public UiResponse getFavoritesCount() throws Exception {
        logger.debug("[Enter] MainController.getFavoritesCount");
        
        List<Integer> favList = this.favoritesService.getFavorites();
        logger.debug("Total favorites count: {}", favList.size());
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("favoritesCount", favList.size());
        
        logger.debug("[Exit] MainController.getFavoritesCount");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    public UiResponse saveFavorite(@RequestBody Map<String, Integer> postedMap) throws Exception {
        logger.debug("[Enter] MainController.saveFavorite");
        
        int contactId = postedMap.get("contactId").intValue();
        this.favoritesService.addFavorite(contactId);
        logger.debug("Successfully saved favorite: {}", contactId);
        
        logger.debug("[Exit] MainController.saveFavorite");
        return new UiResponse(null, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/removeFavorite", method = RequestMethod.POST)
    public UiResponse removeFavorite(@RequestBody Map<String, Integer> postedMap) throws Exception {
        logger.debug("[Enter] MainController.removeFavorite");
        
        int contactId = postedMap.get("contactId").intValue();
        this.favoritesService.removeFavorite(contactId);
        logger.debug("Successfully removed favorite: {}", contactId);
        
        logger.debug("[Exit] MainController.removeFavorite");
        return new UiResponse(null, UiResponseStatus.SUCCESS);
    }
    
    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public UiResponse getFavorites() throws Exception {
        logger.debug("[Enter] MainController.getFavorites");
        
        CommonUtils.simulateLongRunningTask(this.simulateLongRunningTaskMillis);
        
        List<Contact> retList = new ArrayList<Contact>();
        List<Integer> favList = this.favoritesService.getFavorites();
      
        if (favList != null) {
            for (Integer contactId : favList) {
                Contact contact = this.contactService.getById(contactId.intValue());
              
                if (contact != null) {
                    retList.add(contact);    
                }
            }
        }
        
        logger.debug("Returning list of favorites, total found: {}", retList.size());
        
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("favorites", retList);
        
        logger.debug("[Exit] MainController.getFavorites");
        return new UiResponse(retMap, UiResponseStatus.SUCCESS);
    }
}
