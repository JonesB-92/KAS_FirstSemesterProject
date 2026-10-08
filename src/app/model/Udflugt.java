package app.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Udflugt {
    private String adresse;
    private LocalDate dato;

    private double pris;
    private boolean mad;
    private String aktivitet;
    private Konference konferrence;
    //Link att:
    private final ArrayList<Ledsager> ledsagerListe = new ArrayList<>();

    public Udflugt(String adresse, LocalDate dato, double pris, boolean mad, String aktivitet, Konference konference) {
        this.adresse = adresse;
        this.dato = dato;
        this.pris = pris;
        this.mad = mad;
        this.aktivitet = aktivitet;
        this.konferrence = konference;
    }

    public String getAdresse() {
        return adresse;
    }

    public double getPris() {
        return pris;
    }

    public boolean isMad() {
        return mad;
    }

    public String getAktivitet() {
        return aktivitet;
    }
    public Konference getKonferrence(){
        return konferrence;
    }

    //LINKMETODER!!!!
    public ArrayList<Ledsager> getLedsagerListe(){
        return new ArrayList<>(ledsagerListe);
    }

    public void addLedsager (Ledsager ledsager){
        if(!ledsagerListe.contains(ledsager)){
            ledsagerListe.add(ledsager);
            ledsager.addUdflugt(this);
        }
   }
}