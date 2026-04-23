package threadrelay;

/**
 * FRMPista - Finestra principale dell'applicazione (Interfaccia Grafica).
 * Implementa il pattern Observer per ricevere notifiche sui cambiamenti di stato della gara.
 * Si occupa esclusivamente di mostrare gli elementi visivi (barre, bottoni, immagini)
 * e di instradare gli eventi (click dell'utente) verso il GestoreGara.
 */
public class FRMPista extends javax.swing.JFrame implements GaraObserver {
    
    // Logger per registrare eventuali errori dell'interfaccia
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FRMPista.class.getName());
    
    // Riferimento al "cervello" dell'applicazione che contiene la logica della staffetta
    private GestoreGara gestore;  

    /**
     * Costruttore della finestra. Viene invocato all'avvio dell'applicazione.
     */
    public FRMPista() {
        initComponents();  // Disegna i componenti grafici (metodo autogenerato da NetBeans)
        this.gestore = new GestoreGara();  // Inizializza la logica senza parametri
        this.gestore.addObserver(this);    // Registra questa finestra come observer
        inizializzaControlliGara();  // Configura i comportamenti (Listener) dei bottoni
    }

    /**
     * Implementazione dell'interfaccia GaraObserver
     */

    @Override
    public void onProgressoCorridoreAggiornato(int indice, int valore) {
        aggiornaSchermoCorridore(indice, valore);
    }

    @Override
    public void onGaraReimpostata() {
        reimpostaProgressoCorridori();
    }

    @Override
    public void onGaraTerminata() {
        abilitaControlliDopoFine();
    }

    /**
     * Questo metodo viene chiamato dall'interno del costruttore per inizializzare il form.
     * ATTENZIONE: NON modificare questo codice. È rigenerato automaticamente dal Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PNLProgressBar = new javax.swing.JPanel();
        LBLAtleta1 = new javax.swing.JLabel();
        LBLAtleta2 = new javax.swing.JLabel();
        LBLAtleta3 = new javax.swing.JLabel();
        LBLAtleta4 = new javax.swing.JLabel();
        PRGSCorridore1 = new javax.swing.JProgressBar();
        PRGSCorridore2 = new javax.swing.JProgressBar();
        PRGSCorridore3 = new javax.swing.JProgressBar();
        PRGSCorridore4 = new javax.swing.JProgressBar();
        PNLBottoni = new javax.swing.JPanel();
        CMBSelezioneVelocita = new javax.swing.JComboBox<>();
        BTNAvvia = new javax.swing.JButton();
        BTNPausa = new javax.swing.JButton();
        BTNRiprendi = new javax.swing.JButton();
        BTNFerma = new javax.swing.JButton();
        PNLPosizioneCorridori = new javax.swing.JPanel();
        PNLStatCorridore1 = new javax.swing.JPanel();
        LBLCorridore1 = new javax.swing.JLabel();
        LBLAvanzamentoCorridore1 = new javax.swing.JLabel();
        PNLStatCorridore2 = new javax.swing.JPanel();
        LBLCorridore2 = new javax.swing.JLabel();
        LBLAvanzamentoCorridore2 = new javax.swing.JLabel();
        PNLStatCorridore3 = new javax.swing.JPanel();
        LBLCorridore3 = new javax.swing.JLabel();
        LBLAvanzamentoCorridore3 = new javax.swing.JLabel();
        PNLStatCorridore4 = new javax.swing.JPanel();
        LBLCorridore4 = new javax.swing.JLabel();
        LBLAvanzamentoCorridore4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Relay Runners");
        setResizable(false);

        PNLProgressBar.setBorder(javax.swing.BorderFactory.createTitledBorder("Corridori"));
        PNLProgressBar.setPreferredSize(new java.awt.Dimension(1000, 391));
        PNLProgressBar.setLayout(null);

        PRGSCorridore1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        PNLProgressBar.add(PRGSCorridore1);
        PRGSCorridore1.setBounds(5, 19, 990, 116);

        PNLProgressBar.add(PRGSCorridore2);
        PRGSCorridore2.setBounds(5, 185, 990, 116);

        PNLProgressBar.add(PRGSCorridore3);
        PRGSCorridore3.setBounds(5, 351, 990, 116);

        PNLProgressBar.add(PRGSCorridore4);
        PRGSCorridore4.setBounds(5, 517, 990, 116);

        LBLAtleta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Immagini/Corridore.gif")));
        PNLProgressBar.add(LBLAtleta1);
        LBLAtleta1.setBounds(10, 40, 70, 70);

        LBLAtleta2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Immagini/Corridore.gif")));
        PNLProgressBar.add(LBLAtleta2);
        LBLAtleta2.setBounds(10, 210, 70, 70);

        LBLAtleta3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Immagini/Corridore.gif")));
        PNLProgressBar.add(LBLAtleta3);
        LBLAtleta3.setBounds(10, 380, 70, 70);

        LBLAtleta4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Immagini/Corridore.gif")));
        PNLProgressBar.add(LBLAtleta4);
        LBLAtleta4.setBounds(10, 540, 70, 70);

        getContentPane().add(PNLProgressBar, java.awt.BorderLayout.LINE_START);

        PNLBottoni.setBorder(javax.swing.BorderFactory.createTitledBorder("Comandi"));
        PNLBottoni.setPreferredSize(new java.awt.Dimension(1454, 120));
        PNLBottoni.setLayout(new java.awt.GridLayout(1, 5));

        CMBSelezioneVelocita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lento", "Spedito", "Veloce" }));
        PNLBottoni.add(CMBSelezioneVelocita);

        BTNAvvia.setText("Avvia Gara");
        PNLBottoni.add(BTNAvvia);

        BTNPausa.setText("Pausa");
        PNLBottoni.add(BTNPausa);

        BTNRiprendi.setText("Riprendi");
        PNLBottoni.add(BTNRiprendi);

        BTNFerma.setText("Ferma Gara");
        PNLBottoni.add(BTNFerma);

        getContentPane().add(PNLBottoni, java.awt.BorderLayout.PAGE_END);

        PNLPosizioneCorridori.setBorder(javax.swing.BorderFactory.createTitledBorder("Posizione Corridori"));
        PNLPosizioneCorridori.setMinimumSize(new java.awt.Dimension(310, 573));
        PNLPosizioneCorridori.setPreferredSize(new java.awt.Dimension(310, 666));
        PNLPosizioneCorridori.setLayout(new java.awt.GridLayout(4, 2, 0, 50));

        PNLStatCorridore1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PNLStatCorridore1.setLayout(new java.awt.GridLayout(1, 2));

        LBLCorridore1.setText("Corridore 1");
        PNLStatCorridore1.add(LBLCorridore1);
        PNLStatCorridore1.add(LBLAvanzamentoCorridore1);

        PNLPosizioneCorridori.add(PNLStatCorridore1);

        PNLStatCorridore2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PNLStatCorridore2.setLayout(new java.awt.GridLayout(1, 2));

        LBLCorridore2.setText("Corridore 2");
        PNLStatCorridore2.add(LBLCorridore2);
        PNLStatCorridore2.add(LBLAvanzamentoCorridore2);

        PNLPosizioneCorridori.add(PNLStatCorridore2);

        PNLStatCorridore3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PNLStatCorridore3.setLayout(new java.awt.GridLayout(1, 2));

        LBLCorridore3.setText("Corridore 3");
        PNLStatCorridore3.add(LBLCorridore3);
        PNLStatCorridore3.add(LBLAvanzamentoCorridore3);

        PNLPosizioneCorridori.add(PNLStatCorridore3);

        PNLStatCorridore4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PNLStatCorridore4.setLayout(new java.awt.GridLayout(1, 2));

        LBLCorridore4.setText("Corridore 4");
        PNLStatCorridore4.add(LBLCorridore4);
        PNLStatCorridore4.add(LBLAvanzamentoCorridore4);

        PNLPosizioneCorridori.add(PNLStatCorridore4);

        getContentPane().add(PNLPosizioneCorridori, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Inizializza i comportamenti dei bottoni e i limiti delle barre di progresso.
     */
    private void inizializzaControlliGara() {
        // Recupera il valore finale (99) dal gestore
        int valoreMassimo = GestoreGara.getValoreTraguardo();
        
        // Imposta il range di tutte e 4 le barre di progresso visive (da 0 a 99)
        PRGSCorridore1.setMinimum(0);
        PRGSCorridore1.setMaximum(valoreMassimo);
        PRGSCorridore2.setMinimum(0);
        PRGSCorridore2.setMaximum(valoreMassimo);
        PRGSCorridore3.setMinimum(0);
        PRGSCorridore3.setMaximum(valoreMassimo);
        PRGSCorridore4.setMinimum(0);
        PRGSCorridore4.setMaximum(valoreMassimo);

        // Azione Bottone "Avvia Gara": legge la velocità dal menu a tendina, chiede al gestore di avviare 
        // e aggiorna lo stato di abilitazione dei bottoni per impedire doppi avvii.
        BTNAvvia.addActionListener(evt -> {
            gestore.avviaGara(millisecondiVelocitaGara());
            BTNAvvia.setEnabled(false);              // Blocca il tasto avvia (gara già in corso)
            CMBSelezioneVelocita.setEnabled(false);  // Blocca il cambio velocità a gara in corso
            BTNPausa.setEnabled(true);               // Rende possibile mettere in pausa
            BTNRiprendi.setEnabled(false);           // Non si può riprendere se non si è in pausa
            BTNFerma.setEnabled(true);               // Permette di fermare definitivamente la gara
        });
        
        // Azione Bottone "Pausa": chiede al gestore di bloccare i thread e inverte l'abilitazione tra Pausa e Riprendi
        BTNPausa.addActionListener(evt -> {
            gestore.mettiInPausaGara();
            BTNPausa.setEnabled(false);              
            BTNRiprendi.setEnabled(true);            
        });
        
        // Azione Bottone "Riprendi": dice al gestore di svegliare i thread dalla pausa
        BTNRiprendi.addActionListener(evt -> {
            gestore.riprendiGara();
            BTNRiprendi.setEnabled(false);           
            BTNPausa.setEnabled(true);               
        });
        
        // Azione Bottone "Ferma Gara": arresta i thread definitivamente e resetta i bottoni al loro stato originale
        BTNFerma.addActionListener(evt -> {
            gestore.fermaGara();
            abilitaControlliDopoFine();  // Richiama il metodo di reset dell'interfaccia
        });

        // Stato iniziale dei bottoni di controllo: disattivi finché non viene avviata la gara
        BTNPausa.setEnabled(false);
        BTNRiprendi.setEnabled(false);
        BTNFerma.setEnabled(false);
        
        // Al termine del caricamento della GUI, posiziona le icone degli atleti ai nastri di partenza (valore 0).
        // Si usa invokeLater per assicurarsi che i componenti grafici abbiano prima calcolato le proprie dimensioni.
        javax.swing.SwingUtilities.invokeLater(this::reimpostaProgressoCorridori);
    }

    /**
     * Aggiorna a schermo i dati visivi per un singolo atleta in tempo reale.
     * @param indice l'atleta da aggiornare (0-3)
     * @param valore il progresso numerico attuale
     */
    public void aggiornaSchermoCorridore(int indice, int valore) {
        int valoreMassimo = GestoreGara.getValoreTraguardo();
        
        // 1. Fa avanzare la barra verde
        getBarraProgresso(indice).setValue(valore);  
        
        // 2. Aggiorna l'etichetta testuale a destra con il numero. Se arriva a 99 scrive "Fine".
        getEtichettaStato(indice).setText(valore < valoreMassimo ? String.valueOf(valore) : "Fine");  
        
        // 3. Sposta l'immagine (il "Corridore.gif") lungo l'asse X sulla pista
        impostaPosizioneCorridore(indice, valore);  
    }

    /**
     * Rimette tutti gli atleti sulla linea di partenza e azzera il conteggio testuale e le barre.
     */
    public void reimpostaProgressoCorridori() {
        for (int i = 0; i < 4; i++) {
            getBarraProgresso(i).setValue(0);  
            getEtichettaStato(i).setText("");  
            impostaPosizioneCorridore(i, 0);  
        }
    }

    /**
     * Ripristina la disponibilità dei bottoni di interazione per consentire l'avvio di una nuova gara.
     */
    public void abilitaControlliDopoFine() {
        CMBSelezioneVelocita.setEnabled(true);  // Sblocca il menu velocità
        BTNAvvia.setEnabled(true);              // Permette una nuova partenza
        BTNPausa.setEnabled(false);
        BTNPausa.setText("Pausa");
        BTNRiprendi.setEnabled(false);
        BTNFerma.setEnabled(false);
    }

    /**
     * Converte la scelta del menu a tendina in millisecondi concreti che detteranno il ritmo del Thread.sleep.
     * Più il numero è basso, meno il thread dorme, e più l'atleta andrà veloce.
     * @return millisecondi di pausa tra un passo e l'altro
     */
    private int millisecondiVelocitaGara() {
        return switch (CMBSelezioneVelocita.getSelectedIndex()) {
            case 0 -> 120;  // Modalità Lento
            case 1 -> 60;   // Modalità Spedito
            case 2 -> 25;   // Modalità Veloce
            default -> 60;  // Fallback di sicurezza
        };
    }

    /**
     * Metodo matematico per calcolare i pixel in cui spostare l'immagine dell'atleta.
     * @param indice l'atleta (0-3)
     * @param valore il suo progresso attuale (0-99)
     */
    private void impostaPosizioneCorridore(int indice, int valore) {
        javax.swing.JLabel etichettaCorridore = getEtichettaCorridore(indice);
        
        // Definisce l'area in pixel entro cui l'immagine può muoversi (per rimanere all'interno della pista visiva)
        int inizioX = 10;   // Coordinate di partenza
        int fineX = 925;    // Limite destro massimo per l'immagine
        int intervallo = fineX - inizioX;
        int valoreMassimo = GestoreGara.getValoreTraguardo();
        
        // Calcola in proporzione quanti pixel avanzare: (intervallo totale * (percentuale di avanzamento))
        int x = inizioX + (int) Math.round(intervallo * (valore / (double) valoreMassimo));
        
        // Assegna la coordinata Y specifica (ogni corsia ha un'altezza diversa fissa sullo schermo)
        int y = switch (indice) {
            case 0 -> 40;    // Y per la corsia 1
            case 1 -> 210;   // Y per la corsia 2
            case 2 -> 380;   // Y per la corsia 3
            case 3 -> 540;   // Y per la corsia 4
            default -> 40;
        };
        
        // Applica le coordinate calcolate al componente visivo e lo forza in primo piano
        etichettaCorridore.setBounds(x, y, 70, 70);
        portaCorridoreInPrimoPiano(indice);
    }

    /**
     * Impedisce che la barra di progresso copra l'immagine del corridore. 
     * Imposta lo "Z-Order" (livello di profondità) a 0, rendendolo il componente più in alto.
     */
    private void portaCorridoreInPrimoPiano(int indice) {
        javax.swing.JLabel etichettaCorridore = getEtichettaCorridore(indice);
        if (PNLProgressBar.getComponentZOrder(etichettaCorridore) != 0) {
            PNLProgressBar.setComponentZOrder(etichettaCorridore, 0);
            PNLProgressBar.repaint(); // Ridisegna il pannello per forzare la modifica visiva
        }
    }

    /**
     * Funzione di utilità per associare l'indice numerico (0-3) al corretto oggetto grafico JProgressBar.
     */
    private javax.swing.JProgressBar getBarraProgresso(int indice) {
        return switch (indice) {
            case 0 -> PRGSCorridore1;
            case 1 -> PRGSCorridore2;
            case 2 -> PRGSCorridore3;
            default -> PRGSCorridore4;
        };
    }

    /**
     * Funzione di utilità per associare l'indice numerico (0-3) alla JLabel del testo di avanzamento a destra.
     */
    private javax.swing.JLabel getEtichettaStato(int indice) {
        return switch (indice) {
            case 0 -> LBLAvanzamentoCorridore1;
            case 1 -> LBLAvanzamentoCorridore2;
            case 2 -> LBLAvanzamentoCorridore3;
            default -> LBLAvanzamentoCorridore4;
        };
    }

    /**
     * Funzione di utilità per associare l'indice numerico (0-3) alla JLabel contenente l'immagine (GIF) del corridore.
     */
    private javax.swing.JLabel getEtichettaCorridore(int indice) {
        return switch (indice) {
            case 0 -> LBLAtleta1;
            case 1 -> LBLAtleta2;
            case 2 -> LBLAtleta3;
            default -> LBLAtleta4;
        };
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNAvvia;
    private javax.swing.JButton BTNFerma;
    private javax.swing.JButton BTNPausa;
    private javax.swing.JButton BTNRiprendi;
    private javax.swing.JComboBox<String> CMBSelezioneVelocita;
    private javax.swing.JLabel LBLAtleta1;
    private javax.swing.JLabel LBLAtleta2;
    private javax.swing.JLabel LBLAtleta3;
    private javax.swing.JLabel LBLAtleta4;
    private javax.swing.JLabel LBLAvanzamentoCorridore1;
    private javax.swing.JLabel LBLAvanzamentoCorridore2;
    private javax.swing.JLabel LBLAvanzamentoCorridore3;
    private javax.swing.JLabel LBLAvanzamentoCorridore4;
    private javax.swing.JLabel LBLCorridore1;
    private javax.swing.JLabel LBLCorridore2;
    private javax.swing.JLabel LBLCorridore3;
    private javax.swing.JLabel LBLCorridore4;
    private javax.swing.JPanel PNLBottoni;
    private javax.swing.JPanel PNLPosizioneCorridori;
    private javax.swing.JPanel PNLProgressBar;
    private javax.swing.JPanel PNLStatCorridore1;
    private javax.swing.JPanel PNLStatCorridore2;
    private javax.swing.JPanel PNLStatCorridore3;
    private javax.swing.JPanel PNLStatCorridore4;
    private javax.swing.JProgressBar PRGSCorridore1;
    private javax.swing.JProgressBar PRGSCorridore2;
    private javax.swing.JProgressBar PRGSCorridore3;
    private javax.swing.JProgressBar PRGSCorridore4;
    // End of variables declaration//GEN-END:variables
}