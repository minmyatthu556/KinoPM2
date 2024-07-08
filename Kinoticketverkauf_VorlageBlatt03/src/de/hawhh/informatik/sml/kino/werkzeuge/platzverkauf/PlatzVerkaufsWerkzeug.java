package de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;
import de.hawhh.informatik.sml.kino.werkzeuge.bezahlung.BarzahlungsWerkzeug;
import de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung.ErfolgreicheBezahlungsWerkzeug;
import javafx.scene.layout.Pane;
import de.hawhh.informatik.sml.kino.fachwerte.Platz;
import de.hawhh.informatik.sml.kino.materialien.Kinosaal;
import de.hawhh.informatik.sml.kino.materialien.Vorstellung;

/**
 * Mit diesem Werkzeug können Plätze verkauft und storniert werden. Es arbeitet
 * auf einer Vorstellung als Material. Mit ihm kann angezeigt werden, welche
 * Plätze schon verkauft und welche noch frei sind.
 * 
 * Dieses Werkzeug ist ein eingebettetes Subwerkzeug. Es kann nicht beobachtet
 * werden.
 * 
 * @author SE2-Team (Uni HH), PM2-Team
 * @version SoSe 2024
 */
public class PlatzVerkaufsWerkzeug
{
    // Die aktuelle Vorstellung, deren Plätze angezeigt werden. Kann null sein.
    private Vorstellung _vorstellung;

    private PlatzVerkaufsWerkzeugUI _ui;

    private Set<Platz> _ausgewaehltePlaetze;

    /**
     * Initialisiert das PlatzVerkaufsWerkzeug.
     */
    public PlatzVerkaufsWerkzeug()
    {
        _ui = new PlatzVerkaufsWerkzeugUI();

        registriereUIAktionen();
        // Am Anfang wird keine Vorstellung angezeigt:
        setVorstellung(null);
    }

    /**
     * Gibt das Panel dieses Subwerkzeugs zurück. Das Panel sollte von einem
     * Kontextwerkzeug eingebettet werden.
     * 
     * @ensure result != null
     */
    public Pane getUIPane()
    {
        return _ui.getUIPane();
    }

    /**
     * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
     */
    private void registriereUIAktionen()
    {
        _ui.getVerkaufenButton().setOnAction(ae -> {

            Thread zaehlerThread = new Thread(new Zaehler());
            zaehlerThread.start();
//            Zaehler zaehler = new Zaehler();
//            zaehler.run();
//            try
//            {
//                zaehlerThread.join();
//            }
//            catch (InterruptedException e)
//            {
//                throw new RuntimeException(e);
//            }
            verkaufePlaetze(_vorstellung);
        });

        _ui.getStornierenButton().setOnAction(ae -> stornierePlaetze(_vorstellung));

        _ui.getDeselektButton().setOnAction(ae -> deselektierePlaetze(_vorstellung));

        _ui.getPlatzplan().addPlatzSelectionListener(event -> {
            reagiereAufNeuePlatzAuswahl(event.getAusgewaehltePlaetze(), event.getGeklicktenPlatz());
        });
    }

    /**
     * Reagiert darauf, dass sich die Menge der ausgewählten Plätze geändert
     * hat.
     * 
     * @param plaetze die jetzt ausgewählten Plätze.
     * @param platz der zuletzt ausgewählte Platz.
     */
    private void reagiereAufNeuePlatzAuswahl(Set<Platz> plaetze, Platz platz)
    {
        _ui.getVerkaufenButton().setDisable(!istVerkaufenMoeglich(plaetze));
        _ui.getStornierenButton().setDisable(!istStornierenMoeglich(plaetze));
        _ui.getDeselektButton().setDisable(plaetze.isEmpty());
        if (_vorstellung != null)
        {
            _ausgewaehltePlaetze = plaetze;
            _vorstellung.updateAusgewaehltePlaetze(_ausgewaehltePlaetze, platz);
        }
        aktualisierePreisanzeige(plaetze);
    }

    /**
     * Aktualisiert den anzuzeigenden Gesamtpreis
     *
     * @param plaetze die ausgewählten Plätze
     */
    private void aktualisierePreisanzeige(Set<Platz> plaetze)
    {
        if (_vorstellung == null)
        {
            return;
        }

        int preis = _vorstellung.getPreisFuerPlaetze(plaetze);
        //TODO
        Geldbetrag preisGeldbetrag = Geldbetrag.get(preis);
        if (istVerkaufenMoeglich(plaetze))
        {
            _ui.getPreisLabel().setText("Gesamtpreis: " + preisGeldbetrag.getFormatiertenString() + " €");
        }
        else if (istStornierenMoeglich(plaetze))
        {
            _ui.getPreisLabel().setText("Gesamtpreis: -" + preisGeldbetrag.getFormatiertenString() + " €");
        }
        else
        {
            _ui.getPreisLabel().setText("Gesamtpreis:");
        }
    }

    /**
     * Prüft, ob die angegebenen Plätze alle storniert werden können.
     *
     * @param plaetze die zu stornierenden Plätze
     */
    private boolean istStornierenMoeglich(Set<Platz> plaetze)
    {
        return !plaetze.isEmpty() && _vorstellung.sindStornierbar(plaetze);
    }

    /**
     * Prüft, ob die angegebenen Plätze alle verkauft werden können.
     *
     * @param plaetze die zu verkaufenden Plätze
     */
    private boolean istVerkaufenMoeglich(Set<Platz> plaetze)
    {
        return !plaetze.isEmpty() && _vorstellung.sindVerkaufbar(plaetze);
    }

    /**
     * Setzt die Vorstellung. Sie ist das Material dieses Werkzeugs. Wenn die
     * Vorstellung gesetzt wird, muss die Anzeige aktualisiert werden. Die
     * Vorstellung darf auch null sein.
     *
     * @param vorstellung die Vorstellung
     */
    public void setVorstellung(Vorstellung vorstellung)
    {
        _vorstellung = vorstellung;
        aktualisierePlatzplan();
    }

    /**
     * Aktualisiert den Platzplan basierend auf der ausgwählten Vorstellung.
     */
    private void aktualisierePlatzplan()
    {
        if (_vorstellung != null)
        {
            Kinosaal saal = _vorstellung.getKinosaal();
            _ui.getPlatzplan().setAnzahlPlaetze(saal.getAnzahlReihen(),
                    saal.getAnzahlSitzeProReihe());

            for (Platz platz : saal.getPlaetze())
            {
                if (_vorstellung.istPlatzVerkauft(platz))
                {
                    _ui.getPlatzplan().markierePlatzAlsVerkauft(platz);
                }
                if (_vorstellung.istPlatzAusgewaehlt(platz))
                {
                    _ui.getPlatzplan().markierePlatzAlsAusgewaehlt(platz);
                }
            }
            _ui.getPlatzplan().updateAusgewaehltePlaetze(_vorstellung.getAusgewaehltePlaetze());
            aktualisierePreisanzeige(_ui.getPlatzplan().getAusgewaehltePlaetze());
            if (!_vorstellung.getAusgewaehltePlaetze().isEmpty())
            {
                _ui.getDeselektButton().setDisable(false);
                _ui.getVerkaufenButton().setDisable(!istVerkaufenMoeglich(_vorstellung.getAusgewaehltePlaetze()));
                _ui.getStornierenButton().setDisable(!istStornierenMoeglich(_vorstellung.getAusgewaehltePlaetze()));
            }
        }
        else
        {
            _ui.getPlatzplan().setAnzahlPlaetze(0, 0);
        }
    }

    /**
     * Verkauft die ausgewählten Plätze. Hierbei wird ein neues BezahlungsWerkzeug erstellt und die UI angezeigt.
     *
     * @param vorstellung Die Vorstellung, für die Plätze verkauft werden sollen.
     */
    private void verkaufePlaetze(Vorstellung vorstellung)
    {
        _vorstellung = vorstellung;
        _ausgewaehltePlaetze = _vorstellung.getAusgewaehltePlaetze();
        int gesamtpreis = _vorstellung.getPreisFuerPlaetze(_ausgewaehltePlaetze);

        BarzahlungsWerkzeug barzahlungsWerkzeug = new BarzahlungsWerkzeug(gesamtpreis);
        barzahlungsWerkzeug.zeigeUI();
        if (barzahlungsWerkzeug.istBezahlt())
        {
            vorstellung.verkaufePlaetze(_ausgewaehltePlaetze);
            aktualisierePlatzplan();
        }
    }

    /**
     * Storniert die ausgewählten Plaetze.
     */
    private void stornierePlaetze(Vorstellung vorstellung)
    {
        Set<Platz> plaetze = _ui.getPlatzplan().getAusgewaehltePlaetze();
        ErfolgreicheBezahlungsWerkzeug storno = new ErfolgreicheBezahlungsWerkzeug(vorstellung.getPreisFuerPlaetze(plaetze), false);
        storno.zeigeUI();
        vorstellung.stornierePlaetze(plaetze);
        aktualisierePlatzplan();
    }

    public void deselektierePlaetze(Vorstellung vorstellung)
    {
        vorstellung.deselektiereAllePlaetze();
        aktualisierePlatzplan();
    }

    public void handleEnter(Vorstellung vorstellung)
    {
        _vorstellung = vorstellung;
        _ausgewaehltePlaetze = _vorstellung.getAusgewaehltePlaetze();

        if (istVerkaufenMoeglich(_ausgewaehltePlaetze))
        {
            verkaufePlaetze(_vorstellung);
        }
        else if (istStornierenMoeglich(_ausgewaehltePlaetze))
        {
            stornierePlaetze(_vorstellung);
        }
    }

    public void handleEscape(Vorstellung vorstellung)
    {
        _vorstellung = vorstellung;
        deselektierePlaetze(_vorstellung);
    }
}
