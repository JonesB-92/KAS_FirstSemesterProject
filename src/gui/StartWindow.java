package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartWindow extends Application {

    public void start(Stage stage) {
        stage.setTitle("KonferenceAdministrationsSystemet");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Konference-pane
        KonferencePane konferencePane = new KonferencePane();
        Tab tabKonference = new Tab("Opret konference", konferencePane);
        tabKonference.setOnSelectionChanged(e -> konferencePane.updateControls());
        tabPane.getTabs().add(tabKonference);

        // Deltager-pane
        OpretDeltagerPane opretDeltagerPane = new OpretDeltagerPane();
        Tab tabDeltager = new Tab("Opret deltager", opretDeltagerPane);
        tabDeltager.setOnSelectionChanged(e -> konferencePane.updateControls());
        tabPane.getTabs().add(tabDeltager);

        // Udflugt-pane
        OpretUdflugt opretUdflugtPane = new OpretUdflugt();
        Tab tabUdflugt = new Tab("Opret udflugt");
        tabPane.getTabs().add(tabUdflugt);
        tabUdflugt.setOnSelectionChanged(e -> {
            if (tabUdflugt.isSelected()) {
                opretUdflugtPane.updateList();
                tabUdflugt.setContent(opretUdflugtPane);
            }
        });

        //TilmeldDeltager-pane
        TilmeldDeltager tilmeldDeltager = new TilmeldDeltager();
        Tab tabTilmeldDeltager = new Tab("Tilmeld deltager");
        tabPane.getTabs().add(tabTilmeldDeltager);
        tabTilmeldDeltager.setOnSelectionChanged(event -> {
            if (tabTilmeldDeltager.isSelected()) {
                tilmeldDeltager.updateList();
                tabTilmeldDeltager.setContent(tilmeldDeltager);
            }
        });


        //Hotel pane
        OpretHotel opretHotel = new OpretHotel();
        Tab tabHotel = new Tab("Opret Hotel", opretHotel);
        tabHotel.setOnSelectionChanged(e -> konferencePane.updateControls());
        tabPane.getTabs().add(tabHotel);

        KonferenceOversigtPane konferenceOversigtPane = new KonferenceOversigtPane();
        Tab tabKonferenceOversigt = new Tab("Oversigt", konferenceOversigtPane);
        tabPane.getTabs().add(tabKonferenceOversigt);
        tabKonferenceOversigt.setOnSelectionChanged(e -> {
            if (tabKonferenceOversigt.isSelected()) {
                konferenceOversigtPane.updateList();
                tabKonferenceOversigt.setContent(konferenceOversigtPane);
            }
        });


    }


}
