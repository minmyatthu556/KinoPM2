package de.hawhh.informatik.sml.kino.werkzeuge.datumsauswaehler;

import de.hawhh.informatik.sml.kino.uicustomization.CustomButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import de.hawhh.informatik.sml.kino.fachwerte.Datum;
import de.hawhh.informatik.sml.kino.werkzeuge.ObservableSubwerkzeug;

/**
 * Mit diesem Werkzeug kann ein Datum ausgewählt werden.
 * 
 * Dieses Werkzeug ist ein einbettbares Subwerkzeug. Es benachrichtigt sein
 * Kontextwerkzeug, wenn sich das ausgewählte Datum geändert hat.
 * 
 * Bei diesem Werkzeug hätte sich die Trennung in Werkzeug- und UI-Klasse nicht
 * gelohnt.
 * 
 * @author SE2-Team Uni HH, PM2-Team
 * @version SoSe 2024
 */
public class DatumAuswaehlWerkzeug extends ObservableSubwerkzeug
{
    private Datum _ausgewaehltesDatum;

    private Pane _hauptPanel;
    private Button _weiterButton;
    private Button _zurueckButton;
    private Label _datumLabel;

    /**
     * Initialisiert dieses Werkzeug. Das initial ausgewählte Datum ist der
     * heutige Tag.
     */
    public DatumAuswaehlWerkzeug()
    {
        _ausgewaehltesDatum = Datum.heute();
        _hauptPanel = erstellePanel(_ausgewaehltesDatum.getFormatiertenString());
        registriereUIAktionen();
    }

    /**
     * Erstellt das Panel.
     */
    private Pane erstellePanel(String startDatumString)
    {
        VBox panel = new VBox();
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: #343434");
        panel.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        StackPane anzeige = new StackPane();
        _datumLabel = new Label(startDatumString);
        _datumLabel.setStyle("-fx-font-size: 20pt; -fx-text-fill: #f5f3f0");
        anzeige.getChildren().add(_datumLabel);

        HBox navigation = new HBox();

        Image imageZurueck = new Image("images/previous.png");
        ImageView zurueckView = new ImageView(imageZurueck);
        zurueckView.setFitHeight(15);
        zurueckView.setFitWidth(15);
        zurueckView.setPreserveRatio(true);
        _zurueckButton = new Button("Zurueck", zurueckView);

        Image imageWeiter = new Image("images/next.png");
        ImageView weiterView = new ImageView(imageWeiter);
        weiterView.setFitHeight(15);
        weiterView.setFitWidth(15);
        weiterView.setPreserveRatio(true);
        _weiterButton = new Button("Weiter", weiterView);
        _weiterButton.setContentDisplay(ContentDisplay.RIGHT);

        HBox.setHgrow(_zurueckButton, Priority.ALWAYS);
        HBox.setHgrow(_weiterButton, Priority.ALWAYS);
        _zurueckButton.setMaxWidth(Double.MAX_VALUE);
        _weiterButton.setMaxWidth(Double.MAX_VALUE);
        navigation.setSpacing(10);
        CustomButton.setStyle(_zurueckButton, true);
        CustomButton.setStyle(_weiterButton, true);
        navigation.getChildren().addAll(_zurueckButton,_weiterButton);

        panel.getChildren().addAll(anzeige,navigation);

        return panel;
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Zurueck-Button gedrueckt wurde.
     */
    private void zurueckButtonWurdeGedrueckt()
    {
        _ausgewaehltesDatum = _ausgewaehltesDatum.vorherigerTag();
        _datumLabel.setText(_ausgewaehltesDatum.getFormatiertenString());
        informiereUeberAenderung();
    }

    /**
     * Diese Methode wird aufgerufen, wenn der Weiter-Button gedrueckt wurde.
     */
    private void weiterButtonWurdeGedrueckt()
    {
        _ausgewaehltesDatum = _ausgewaehltesDatum.naechsterTag();
        _datumLabel.setText(_ausgewaehltesDatum.getFormatiertenString());
        informiereUeberAenderung();
    }

    /**
     * Gibt das Panel dieses Subwerkzeugs zurück. Das Panel sollte von einem
     * Kontextwerkzeug eingebettet werden.
     * 
     * @ensure result != null
     */
    public Pane getUIPane()
    {
        return _hauptPanel;
    }

    /**
     * Gibt das im Werkzeug ausgewählte Datum zurück.
     * 
     * @ensure result != null
     */
    public Datum getSelektiertesDatum()
    {
        return _ausgewaehltesDatum;
    }

    /**
     * Verbindet die fachlichen Aktionen mit den Interaktionselementen der
     * graphischen Benutzungsoberfläche.
     */
    // TODO hier
    private void registriereUIAktionen()
    {
        _zurueckButton.setOnAction(ae -> zurueckButtonWurdeGedrueckt());
        
        _weiterButton.setOnAction(ae -> weiterButtonWurdeGedrueckt());
    }
}
