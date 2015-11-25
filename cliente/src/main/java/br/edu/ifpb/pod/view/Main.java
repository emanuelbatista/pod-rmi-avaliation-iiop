/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.view;

import br.edu.ifpb.pod.exception.LoginException;
import br.edu.ifpb.pod.to.AcessTO;
import br.edu.ifpb.pod.to.CredentialTO;
import br.edu.ifpb.pod.to.MensageTO;
import br.edu.ifpb.pod.interfaces.RMIService;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

/**
 *
 * @author Emanuel Batista da Silva Filho - https://github.com/emanuelbatista
 */
public class Main extends javax.swing.JFrame {

    private RMIService service;
    private AcessTO acess;
    private String nameUser;

    /**
     * Creates new form Main
     *
     * @param service
     */
    public Main() {
        try {
            try {
                addService();
                login();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Servidor fora do ar", "Erro", JOptionPane.ERROR_MESSAGE);
                throw new Exception();
            }
            initComponents();
            try {
                addQuantUser();
                if (acess.isContainsMensage()) {
                    recuperarMensagens();
                    acess.setContainsMensage(false);
                }
            } catch (RemoteException ex) {
                tentarReconectar();
            }
            this.setVisible(true);
        } catch (Exception e) {
            dispose();
        }
    }

    private void tentarReconectar() {
        Runnable run = () -> {
            boolean conectou = false;
            jEnvMensagemButton.setEnabled(false);
            jInfoLabel.setText("Servidor fora do alcance. Espere conectar automaticamente");
            this.setEnabled(false);
            do {
                try {
                    System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
                    System.setProperty(Context.PROVIDER_URL, "iiop://localhost:1050");
                    Context context = new InitialContext();
                    RMIService serv = (RMIService) context.lookup("service");
                    service = serv;
                    acess = service.login(new CredentialTO(nameUser));
                    conectou = false;
                } catch (RemoteException ex) {
                    conectou = true;
                } catch (LoginException | NamingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } while (conectou);
            this.setEnabled(true);
            jEnvMensagemButton.setEnabled(true);
            jInfoLabel.setText("");
            atualizar();

        };
        new Thread(run).start();

    }

    private void addService() throws RemoteException {
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
            System.setProperty(Context.PROVIDER_URL, "iiop://localhost:1050");
            Context context = new InitialContext();
            RMIService service = (RMIService) context.lookup("service");
            this.service = service;
        } catch (NamingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addQuantUser() throws RemoteException {
        try {
            jQuantUserLabel.setText(String.valueOf(service.howMany(acess).getQuant()));
        } catch (LoginException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void recuperarMensagens() throws RemoteException {
        List<MensageTO> msns = null;
        try {
            msns = service.receiveAll(acess);
        } catch (LoginException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        msns.forEach((value) -> {
            jMensagemTextPane.setText(jMensagemTextPane.getText() + "\n" + value.getMsn());
        });
    }

    private void login() throws RemoteException {
        boolean loger;
        do {
            String msn = JOptionPane.showInputDialog(this, "Digite seu nome: ", "Login do Usuário", JOptionPane.QUESTION_MESSAGE);
            if (msn == null || msn.isEmpty()) {
                loger = true;
            } else {
                loger = false;
                try {
                    nameUser = msn;
                    acess = service.login(new CredentialTO(msn));
                } catch (LoginException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (loger);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTituloLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jMensagemTextPane = new javax.swing.JTextPane();
        jMensagemTextField = new javax.swing.JTextField();
        jEnvMensagemButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jQuantUserLabel = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jInfoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.gray);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTituloLabel.setBackground(java.awt.Color.lightGray);
        jTituloLabel.setForeground(java.awt.Color.black);
        jTituloLabel.setText("Chat "+nameUser);
        jTituloLabel.setOpaque(true);

        jMensagemTextPane.setEditable(false);
        jScrollPane1.setViewportView(jMensagemTextPane);

        jEnvMensagemButton.setText("Env. Mensagem");
        jEnvMensagemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEnvMensagemButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Atualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Quant. Usuário: ");

        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jInfoLabel.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTituloLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jQuantUserLabel)
                .addGap(51, 51, 51))
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jMensagemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(jEnvMensagemButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTituloLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jQuantUserLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jMensagemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jEnvMensagemButton)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void jEnvMensagemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEnvMensagemButtonActionPerformed
        String msn = jMensagemTextField.getText();
        if (msn != null && !msn.isEmpty()) {
            try {
                service.send(acess, new MensageTO(nameUser + ": " + msn));
                recuperarMensagens();
                jMensagemTextField.setText("");
            } catch (RemoteException ex) {
                tentarReconectar();
            } catch (LoginException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Digite uma mensagem", "Atenção", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jEnvMensagemButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        atualizar();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void atualizar() {
        try {
            addQuantUser();
            recuperarMensagens();
        } catch (RemoteException ex) {
            tentarReconectar();
            JOptionPane.showMessageDialog(this, "O sistema não está conectado ao servidor.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            service.logout(acess);
        } catch (RemoteException ex) {
            tentarReconectar();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            service.logout(acess);
            this.dispose();
        } catch (RemoteException ex) {
            tentarReconectar();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        new Main();
        //</editor-fold>

        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jEnvMensagemButton;
    private javax.swing.JLabel jInfoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jMensagemTextField;
    private javax.swing.JTextPane jMensagemTextPane;
    private javax.swing.JLabel jQuantUserLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTituloLabel;
    // End of variables declaration//GEN-END:variables
}
