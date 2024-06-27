package de.hawhh.informatik.sml.kino.werkzeuge.bezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung.ErfolgreicheBezahlungsWerkzeug;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Die Klasse BezahlungsWerkzeug stellt das Werkzeug für die Bezahlung bereit.
 */
public class BarzahlungsWerkzeug
{
    private BarzahlungsWerkzeugUI _ui;

    private int _preis;

    private Geldbetrag _preisGeldBetrag;

    private ErfolgreicheBezahlungsWerkzeug _eb;
    
    private boolean _istVerkauft;

    private String _rueckgeld;

    /**
     * Erstellt ein neues BezahlungsWerkzeug.
     *
     * @param gesamtpreis Der Preis, der bezahlt werden soll.
     */
    public BarzahlungsWerkzeug(int gesamtpreis)
    {
        _preis = gesamtpreis;
        _preisGeldBetrag = Geldbetrag.get(gesamtpreis);
        _ui = new BarzahlungsWerkzeugUI("Bezahlung", _preisGeldBetrag.getFormatiertenString(), _rueckgeld);
        _rueckgeld = "";
        _istVerkauft = false;

        _ui.getGegeben().textProperty().addListener((observable, oldValue, newValue) ->
        {
            int gegeben;
            try
            {
                if (newValue.matches("\\d+,\\d{2}$"))
                {
                    gegeben = Integer.parseInt(newValue.replace(",", ""));
                }
                else
                {
                    gegeben = Integer.parseInt(newValue);
                }
                int rueckgeld = gegeben - _preis;
                if (rueckgeld >= 0)
                {
                    _rueckgeld = Geldbetrag.get(rueckgeld).getFormatiertenString();
                    _ui.updateRueckgeldInUI(_rueckgeld);
                }
                else
                {
                    _rueckgeld = "";
                    _ui.updateRueckgeldInUI(_rueckgeld);
                }

                _ui.setVerkaufButtonDisabled(rueckgeld < 0);
            }
            catch (NumberFormatException e)
            {
                _ui.setVerkaufButtonDisabled(true);
            }
        });

        verkaufenWurdeGedrueckt();
        beendenWurdeGedrueckt();
        reagiereAufEnter();
        reagiereAufEscape();
    }

    /**
     * Reagiert auf das Klicken des Verkauf-Buttons.
     */
    public void verkaufenWurdeGedrueckt()
    {
        _ui.getVerkauf().setOnAction(ae ->
        {
            String uiText = _ui.getGegeben().getText();
            int betrag;

            if (uiText.matches("\\d+,\\d{2}$"))
            {
                betrag = Integer.parseInt(uiText.replace(",", ""));
            }
            else
            {
                betrag = Integer.parseInt(uiText);
            }

            int rueckgeld = betrag - _preis;
            _eb = new ErfolgreicheBezahlungsWerkzeug(rueckgeld, true);
            _istVerkauft = true;
            _ui.close();
            _eb.zeigeUI();
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

    public boolean istBezahlt()
    {
        return _istVerkauft;
    }
}
