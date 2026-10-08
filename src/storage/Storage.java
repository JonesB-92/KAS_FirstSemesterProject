package storage;

import app.model.*;

import java.util.ArrayList;

public class Storage {
    private static ArrayList<Deltager> deltagerListe = new ArrayList<>();
    private static ArrayList<Hotel> hotelListe = new ArrayList<>();
    private static ArrayList<Konference> konferrenceListe = new ArrayList<>();
    private static ArrayList<Ledsager> ledsagerListe = new ArrayList<>();
    private static ArrayList<Tilmelding> tilmeldingsListe = new ArrayList<>();
    private static ArrayList<Tilvalg> tilvalgsListe = new ArrayList<>();
    private static ArrayList<Udflugt> udflugtsListe = new ArrayList<>();

    // -------------------------------------------------------------------------

    public static ArrayList<Deltager> getDeltagere(){
        return new ArrayList<>(deltagerListe);
    }

    public static void addDeltager(Deltager deltager){
        deltagerListe.add(deltager);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Hotel> getHotelListe(){
        return new ArrayList<>(hotelListe);
    }

    public static void addHotel(Hotel hotel){
        hotelListe.add(hotel);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Konference> getKonferrenceListe(){
        return new ArrayList<>(konferrenceListe);
    }

    public static void addKonferrence(Konference konferrence){
        konferrenceListe.add(konferrence);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Ledsager> getLedsagerListe(){
        return new ArrayList<>(ledsagerListe);
    }

    public static void addLedsager(Ledsager ledsager){
        ledsagerListe.add(ledsager);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Tilmelding> getTilmeldingsListe(){
        return new ArrayList<>(tilmeldingsListe);
    }

    public static void addTilmelding(Tilmelding tilmelding){
        tilmeldingsListe.add(tilmelding);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Tilvalg> getTilvalgsListe(){
        return new ArrayList<>(tilvalgsListe);
    }

    public static void addTilvalg(Tilvalg tilvalg){
        tilvalgsListe.add(tilvalg);
    }

    // -------------------------------------------------------------------------

    public static ArrayList<Udflugt> getUdflugtsListe(){
        return new ArrayList<>(udflugtsListe);
    }

    public static void addUdflugt (Udflugt udflugt){
        udflugtsListe.add(udflugt);
    }
}
