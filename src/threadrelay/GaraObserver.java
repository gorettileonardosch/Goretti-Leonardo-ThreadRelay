package threadrelay;

/**
 * GaraObserver - Interfaccia del pattern Observer.
 * Definisce i metodi che gli observer (come la UI) devono implementare
 * per ricevere notifiche sui cambiamenti di stato della gara.
 */
public interface GaraObserver {

    /**
     * Notifica che il progresso di un corridore è stato aggiornato.
     * @param indice il numero del corridore (0-3)
     * @param valore il nuovo valore di progresso (0-99)
     */
    void onProgressoCorridoreAggiornato(int indice, int valore);

    /**
     * Notifica che la gara è stata reimpostata.
     */
    void onGaraReimpostata();

    /**
     * Notifica che tutti i corridori hanno terminato.
     */
    void onGaraTerminata();
}
