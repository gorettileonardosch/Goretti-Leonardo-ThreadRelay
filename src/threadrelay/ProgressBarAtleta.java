package threadrelay;

import java.awt.*;
import javax.swing.*;

/**
 * ProgressBarAtleta - Componente personalizzato che estende la JProgressBar standard di Swing.
 * Aggiunge un tocco grafico sovrascrivendo la logica di disegno: dipinge a mano uno sfondo 
 * e posiziona dinamicamente un'emoji animata (il corridore) sopra la barra in base alla percentuale raggiunta.
 */
public class ProgressBarAtleta extends JProgressBar {

    // Costante che contiene l'emoji da disegnare
    private static final String EMOJI_CORRIDORE = "🏃‍➡️";

    /**
     * Costruttore: configura la barra impostando i limiti standard della corsa (0 e 99).
     */
    public ProgressBarAtleta() {
        super(0, 99);
        setStringPainted(false); // Disattiva il testo predefinito generato dalla JProgressBar per farlo da soli custom
        setOpaque(true);
    }

    /**
     * Metodo Override fondamentale in Swing: viene invocato dal sistema operativo ogni volta che la componente
     * ha bisogno di essere ridisegnata (ad esempio ad ogni variazione del value).
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Creiamo una copia "potenziata" 2D del contesto grafico per applicare impostazioni di alta qualità
        Graphics2D grafica = (Graphics2D) g.create();
        
        // Abilitiamo l'Antialiasing per ammorbidire i bordi e rendere sia il disegno che il testo "non sgranati"
        grafica.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grafica.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Catturiamo larghezza e altezza correnti della barra
        int larghezza = getWidth();
        int altezza = getHeight();

        // 1. DISEGNO DELLO SFONDO
        grafica.setColor(new Color(230, 230, 230)); // Colore Grigio Chiaro
        grafica.fillRect(0, 0, larghezza, altezza);   // Riempiamo l'intero rettangolo

        // 2. DISEGNO DEL BORDO ESTERNO
        grafica.setColor(new Color(180, 180, 180)); // Colore Grigio più scuro per delineare i contorni
        grafica.drawRect(0, 0, larghezza - 1, altezza - 1); // Ricalchiamo i bordi. -1 impedisce di sforare fuori dal pannello

        // 3. DISEGNO DEL TESTO (La percentuale fissa in basso a destra)
        String testoPercentuale = getValue() + "%";
        grafica.setColor(new Color(80, 80, 80)); // Grigio scuro per il testo
        // Proporziona dinamicamente la dimensione del font basandosi sull'altezza totale
        grafica.setFont(new Font("Segoe UI", Font.BOLD, altezza / 4));
        
        // FontMetrics ci permette di misurare quanti pixel ingombra a schermo una stringa per posizionarla correttamente
        FontMetrics metricaCarattere = grafica.getFontMetrics();
        // Calcola coordinate (X,Y) posizionando la stringa tutta a destra (larghezza - larghezzaTesto - padding)
        grafica.drawString(testoPercentuale, larghezza - metricaCarattere.stringWidth(testoPercentuale) - 8, altezza - 6);

        // 4. DISEGNO DELL'EMOJI ANIMATA CHE CORRE
        // Diamo all'emoji una dimensione pari a metà altezza della barra intera
        int dimensioneEmoji = (int) (altezza * 0.5);
        grafica.setFont(new Font("Segoe UI Emoji", Font.PLAIN, dimensioneEmoji));
        metricaCarattere = grafica.getFontMetrics();

        // LOGICA DI MOVIMENTO:
        // Misuriamo l'ingombro dell'emoji per non farla uscire dai bordi destri
        int larghezzaEmoji = metricaCarattere.stringWidth(EMOJI_CORRIDORE);
        int xMinimo = 0; // Punto di inizio a sinistra
        int xMassimo = Math.max(0, larghezza - larghezzaEmoji); // Punto di fine massimo a destra
        
        // Interpolazione lineare: calcola l'attuale X basandosi sul progress score attuale diviso per il massimo (99)
        int posizioneX = xMinimo + (int) ((xMassimo - xMinimo) * (getValue() / 99.0));

        // LOGICA DI CENTRAMENTO VERTICALE:
        // Usa parametri tipografici (Altezza totale - altezzaFont) / 2 + Ascent(punto di partenza linea di testo) per il posizionamento Y perfetto
        int posizioneY = (altezza - metricaCarattere.getHeight()) / 2 + metricaCarattere.getAscent();

        // Stampa fisicamente l'emoji alle coordinate X e Y appena calcolate
        grafica.drawString(EMOJI_CORRIDORE, posizioneX, posizioneY);

        // Molto importante: rilasciare sempre il Graphics2D alla fine per evitare gravi memory leak di interfaccia
        grafica.dispose();
    }
}