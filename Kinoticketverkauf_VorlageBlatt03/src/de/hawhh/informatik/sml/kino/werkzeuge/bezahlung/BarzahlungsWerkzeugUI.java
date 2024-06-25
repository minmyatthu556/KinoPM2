package de.hawhh.informatik.sml.kino.werkzeuge.bezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Die Klasse BezahlungsWerkzeugUI stellt die Benutzeroberfläche für das Bezahlungswerkzeug bereit.
 */
public class BarzahlungsWerkzeugUI
{
    private Stage _stage;
    private Button _verkauf;
    private Button _beenden;
    private TextField _gegeben;
    private Scene _scene;

    /**
     * Erstellt eine neue BezahlungsWerkzeugUI.
     *
     * @param title Der Titel des Fensters.
     * @param preis Der Preis, der angezeigt werden soll.
     */
    public BarzahlungsWerkzeugUI(String title, String preis)
    {
        _stage = new Stage();
        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.setTitle(title);

        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(20);
        pane.setPadding(new Insets(20, 20, 20, 20));

        Label preisLabel = new Label("Preis: " + preis + " €");
        Label zahlungslabel = new Label("Zahlungsbetrag eingeben (in Euro Cent): ");
        _gegeben = new TextField();
        _verkauf = new Button("OK");
        _beenden = new Button("Abbrechen");

        GridPane.setConstraints(preisLabel, 0, 0);
        GridPane.setConstraints(zahlungslabel, 0, 1);
        GridPane.setConstraints(_gegeben, 1, 1);
        GridPane.setConstraints(_verkauf, 1, 2);
        GridPane.setConstraints(_beenden, 0, 2);

        pane.getChildren().addAll(preisLabel, zahlungslabel, _gegeben, _verkauf, _beenden);

        _verkauf.setDisable(true);

        _scene = new Scene(pane);
        _stage.setScene(_scene);
    }

    public void setVerkaufButtonDisabled(boolean disabled)
    {
    _verkauf.setDisable(disabled);
    }

    /**
     * Gibt den Verkauf-Button zurück.
     *
     * @return Der Verkauf-Button.
     */
    public Button getVerkauf()
    {
        return _verkauf;
    }

    /**
     * Gibt den Beenden-Button zurück.
     *
     * @return Der Beenden-Button.
     */
    public Button getBeenden()
    {
        return _beenden;
    }

    /**
     * Zeigt das Fenster an.
     */
    public void zeigeAn()
    {
        _stage.showAndWait();
    }

    /**
     * Schließt das Fenster.
     */
    public void close()
    {
        _stage.close();
    }

    /**
     * Gibt den eingegebenen Betrag zurück.
     *
     * @return Der eingegebene Betrag.
     */
    public TextField getGegeben()
    {
        return _gegeben;
    }


    public Scene getScene()
    {
        return _scene;
    }
}
