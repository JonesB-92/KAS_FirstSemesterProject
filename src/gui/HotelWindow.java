package gui;

import app.model.Hotel;
import app.model.Tilvalg;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class HotelWindow extends GridPane {
    private final ListView<Hotel> lvwHoteller = new ListView<>();
    private final ListView<Tilvalg> lvwTilvalg = new ListView<>();
    private final Button btnChooseHotel = new Button("Vælg hotel");

    public HotelWindow(ArrayList<Hotel> hotelListe) {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Stage window = new Stage();
        window.setTitle("Hotel Information");

        Label lblHotelNavn = new Label("Hoteller:");
        if (hotelListe != null) {
            lvwHoteller.getItems().addAll(hotelListe);
        }
        this.add(lblHotelNavn, 0, 0);
        this.add(lvwHoteller, 0, 1);

        Label lblTilvalg = new Label("Tilvalg:");
        this.add(lblTilvalg, 1, 0);
        this.add(lvwTilvalg, 1, 1);

        lvwTilvalg.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        lvwHoteller.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                lvwTilvalg.getItems().setAll(newVal.getTilvalgsListe());
            }
        });

        btnChooseHotel.setOnAction(e -> {
            Hotel valgtHotel = lvwHoteller.getSelectionModel().getSelectedItem();
            var valgteTilvalg = lvwTilvalg.getSelectionModel().getSelectedItems();

            if (valgtHotel != null) {
                System.out.println("Valgt hotel: " + valgtHotel.getNavn());
                System.out.println("Valgte tilvalg: " + valgteTilvalg);
                window.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Vælg et hotel først.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        this.add(btnChooseHotel, 0, 2, 2, 1);

        Scene scene = new Scene(this, 500, 400);
        window.setScene(scene);
        window.show();
    }
}
