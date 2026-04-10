/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package threadrelay;

/**
 * ThreadRelay - Classe di ingresso principale dell'applicazione
 * 
 * Semplicemente crea e lancia la finestra principale della gara di staffetta.
 * Il vero lavoro è fatto dalla classe FRMPista.
 */
public class ThreadRelay {

    /**
     * Metodo main: punto di inizio dell'applicazione
     * 
     * @param args parametri della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        // Lancia la finestra principale nel thread di Swing (thread-safe per operazioni GUI)
        java.awt.EventQueue.invokeLater(() -> new FRMPista().setVisible(true));
    }
    
}
