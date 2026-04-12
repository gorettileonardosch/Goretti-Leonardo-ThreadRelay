package threadrelay;

import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingUtilities;

/**
 * GestoreGara - Controlla tutta la logica della gara di staffetta
 * Gestisce i thread, la pausa, lo stop e gli aggiornamenti della UI
 */
public class GestoreGara {
    
    // Costanti
    private static final int RUNNERS = 4;           // Numero di atleti
    private static final int FINISH = 99;           // Valore di arrivo
    
    // Stato della gara
    private final Object pauseLock = new Object();  // Per sincronizzare la pausa
    private volatile boolean paused = false;        // La gara è in pausa?
    private volatile boolean stopped = false;       // La gara è ferma?
    
    // Thread e atleti
    private final AtletaRelay[] runners = new AtletaRelay[RUNNERS];
    private final Thread[] runnerThreads = new Thread[RUNNERS];
    private final AtomicInteger finishedCount = new AtomicInteger(0);  // Atleti terminati
    
    private final FRMPista frame;  // La finestra principale
    
    /**
     * Crea il gestore della gara
     * @param frame la finestra dove mostrare i risultati
     */
    public GestoreGara(FRMPista frame) {
        this.frame = frame;
    }
    
    /**
     * Resetta la gara e il display
     */
    public void resetRace() {
        paused = false;
        stopped = false;
        finishedCount.set(0);
        
        // Aggiorna la UI nel thread di Swing
        SwingUtilities.invokeLater(() -> {
            frame.resetRunnerProgress();
        });
    }
    
    /**
     * Avvia la gara: crea i 4 atleti e inizia il primo
     * @param speedMillis quanto tempo aspettare tra un passo e l'altro
     */
    public void startRace(int speedMillis) {
        resetRace();
        stopped = false;
        
        // Crea un atleta per ogni numero
        for (int i = 0; i < RUNNERS; i++) {
            runners[i] = new AtletaRelay(i, speedMillis, pauseLock, this);
            runnerThreads[i] = new Thread(runners[i], "Atleta-" + (i + 1));
        }
        
        // Avvia solo il primo atleta (il relay parte quando arriva a 90)
        if (runnerThreads[0] != null) {
            runnerThreads[0].start();
        }
    }
    
    /**
     * Mette in pausa la gara
     */
    public void pauseRace() {
        if (!paused && !stopped) {
            paused = true;
        }
    }
    
    /**
     * Riprende la gara da pausa
     */
    public void resumeRace() {
        if (paused && !stopped) {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();  // Sveglia i thread in attesa
            }
        }
    }
    
    /**
     * Ferma completamente la gara
     */
    public void stopRace() {
        stopped = true;
        paused = false;
        synchronized (pauseLock) {
            pauseLock.notifyAll();  // Sveglia i thread in attesa
        }
    }
    
    /**
     * Avvia il prossimo atleta (al relay)
     * @param currentIndex l'atleta che passa il testimone
     */
    public void startNextRunner(int currentIndex) {
        int nextIndex = currentIndex + 1;
        if (nextIndex < RUNNERS && !stopped) {
            Thread nextThread = runnerThreads[nextIndex];
            if (nextThread != null && !nextThread.isAlive()) {
                nextThread.start();  // Avvia il prossimo
            }
        }
    }
    
    /**
     * Aggiorna il progresso di un atleta sullo schermo
     * @param index numero dell'atleta
     * @param value il suo conteggio attuale
     */
    public void updateRunnerProgress(int index, int value) {
        SwingUtilities.invokeLater(() -> {
            frame.updateRunnerDisplay(index, value);
        });
    }
    
    /**
     * Segna che un atleta ha finito
     * Quando tutti e 4 hanno finito, la gara è completa
     */
    public void incrementFinishedCount() {
        if (finishedCount.incrementAndGet() == RUNNERS) {
            SwingUtilities.invokeLater(frame::enableControlsAfterFinish);
        }
    }
    
    // --- Getter per altri oggetti ---
    
    /**
     * Restituisce l'oggetto per sincronizzare la pausa
     */
    public Object getPauseLock() {
        return pauseLock;
    }
    
    /**
     * La gara è in pausa?
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * La gara è ferma?
     */
    public boolean isStopped() {
        return stopped;
    }
    
    /**
     * Quanti atleti ci sono?
     */
    public static int getNumRunners() {
        return RUNNERS;
    }
    
    /**
     * Qual è il valore di arrivo?
     */
    public static int getFinishValue() {
        return FINISH;
    }
}
