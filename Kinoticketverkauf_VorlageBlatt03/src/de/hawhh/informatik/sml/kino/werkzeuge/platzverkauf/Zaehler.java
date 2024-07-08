package de.hawhh.informatik.sml.kino.werkzeuge.platzverkauf;

public class Zaehler implements Runnable
{
    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            System.err.println(i);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.err.println("Ich hör schon auf");
                break;
            }
        }

    }
}
