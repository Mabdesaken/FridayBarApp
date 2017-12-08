package com.example.aupke.fridaybar;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aupke on 06-12-2017.
 */

public class OfferManager {

    public static Set<Offer> getOffers(Context context){

        SharedPreferences preferences = context.getSharedPreferences("database", Context.MODE_PRIVATE);
        String json = preferences.getString("activity_favorites","");
        Log.e("LO", json + "lol");
        Set<Offer> favorites = new Gson().fromJson(json, new TypeToken<Set<Offer>>() {}.getType());
        if(favorites == null){
            favorites = new HashSet<>();
        }
        return favorites;
    }

    public static void addOffer(Offer offer, Context context){
        Set<Offer> favorites = new HashSet<>(getOffers(context));
        favorites.add(offer);
        SharedPreferences preferences = context.getSharedPreferences("database", Context.MODE_PRIVATE);
        preferences.edit().putString("activity_favorites", new Gson().toJson(favorites)).apply();
    }

    public static void deleteOffer(Offer offer, Context context){
        Set<Offer> favorites = new HashSet<>(getOffers(context));
        favorites.remove(offer);
        SharedPreferences preferences = context.getSharedPreferences("database", Context.MODE_PRIVATE);
        preferences.edit().putString("activity_favorites", new Gson().toJson(favorites)).apply();


    }
}
