package app.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Konference {
    private String navn;
    private LocalDate startdato;
    private LocalDate slutdato;
    private String adresse;
    private double pris;
    //LinkATT
    private ArrayList<Udflugt> udflugtListe = new ArrayList<>();
    private ArrayList<Hotel> hotelListe = new ArrayList<>();
    private ArrayList<Tilmelding> tilmeldingsListe = new ArrayList<>();

    public Konference(String navn, LocalDate startdato, LocalDate slutdato, String adresse, double pris) {
        this.navn = navn;
        this.startdato = startdato;
        this.slutdato = slutdato;
        this.adresse = adresse;
        this.pris = pris;

    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getStartdato() {
        return startdato;
    }

    public LocalDate getSlutdato() {
        return slutdato;
    }


    public double getPris() {
        return pris;
    }

    public ArrayList<Udflugt> getUdflugtListe() {
        return new ArrayList<>(udflugtListe);
    }

    public Udflugt createUdflugt(String adresse, LocalDate dato, double pris, boolean mad, String aktivitet) {
        Udflugt udflugt = new Udflugt(adresse, dato, pris, mad, aktivitet, this);
        udflugtListe.add(udflugt);
        return udflugt;
    }

    public void addHotel(Hotel hotel) {
        if (!hotelListe.contains(hotel)) {
            hotelListe.add(hotel);
            hotel.addKonferrence(this);
        }
    }

    public ArrayList<Tilmelding> getTilmeldingsListe() {
        return new ArrayList<>(tilmeldingsListe);
    }

    public ArrayList<Hotel> getHotelListe() {
        return new ArrayList<>(hotelListe);
    }

    public void addTilmelding(Tilmelding tilmelding) {
        if (!tilmeldingsListe.contains(tilmelding)) {
            tilmeldingsListe.add(tilmelding);
            tilmelding.setKonferrence(this);
        }
    }

    public void removeTilmelding(Tilmelding tilmelding) {
        if (tilmeldingsListe.contains(tilmelding)) {
            tilmeldingsListe.remove(tilmelding);
            tilmelding.setKonferrence(null);
        }
    }

    @Override
    public String toString() {
        return navn;
    }
}
