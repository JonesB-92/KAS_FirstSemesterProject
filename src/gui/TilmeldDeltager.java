package gui;

import app.controller.Controller;
import app.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

import java.util.ArrayList;

public class TilmeldDeltager extends GridPane {
    private ListView<Deltager> lvwDeltagere = new ListView<>();
    private ListView<Konference> lvwKonferencer = new ListView<>();
    private CheckBox cbxForedragsHolder = new CheckBox();

    Button btnCreateLedsager = new Button();
    Button btnChooseHotel = new Button();
    Button btnOpretTilmelding = new Button("Opret Tilmelding");

    private Deltager valgtDeltager;
    private Konference valgtKonference;
    private Ledsager ledsager;
    private LedsagerWindow ledsagerWindow;

    // ComboBox for værelsetype
    private ComboBox<VærelsesType> cbxVærelsesType = new ComboBox<>();

    public TilmeldDeltager() {
        Tilmelding tilmelding = Controller.createTilmeldingBlank();  // Metode til at oprette tilmeldingen

        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        // Deltagere
        Label lblDeltagere = new Label("Deltagere");
        lvwDeltagere.getItems().setAll(Controller.getDeltagerListe());
        this.add(lblDeltagere, 0, 0);
        this.add(lvwDeltagere, 1, 0);

        // Konferencer
        Label lblKonferencer = new Label("Konferencer");
        lvwKonferencer.getItems().setAll(Controller.getKonferenceListe());
        this.add(lblKonferencer, 0, 1);
        this.add(lvwKonferencer, 1, 1);

        // Vælg Ledsager
        btnCreateLedsager.setText("Vælg Ledsager");
        this.add(btnCreateLedsager, 2, 0);
        btnCreateLedsager.setOnAction(e -> {
            valgtKonference = lvwKonferencer.getSelectionModel().getSelectedItem();
            valgtDeltager = lvwDeltagere.getSelectionModel().getSelectedItem();

            if (valgtKonference != null && valgtDeltager != null) {
                ArrayList<Udflugt> udflugter = valgtKonference.getUdflugtListe();

                ledsagerWindow = new LedsagerWindow(tilmelding, udflugter, nyLedsager -> {
                    this.ledsager = nyLedsager;

                    // Deaktiver enkeltværelse, hvis der er valgt en ledsager
                    if (ledsager != null) {
                        cbxVærelsesType.getItems().remove(VærelsesType.ENKELT);
                    } else {
                        if (!cbxVærelsesType.getItems().contains(VærelsesType.ENKELT)) {
                            cbxVærelsesType.getItems().add(VærelsesType.ENKELT);
                        }
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Vælg en deltager og en konference først.", ButtonType.OK);
                alert.showAndWait();
            }
        });
        // Vælg Hotel
        btnChooseHotel.setText("Vælg Hotel");
        this.add(btnChooseHotel, 2, 1);

        btnChooseHotel.setOnAction(e -> {
            valgtKonference = lvwKonferencer.getSelectionModel().getSelectedItem();

            if (valgtKonference != null && !cbxVærelsesType.getValue().equals(VærelsesType.INTETVÆRELSE)) {
                new HotelWindow(valgtKonference.getHotelListe());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Vælg en konference først, og vær opmærksom på, at 'Intet Værelse' ikke kræver hotel.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // ForedragsHolder CheckBox
        cbxForedragsHolder.setText("ForedragsHolder");
        this.add(cbxForedragsHolder, 1, 4);

        // ComboBox for værelsetype
        cbxVærelsesType.getItems().setAll(VærelsesType.values());
        cbxVærelsesType.setValue(VærelsesType.INTETVÆRELSE);  // Default værdi
        this.add(new Label("Værelsestype"), 0, 3);
        this.add(cbxVærelsesType, 1, 3);

        // Opret Tilmelding
        btnOpretTilmelding.setOnAction(e -> opretTilmelding());
        this.add(btnOpretTilmelding, 1, 5);
    }

    private void opretTilmelding() {
        valgtDeltager = lvwDeltagere.getSelectionModel().getSelectedItem();
        valgtKonference = lvwKonferencer.getSelectionModel().getSelectedItem();

        // Hent den valgte værelsetype fra ComboBox
        VærelsesType værelseType = cbxVærelsesType.getValue();

        if (valgtDeltager != null && valgtKonference != null) {
            if (værelseType.equals(VærelsesType.INTETVÆRELSE)) {
                // Hvis intet værelse er valgt, så skal hotel ikke bruges
                Controller.createTilmelding(
                        cbxForedragsHolder.isSelected(),
                        ledsager != null,
                        værelseType,  // Værelsetype er intet værelse
                        valgtDeltager
                );
            } else {
                // Hvis et værelse er valgt, så brug hotellet
                Controller.createTilmelding(
                        cbxForedragsHolder.isSelected(),
                        ledsager != null,
                        værelseType,  // Værelsetype som valgt i ComboBox
                        valgtDeltager
                );
            }

            // Print tilmeldingens detaljer i konsollen
            System.out.println("Tilmelding oprettet:");
            System.out.println("Deltager: " + valgtDeltager.getNavn());
            System.out.println("Konference: " + valgtKonference.getNavn());
            System.out.println("ForedragsHolder: " + cbxForedragsHolder.isSelected());
            if (ledsager != null) {
                if (værelseType != VærelsesType.INTETVÆRELSE) {
                    værelseType = VærelsesType.DOBBELT;
                }
                System.out.println("Ledsager: " + ledsager.getNavn());
            } else {
                System.out.println("Ingen ledsager.");
            }

            System.out.println("Værelsestype: " + værelseType);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tilmelding oprettet!", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vælg både deltager og konference.", ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void updateList() {
        lvwKonferencer.getItems().setAll(Controller.getKonferenceListe());
        lvwDeltagere.getItems().setAll(Controller.getDeltagerListe());
        if (ledsagerWindow != null && valgtKonference != null) {
            ledsagerWindow.updateList(valgtKonference.getUdflugtListe());
        }
    }
}
