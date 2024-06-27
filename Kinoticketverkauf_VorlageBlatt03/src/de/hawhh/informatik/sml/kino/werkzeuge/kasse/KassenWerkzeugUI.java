package de.hawhh.informatik.sml.kino.werkzeuge.kasse;

import de.hawhh.informatik.sml.kino.uicustomization.CustomButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Das UI vom {@link KassenWerkzeug}.
 * 
 * @author SE2-Team Uni HH, PM2-Team
 * @version SoSe 2024
 */
public class KassenWerkzeugUI
{
    /*
     * Die in der Oberfläche verwendeten Icons stammen aus dem Tango-Desktop-
     * Project und dürfen frei verwendet werden. Siehe
     * http://tango.freedesktop.org/
     */
    private static final String TITEL = "PM2-Kinokartenverkauf";

    private Button _beendenButton;
    private Stage _stage;

    public KassenWerkzeugUI(Pane datumWaehlerPane,
                            Pane vorstellungenPane, Pane platzverkaufsPane)
    {
        Stage primaryStage = new Stage();
        primaryStage.setTitle(TITEL);
        BorderPane contentPane = new BorderPane();
        Scene scene = new Scene(contentPane, 1300, 650);


        SplitPane splitter = new SplitPane();
        splitter.setDividerPositions(0.25f);

        BorderPane vorstellungsauswahl = new BorderPane();
        vorstellungsauswahl.setStyle("-fx-border-color: #343434");
        vorstellungsauswahl.setTop(datumWaehlerPane);
        vorstellungsauswahl.setCenter(vorstellungenPane);

        splitter.getItems().addAll(vorstellungsauswahl, platzverkaufsPane);
        
        // Bei Fenstergröße ändern: nur Platzverkauf soll zusätzlichen Platz nutzen
        SplitPane.setResizableWithParent(vorstellungsauswahl, false);

        contentPane.setTop(erstelleUeberschriftPanel());
        contentPane.setCenter(splitter);
        contentPane.setBottom(erstelleBeendenPanel());

        scene.getStylesheets().add("de/hawhh/informatik/sml/kino/werkzeuge/kasse/Kasse.css");
        primaryStage.setScene(scene);
        _stage = primaryStage;
    }

    /**
     * Zeigt das Fenster an.
     */
    public void zeigeFenster()
    {
        _stage.show();
    }

    /**
     * Schließt das Fenster.
     */
    public void schliesseFenster()
    {
        System.exit(0);
    }


    /**
     * Erzeugt das Panel mit der Überschrift fuer das Programm.
     */
    private Pane erstelleUeberschriftPanel()
    {
        StackPane topPanel = new StackPane();  // Kinder per Default zentriert
        topPanel.setStyle("-fx-background-color: #F6B17A");
        topPanel.setPadding(new Insets(10));

        Label label = new Label(TITEL);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.ITALIC, 30));
        label.setTextFill(Color.valueOf("#121212"));

        topPanel.getChildren().add(label);

        return topPanel;
    }

    /**
     * Erzeugt das Panel mit dem Beenden-Button.
     */
    private Pane erstelleBeendenPanel()
    {
        FlowPane bottomPane = new FlowPane();
        // bottomPane.setOrientation(Orientation.HORIZONTAL);
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        bottomPane.setPadding(new Insets(10));
        bottomPane.setStyle("-fx-background-color: #343434");
        _beendenButton = new Button("Beenden");
        CustomButton.setStyle(_beendenButton, false);
        bottomPane.getChildren().add(_beendenButton);

        return bottomPane;
    }


    protected Scene getScene()
    {
        return _stage.getScene();
    }
    /**
     * Liefert den Beenden-Button.
     */
    public Button getBeendenButton()
    {
        return _beendenButton;
    }

}
