package gui;


import app.controller.Controller;
import app.model.Konference;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import javafx.util.Callback;
import storage.Storage;

import java.security.PublicKey;
import java.time.LocalDate;


public class KonferencePane extends GridPane {

    TextField txfKonferenceNavn = new TextField();
    TextField txfKonferenceAdresse = new TextField();
    TextField txfKonferencePris = new TextField();
    DatePicker dprStartDato = new DatePicker();
    DatePicker dprSlutDato = new DatePicker();
    Button btnOpretKonference = new Button();
    ComboBox<Konference> cbxKonferencer = new ComboBox<>();

    public KonferencePane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblNavn = new Label("Konferencenavn: ");
        this.add(lblNavn, 0, 0);
        this.add(txfKonferenceNavn, 1, 0);

        Label lblAdresse = new Label("Adresse: ");
        this.add(lblAdresse, 0, 1);
        this.add(txfKonferenceAdresse, 1, 1);

        Label lblPris = new Label("Pris:");
        this.add(lblPris, 0, 2);
        this.add(txfKonferencePris, 1, 2);

        Label lblDato = new Label("Dato:");
        this.add(lblDato, 0, 3);

        this.add(dprStartDato, 0, 4);
        this.add(dprSlutDato, 1, 4);

        btnOpretKonference.setText("Opret");
        btnOpretKonference.setOnAction(e->btnOpretKonference());
        this.add(btnOpretKonference,1, 5);


        //Begrænser startdato til samme dag og frem
        dprStartDato.setDayCellFactory(getStartDateCellFactory());

        dprStartDato.valueProperty().addListener((obs, oldVal, newVal) -> {
            dprSlutDato.setValue(null); // Nulstil tidligere valgt slutdato
            dprSlutDato.setDayCellFactory(getEndDateCellFactory(newVal));
        });
    }

    private Callback<DatePicker, DateCell> getStartDateCellFactory() {
        return dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Kun datoer fra i dag og frem er tilladt
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        };
    }

    private Callback<DatePicker, DateCell> getEndDateCellFactory(LocalDate minDate) {
        return dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Slutdato skal være efter (eller samme som) startdato
                setDisable(empty || date.isBefore(minDate));
            }
        };
    }


    private void btnOpretKonference() {
        String konferenceNavn = txfKonferenceNavn.getText().trim();
        String konferenceAdresse = txfKonferenceAdresse.getText().trim();
        String konferencePris = txfKonferencePris.getText().trim();
        LocalDate konferenceStartDato = dprStartDato.getValue();
        LocalDate konferenceSlutDato = dprSlutDato.getValue();

        if (konferenceNavn.isEmpty()) {
            showError("Konferencenavn må ikke være tomt.");
            return;
        }

        if (konferenceAdresse.isEmpty()) {
            showError("Adresse må ikke være tom.");
            return;
        }

        if (!konferencePris.matches("\\d+(\\.\\d{1,2})?")) {
            showError("Pris skal være et gyldigt tal (f.eks. 100 eller 99.99).");
            return;
        }

        double pris = Double.parseDouble(konferencePris);
        try {
            if (pris < 0) {
                showError("Pris må ikke være negativ.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Pris skal være et tal.");
            return;
        }

        if (konferenceStartDato == null) {
            showError("Vælg venligst en startdato.");
            return;
        }

        if (konferenceSlutDato == null) {
            showError("Vælg venligst en slutdato.");
            return;
        }

        if (konferenceSlutDato.isBefore(konferenceStartDato)) {
            showError("Slutdato skal være efter startdato.");
            return;
        }

        // Opret konference
        Controller.createKonference(konferenceNavn, konferenceStartDato,    konferenceSlutDato, konferenceAdresse, pris);

        cbxKonferencer.getItems().setAll(Storage.getKonferrenceListe());

        // Ryd felterne
        txfKonferenceNavn.clear();
        txfKonferenceAdresse.clear();
        txfKonferencePris.clear();
        dprStartDato.setValue(null);
        dprSlutDato.setValue(null);

        System.out.println(Storage.getKonferrenceListe());
    }

    public void updateControls() {

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Ugyldig indtastning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Konference getValgtKonference() {
        return cbxKonferencer.getValue();
    }
}
