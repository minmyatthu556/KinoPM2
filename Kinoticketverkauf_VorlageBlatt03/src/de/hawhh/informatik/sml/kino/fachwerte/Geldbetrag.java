package de.hawhh.informatik.sml.kino.fachwerte;

/**
 * Ein Geldbetrag in Euro, gespeichert als ganze Euro- und ganze Cent-Beträge.
 *
 * @author SE2-Team, PM2-Team
 * @version SoSe 2024
 */
public final class Geldbetrag implements Comparable<Geldbetrag>
{
    private final int _euroAnteil;
    private final int _centAnteil;

    /**
     * Wählt einen Geldbetrag aus.
     *
     * @param eurocent
     *            Der Betrag in ganzen Euro-Cent
     *
     * @require eurocent >= 0;
     */
    public static Geldbetrag get(int eurocent)
    {
        assert eurocent >= 0 : "Vorbedingung verletzt: eurocent >= 0";
        return new Geldbetrag(eurocent);
    }

    private Geldbetrag(int eurocent)
    {
        _euroAnteil = eurocent / 100;
        _centAnteil = eurocent % 100;
    }

    /**
     * Gibt den Eurobetrag ohne Cent zurück.
     *
     * @return Den Eurobetrag ohne Cent.
     */
    public int getEuroAnteil()
    {
        return _euroAnteil;
    }

    /**
     * Gibt den Centbetrag ohne Eurobetrag zurück.
     */
    public int getCentAnteil()
    {
        return _centAnteil;
    }

    /**
     * Liefert einen formatierten String des Geldbetrags in der Form "10,23"
     * zurück.
     *
     * @return eine String-Repräsentation.
     */
    public String getFormatiertenString()
    {
        return _euroAnteil + "," + getFormatiertenCentAnteil();
    }

    /**
     * Liefert einen zweistelligen Centbetrag zurück.
     * Vom Mediathek kopiert
     *
     * @return eine String-Repräsentation des Cent-Anteils.
     */
    private String getFormatiertenCentAnteil()
    {
        String result = "";
        if (_centAnteil < 10)
        {
            result += "0";
        }
        result += _centAnteil;
        return result;
    }

    /**
     * Addiert den übergebenen Geldbetrag zum aktuellen Geldbetrag und gibt das Ergebnis als neuen Geldbetrag zurück.
     *
     * @param otherGb Der zu addierende Geldbetrag.
     * @require otherGb != null
     * @return Das Ergebnis der Addition als neuer Geldbetrag.
     */
    public Geldbetrag addiere(Geldbetrag otherGb)
    {
        assert otherGb != null : "Vorbedingung verletzt: otherGb != null";

        return new Geldbetrag((getEuroAnteil() * 100 + getCentAnteil()) + (otherGb.getEuroAnteil() * 100 + otherGb.getCentAnteil()));
    }

    /**
     * Subtrahiert den übergebenen Geldbetrag vom aktuellen Geldbetrag und gibt das Ergebnis als neuen Geldbetrag zurück, sofern das Ergebnis nicht negativ ist.
     *
     * @param otherGb Der zu subtrahierende Geldbetrag.
     * @require otherGb != null
     * @return Das Ergebnis der Subtraktion als neuer Geldbetrag.
     * @throws IllegalArgumentException Wenn das Ergebnis der Subtraktion negativ wäre.
     */
    public Geldbetrag subtrahiere(Geldbetrag otherGb)
    {
        assert otherGb != null : "Vorbedingung verletzt: otherGb != null";

        if (compareTo(otherGb) < 0)
        {
            throw new IllegalArgumentException("Der resultierende Geldbetrag darf nicht negativ sein.");
        }

        return new Geldbetrag((getEuroAnteil() * 100 + getCentAnteil()) - (otherGb.getEuroAnteil() * 100 + otherGb.getCentAnteil()));
    }

    /**
     * Multipliziert den aktuellen Geldbetrag mit dem übergebenen Faktor und gibt das Ergebnis als neuen Geldbetrag zurück.
     *
     * @param faktor Der Multiplikationsfaktor.
     * @require faktor >= 0
     * @return Das Ergebnis der Multiplikation als neuer Geldbetrag.
     */
    public Geldbetrag multipliziere(int faktor)
    {
        assert faktor >= 0 : "Vorbedingung verletzt: faktor >= 0";

        return new Geldbetrag((getEuroAnteil() * 100 + getCentAnteil()) * faktor);
    }

    /**
     * Generiert einen Hashcode für das Geldbetrag-Objekt.
     *
     * @return Der generierte Hashcode.
     */
    // vom Meditathek kopiert
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime + _centAnteil;
        result = prime * result + _euroAnteil;
        return result;
    }

    /**
     * Überprüft, ob das übergebene Objekt gleich dem aktuellen Geldbetrag-Objekt ist.
     *
     * @param obj Das zu vergleichende Objekt.
     * @return true, wenn das übergebene Objekt gleich dem aktuellen Geldbetrag-Objekt ist, sonst false.
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Geldbetrag)
        {
            Geldbetrag other = (Geldbetrag) obj;
            result = (_centAnteil == other._centAnteil)
                    && (_euroAnteil == other._euroAnteil);
        }
        return result;
    }

    /**
     * Gibt diesen Geldbetrag in der Form "10,21" zurück.
     */
    @Override
    public String toString()
    {
        return getFormatiertenString();
    }

    /**
     * Vergleicht diesen Geldbetrag mit dem angegebenen Geldbetrag.
     * Das Ergebnis ist negativ, wenn dieser Geldbetrag kleiner als der angegebene Geldbetrag ist.
     * Das Ergebnis ist positiv, wenn dieser Geldbetrag größer als der angegebene Geldbetrag ist.
     * Das Ergebnis ist null, wenn beide Geldbeträge gleich sind.
     *
     * @param o Der zu vergleichende Geldbetrag.
     * @return Ein negativer Integer, null oder ein positiver Integer, je nachdem, ob dieser Geldbetrag kleiner, gleich oder größer als der angegebene Geldbetrag ist.
     */
    @Override
    public int compareTo(Geldbetrag o) {
        return (_euroAnteil * 100 + _centAnteil) - (o._euroAnteil * 100 + o._centAnteil);
    }
}

