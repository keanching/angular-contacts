package com.keanching.angularcontacts.service;

import java.util.List;

public interface FavoritesService {
    public void addFavorite(int contactId) throws Exception;
    public void removeFavorite(int contactId) throws Exception;
    public List<Integer> getFavorites() throws Exception;
}
