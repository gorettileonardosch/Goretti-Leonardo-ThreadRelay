package threadrelay;

import java.util.Random;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class Corridore implements Runnable {
    private final int pos;
    private int progresso;
    private final double velocita;
    private boolean inPausa;
    private final JProgressBar barra;
    private final FRMPista finestra;

    public Corridore(FRMPista finestra, JProgressBar barra, double velocita, int pos) {
        this.pos = pos;
        this.progresso = 0;
        this.velocita = velocita;
        this.inPausa = false;
        this.barra = barra;
        this.finestra = finestra;
    }

    public void riprendi() {
        synchronized (FRMPista.class) {
            inPausa = false;
            FRMPista.class.notifyAll();
        }
    }

    public void setInPausa(boolean inPausa) {
        this.inPausa = inPausa;
    }

    public boolean isInPausa() {
        return inPausa;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (progresso < FRMPista.TRAGUARDO) {
            synchronized (FRMPista.class) {
                while (inPausa || pos > FRMPista.posAttuale) {
                    try {
                        FRMPista.class.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            int passo = (int) Math.round(random.nextInt(5) * velocita);
            if (passo == 0) {
                passo = 1;
            }

            progresso += passo;

            if (pos == FRMPista.posAttuale && progresso >= FRMPista.PUNTODISCAMBIO) {
                synchronized (FRMPista.class) {
                    if (pos == FRMPista.posAttuale) {
                        FRMPista.posAttuale++;
                        FRMPista.class.notifyAll();
                    }
                }
            }

            if (progresso > FRMPista.TRAGUARDO) {
                progresso = FRMPista.TRAGUARDO;
            }

            final int valore = progresso;
            SwingUtilities.invokeLater(() -> {
                barra.setValue(valore);
                finestra.aggiornaProgressioneCorridore(pos, valore);
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (progresso >= FRMPista.TRAGUARDO) {
            SwingUtilities.invokeLater(finestra::corridoreCompletato);
        }
    }
}
