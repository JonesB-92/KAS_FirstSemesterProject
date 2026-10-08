package app.model;

import java.util.ArrayList;

public class Ledsager {
    private String navn;
    //Linkattribut
    private ArrayList<Udflugt> udflugtsListe = new ArrayList<>();
    private Tilmelding tilmelding;

    public Ledsager(String navn){
        this.navn = navn;
        this.tilmelding = tilmelding;
    }

    public String getNavn(){
        return navn;
    }

    //Linkmetoder
    public ArrayList<Udflugt> getUdflugter(){
        return new ArrayList<>(udflugtsListe);
    }

    public void addUdflugt(Udflugt udflugt){
        if(!udflugtsListe.contains(udflugt)){
            udflugtsListe.add(udflugt);
            udflugt.addLedsager(this);
        }
    }

}
