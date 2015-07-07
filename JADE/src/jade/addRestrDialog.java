/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

import com.sun.glass.events.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gunars
 */
public class addRestrDialog extends javax.swing.JDialog {
    public List<DBOperations.typeList> typeList = new ArrayList<>();
    /**
     * Creates new form addRestrDialog
     */
    public addRestrDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        updateCmbBox();
    }
    
    private void updateCmbBox()
    {
        typeList = DBOperations.getTypeList();
        
        for (DBOperations.typeList type : typeList) {
            restrTypeCmbBox.addItem(type.typeName);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hhTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        saveRestrBtn = new javax.swing.JButton();
        cancelRestrBtn = new javax.swing.JButton();
        restrTypeCmbBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        mmTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ssTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        hhTxt.setText("00");
        hhTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hhTxtActionPerformed(evt);
            }
        });
        hhTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hhTxtKeyTyped(evt);
            }
        });

        jLabel1.setText("Tips:");

        saveRestrBtn.setText("Saglabāt");
        saveRestrBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveRestrBtnActionPerformed(evt);
            }
        });

        cancelRestrBtn.setText("Atcelt");
        cancelRestrBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelRestrBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Laiks(hh:mm:ss):");

        jLabel3.setText("Pievienot jaunu ierobežojumu:");

        jLabel4.setText(":");

        mmTxt.setText("00");
        mmTxt.setPreferredSize(new java.awt.Dimension(20, 20));
        mmTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmTxtActionPerformed(evt);
            }
        });
        mmTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mmTxtKeyTyped(evt);
            }
        });

        jLabel5.setText(":");

        ssTxt.setText("00");
        ssTxt.setPreferredSize(new java.awt.Dimension(20, 20));
        ssTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ssTxtActionPerformed(evt);
            }
        });
        ssTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ssTxtKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(restrTypeCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hhTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(saveRestrBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelRestrBtn)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mmTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ssTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(restrTypeCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hhTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(ssTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mmTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveRestrBtn)
                    .addComponent(cancelRestrBtn))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelRestrBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelRestrBtnActionPerformed
        dispose();
    }//GEN-LAST:event_cancelRestrBtnActionPerformed

    private void saveRestrBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveRestrBtnActionPerformed
        for (DBOperations.typeList type : typeList) {
            if (type.typeName.equals(restrTypeCmbBox.getSelectedItem()))
            
            DBOperations.addRestriction(type.ID, DBOperations.getSeconds(Integer.parseInt(hhTxt.getText()), Integer.parseInt(mmTxt.getText()), Integer.parseInt(ssTxt.getText())));
            mainForm.UpdateRestrTable();
            dispose();
        }
    }//GEN-LAST:event_saveRestrBtnActionPerformed

    private void ssTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ssTxtKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_ssTxtKeyTyped

    private void ssTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ssTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ssTxtActionPerformed

    private void hhTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hhTxtKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_hhTxtKeyTyped

    private void hhTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hhTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hhTxtActionPerformed

    private void mmTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mmTxtKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c)) || (c==KeyEvent.VK_BACKSPACE) || (c==KeyEvent.VK_DELETE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_mmTxtKeyTyped

    private void mmTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mmTxtActionPerformed

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
            java.util.logging.Logger.getLogger(addRestrDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addRestrDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addRestrDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addRestrDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addRestrDialog dialog = new addRestrDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelRestrBtn;
    private javax.swing.JTextField hhTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField mmTxt;
    private javax.swing.JComboBox restrTypeCmbBox;
    private javax.swing.JButton saveRestrBtn;
    private javax.swing.JTextField ssTxt;
    // End of variables declaration//GEN-END:variables
}
