package gui;

import app.model.Ledsager;
import app.model.Tilmelding;
import app.model.Udflugt;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class LedsagerWindow extends GridPane {

    private TextField txfLedsagerNavn = new TextField();
    private Button btnCreateLedsager = new Button("Opret Ledsager");

    // Map CheckBox -> Udflugt
    private Map<CheckBox, Udflugt> valgtUdflugtMap = new HashMap<>();

    public LedsagerWindow(Tilmelding tilmelding, ArrayList<Udflugt> udflugtsListe, Consumer<Ledsager> onLedsagerCreated) {
        Stage window = new Stage();
        window.setTitle("Ledsager Information");

        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);

        Label lblLedsagerNavn = new Label("Ledsagers navn:");
        this.add(lblLedsagerNavn, 0, 0);
        this.add(txfLedsagerNavn, 1, 0);

        Label lblUdflugtsListe = new Label("Udflugter:");
        this.add(lblUdflugtsListe, 0, 1, 2, 1);

        int row = 2;
        for (Udflugt udflugt : udflugtsListe) {
            Label lblAktivitet = new Label(udflugt.getAktivitet());
            Label lblAdresse = new Label(udflugt.getAdresse());
            Label lblPris = new Label("Pris: " + udflugt.getPris() + " kr");

            CheckBox cbxMad = new CheckBox("Mad inkluderet");
            cbxMad.setSelected(udflugt.isMad());
            cbxMad.setDisable(true);

            CheckBox cbxChosen = new CheckBox("Vælg");
            valgtUdflugtMap.put(cbxChosen, udflugt); // GEM forbindelsen

            this.add(lblAktivitet, 0, row);
            this.add(lblAdresse, 1, row);
            this.add(lblPris, 2, row);
            this.add(cbxMad, 3, row);
            this.add(cbxChosen, 4, row);
            row++;
        }

        btnCreateLedsager.setOnAction(e -> {
            String navn = txfLedsagerNavn.getText().trim();
            if (navn.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Indtast ledsagers navn", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            Ledsager ledsager = tilmelding.createLedsager(navn);
            for (Map.Entry<CheckBox, Udflugt> entry : valgtUdflugtMap.entrySet()) {
                if (entry.getKey().isSelected()) {
                    ledsager.addUdflugt(entry.getValue());
                }
            }

            onLedsagerCreated.accept(ledsager);

            window.close();
        });

        this.add(btnCreateLedsager, 0, row + 1);

        Scene scene = new Scene(this, 700, 600);
        window.setScene(scene);
        window.show();
    }

    public void updateList(ArrayList<Udflugt> nyeUdflugter) {
        // Ryd gamle udflugter fra UI
        this.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 2);
        valgtUdflugtMap.clear();

        int row = 2;
        for (Udflugt udflugt : nyeUdflugter) {
            Label lblAktivitet = new Label(udflugt.getAktivitet());
            Label lblAdresse = new Label(udflugt.getAdresse());
            Label lblPris = new Label("Pris: " + udflugt.getPris() + " kr");

            CheckBox cbxMad = new CheckBox("Mad inkluderet");
            cbxMad.setSelected(udflugt.isMad());
            cbxMad.setDisable(true);

            CheckBox cbxChosen = new CheckBox("Vælg");
            valgtUdflugtMap.put(cbxChosen, udflugt);

            this.add(lblAktivitet, 0, row);
            this.add(lblAdresse, 1, row);
            this.add(lblPris, 2, row);
            this.add(cbxMad, 3, row);
            this.add(cbxChosen, 4, row);
            row++;
        }

        // Tilføj knappen igen efter listen
        this.getChildren().remove(btnCreateLedsager);
        this.add(btnCreateLedsager, 0, row + 1);
    }
}
