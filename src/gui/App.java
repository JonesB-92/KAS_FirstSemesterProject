package gui;

import app.controller.Controller;
import app.model.*;
import javafx.application.Application;

import java.time.LocalDate;


public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }

    public static void initStorage() {
        //Controller.createKonference("Hav og Himmel", LocalDate.of(2025,05,9),LocalDate.of(2025,05,12),"test", 1500);
        // Konference
        Konference havOgHimmel = Controller.createKonference("Hav og Himmel",
                LocalDate.of(2025, 5, 28),
                LocalDate.of(2025, 5, 30),
                "Odense Uni",
                1500);
        // Hoteller
        Hotel denHvideSvane = Controller.createHotel("Den Hvide Svane", "hej", 1050, 1250);
        Hotel høtelPhønix = Controller.createHotel("Høtel Phønix", "hej", 700, 800);
        Hotel pensionTusindfryd = Controller.createHotel("Pension Tusindfryd", "hej", 500, 600);

        havOgHimmel.addHotel(denHvideSvane);
        havOgHimmel.addHotel(høtelPhønix);
        havOgHimmel.addHotel(pensionTusindfryd);
        // Tilvalg
        //Hvide Svane
        Tilvalg wifiHvideSvane = denHvideSvane.createTilvalg("Wifi", 50);
        //Høtel Phønix
        Tilvalg badPhønix = høtelPhønix.createTilvalg("Bad", 200);
        Tilvalg wifiPhønix = høtelPhønix.createTilvalg("Wifi", 75);
        //Pension Tusindfryd
        Tilvalg morgenmad = pensionTusindfryd.createTilvalg("Morgenmad", 100);


        // Udflugter

        Udflugt odense = havOgHimmel.createUdflugt("Odense", LocalDate.of(2025, 5, 28), 125, true, "Byrundtur");
        Udflugt egeskov = havOgHimmel.createUdflugt("Egeskov", LocalDate.of(2025, 5, 29), 75, false, "Tur");
        Udflugt trapholt = havOgHimmel.createUdflugt("Trapholt", LocalDate.of(2025, 5, 30), 200, true, "Trapholt Museum");


        // ========== Testdata ========== //


        // 1. Finn Madsen – 3 dage, intet hotel, ingen ledsager
        Deltager finn = Controller.createDeltager("Finn", "21437264", "Hej", "CVR", ", ", "false");
        Tilmelding t1 = Controller.createTilmelding(false, false, VærelsesType.INTETVÆRELSE, finn);
        t1.setKonferrence(havOgHimmel);

        // 2. Niels Petersen – 3 dage, enkeltværelse, ingen tilvalg, ingen ledsager

        Deltager niels = Controller.createDeltager("Niels", "21437264", "Hej", "CVR", ", ", "false");
        Tilmelding t2 = Controller.createTilmelding(false, false, VærelsesType.ENKELT, niels);
        t2.setHotel(denHvideSvane);
        t2.setKonferrence(havOgHimmel);

        // 3. Peter Sommer – 3 dage, dobbeltværelse, wifi, ledsager Mie med 2 udflugter

        Deltager peter = Controller.createDeltager("Peter", "21437264", "Hej", "CVR", ", ", "false");
        Tilmelding t3 = Controller.createTilmelding(false, true, VærelsesType.DOBBELT, peter);
        t3.setHotel(denHvideSvane);
        t3.addTilvalg(wifiHvideSvane);
        t3.setKonferrence(havOgHimmel);

        Ledsager mie = t3.createLedsager("mie");
        mie.addUdflugt(egeskov);
        mie.addUdflugt(trapholt);


// 4. Lone Jensen – 3 dage, dobbeltværelse, wifi, ledsager Jan med 2 udflugter, foredragsholder

        Deltager lone = Controller.createDeltager("lone", "21437264", "Hej", "CVR", ", ", "false");
        Tilmelding t4 = Controller.createTilmelding(true, true, VærelsesType.DOBBELT, lone);
        t4.setHotel(denHvideSvane);
        t4.addTilvalg(wifiHvideSvane);
        t4.setKonferrence(havOgHimmel);

        Ledsager jan = t4.createLedsager("Jan");
        jan.addUdflugt(egeskov);
        jan.addUdflugt(odense);


        System.out.println(Controller.samletPris(t1));
        System.out.println(Controller.samletPris(t2));
        System.out.println(Controller.samletPris(t3));
        System.out.println(Controller.samletPris(t4));

    }


}
