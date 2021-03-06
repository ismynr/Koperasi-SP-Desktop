package koperasi;

import DB.konek;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import kelas.Const;

/**
 *
 * @author ISMYNR
 */
public class APP extends javax.swing.JFrame {

    private konek koneksi;
    
    public APP(){
        initComponents();
        setTitle("Koperasi Simpan Pinjam");
    }
    public static void RMComp(JPanel utama, JPanel panel){
        if(utama.getComponentCount() > 0){
            utama.removeAll();
        }
        utama.add(panel, BorderLayout.CENTER);
        utama.revalidate();
        utama.repaint();
    }
    
    private void ambilWaktu() throws UnsupportedEncodingException{
        String sql = "UPDATE users SET logged_at=NOW() WHERE username = '"+Const.encrypt(Const.username)+"'";
        try {
            koneksi = new konek();
            koneksi.eksekusiUpdate(sql);
            koneksi.tutupKoneksi();
        } catch (Exception e) {}
    }
    public void getLogin(String u, String p){
        Const.username = u;
        Const.password = p;
    }
    public void login(){
        try {
            String username = txtf_username.getText();
            String password = txtf_password.getText();
            String ecu = Const.encrypt(username);
            String ecp = Const.encrypt(password);
            String sql = "SELECT * FROM users WHERE username='"+ecu+"' AND password='"+ecp+"'";
            try {
                koneksi = new konek();
                ResultSet rs = koneksi.eksekusiSelect(sql);
                if (rs.next()) {
                    APP.RMComp(container, new Home());
                    getLogin(username, password);
                    ambilWaktu();
                }else{
                    JOptionPane.showMessageDialog(null, "Mohon cek username dan password anda");
                }
                koneksi.tutupKoneksi();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Login : " + e);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(APP.class.getName()).log(Level.SEVERE, null, ex);
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

        container = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtf_username = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtf_password = new javax.swing.JPasswordField();
        btn_masuk = new javax.swing.JButton();
        GAMBAR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        container.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(187, 231, 247));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_koperasi.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, 110));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/username.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(498, 241, 40, 40));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/password.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(498, 301, -1, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("KOPERASI SIMPAN PINJAM NR");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Jalan Anggrek RT22 / 06 Kecamatan Talang");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kabupaten Tegal");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("LOGIN");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 520, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("USERNAME : ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 183, 45));

        txtf_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_usernameActionPerformed(evt);
            }
        });
        jPanel1.add(txtf_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 309, 43));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PASSWORD : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 183, 49));

        txtf_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtf_passwordActionPerformed(evt);
            }
        });
        jPanel1.add(txtf_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, 309, 43));

        btn_masuk.setText("MASUK");
        btn_masuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_masukActionPerformed(evt);
            }
        });
        jPanel1.add(btn_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 360, 122, 41));

        GAMBAR.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        GAMBAR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        GAMBAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/x.png"))); // NOI18N
        jPanel1.add(GAMBAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1390, 850));

        container.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(container, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtf_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_usernameActionPerformed
        login();
    }//GEN-LAST:event_txtf_usernameActionPerformed

    private void txtf_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtf_passwordActionPerformed
        login();
    }//GEN-LAST:event_txtf_passwordActionPerformed

    private void btn_masukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_masukActionPerformed
        login();
    }//GEN-LAST:event_btn_masukActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(APP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(APP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(APP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(APP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                APP app = new APP();
                app.setExtendedState(Frame.MAXIMIZED_BOTH);
                app.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GAMBAR;
    private javax.swing.JButton btn_masuk;
    private javax.swing.JPanel container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtf_password;
    private javax.swing.JTextField txtf_username;
    // End of variables declaration//GEN-END:variables
}
