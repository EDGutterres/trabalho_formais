/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dto.FiniteAutomataDTO;
import service.FiniteAutomataService;
import service.RegularExpressionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author allu
 */
public class mainGUI extends javax.swing.JFrame {

    /**
     * Creates new form JFrame
     */
    public mainGUI() {
        finiteAutomataList = new ArrayList<>();
        symbolTable = new HashMap<>();
        regularExpressionService = new RegularExpressionService();
        finiteAutomataService = new FiniteAutomataService();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtInputAL = new javax.swing.JTextArea();
        btnAtualizarAL = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblDescricaoAL = new javax.swing.JLabel();
        lblDescricaoTS = new javax.swing.JLabel();
        lblDescricaoPseudo = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtPseudo = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAnalisarPseudo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1024, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1024, 600));

        lblTitulo.setFont(new java.awt.Font("Liberation Sans", 1, 36)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Linguagens Formais e Compiladores");

        lblSubtitulo.setFont(new java.awt.Font("Liberation Sans", 0, 20)); // NOI18N
        lblSubtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtitulo.setText("Geradores de Analisadores Léxicos e Sintáticos");

        txtInputAL.setColumns(20);
        txtInputAL.setRows(5);
        txtInputAL.setText("function_definition: def");
        jScrollPane1.setViewportView(txtInputAL);

        btnAtualizarAL.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnAtualizarAL.setText("Atualizar A.L.");
        btnAtualizarAL.setActionCommand("");
        btnAtualizarAL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarALActionPerformed(evt);
            }
        });

        lblDescricaoAL.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        lblDescricaoAL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescricaoAL.setText("Analisador Léxico");

        lblDescricaoTS.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        lblDescricaoTS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescricaoTS.setText("TS");

        lblDescricaoPseudo.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        lblDescricaoPseudo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescricaoPseudo.setText("Pseudocódigo");

        txtPseudo.setColumns(20);
        txtPseudo.setRows(5);
        txtPseudo.setText("def verify_if_1(self, x)\n    if (x == 1)\n        return true\n    else\n        return false");
        jScrollPane3.setViewportView(txtPseudo);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Token", "Lexema"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable1);

        btnAnalisarPseudo.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        btnAnalisarPseudo.setText("Analisar Pseudocódigo");
        btnAnalisarPseudo.setActionCommand("");
        btnAnalisarPseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisarPseudoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblSubtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(btnAtualizarAL, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(221, 221, 221)
                                .addComponent(btnAnalisarPseudo))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDescricaoAL, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(70, 70, 70)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDescricaoPseudo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(lblDescricaoTS, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                        .addGap(53, 53, 53)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(lblSubtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricaoAL, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescricaoTS, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescricaoPseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtualizarAL)
                    .addComponent(btnAnalisarPseudo))
                .addGap(49, 49, 49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtualizarALActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarALActionPerformed
        System.out.println("Atualizando AL");
        FiniteAutomataDTO newAutomata;
        boolean modified = false;
        for (String line : txtInputAL.getText().split("\\n")) {
            String[] splitAux = line.split(":");
            String lexeme = splitAux[0].trim();
            String token = splitAux[1].trim();
            if (!symbolTable.containsKey(lexeme) && !symbolTable.containsValue(token)) {
                symbolTable.put(lexeme, token);
                newAutomata = regularExpressionService.getDFA(token);
                finiteAutomataList.add(newAutomata);
                modified = true;
            }
        }
        if (modified) {
            finalAutomata = new FiniteAutomataDTO();
            for (FiniteAutomataDTO finiteAutomata : finiteAutomataList) {
                finalAutomata = finiteAutomataService.union(finiteAutomata, finalAutomata);
            }
            finalAutomata = finiteAutomataService.determinize(finalAutomata);
        }
    }//GEN-LAST:event_btnAtualizarALActionPerformed

    private void btnAnalisarPseudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalisarPseudoActionPerformed
        System.out.println("Analisar Pseudocódigo");
    }//GEN-LAST:event_btnAnalisarPseudoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainGUI().setVisible(true);
            }
        });
    }

    private List<FiniteAutomataDTO> finiteAutomataList;
    private FiniteAutomataDTO finalAutomata;
    private Map<String, String> symbolTable;
    private RegularExpressionService regularExpressionService;
    private FiniteAutomataService finiteAutomataService;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalisarPseudo;
    private javax.swing.JButton btnAtualizarAL;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDescricaoAL;
    private javax.swing.JLabel lblDescricaoPseudo;
    private javax.swing.JLabel lblDescricaoTS;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea txtInputAL;
    private javax.swing.JTextArea txtPseudo;
    // End of variables declaration//GEN-END:variables
}
