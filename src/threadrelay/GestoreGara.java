package threadrelay;

import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.SwingUtilities;

/**
 * GestoreGara - Il "Direttore di Gara". È la classe ponte tra la logica multithread 
 * (gli atleti che corrono) e l'interfaccia grafica (la finestra che mostra i risultati).
 * Mantiene lo stato globale della gara ed espone metodi sicuri per gestire pausa, ripresa e fine.
 */
public class GestoreGara {
    
    // Costanti generali della gara
    private static final int CORRIDORI = 4;           // Quanti membri ha una squadra di staffetta
    private static final int TRAGUARDO = 99;          // Valore target da raggiungere per completare la propria frazione
    
    // Variabili per gestire lo stato di esecuzione e la sincronizzazione
    private final Object bloccoPausa = new Object();  // Oggetto fittizio usato come "semaforo" per la sincronizzazione dei Thread
    private volatile boolean inPausa = false;         // 'volatile' assicura che il cambio di valore sia immediatamente visibile a tutti i thread
    private volatile boolean fermato = false;         // Segnala ai thread che la gara è stata annullata e devono morire
    
    // Vettori (Array) per tenere traccia degli oggetti Atleta e dei rispettivi Thread
    private final AtletaRelay[] corridori = new AtletaRelay[CORRIDORI];
    private final Thread[] threadCorridori = new Thread[CORRIDORI];
    
    // Variabile atomica per contare quanti hanno finito la corsa. 
    // AtomicInteger previene conflitti se più thread provano ad aggiornarla contemporaneamente.
    private final AtomicInteger conteggioTerminati = new AtomicInteger(0);  
    
    private final FRMPista finestra;  // Riferimento alla UI per delegare l'aggiornamento visivo
    
    /**
     * Costruttore: riceve l'istanza della finestra per poterle inviare aggiornamenti.
     * @param finestra l'interfaccia principale dell'app
     */
    public GestoreGara(FRMPista finestra) {
        this.finestra = finestra;
    }
    
    /**
     * Riporta la logica e l'interfaccia alle condizioni di partenza.
     * Pulisce gli stati di interruzione (pausa/stop) e azzera chi ha terminato.
     */
    public void reimpostaGara() {
        inPausa = false;
        fermato = false;
        conteggioTerminati.set(0); // Resetta il contatore atomico a zero
        
        // L'aggiornamento visivo deve avvenire TASSATIVAMENTE sul thread speciale della UI (EDT),
        // per questo usiamo SwingUtilities.invokeLater.
        SwingUtilities.invokeLater(() -> {
            finestra.reimpostaProgressoCorridori();
        });
    }
    
    /**
     * Funzione innescata dal bottone "Avvia Gara". Prepara i 4 atleti, assegna loro un Thread
     * ed innesca la partenza del primo frazionista.
     * @param millisecondiVelocita il ritardo da applicare ad ogni passo degli atleti
     */
    public void avviaGara(int millisecondiVelocita) {
        reimpostaGara(); // Assicuriamoci che tutto sia pulito
        fermato = false;
        
        // Ciclo for che "assume" e posiziona ai blocchi i 4 atleti
        for (int i = 0; i < CORRIDORI; i++) {
            // Crea l'oggetto che contiene la logica usando la classe originale AtletaRelay
            corridori[i] = new AtletaRelay(i, millisecondiVelocita, bloccoPausa, this);
            // Inserisce l'oggetto logico all'interno di un Thread, nominandolo per facilità di debug
            threadCorridori[i] = new Thread(corridori[i], "Atleta-" + (i + 1));
        }
        
        // Essendo una staffetta, NON facciamo partire tutti insieme.
        // Diamo lo start esclusivamente al PRIMO thread (il corridore 0).
        if (threadCorridori[0] != null) {
            threadCorridori[0].start();
        }
    }
    
    /**
     * Mette in pausa la competizione alzando la flag booleana.
     * Al loro prossimo ciclo (o uscita da uno sleep), i thread vedranno questa flag 
     * e chiameranno wait() sul bloccoPausa.
     */
    public void mettiInPausaGara() {
        if (!inPausa && !fermato) {
            inPausa = true;
        }
    }
    
    /**
     * Riattiva i thread dormienti ripristinando la competizione.
     */
    public void riprendiGara() {
        if (inPausa && !fermato) {
            // Usiamo il blocco sincronizzato sullo stesso oggetto usato dai thread per la wait()
            synchronized (bloccoPausa) {
                inPausa = false;           // Abbassiamo la flag
                bloccoPausa.notifyAll();   // Emettiamo il segnale di sveglia universale a tutti i thread in attesa
            }
        }
    }
    
    /**
     * Interrompe la gara di netto in modo irrecuperabile.
     */
    public void fermaGara() {
        fermato = true;   // Indirizza tutti i thread attivi verso l'uscita dai loro cicli while
        inPausa = false;  // Disattiva un'eventuale pausa residua
        
        // Sveglia forzatamente eventuali thread attualmente "congelati" in pausa affinché 
        // possano vedere che 'fermato' è diventato true ed auto-distruggersi regolarmente.
        synchronized (bloccoPausa) {
            bloccoPausa.notifyAll();  
        }
    }
    
    /**
     * Gestisce il delicato momento del "Passaggio del Testimone".
     * Viene invocato dal thread dell'atleta in corsa quando il suo contatore raggiunge quota 90.
     * @param indiceAttuale il numero dell'atleta che sta consegnando il testimone
     */
    public void avviaProssimoCorridore(int indiceAttuale) {
        int indiceProssimo = indiceAttuale + 1;
        // Controlla che esista effettivamente un prossimo atleta e che la gara non sia stata annullata
        if (indiceProssimo < CORRIDORI && !fermato) {
            Thread prossimoThread = threadCorridori[indiceProssimo];
            // Controllo di sicurezza: verifichiamo che il nuovo thread non sia già stato erroneamente avviato
            if (prossimoThread != null && !prossimoThread.isAlive()) {
                prossimoThread.start();  // Da lo sparo di partenza al compagno
            }
        }
    }
    
    /**
     * Riceve un aggiornamento da un thread Atleta e inoltra la richiesta di update grafico alla UI.
     * @param indice numero del corridore che sta fornendo un aggiornamento
     * @param valore la nuova posizione raggiunta (0-99)
     */
    public void aggiornaProgressoCorridore(int indice, int valore) {
        // Obbligatorio usare invokeLater perché Swing non è thread-safe: l'interfaccia 
        // deve essere modificata solo ed esclusivamente dal suo thread dedicato (Event Dispatch Thread).
        SwingUtilities.invokeLater(() -> {
            finestra.aggiornaSchermoCorridore(indice, valore);
        });
    }
    
    /**
     * Traccia la fine della corsa del singolo atleta. Quando il contatore atomico segnala 
     * che tutti e 4 sono giunti al traguardo, comunica all'interfaccia di sbloccare i comandi iniziali.
     */
    public void incrementaConteggioTerminati() {
        // incrementAndGet somma 1 alla variabile atomica e restituisce subito il nuovo valore.
        if (conteggioTerminati.incrementAndGet() == CORRIDORI) {
            SwingUtilities.invokeLater(finestra::abilitaControlliDopoFine);
        }
    }
    
    // --- Metodi Getter Accessori per fornire dati all'esterno in sola lettura ---
    
    public Object getBloccoPausa() {
        return bloccoPausa;
    }
    
    public boolean isInPausa() {
        return inPausa;
    }
    
    public boolean isFermato() {
        return fermato;
    }
    
    public static int getNumeroCorridori() {
        return CORRIDORI;
    }
    
    public static int getValoreTraguardo() {
        return TRAGUARDO;
    }
}