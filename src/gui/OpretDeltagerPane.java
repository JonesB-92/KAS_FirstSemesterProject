package gui;

import app.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class OpretDeltagerPane extends GridPane {
   //Alt på deltager
   TextField txfDeltagerNavn = new TextField();
   TextField txfTlfNummer = new TextField();
   TextField txfFirmaNavn = new TextField();
   TextField txfFirmaCVR = new TextField();
   TextField txfAdresse = new TextField();
   TextField txfDeltagerType = new TextField();

   Button btnOpretDeltager = new Button();

   public OpretDeltagerPane() {
      this.setPadding(new Insets(20));
      this.setHgap(20);
      this.setVgap(10);
      this.setGridLinesVisible(false);

      Label lblNavn = new Label("Navn:");
      this.add(lblNavn, 0, 0);
      this.add(txfDeltagerNavn, 1, 0);

      Label lblAdresse = new Label("Adresse:");
      this.add(lblAdresse, 0, 1);
      this.add(txfAdresse, 1, 1);

      Label lblNummer = new Label("Nummer:");
      this.add(lblNummer, 0, 2);
      this.add(txfTlfNummer, 1, 2);

      Label lblDeltagerType = new Label("Deltagertype:");
      this.add(lblDeltagerType, 0, 3);
      this.add(txfDeltagerType, 1, 3);

      Label lblFirmaNavn = new Label("Firmanavn:");
      this.add(lblFirmaNavn, 0, 4);
      this.add(txfFirmaNavn, 1, 4);

      Label lblFirmaCVR = new Label("CVR-nummer:");
      this.add(lblFirmaCVR, 0, 5);
      this.add(txfFirmaCVR, 1, 5);

      btnOpretDeltager.setText("Opret");
      btnOpretDeltager.setOnAction(e-> btnOpretDeltager());
      this.add(btnOpretDeltager,1, 6);

   }

   private void btnOpretDeltager(){
      String deltagerNavn = txfDeltagerNavn.getText().trim();
      String tlfNummer = txfTlfNummer.getText().trim();
      String firmaNavn = txfFirmaNavn.getText().trim();
      String firmaCVR = txfFirmaCVR.getText().trim();
      String adresse = txfAdresse.getText().trim();
      String deltagerType = txfDeltagerType.getText().trim();


      if (deltagerNavn.isEmpty()) {
         showError("Deltagernavn må ikke være tomt.");
         return;
      }
      if (tlfNummer.isEmpty()){
         showError("Telefonnummer må ikke være tomt.");
         return;
      }
      if (deltagerType.isEmpty()){
         showError("Deltager type må ikke være tomt.");
         return;
      }
      if ((firmaCVR.isEmpty() && !firmaNavn.isEmpty()) || (!firmaCVR.isEmpty() && firmaNavn.isEmpty())){
         showError("Både firmanavn og firmaCVR skal være udfyldt!");
         return;
      }

      if (adresse.isEmpty()) {
         showError("Adresse må ikke være tom.");
         return;
      }

      if (!deltagerNavn.isEmpty() || !tlfNummer.isEmpty() || !adresse.isEmpty() || !deltagerType.isEmpty()) {

         if (firmaNavn.isEmpty() && firmaCVR.isEmpty()) {
            firmaCVR = "Intet firma";
            firmaNavn = "Intet firma";

         }

         Controller.createDeltager(deltagerNavn,tlfNummer,firmaNavn,firmaCVR,adresse,deltagerType);

         //Update felter
         txfDeltagerNavn.clear();
         txfTlfNummer.clear();
         txfFirmaNavn.clear();
         txfAdresse.clear();
         txfFirmaCVR.clear();
         txfDeltagerType.clear();
      }
      System.out.println(Storage.getDeltagere());
      showInfo("Deltager oprettet");
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
