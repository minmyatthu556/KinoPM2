package de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Die Klasse ErfolgreicheBezahlungsWerkzeugUI stellt die Benutzeroberfläche für eine erfolgreiche Bezahlung dar.
 */
public class ErfolgreicheBezahlungsWerkzeugUI
{
    private Stage _stage;
    private Label _message;
    private Button _okButton;
    private Label _rueckgeldLabel;
    private Scene _scene;

    /**
     * Erstellt eine neue ErfolgreicheBezahlungsWerkzeugUI.
     *
     * @param title Der Titel des Fensters.
     * @param rueckgeld Der Betrag des Rückgelds.
     */
    public ErfolgreicheBezahlungsWerkzeugUI(String title, Geldbetrag rueckgeld)
    {
        _stage = new Stage();
        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.setTitle(title);

       VBox pane = new VBox();

        _message = new Label("Verkauf wurde erfolgreich abgeschlossen.");
        _rueckgeldLabel = new Label("Rückgeld: " + rueckgeld + " €");
        _okButton = new Button("OK");

        _stage.setWidth(350);
        _stage.setHeight(150);


        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        pane.setPadding(new Insets(20, 20, 20, 20));

        pane.getChildren().addAll(_message, _rueckgeldLabel, _okButton);

        _scene = new Scene(pane);
        _stage.setScene(_scene);
    }

    /**
     * Zeigt das Fenster an.
     */
    public void zeigeAn()
    {
        _stage.showAndWait();
    }

    /**
     * Gibt den OK-Button zurück.
     *
     * @return Der OK-Button.
     */
    public Button getOKButton()
    {
        return _okButton;
    }

    /**
     * Schließt das Fenster.
     */
    public void close()
    {
        _stage.close();
    }

    public Scene getScene()
    {
        return _scene;
    }
}
