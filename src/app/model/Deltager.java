package app.model;

import java.util.ArrayList;

public class Deltager {
    private String navn;
    private String tlfNummer;
    private String firmaNavn;
    private String firmaCVR;
    private String adresse;
    private String deltagerType;
    //Linkattributer
    private ArrayList<Tilmelding> tilmeldingsListe;

    //Overload Deltager constructor som ikke har firma?

    public Deltager(String navn, String tlfNummer, String firmaNavn, String firmaCVR, String adresse, String deltagerType) {
        this.navn = navn;
        this.tlfNummer = tlfNummer;
        this.firmaNavn = firmaNavn;
        this.firmaCVR = firmaCVR;
        this.adresse = adresse;
        this.deltagerType = deltagerType;
    }

    public String getNavn() {
        return navn;
    }

    public String getTlfNummer() {
        return tlfNummer;
    }

    public String getFirmaNavn() {
        return firmaNavn;
    }

    public String getFirmaCVR() {
        return firmaCVR;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDeltagerType() {
        return deltagerType;
    }

    //Linkmetoder
    public ArrayList<Tilmelding> getTilmeldingsListe() {
        return new ArrayList<>(tilmeldingsListe);
    }

    public void addTilmelding(Tilmelding tilmelding) {
        if (!tilmeldingsListe.contains(tilmelding)) {
            tilmeldingsListe.add(tilmelding);
            tilmelding.setDeltager(this);
        }
    }



    @Override
    public String toString() {
        return navn + " " + tlfNummer;
    }
}
