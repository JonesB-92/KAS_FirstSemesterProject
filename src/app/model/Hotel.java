package app.model;

import java.util.ArrayList;

public class Hotel {
    private String navn;
    private String adresse;
    private double etVærelsesPrisPrDag;
    private double toVærelsesPrisPrDag;
    //Linkattributter
    private ArrayList<Tilvalg> tilvalgsListe = new ArrayList<>();
    private ArrayList<Konference> konferrenceListe = new ArrayList<>();
    private ArrayList<Tilmelding> tilmeldingsListe = new ArrayList<>();

    public Hotel(String navn, String adresse, double etVærelsesPrisPrDag, double toVærelsesPrisPrDag) {
        this.navn = navn;
        this.adresse = adresse;
        this.etVærelsesPrisPrDag = etVærelsesPrisPrDag;
        this.toVærelsesPrisPrDag = toVærelsesPrisPrDag;
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public double getEtVærelsesPris() {
        return etVærelsesPrisPrDag;
    }

    public double getToVærelsesPris() {
        return toVærelsesPrisPrDag;
    }



    //Linkmetoder
    public ArrayList<Tilvalg> getTilvalgsListe() {
        return new ArrayList<>(tilvalgsListe);
    }

    public Tilvalg createTilvalg(String beskrivelse, double pris) {
        Tilvalg tilvalg = new Tilvalg(beskrivelse, pris, this);
        tilvalgsListe.add(tilvalg);
        return tilvalg;
    }


    public void addKonferrence(Konference konferrence) {
        if (!konferrenceListe.contains(konferrence)) {
            konferrenceListe.add(konferrence);
            konferrence.addHotel(this);
        }
    }


    public void addTilmeldingHotel(Tilmelding tilmelding){
        if (!tilmeldingsListe.contains(tilmelding)){
            tilmeldingsListe.add(tilmelding);
            tilmelding.setHotel(this);
        }
    }

    public void removeTilmeldingHotel(Tilmelding tilmelding) {
        tilmeldingsListe.remove(tilmelding);
        tilmelding.setHotel(null);
    }

    @Override
    public String toString() {
        return navn + " " + adresse;
    }
}
