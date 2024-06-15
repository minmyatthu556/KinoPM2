package de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf;

/**
 * Das Interface VerkaufBeobachter definiert die Methoden, die ein Beobachter des Verkaufs implementieren muss.
 */
public interface VerkaufBeobachter
{
    /**
     * Wird aufgerufen, wenn ein Verkauf durchgeführt wurde.
     */
    void verkaufWurdeDurchgefuehrt();
}
