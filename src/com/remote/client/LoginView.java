package com.remote.client;

import com.remote.server.InterfaceServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class LoginView extends javax.swing.JFrame {
    private InterfaceServer server;
    private static final String IPV4_REGEX =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    
    public static boolean isValidIPv4(String ipAddress) {
        return Pattern.matches(IPV4_REGEX, ipAddress);
    }
    public LoginView() {
        initComponents();
        this.setLocationRelativeTo(null);
        txtNombre.setEnabled(false);
        lbNombre.setEnabled(false);
        btnEntrar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnConexion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtIp = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnEntrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log in");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(155, 0, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnConexion.setBackground(new java.awt.Color(155, 0, 242));
        btnConexion.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnConexion.setForeground(new java.awt.Color(255, 255, 255));
        btnConexion.setText("Conectar");
        btnConexion.setToolTipText("");
        btnConexion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConexionActionPerformed(evt);
            }
        });
        jPanel1.add(btnConexion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 172, -1));

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("IP");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        txtIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIpActionPerformed(evt);
            }
        });
        jPanel1.add(txtIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 153, 27));

        jLabel2.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Escribir el nombre del cliente");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jLabel4.setFont(new java.awt.Font("Courier New", 3, 12)); // NOI18N
        jLabel4.setForeground(java.awt.Color.pink);
        jLabel4.setText("En esta parte de aqui pondras el nombre del cliente");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 102, 360, -1));

        lbNombre.setFont(new java.awt.Font("Courier New", 3, 18)); // NOI18N
        lbNombre.setForeground(new java.awt.Color(255, 255, 255));
        lbNombre.setText("Nombre");
        jPanel1.add(lbNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 153, 27));

        btnEntrar.setBackground(new java.awt.Color(155, 0, 242));
        btnEntrar.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setText("Entrar");
        btnEntrar.setToolTipText("");
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 172, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 240));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConexionActionPerformed
    if(isValidIPv4(txtIp.getText())){
        String ip = txtIp.getText();
        try {
            server = (InterfaceServer) Naming.lookup("rmi://" + ip + ":4321/remote");
            txtNombre.setEnabled(true);
            lbNombre.setEnabled(true);
            btnEntrar.setEnabled(true);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(new JFrame(),"No es una Ipv4 valida","Alert",JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnConexionActionPerformed

    private void txtIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIpActionPerformed

    }//GEN-LAST:event_txtIpActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        
        try {
            if(!server.checkUsername(txtNombre.getText())){
                if(!txtNombre.getText().equals("") && !txtNombre.getText().contains(" ")){
                    String nombre = txtNombre.getText();
                    new ImageProcessingView(nombre,server).setVisible(true);
                    this.dispose();
                }
            }else{
                JOptionPane.showMessageDialog(new JFrame(),"este usuario ya está tomado","Alert",JOptionPane.WARNING_MESSAGE);
            }
        } catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnEntrarActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConexion;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JTextField txtIp;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
