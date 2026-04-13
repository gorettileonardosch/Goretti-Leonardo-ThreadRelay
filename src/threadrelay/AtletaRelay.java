package threadrelay;

/**
 * AtletaRelay - Rappresenta e gestisce l'esecuzione di un singolo atleta nella gara.
 * Implementa Runnable in modo da poter essere eseguito su un Thread separato.
 * L'atleta "corre" incrementando il proprio conteggio da 0 fino a raggiungere il traguardo (99).
 * Quando raggiunge un punto specifico (90), "passa il testimone" avviando il compagno successivo.
 */
public class AtletaRelay implements Runnable {
    
    // Attributi che definiscono l'identità e le tempistiche dell'atleta
    private final int indice;               // Identificativo della posizione dell'atleta (0, 1, 2 o 3)
    private final int millisecondiPasso;    // Tempo di attesa in millisecondi tra un "passo" e l'altro (determina la velocità)
    private int conteggio;                  // Variabile che traccia il progresso attuale dell'atleta (da 0 a 99)
    private final GestoreGara gestore;      // Riferimento al controllore centrale che orchestra la gara
    
    // Costanti per le regole della corsa
    private static final int TRAGUARDO = 99;           // Valore che decreta la fine della corsa per il singolo atleta
    private static final int PASSAGGIO_TESTIMONE = 90; // Valore a cui l'atleta innesca la partenza del compagno successivo

    /**
     * Costruttore: inizializza le caratteristiche fondamentali dell'atleta al momento della creazione.
     * @param indice numero dell'atleta (da 0 a 3, rappresenta l'ordine nella staffetta)
     * @param millisecondiPasso millisecondi di pausa tra un incremento e l'altro
     * @param bloccoPausa oggetto usato per la sincronizzazione (gestito internamente dal gestore)
     * @param gestore il gestore centrale a cui l'atleta fa riferimento per aggiornare la UI e verificare gli stati
     */
    public AtletaRelay(int indice, int millisecondiPasso, Object bloccoPausa, GestoreGara gestore) {
        this.indice = indice;
        this.millisecondiPasso = millisecondiPasso;
        this.conteggio = 0; // Si parte sempre dalla linea di partenza (0)
        this.gestore = gestore;
    }

    /**
     * Metodo core dell'interfaccia Runnable. Contiene il ciclo di vita dell'atleta.
     * Viene chiamato automaticamente quando si esegue il metodo start() sul Thread associato.
     */
    @Override
    public void run() {
        // Appena il thread parte, comunichiamo al gestore la nostra posizione iniziale (0) per aggiornare lo schermo
        gestore.aggiornaProgressoCorridore(indice, conteggio);
        
        // Inizia il ciclo di corsa: continua a correre solo se la gara NON è stata fermata
        // e se l'atleta NON ha ancora raggiunto il traguardo.
        while (!gestore.isFermato() && conteggio < TRAGUARDO) {
            
            // CONTROLLO PASSAGGIO TESTIMONE:
            // Se l'atleta è arrivato esattamente al punto di scambio (90) e non è l'ultimo atleta (indice < 3),
            // avvisa il gestore di far partire il Thread dell'atleta successivo.
            if (conteggio == PASSAGGIO_TESTIMONE && indice < GestoreGara.getNumeroCorridori() - 1) {
                gestore.avviaProssimoCorridore(indice);
            }

            // SIMULAZIONE DELLO SFORZO FISICO (Velocità):
            // L'atleta si "ferma" per i millisecondi definiti, simulando il tempo impiegato per fare un passo.
            try {
                Thread.sleep(millisecondiPasso);
            } catch (InterruptedException ex) {
                // Se il thread viene interrotto durante l'attesa, ripristiniamo lo stato di interruzione ed usciamo dal ciclo
                Thread.currentThread().interrupt();
                break;
            }

            // GESTIONE DELLA PAUSA:
            // Sincronizziamo questo blocco di codice sul "bloccoPausa" condiviso fornito dal Gestore.
            synchronized (gestore.getBloccoPausa()) {
                // Finché il gestore segnala che la gara è in pausa (e non è stata interrotta definitivamente),
                // l'atleta rimane in attesa (wait). Non consuma cicli CPU mentre aspetta.
                while (gestore.isInPausa() && !gestore.isFermato()) {
                    try {
                        gestore.getBloccoPausa().wait(); // Resta congelato qui finché non viene chiamato notifyAll() dal Gestore
                    } catch (InterruptedException ex) {
                        // Se interrotto mentre era in pausa, esce per terminare la corsa
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }

            // CONTROLLO DI STOP DEFINITIVO:
            // Uscito dall'attesa o dalla pausa, verifichiamo subito se nel frattempo l'utente ha premuto "Ferma Gara".
            // Se sì, interrompiamo immediatamente il ciclo while.
            if (gestore.isFermato()) {
                break;
            }

            // PASSO COMPIUTO:
            // L'atleta avanza di un'unità e segnala al gestore di aggiornare la barra di progresso e l'interfaccia.
            conteggio++;
            gestore.aggiornaProgressoCorridore(indice, conteggio);
        }

        // VERIFICA DI FINE CORSA:
        // Una volta uscito dal ciclo while, l'atleta controlla perché è uscito.
        // Se è uscito perché ha raggiunto regolarmente il traguardo (e non perché la gara è stata fermata),
        // notifica al gestore che la sua frazione di corsa è completata.
        if (!gestore.isFermato() && conteggio >= TRAGUARDO) {
            gestore.incrementaConteggioTerminati();
        }
    }

    /**
     * Metodo di servizio per ottenere l'attuale livello di avanzamento dell'atleta.
     */
    public int getConteggio() {
        return conteggio;
    }

    /**
     * Metodo di servizio per ottenere il numero (indice) dell'atleta.
     */
    public int getIndice() {
        return indice;
    }
}