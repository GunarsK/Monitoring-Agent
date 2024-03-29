/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jade;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.table.*;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 *
 * @author Gunars
 */
public class mainForm extends javax.swing.JFrame {

    /**
     * Creates new form mainForm
     */
    public mainForm() {
        
        initComponents();
        
        DefaultTableModel tm = (DefaultTableModel) restrTable.getModel(); 
        UpdateProcessTable();
        UpdateTypeTable();
        UpdateRestrTable();
        UpdateEmailSetting();
    }
    
    
    
    public static void UpdateProcessTable()
    {
        List<DBOperations.processList> proctypeList = DBOperations.getProcessList();
        try
        {
        int TotalTime = DBOperations.getTotalTime();
        totalTimeLabel.setText(DBOperations.getTimeFormat(DBOperations.getTotalTimeToday()));
        Object[] columnNames = {"Process", "Laiks šodien", "Tips", "Sadalījums(visu laiku)"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames)
        {
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
        }
        };
        
        for (DBOperations.processList proclist : proctypeList) {
            Object[] o = new Object[4];
            o[0] = proclist.processName;
            o[1] = DBOperations.getTimeFormat(proclist.timeCount_day);
            o[2] = proclist.processType;
            double rounded = Math.round((((double)proclist.timeCount_all/TotalTime)*100) * 1000) / 1000;
            o[3] = rounded + "%";
            model.addRow(o);
        }
        processAllTable.setModel(model);
        }
       catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }  
    }
    
     public static void UpdateTypeTable()
    {
        List<DBOperations.typeList> typeList = DBOperations.getTypeList();
        try
        {
        int TotalTime = DBOperations.getTotalTime();
        //Object[] columnNames = {"Type name", "Time(sec/day)", "Time(sec/all)"};
        //DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
        DefaultTableModel model = (DefaultTableModel) typeAllTable.getModel(); 
        model.setRowCount(0);    
            
        for (DBOperations.typeList type : typeList) {
            Object[] o = new Object[4];
            
            o[0] = type.typeName;
            o[1] = DBOperations.getTimeFormat(type.typeTimeCount_day);
            double rounded = Math.round((((double)type.typeTimeCount_all/TotalTime)*100) * 1000) / 1000;
            o[2] = rounded + "%";
            o[3] = false;
            model.addRow(o);
            model.fireTableDataChanged();
        }
        typeAllTable.setModel(model);
        }
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }  
    }
     
     
    public static void UpdateRestrTable()
    {
        List<DBOperations.restrictionsList> restrList = DBOperations.getRestrictions();
        try
        {
        //Object[] columnNames = {"Nr", "Type name", "Limit(sec)", "Used(sec)", "Boolean"};
        //DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);
            
        DefaultTableModel model = (DefaultTableModel) restrTable.getModel(); 
        model.setRowCount(0);
        int i = 0;
        for (DBOperations.restrictionsList type : restrList) {
            Object[] o = new Object[5];
            
            o[0] = type.ID;
            o[1] = type.type_name;
            o[2] = DBOperations.getTimeFormat(type.limit);
            o[3] = DBOperations.getTimeFormat(type.used);
            o[4] = false;
            model.addRow(o);
        }
        restrTable.setModel(model);
        }
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }  
    }
    
     public static void UpdateEmailSetting()
    {
        try
        {
            String receiver = "";
            receiver = DBOperations.getReceiverInfo();
            
            recieverTxt.setText(receiver);
        }
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
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

        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        processAllTable = new javax.swing.JTable();
        changeTypeBtn = new javax.swing.JButton();
        addWebBtn = new javax.swing.JButton();
        delWebBtn = new javax.swing.JButton();
        delProcListBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        typeAllTable = new javax.swing.JTable();
        delTypeBtn = new javax.swing.JButton();
        addTypeBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        restrTable = new javax.swing.JTable();
        addRestrBtn = new javax.swing.JButton();
        delRestrBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        saveInfoBtn = new javax.swing.JButton();
        recieverTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        deleteInfoBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        totalTimeLabel = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        processAllTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Process", "Laiks šodien", "Tips", "Sadalījums(visu laiku)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(processAllTable);

        changeTypeBtn.setText("Mainīt tipu");
        changeTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTypeBtnActionPerformed(evt);
            }
        });

        addWebBtn.setText("Pievienot web lapu");
        addWebBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWebBtnActionPerformed(evt);
            }
        });

        delWebBtn.setText("Dzēst web lapu");
        delWebBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delWebBtnActionPerformed(evt);
            }
        });

        delProcListBtn.setText("Notīrīt procesu sarakstu");
        delProcListBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delProcListBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(changeTypeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(delProcListBtn)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(delWebBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addWebBtn))
                        .addGap(108, 108, 108))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(changeTypeBtn)
                        .addComponent(delProcListBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(addWebBtn)
                        .addGap(11, 11, 11)
                        .addComponent(delWebBtn)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Procesi", jPanel1);

        typeAllTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nosaukums", "Laiks šodien", "Sadalījums(visu laiku)", "Dzēst"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        typeAllTable.setPreferredSize(null);
        jScrollPane3.setViewportView(typeAllTable);

        delTypeBtn.setText("Dzēst");
        delTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delTypeBtnActionPerformed(evt);
            }
        });

        addTypeBtn.setText("Pievienot");
        addTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTypeBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addTypeBtn)
                        .addGap(30, 30, 30)
                        .addComponent(delTypeBtn)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addTypeBtn)
                    .addComponent(delTypeBtn))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tipi", jPanel2);

        restrTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nosaukums", "Limits", "Šodien izmantots", "Dzēst"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(restrTable);

        addRestrBtn.setText("Pievienot");
        addRestrBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRestrBtnActionPerformed(evt);
            }
        });

        delRestrBtn.setText("Dzēst");
        delRestrBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delRestrBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(addRestrBtn)
                        .addGap(30, 30, 30)
                        .addComponent(delRestrBtn))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addRestrBtn)
                    .addComponent(delRestrBtn))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ierobežojumi", jPanel3);

        saveInfoBtn.setText("Saglabāt");
        saveInfoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveInfoBtnActionPerformed(evt);
            }
        });

        recieverTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recieverTxtActionPerformed(evt);
            }
        });

        jLabel5.setText("Saņēmēja e-pasts:");

        jLabel6.setText("<html>Šeit tu vari pievienot e-pastu, uz kuru reizi dienā<br> tiks sūtītas atskaites ar taviem datora izmantošanas parametriem</html>");

        deleteInfoBtn.setText("Dzēst");
        deleteInfoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteInfoBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(recieverTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(194, 194, 194)
                        .addComponent(saveInfoBtn)
                        .addGap(18, 18, 18)
                        .addComponent(deleteInfoBtn)))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recieverTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveInfoBtn)
                    .addComponent(deleteInfoBtn))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pievienot e-pastu", jPanel4);

        jButton2.setText("Apturēt aģentu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Laiks šodien kopā: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalTimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(totalTimeLabel)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void delTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delTypeBtnActionPerformed
        int row = typeAllTable.getRowCount();
        for (int j = 0; j  < row; j++) 
        {
            Boolean bool = (Boolean) typeAllTable.getValueAt(j, 3);
            if (bool == true)
            {
                DBOperations.deleteType((String) typeAllTable.getValueAt(j, 0));                
            }
        }
        UpdateTypeTable();
    }//GEN-LAST:event_delTypeBtnActionPerformed

    private void addTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTypeBtnActionPerformed
        JDialog dialog = new addTypeDialog(null, false);
        dialog.setVisible(true);
        //addTypeDialog.main(null); 
    }//GEN-LAST:event_addTypeBtnActionPerformed

    private void changeTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTypeBtnActionPerformed
        JDialog dialog = new changeProcTypeDialog(null, false);
        dialog.setVisible(true);
    }//GEN-LAST:event_changeTypeBtnActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
       UpdateProcessTable();
       UpdateTypeTable();
       UpdateRestrTable();
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void addRestrBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRestrBtnActionPerformed
        JDialog dialog = new addRestrDialog(null, false);
        dialog.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_addRestrBtnActionPerformed

    private void delRestrBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delRestrBtnActionPerformed
        int row = restrTable.getRowCount();
        for (int j = 0; j  < row; j++) 
        {
            Boolean bool = (Boolean) restrTable.getValueAt(j, 4);
            if (bool == true)
            {
                DBOperations.deleteRestriction((int) restrTable.getValueAt(j, 0));
            }
        }
        UpdateRestrTable();
    }//GEN-LAST:event_delRestrBtnActionPerformed

    private void saveInfoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInfoBtnActionPerformed
        DBOperations.addUserinfo(recieverTxt.getText());
    }//GEN-LAST:event_saveInfoBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      System.exit(0);  // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void recieverTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recieverTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recieverTxtActionPerformed

    private void addWebBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWebBtnActionPerformed
         JDialog dialog = new addPageDialog(null, false);
         dialog.setVisible(true);
    }//GEN-LAST:event_addWebBtnActionPerformed

    private void delWebBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delWebBtnActionPerformed
       JDialog dialog = new deleteWebPageDialog(null, false);
       dialog.setVisible(true);
    }//GEN-LAST:event_delWebBtnActionPerformed

    private void delProcListBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delProcListBtnActionPerformed
        DBOperations.deleteProcess();
        UpdateProcessTable();
    }//GEN-LAST:event_delProcListBtnActionPerformed

    private void deleteInfoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteInfoBtnActionPerformed
        DBOperations.deleteUserInfo();
        UpdateEmailSetting();
    }//GEN-LAST:event_deleteInfoBtnActionPerformed

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
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRestrBtn;
    private javax.swing.JButton addTypeBtn;
    private javax.swing.JButton addWebBtn;
    private javax.swing.JButton changeTypeBtn;
    private javax.swing.JButton delProcListBtn;
    private javax.swing.JButton delRestrBtn;
    private javax.swing.JButton delTypeBtn;
    private javax.swing.JButton delWebBtn;
    private javax.swing.JButton deleteInfoBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static javax.swing.JTable processAllTable;
    private static javax.swing.JTextField recieverTxt;
    private static javax.swing.JTable restrTable;
    private javax.swing.JButton saveInfoBtn;
    private static javax.swing.JLabel totalTimeLabel;
    private static javax.swing.JTable typeAllTable;
    // End of variables declaration//GEN-END:variables
}
