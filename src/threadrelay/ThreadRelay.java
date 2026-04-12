package threadrelay;

/**
 * ThreadRelay - Punto di partenza dell'applicazione
 * Crea e mostra la finestra della gara di staffetta
 */
public class ThreadRelay {

    /**
     * Avvia l'applicazione
     */
    public static void main(String[] args) {
        // Crea e mostra la finestra nel thread di Swing (per operazioni GUI sicure)
        java.awt.EventQueue.invokeLater(() -> new FRMPista().setVisible(true));
    }
}
