package de.hawhh.informatik.sml.kino.fachwerte;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author SE2-Team, PM2-Team
 * @version SoSe 2024
 *
 */
public class GeldbetragTest {

    @Test
    public final void testGeldbetrag() {
        Geldbetrag betrag = Geldbetrag.get(100);
        assertEquals(1, betrag.getEuroAnteil());
        assertEquals(0, betrag.getCentAnteil());
        assertEquals("1,00", betrag.getFormatiertenString());

        betrag = Geldbetrag.get(0);
        assertEquals(0, betrag.getEuroAnteil());
        assertEquals(0, betrag.getCentAnteil());
        assertEquals("0,00", betrag.getFormatiertenString());

        betrag = Geldbetrag.get(99);
        assertEquals(0, betrag.getEuroAnteil());
        assertEquals(99, betrag.getCentAnteil());
        assertEquals("0,99", betrag.getFormatiertenString());

        betrag = Geldbetrag.get(101);
        assertEquals(1, betrag.getEuroAnteil());
        assertEquals(1, betrag.getCentAnteil());
        assertEquals("1,01", betrag.getFormatiertenString());
    }

    @Test
    public final void testEqualsHashcode() {
        Geldbetrag betrag1 = Geldbetrag.get(100);
        Geldbetrag betrag2 = Geldbetrag.get(100);
        assertTrue(betrag1.equals(betrag2));
        assertTrue(betrag1.hashCode() == betrag2.hashCode());

        Geldbetrag betrag3 = Geldbetrag.get(101);
        assertFalse(betrag1.equals(betrag3));
        assertFalse(betrag1.hashCode() == betrag3.hashCode());

        Geldbetrag betrag4 = Geldbetrag.get(1000);
        assertFalse(betrag1.equals(betrag4));
        assertFalse(betrag1.hashCode() == betrag4.hashCode());
    }

    @Test
    public final void testCompareTo() {
        Geldbetrag betrag1 = Geldbetrag.get(100);
        Geldbetrag betrag2 = Geldbetrag.get(100);
        assertEquals(0, betrag1.compareTo(betrag2));

        Geldbetrag betrag3 = Geldbetrag.get(200);
        assertTrue(betrag1.compareTo(betrag3) < 0);

        Geldbetrag betrag4 = Geldbetrag.get(50);
        assertTrue(betrag1.compareTo(betrag4) > 0);
    }

    @Test
    public final void testAddiere()
    {
        Geldbetrag betrag1 = Geldbetrag.get(100);
        Geldbetrag betrag2 = Geldbetrag.get(100);
        assertEquals(Geldbetrag.get(200), betrag1.addiere(betrag2));

        Geldbetrag betrag3 = Geldbetrag.get(50);
        assertEquals(Geldbetrag.get(150), betrag1.addiere(betrag3));
    }

    @Test
    public final void testSubtrahiere()
    {
        Geldbetrag betrag1 = Geldbetrag.get(100);
        Geldbetrag betrag2 = Geldbetrag.get(100);
        assertEquals(Geldbetrag.get(0), betrag1.subtrahiere(betrag2));

        Geldbetrag betrag3 = Geldbetrag.get(50);
        assertEquals(Geldbetrag.get(50), betrag1.subtrahiere(betrag3));

        Geldbetrag betrag4 = Geldbetrag.get(150);
        assertThrows(IllegalArgumentException.class, () -> betrag1.subtrahiere(betrag4));
    }

    @Test
    public final void testMultipliziere()
    {
        Geldbetrag betrag1 = Geldbetrag.get(100);
        assertEquals(Geldbetrag.get(200), betrag1.multipliziere(2));

        assertEquals(Geldbetrag.get(100), betrag1.multipliziere(1));

        assertEquals(Geldbetrag.get(0), betrag1.multipliziere(0));
    }
}
