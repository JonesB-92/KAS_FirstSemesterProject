
package app.model;

import java.util.ArrayList;

public class Tilmelding {
    private boolean foredragsHolder;
    private boolean medbringerLedsager;
    private VærelsesType værelsesType;

    //linkattributter
    private Deltager deltager;
    private Konference konferrence;
    private Hotel hotel;
    private Ledsager ledsager;

    private ArrayList<Tilvalg> tilvalgsListe = new ArrayList<>();

    public Tilmelding(boolean foredragsHolder, boolean medbringLedsager, VærelsesType værelsesType, Deltager deltager) {
        this.foredragsHolder = foredragsHolder;
        this.medbringerLedsager = medbringLedsager;
        this.værelsesType = værelsesType;
        this.deltager = deltager;
    }
    public Tilmelding(){
        this.foredragsHolder = false;
        this.medbringerLedsager = false;
        this.værelsesType = null;
        this.deltager = null;
    }

    public boolean isForedragsHolder() {
        return foredragsHolder;
    }

    public boolean medbringerLedsager() {
        return medbringerLedsager;
    }

    public VærelsesType getVærelsesType() {
        return værelsesType;
    }



    //Linkmetoder ----------------------------------------

    public Deltager getDeltager() {
        return deltager;
    }

    public void setDeltager(Deltager deltager) {
        if (deltager != null) {
            this.deltager = deltager;
        }
    }

    public Ledsager getLedsager() {
        return ledsager;
    }

    public void setLedsager(Ledsager ledsager) {
        this.ledsager = ledsager;
    }

    //Kompositerer Ledsager og står så for metoden, der opretter den:
    public Ledsager createLedsager(String navn) {
        Ledsager ledsager = new Ledsager(navn);
        this.ledsager = ledsager;
        return ledsager;
    }



    public Konference getKonferrence() {
        return konferrence;
    }

    public void setKonferrence(Konference konferrence) {
        if (this.konferrence != konferrence) {
            Konference oldKonferrence = this.konferrence;
            if (oldKonferrence != null) {
                oldKonferrence.removeTilmelding(this);
            }
            this.konferrence = konferrence;
            if (konferrence != null) {
                konferrence.addTilmelding(this);
            }
        }
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        if (this.hotel != hotel) {
            Hotel oldHotel = this.hotel;
            if (oldHotel != null) {
                oldHotel.removeTilmeldingHotel(this);
            }
            this.hotel = hotel;
            if (hotel != null) {
                hotel.addTilmeldingHotel(this);
            }
        }
    }

    public ArrayList<Tilvalg> getTilvalgsListe() {
        return new ArrayList<>(tilvalgsListe);
    }

    public void addTilvalg(Tilvalg tilvalg) {
        if (!tilvalgsListe.contains(tilvalg)){
            tilvalgsListe.add(tilvalg);
        }
    }

    @Override
    public String toString() {
        return ""+ deltager;
    }

    //vi skal bruge en foreach i vores GUI som hver gang et tilvalg er valgt skal den køre den her.
}
