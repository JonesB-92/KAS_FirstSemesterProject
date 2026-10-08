package app.model;

public class Tilvalg {
    private String Beskrivelse;
    private double pris;
    private Hotel hotel;

    public Tilvalg(String beskrivelse, double pris, Hotel hotel) {
        Beskrivelse = beskrivelse;
        this.pris = pris;
        this.hotel = hotel;
    }

    public String getBeskrivelse() {
        return Beskrivelse;
    }

    public double getPris() {
        return pris;
    }

    public Hotel getHotel() {
        return hotel;
    }

    @Override
    public String toString() {
        return Beskrivelse + ", " + pris + "kr";
    }
}
