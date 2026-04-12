package threadrelay;

/**
 * AtletaRelay - Esegue un singolo atleta nella gara di staffetta
 * Ogni atleta corre da 0 a 99, poi passa il testimone al prossimo
 */
public class AtletaRelay implements Runnable {
    
    // Dati dell'atleta
    private final int index;              // Posizione (0, 1, 2 o 3)
    private final int stepMillis;         // Tempo di attesa tra i passi (millisecondi)
    private int count;                    // Conteggio del progresso (0-99)
    private final GestoreGara gestore;    // Riferimento al gestore della gara
    
    // Costanti
    private static final int FINISH = 99;           // Linea di arrivo
    private static final int RELAY_HANDOFF = 90;    // A quale numero passa il testimone

    /**
     * Crea un atleta per la gara
     * @param index numero dell'atleta (0, 1, 2, 3)
     * @param stepMillis millisecondi tra un passo e l'altro
     * @param pauseLock non usato (sincronizzazione via gestore)
     * @param gestore il gestore della gara
     */
    public AtletaRelay(int index, int stepMillis, Object pauseLock, GestoreGara gestore) {
        this.index = index;
        this.stepMillis = stepMillis;
        this.count = 0;
        this.gestore = gestore;
    }

    /**
     * Esegue la corsa dell'atleta
     */
    @Override
    public void run() {
        // Mostra la posizione iniziale
        gestore.updateRunnerProgress(index, count);
        
        // Continua finché non è fermato e non ha raggiunto il traguardo
        while (!gestore.isStopped() && count < FINISH) {
            
            // Se siamo a 90, avvia il prossimo atleta (relay)
            if (count == RELAY_HANDOFF && index < GestoreGara.getNumRunners() - 1) {
                gestore.startNextRunner(index);
            }

            // Aspetta un po' prima di fare il prossimo passo
            try {
                Thread.sleep(stepMillis);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }

            // Se la gara è in pausa, aspetta qui
            synchronized (gestore.getPauseLock()) {
                while (gestore.isPaused() && !gestore.isStopped()) {
                    try {
                        gestore.getPauseLock().wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            // Se è stato controllato lo stop, esci
            if (gestore.isStopped()) {
                break;
            }

            // Vai al passo successivo e aggiorna il display
            count++;
            gestore.updateRunnerProgress(index, count);
        }

        // Quando finisce, segnala il completamento
        if (!gestore.isStopped() && count >= FINISH) {
            gestore.incrementFinishedCount();
        }
    }

    /**
     * Restituisce il conteggio attuale
     */
    public int getCount() {
        return count;
    }

    /**
     * Restituisce l'indice dell'atleta
     */
    public int getIndex() {
        return index;
    }
}
