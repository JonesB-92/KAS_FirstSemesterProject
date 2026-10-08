package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class OpretHotel extends GridPane {

    TextField txfNavn = new TextField();
    TextField txfAdresse = new TextField();
    TextField txfEtVærelsesPris = new TextField();
    TextField txfToVærelsesPris = new TextField();

    Button btnOpretHotel = new Button();

    public OpretHotel() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblName = new Label("Navn");
        this.add(lblName,0,0);
        this.add(txfNavn,1,0);

        Label lblAdresse = new Label("Adresse");
        this.add(lblAdresse,0,1);
        this.add(txfAdresse,1,1);

        Label lblEtVærelsesPris = new Label("Pris for single værelse");
        this.add(lblEtVærelsesPris,0,2);
        this.add(txfEtVærelsesPris,1,2);

        Label lblToVærelsesPris = new Label("Pris for double værelse");
        this.add(lblToVærelsesPris,0,3);
        this.add(txfToVærelsesPris,1,3);

        btnOpretHotel.setText("Opret hotel");
        btnOpretHotel.setOnAction(e-> btnOpretHotel());
        this.add(btnOpretHotel,1, 6);
    }

    private void btnOpretHotel() {
        String navn = txfNavn.getText().trim();
        String adresse = txfAdresse.getText().trim();
        String etværelsespris = txfEtVærelsesPris.getText().trim();
        String toværelsespris = txfToVærelsesPris.getText().trim();

        double etVpris = Double.parseDouble(etværelsespris);
        double toVpris = Double.parseDouble(toværelsespris);

        if (navn.isEmpty()) {
            showError("Hotel navn må ikke være tomt.");
            return;
        }
        if (adresse.isEmpty()) {
            showError("Hotellet skal have en adresse");
            return;
        }

        if (etværelsespris.isEmpty()) {
            showError("Konferencenavn må ikke være tomt.");
            return;
        }

        txfToVærelsesPris.clear();
        txfEtVærelsesPris.clear();
        txfAdresse.clear();
        txfNavn.clear();

        showInfo("Hotel oprettet");
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
}
