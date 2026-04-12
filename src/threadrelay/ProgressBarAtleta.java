package threadrelay;

import java.awt.*;
import javax.swing.*;

/**
 * ProgressBarAtleta - Barra di progresso con emoji corridore
 * Mostra il progresso della gara con un'emoji che si muove lungo la barra
 */
public class ProgressBarAtleta extends JProgressBar {

    private static final String EMOJI_CORRIDORE = "🏃‍➡️";

    /**
     * Crea una barra di progresso (da 0 a 99)
     */
    public ProgressBarAtleta() {
        super(0, 99);
        setStringPainted(false);
        setOpaque(true);
    }

    /**
     * Disegna la barra con sfondo, bordo, percentuale ed emoji
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D grafica = (Graphics2D) g.create();
        grafica.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grafica.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Disegna lo sfondo grigio della barra
        grafica.setColor(new Color(230, 230, 230));
        grafica.fillRect(0, 0, w, h);

        // Disegna il bordo grigio scuro
        grafica.setColor(new Color(180, 180, 180));
        grafica.drawRect(0, 0, w - 1, h - 1);

        // Disegna la percentuale in basso a destra
        String percentText = getValue() + "%";
        grafica.setColor(new Color(80, 80, 80));
        grafica.setFont(new Font("Segoe UI", Font.BOLD, h / 4));
        FontMetrics fm = grafica.getFontMetrics();
        grafica.drawString(percentText, w - fm.stringWidth(percentText) - 8, h - 6);

        // Disegna l'emoji corridore
        int emojiSize = (int) (h * 0.5);
        grafica.setFont(new Font("Segoe UI Emoji", Font.PLAIN, emojiSize));
        fm = grafica.getFontMetrics();

        // Calcola la posizione X dell'emoji (segue il progresso)
        int emojiW = fm.stringWidth(EMOJI_CORRIDORE);
        int xMin = 0;
        int xMax = Math.max(0, w - emojiW);
        int xPos = xMin + (int) ((xMax - xMin) * (getValue() / 99.0));

        // Centra l'emoji verticalmente
        int yPos = (h - fm.getHeight()) / 2 + fm.getAscent();

        // Disegna l'emoji nella posizione calcolata
        grafica.drawString(EMOJI_CORRIDORE, xPos, yPos);

        grafica.dispose();
    }
}
