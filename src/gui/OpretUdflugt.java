package gui;

import app.controller.Controller;
import app.model.Konference;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.time.LocalDate;

public class OpretUdflugt extends GridPane {
    private ListView<Konference> lvwKonferencer = new ListView<>();
    //Alt på deltager
    TextField txfUdflugtAdresse = new TextField();
    TextField txfUdflugtPris = new TextField();
    TextField txfAktivitet = new TextField();
    CheckBox cbxMad = new CheckBox();
     dprUdlugt = new ();
    Button btnOpetUdflugt = new Button();

    public OpretUdflugt() {

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblKonferencer = new Label("Vælg konference:");
        this.add(lblKonferencer, 0, 0);
        this.add(lvwKonferencer, 1, 0);
        lvwKonferencer.getItems().setAll(Controller.getKonferenceListe());
        lvwKonferencer.setMaxHeight(100);

        Label lblAdresse = new Label("Adresse: ");
        this.add(lblAdresse, 2, 0);
        this.add(txfUdflugtAdresse, 3, 0);

        Label lblPris = new Label("Pris:");
        this.add(lblPris, 2, 1);
        this.add(txfUdflugtPris, 3, 1);

        Label lblAktivitet = new Label("Aktivitet: ");
        this.add(lblAktivitet, 2, 2);
        this.add(txfAktivitet, 3, 2);

        Label lblMad = new Label("Mad:");
        this.add(lblMad, 2, 3);
        this.add(cbxMad, 3, 3);

        Label lblDato = new Label("Dato:");
        this.add(lblDato,2,4);
        this.add(dprUdlugt,3,4);

        Button btnOpret = new Button("Opret udflugt");
        this.add(btnOpret, 1, 6);
        btnOpret.setOnAction(e -> opretUdflugt());
    }

    private void opretUdflugt() {
        String adresse = txfUdflugtAdresse.getText().trim();
        String prisUdflugt = txfUdflugtPris.getText().trim();
        String aktivitet = txfAktivitet.getText().trim();
        LocalDate dato = dprUdlugt.getValue();
        boolean mad = cbxMad.isSelected();

        Konference valgtKonf = lvwKonferencer.getSelectionModel().getSelectedItem();
        if (valgtKonf == null) {
            showError("Du skal vælge en konference først.");
            return;
        }

        if (adresse.isEmpty()) {
            showError("Adresse må ikke være tom.");
            return;
        }

        if (aktivitet.isEmpty()) {
            showError("Aktivitet må ikke være tom.");
            return;
        }

        if (dato == null) {
            showError("Vælg en dato for udflugten.");
            return;
        }

        if (!prisUdflugt.matches("\\d+(\\.\\d{1,2})?")) {
            showError("Pris skal være et gyldigt tal (f.eks. 100 eller 99.99).");
            return;
        }

        double pris = Double.parseDouble(prisUdflugt);
        try {
            if (pris < 0) {
                showError("Pris må ikke være negativ.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Pris skal være et tal.");
            return;
        }

        // Opret udflugt via Controller
        Controller.createUdflugt(adresse, dato, pris, mad, aktivitet, valgtKonf);

        // Ryd felter
        txfUdflugtAdresse.clear();
        txfUdflugtPris.clear();
        txfAktivitet.clear();
        cbxMad.setSelected(false);
        dprUdlugt.setValue(null);

        showInfo("Udflugt oprettet!");
    }



    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fejl");
        alert.setHeaderText("Ugyldig indtastning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setHeaderText("Succes");
        alert.showAndWait();
    }

    public void updateList() {
        lvwKonferencer.getItems().setAll(Storage.getKonferrenceListe());
    }

}

