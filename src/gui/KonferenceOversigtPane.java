package gui;

import app.controller.Controller;
import app.model.Konference;
import app.model.Tilmelding;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import storage.Storage;

public class KonferenceOversigtPane extends GridPane {

    private final ListView<Konference> lvwKonference = new ListView<>();
    private final ListView<Tilmelding> lvwKonferenceDeltager = new ListView<>();

    public KonferenceOversigtPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // Konferencer
        Label lblKonferencer = new Label("Alle konferencer");
        this.add(lblKonferencer, 0, 0);
        lvwKonference.getItems().setAll(Controller.getKonferenceListe());
        this.add(lvwKonference, 0, 1);

        // Deltagere
        Label lblDeltagerListe = new Label("Deltagere");
        this.add(lblDeltagerListe, 1, 0);
        this.add(lvwKonferenceDeltager, 1, 1);

        // Deltagerdata-visning
        Label lblData = new Label("Deltager data");
        this.add(lblData, 2, 0);

        VBox vBoxData = new VBox();
        this.add(vBoxData, 2, 1);
        vBoxData.setSpacing(10);
        vBoxData.setPadding(new Insets(10));

        Label lblNavn = new Label("Navn: ");
        Label lblAdresse = new Label("Adresse: ");
        Label lblTlfnummer = new Label("Tlf: ");
        Label lblFirma = new Label("Firma: ");
        Label lblDeltagertype = new Label("Deltagertype: ");
        Label lblForedragsholder = new Label("Foredragsholder: ");
        Label lblLedsager = new Label("Medbringer ledsager: ");
        Label lblVærelsestype = new Label("Værelsestype: ");
        Label lblUdflugter = new Label("Udflugter: ");

        vBoxData.getChildren().addAll(
                lblNavn,
                lblAdresse,
                lblTlfnummer,
                lblFirma,
                lblDeltagertype,
                lblForedragsholder,
                lblLedsager,
                lblVærelsestype,
                lblUdflugter
        );

        // Når en konference vælges
        lvwKonference.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lvwKonferenceDeltager.getItems().setAll(newVal.getTilmeldingsListe());
            }
        });

        // Når en deltager (tilmelding) vælges
        lvwKonferenceDeltager.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                var deltager = newVal.getDeltager();
                lblNavn.setText("Navn: " + deltager.getNavn());
                lblAdresse.setText("Adresse: " + deltager.getAdresse());
                lblTlfnummer.setText("Tlf: " + deltager.getTlfNummer());
                lblFirma.setText("Firma: " + deltager.getFirmaNavn() + " (CVR: " + deltager.getFirmaCVR() + ")");
                lblDeltagertype.setText("Deltagertype: " + deltager.getDeltagerType());
                lblForedragsholder.setText("Foredragsholder: " + (newVal.isForedragsHolder() ? "Ja" : "Nej"));
                lblLedsager.setText("Medbringer ledsager: " + (newVal.getLedsager()!=null ? "Ja" : "Nej"));
                lblVærelsestype.setText("Værelsestype: " + newVal.getVærelsesType());

                if (newVal.getLedsager() != null) {
                    String udflugter = newVal.getLedsager().getUdflugter().stream()
                            .map(u -> u.getAktivitet())
                            .reduce((a, b) -> a + ", " + b)
                            .orElse("Ingen");
                    lblUdflugter.setText("Udflugter: " + udflugter);
                } else {
                    lblUdflugter.setText("Udflugter: -");
                }
            }
        });
    }

    public void updateList() {
        lvwKonference.getItems().setAll(Storage.getKonferrenceListe());
    }
}
