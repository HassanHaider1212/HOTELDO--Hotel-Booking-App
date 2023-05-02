package com.hoteldo.app;
import java.util.ArrayList;
import java.util.Hashtable;

public interface IDataManager {
    public void saveFavourite(Hashtable<String,String> attributes);
    public void saveOrder(Hashtable<String,String> attributes);
    public void deleteFavourite(String id);
    public void saveUser(Hashtable<String, String> attribute);
}
