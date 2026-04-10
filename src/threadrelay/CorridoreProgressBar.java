package threadrelay;

import javax.swing.*;
import java.awt.*;

public class CorridoreProgressBar extends JProgressBar {

    private static final String EMOJI = "🏃‍➡️";

    public CorridoreProgressBar() {
        super(0, FRMPista.TRAGUARDO);
        setStringPainted(false);
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D grafica = (Graphics2D) g.create();
        grafica.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grafica.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Sfondo corsia
        grafica.setColor(new Color(230, 230, 230));
        grafica.fillRect(0, 0, w, h);

        // Bordo
        grafica.setColor(new Color(180, 180, 180));
        grafica.drawRect(0, 0, w - 1, h - 1);

        // Percentuale testo (angolo in basso a destra)
        String percentText = getValue() + "%";
        grafica.setColor(new Color(80, 80, 80));
        grafica.setFont(new Font("Segoe UI", Font.BOLD, h / 4));
        FontMetrics fm = grafica.getFontMetrics();
        grafica.drawString(percentText, w - fm.stringWidth(percentText) - 8, h - 6);

        // Emoji corridore
        int emojiSize = (int) (h * 0.5);
        grafica.setFont(new Font("Segoe UI Emoji", Font.PLAIN, emojiSize));
        fm = grafica.getFontMetrics();

        int emojiW = fm.stringWidth(EMOJI);
        int xMin = 0;
        int xMax = Math.max(0, w - emojiW);
        int xPos = xMin + (int) ((xMax - xMin) * (getValue() / (double) FRMPista.TRAGUARDO));

        int yPos = (h - fm.getHeight()) / 2 + fm.getAscent();

        grafica.drawString(EMOJI, xPos, yPos);
        grafica.dispose();
    }
}
