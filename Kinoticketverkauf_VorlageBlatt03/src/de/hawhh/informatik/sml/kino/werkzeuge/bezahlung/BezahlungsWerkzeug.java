package de.hawhh.informatik.sml.kino.werkzeuge.bezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung.ErfolgreicheBezahlungsWerkzeug;
import de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf.VerkaufListener;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse BezahlungsWerkzeug stellt das Werkzeug für die Bezahlung bereit.
 */
public class BezahlungsWerkzeug
{
    private BezahlungsWerkzeugUI _ui;

    private int _preis;

    private Geldbetrag _preisGeldBetrag;

    private List<VerkaufListener> _beobachter;

    private ErfolgreicheBezahlungsWerkzeug _eb;

    /**
     * Erstellt ein neues BezahlungsWerkzeug.
     *
     * @param gesamtpreis Der Preis, der bezahlt werden soll.
     */
    public BezahlungsWerkzeug(int gesamtpreis)
    {
        _preis = gesamtpreis;
        _preisGeldBetrag = new Geldbetrag(gesamtpreis);
        _ui = new BezahlungsWerkzeugUI("Bezahlung", _preisGeldBetrag.getFormatiertenString());
        _beobachter = new ArrayList<>();


        verkaufenWurdeGedrueckt();
        beendenWurdeGedrueckt();
        reagiereAufEnter();
        reagiereAufEscape();
    }

    /**
     * Registriert einen Beobachter.
     *
     * @param beobachter Der Beobachter, der registriert werden soll.
     */
    public void registriereBeobachter(VerkaufListener beobachter)
    {
        _beobachter.add(beobachter);
    }

    /**
     * Informiert alle registrierten Beobachter über eine Änderung.
     */
    private void informiereUeberAenderung()
    {
        for (VerkaufListener beobachter : _beobachter)
        {
            beobachter.verkaufWurdeDurchgefuehrt();
        }
    }

    /**
     * Reagiert auf das Klicken des Verkauf-Buttons.
     */
    public void verkaufenWurdeGedrueckt()
    {
        _ui.getVerkauf().setOnAction(ae ->
        {
            if (_ui.getBeenden().getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Zahlungsfehler");
                alert.setHeaderText(null);
                alert.setContentText("Kein Betrag eingegeben. Bitte geben Sie einen Betrag ein.");
                alert.showAndWait();
            }
            else
            {
                try
                {
                    Integer.parseInt(_ui.getGegeben().getText());
                }
                catch (NumberFormatException e)
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Zahlungsfehler");
                    alert.setHeaderText(null);
                    alert.setContentText("Bitte geben Sie einen gültigen Betrag ein.");
                    alert.showAndWait();
                    return;
                }

                int betrag = Integer.parseInt(_ui.getGegeben().getText());
                int rueckgeld = betrag - _preis;

                if (betrag >= _preis)
                    {
                        _eb = new ErfolgreicheBezahlungsWerkzeug(rueckgeld);
                        informiereUeberAenderung();
                        _ui.close();
                        _eb.zeigeUI();
                    }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Zahlungsfehler");
                    alert.setHeaderText(null);
                    alert.setContentText("Zu wenig Geld gegeben. Bitte geben Sie einen Betrag größer oder gleich dem Preis " + _preisGeldBetrag + " € ein.");
                    alert.showAndWait();
                }
            }
        });
    }

    /**
     * Reagiert auf das Klicken des Beenden-Buttons.
     */
    public void beendenWurdeGedrueckt()
    {
        _ui.getBeenden().setOnAction(ae ->
        {
            _ui.close();
        });
    }

    public void reagiereAufEnter()
    {

        EventHandler<KeyEvent> enterHandler = ke -> {
            if (ke.getCode() == KeyCode.ENTER)
            {
                _ui.getVerkauf().fire();
            }
        };
        _ui.getGegeben().setOnKeyPressed(enterHandler);
    }

    public void reagiereAufEscape()
    {
        _ui.getScene().setOnKeyPressed(ke -> {
            if (ke.getCode().toString().equals("ESCAPE"))
            {
                _ui.getBeenden().fire();
            }
        });
    }

    /**
     * Zeigt die Benutzeroberfläche an.
     */
    public void zeigeUI()
    {
        _ui.zeigeAn();
    }
}
