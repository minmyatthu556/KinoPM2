package de.hawhh.informatik.sml.kino.werkzeuge.erfolgreichebezahlung;

import de.hawhh.informatik.sml.kino.fachwerte.Geldbetrag;

/**
 * Die Klasse ErfolgreicheBezahlungsWerkzeug stellt das Werkzeug für eine erfolgreiche Bezahlung dar.
 */
public class ErfolgreicheBezahlungsWerkzeug
{
    private ErfolgreicheBezahlungsWerkzeugUI _ui;

    private boolean istVerkaufErfolgreich;

    private Geldbetrag _rueckgeld;

    /**
     * Erstellt ein neues ErfolgreicheBezahlungsWerkzeug.
     *
     * @param rueckgeld Der Betrag des Rückgelds.
     */
    public ErfolgreicheBezahlungsWerkzeug(int rueckgeld, boolean istVerkaufErfolgreich)
    {
        _rueckgeld = Geldbetrag.get(rueckgeld);
        if (istVerkaufErfolgreich)
        {
            _ui = new ErfolgreicheBezahlungsWerkzeugUI("Erfolgreicher Verkauf", _rueckgeld, istVerkaufErfolgreich);
        }
        else
        {
            _ui = new ErfolgreicheBezahlungsWerkzeugUI("Erfolgreiche Stornierung", _rueckgeld, istVerkaufErfolgreich);
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
