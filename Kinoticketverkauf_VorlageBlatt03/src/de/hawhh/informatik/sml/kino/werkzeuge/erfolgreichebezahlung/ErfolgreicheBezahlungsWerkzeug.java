package de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;

/**
 * Die Klasse ErfolgreicheBezahlungsWerkzeug stellt das Werkzeug für eine erfolgreiche Bezahlung dar.
 */
public class ErfolgreicheBezahlungsWerkzeug
{
    private ErfolgreicheBezahlungsWerkzeugUI _ui;

    private boolean istVerkaufErfolgreich;
    //TODO
    private Geldbetrag _rueckgeld;

    /**
     * Erstellt ein neues ErfolgreicheBezahlungsWerkzeug.
     *
     * @param rueckgeld Der Betrag des Rückgelds.
     * @param istVerkaufErfolgreich Ob der Verkauf erfolgreich war.
     * @require rueckgeld != null
     */
    public ErfolgreicheBezahlungsWerkzeug(int rueckgeld, boolean istVerkaufErfolgreich)
    {
        assert rueckgeld >= 0 : "Vorbedingung verletzt: rueckgeld >= 0";

        _rueckgeld = Geldbetrag.get(rueckgeld);

        if (istVerkaufErfolgreich)
        {
            _ui = new ErfolgreicheBezahlungsWerkzeugUI("Erfolgreicher Verkauf", _rueckgeld.getFormatiertenString(), true);
        }
        else
        {
            _ui = new ErfolgreicheBezahlungsWerkzeugUI("Erfolgreiche Stornierung", _rueckgeld.getFormatiertenString(), false);
        }
        okWurdeGedrueckt();
        reagiereAufEnter();
    }

    /**
     * Reagiert auf das Klicken des OK-Buttons.
     */
    public void okWurdeGedrueckt()
    {
        _ui.getOKButton().setOnAction(ae -> _ui.close());
    }

    public void reagiereAufEnter()
    {
        _ui.getScene().setOnKeyPressed(ke -> {
            if (ke.getCode().toString().equals("ENTER"))
            {
                _ui.close();
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
