package com.hoteldo.app;

import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;



public class FirebaseDataManager implements IDataManager{
    public interface DataObserver{
        public void updateHotels(ArrayList<Hashtable<String, String>> loadedhotels);
        public void updateRooms(ArrayList<Hashtable<String, String>> loadedrooms);
        public void updateOrders(ArrayList<Hashtable<String, String>> loadedorders);
        public void updateFavourites(ArrayList<Hashtable<String, String>> loadedfavourites);

        public void updateUser(ArrayList<Hashtable<String, String>> data);
    }

    private DataObserver observer;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;

    ArrayList<Hashtable<String,String>> data;

    public FirebaseDataManager(DataObserver obs){
        user = FirebaseAuth.getInstance().getCurrentUser();
        observer = obs;
        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
        myRef = database.getReference();

        myRef.child("Hotels").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    data = new ArrayList<Hashtable<String,String>>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        GenericTypeIndicator<HashMap<String,Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String,Object> map =  d.getValue(type);

                        Hashtable<String,String> obj = new Hashtable<String,String>();
                        obj.put("hotelID", d.getKey());
                        for(String key : map.keySet()){
                            obj.put(key,map.get(key).toString());
                        }
                        data.add(obj);
                    }

                    observer.updateHotels(data);
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });
        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    data = new ArrayList<Hashtable<String,String>>();
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        GenericTypeIndicator<HashMap<String,Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String,Object> map =  d.getValue(type);

                        Hashtable<String,String> obj = new Hashtable<String,String>();
                        obj.put("roomID", d.getKey());
                        for(String key : map.keySet()){
                            obj.put(key,map.get(key).toString());
                        }
                        data.add(obj);
                    }

                    observer.updateRooms(data);
                }
                catch (Exception ex) {
                    Log.e("firebasedb", ex.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("firebasedb", "Failed to read value.", error.toException());
            }
        });

        if(user != null) {
            myRef.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        data = new ArrayList<Hashtable<String, String>>();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            GenericTypeIndicator<HashMap<String, Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            HashMap<String, Object> map = d.getValue(type);

                            Hashtable<String, String> obj = new Hashtable<String, String>();
                            obj.put("email", d.getKey());
                            for (String key : map.keySet()) {
                                obj.put(key, map.get(key).toString());
                            }
                            data.add(obj);
                        }

                        observer.updateFavourites(data);
                    } catch (Exception ex) {
                        Log.e("firebasedb", ex.getMessage());
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("firebasedb", "Failed to read value.", error.toException());
                }
            });
            myRef.child("Orders").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        data = new ArrayList<Hashtable<String, String>>();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            GenericTypeIndicator<HashMap<String, Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            HashMap<String, Object> map = d.getValue(type);

                            Hashtable<String, String> obj = new Hashtable<String, String>();
                            obj.put("orderID", d.getKey());
                            for (String key : map.keySet()) {
                                obj.put(key, map.get(key).toString());
                            }
                            data.add(obj);
                        }

                        observer.updateOrders(data);
                    } catch (Exception ex) {
                        Log.e("firebasedb", ex.getMessage());
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("firebasedb", "Failed to read value.", error.toException());
                }
            });
            myRef.child("FavouriteHotels").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        data = new ArrayList<Hashtable<String, String>>();
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            GenericTypeIndicator<HashMap<String, Object>> type = new GenericTypeIndicator<HashMap<String, Object>>() {
                            };
                            HashMap<String, Object> map = d.getValue(type);

                            Hashtable<String, String> obj = new Hashtable<String, String>();
                            obj.put("favouriteID", d.getKey());
                            for (String key : map.keySet()) {
                                obj.put(key, map.get(key).toString());
                            }
                            data.add(obj);
                        }

                        observer.updateFavourites(data);
                    } catch (Exception ex) {
                        Log.e("firebasedb", ex.getMessage());
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("firebasedb", "Failed to read value.", error.toException());
                }
            });
        }
    }

    @Override
    public void saveOrder(Hashtable<String, String> attributes) {
        String id = attributes.get("orderID");

        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            if (!key.equals("orderID")){
                myRef.child("Orders").child(user.getUid()).child(id).child(key).setValue(attributes.get(key));
            }
        }
    }

    @Override
    public void saveFavourite(Hashtable<String, String> attributes) {
        String id = attributes.get("favouriteID");

        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            if (!key.equals("favouriteID")){
                myRef.child("FavouriteHotels").child(user.getUid()).child(id).child(key).setValue(attributes.get(key));
            }
        }
    }


    @Override
    public void deleteFavourite(String id) {
        myRef.child("FavouriteHotels").child(user.getUid()).child(id).removeValue();
    }

    @Override
    public void saveUser(Hashtable<String, String> attributes){
        Enumeration<String> keys = attributes.keys();
        String id = attributes.get("id");
        while (keys.hasMoreElements()){

            String key = keys.nextElement();
            if(!key.equals(id))
                myRef.child("Users").child(id).child(key).setValue(attributes.get(key));
        }
    }

}
