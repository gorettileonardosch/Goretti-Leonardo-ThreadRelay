package threadrelay;

/**
 * ThreadRelay - Il punto di ingresso principale (Entry Point) di tutta l'applicazione Java.
 * Il suo unico scopo è contenere il metodo Main e innescare la costruzione dell'interfaccia grafica.
 */
public class ThreadRelay {

    /**
     * Metodo di avvio universale chiamato dalla Java Virtual Machine.
     * @param args parametri facoltativi inseribili da riga di comando all'avvio.
     */
    public static void main(String[] args) {
        
        // Essendo un'applicazione basata su Java Swing, la creazione e manipolazione 
        // della GUI non deve mai avvenire nel thread "Main".
        // invokeLater invia una richiesta all'Event Dispatch Thread (EDT) di Swing: 
        // "Appena sei pronto, crea un nuovo oggetto finestra FRMPista e rendilo visibile".
        java.awt.EventQueue.invokeLater(() -> new FRMPista().setVisible(true));
    }
}