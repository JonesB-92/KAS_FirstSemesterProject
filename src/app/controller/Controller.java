package app.controller;

import app.model.*;
import storage.Storage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class    Controller {

    public static Konference createKonference(String navn, LocalDate startDato, LocalDate slutDato, String adresse, double pris) {
        Konference konference = new Konference(navn, startDato, slutDato, adresse, pris);
        Storage.addKonferrence(konference);
        return konference;
    }

/*    public static void updateKonference(Konferrence konferrence, String navn, LocalDate startDato, LocalDate slutDato,String adresse, double pris ) {
        konferrence.setNavn(navn);
        konferrence.setSlutdato(slutDato);
        konferrence.setStartdato(startDato);
        konferrence.setAdresse(adresse);
        konferrence.setPris(pris);
    }*/
    //tiltænkt at man kan opdatere konferrencen i GUI

    //Deltager
    public static Deltager createDeltager(String navn, String tlfNummer, String firmaNavn, String firmaCVR, String adresse, String deltagerType) {
        Deltager deltager = new Deltager(navn, tlfNummer, firmaNavn, firmaCVR, adresse, deltagerType);
        Storage.addDeltager(deltager);
        return deltager;
    }

    //Hotel
    public static Hotel createHotel(String navn, String adresse, double etVærelsesPris, double toVærelsesPris) {
        Hotel hotel = new Hotel(navn, adresse, etVærelsesPris, toVærelsesPris);
        Storage.addHotel(hotel);
        return hotel;
    }

    public static Tilmelding createTilmelding(boolean foredragsHolder, boolean medbringLedsager, VærelsesType værelsesType, Deltager deltager) {
        Tilmelding tilmelding = new Tilmelding(foredragsHolder, medbringLedsager, værelsesType, deltager);
        Storage.addTilmelding(tilmelding);
        return tilmelding;
    }
    public static Tilmelding createTilmeldingBlank(){
        Tilmelding tilmelding = new Tilmelding();
        return tilmelding;
    }

    public static Udflugt createUdflugt(String adresse, LocalDate dato, double pris, boolean mad, String aktivitet, Konference konference) {
        Udflugt udflugt = new Udflugt(adresse, dato, pris, mad, aktivitet, konference);
        Storage.addUdflugt(udflugt);
        return udflugt;
    }

    //BEREGNING AF SAMLET PRIS
    public static double samletPris(Tilmelding tilmelding) {
        //Samlet pris = antal dage på konference (hvis ikke foredragsholder) + antal overnatninger på hotel (hvis hotel) +
        //antal tilvalg (hvis tilvalg) + antal udflugter (hvis ledsager)

        double samletPris = 0;
        double hotelPris = 0;
        double tilvalgsPris = 0;
        double udflugtsPris = 0;
        //Antal konferrencedage
        int konferrenceDage = (int) ChronoUnit.DAYS.between(
                tilmelding.getKonferrence().getStartdato(),
                tilmelding.getKonferrence().getSlutdato()) + 1;
        //Overnatninger på hotel
        int hotelNætter = konferrenceDage - 1;

        if (tilmelding.getHotel() == null || tilmelding.getVærelsesType() == VærelsesType.INTETVÆRELSE) {
            hotelPris = 0;
        } else if (tilmelding.getVærelsesType() == VærelsesType.ENKELT) {
            hotelPris = tilmelding.getHotel().getEtVærelsesPris();
        } else if (tilmelding.getVærelsesType() == VærelsesType.DOBBELT) {
            hotelPris = tilmelding.getHotel().getToVærelsesPris();
        }

        //Antal tilvalg
        for (Tilvalg tilvalg : tilmelding.getTilvalgsListe()) {
            tilvalgsPris += tilvalg.getPris() * hotelNætter;
        }

        //Antal udflugter
        if (tilmelding.getLedsager() != null) {
            for (Udflugt udflugt : tilmelding.getLedsager().getUdflugter()) {
                udflugtsPris += udflugt.getPris();
            }
        }

        //Foredragsholder betaler ikke for konferrencen
        if (!tilmelding.isForedragsHolder()) {
            samletPris += tilmelding.getKonferrence().getPris() * konferrenceDage;
        }

        samletPris += hotelPris * hotelNætter;
        samletPris += udflugtsPris;
        samletPris += tilvalgsPris;

        return samletPris;
    }

    public static ArrayList<Hotel> getHotelListe() {
        return new ArrayList<>(Storage.getHotelListe());
    }

    public static ArrayList<Udflugt> getUdlugtListe(){
        return new ArrayList<>(Storage.getUdflugtsListe());
    }
    public static ArrayList<Konference> getKonferenceListe(){
        return new ArrayList<>(Storage.getKonferrenceListe());
    }
    public static ArrayList<Deltager> getDeltagerListe(){
        return new ArrayList<>(Storage.getDeltagere());
    }
    public static ArrayList<Tilvalg> getTilvalgsListe(){
        return new ArrayList<>(Storage.getTilvalgsListe());
    }
    public static ArrayList<Tilmelding> getTilmeldingsListe(){
        return new ArrayList<>(Storage.getTilmeldingsListe());
    }
    public static ArrayList<Ledsager> getLedsagerListe(){
        return new ArrayList<>(Storage.getLedsagerListe());
    }

    public static Tilmelding findTilmelding(Deltager deltager, Konference konference) {
        for (Tilmelding t : Storage.getTilmeldingsListe()) {
            if (t.getDeltager().equals(deltager) && t.getKonferrence().equals(konference)) {
                return t;
            }
        }
        return null;
    }
}
