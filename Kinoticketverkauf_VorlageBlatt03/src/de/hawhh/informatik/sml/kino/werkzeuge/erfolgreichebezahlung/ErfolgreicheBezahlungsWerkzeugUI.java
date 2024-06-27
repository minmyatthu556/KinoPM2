package de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.uicustomization.CustomButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Currency;

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
    public ErfolgreicheBezahlungsWerkzeugUI(String title, Geldbetrag rueckgeld, boolean istVerkaufErfolgreich)
    {
        _stage = new Stage();
        _stage.initModality(Modality.APPLICATION_MODAL);
        _stage.setTitle(title);

        VBox pane = new VBox();

        if (istVerkaufErfolgreich)
        {
            _message = new Label("Verkauf wurde erfolgreich abgeschlossen.");
            _rueckgeldLabel = new Label("Rückgeld: " + rueckgeld + " €");
        }
        else
        {
            _message = new Label("Stornierung wurde erfolgreich abgeschlossen.");
            _rueckgeldLabel = new Label("Summe: -" + rueckgeld + " €");
        }
        _okButton = new Button("OK");
        CustomButton.setStyle(_okButton, true);

        _stage.setWidth(450);
        _stage.setHeight(250);


        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        pane.setPadding(new Insets(20, 20, 20, 20));

        pane.getChildren().addAll(_message, _rueckgeldLabel, _okButton);

        _scene = new Scene(pane);
        _scene.getStylesheets().add(getClass().getResource("ErfolgreicheBezahlungsWerkzeug.css").toExternalForm());
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
