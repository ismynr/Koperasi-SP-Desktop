package koperasi;

import DB.konek;
import java.awt.Frame;
import java.io.File;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ISMYNR
 */
public class Report extends javax.swing.JPanel {

    private konek koneksi;
    
    public Report() {
        initComponents();
    }
    private void ireport(String path, String title){
        try {
            koneksi = new konek();
            File reportFile = new File(path);
            JasperDesign jasperDesign = JRXmlLoader.load(reportFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, koneksi.getConnection());
            JasperViewer jv = new JasperViewer(jasperPrint);
            JFrame viewer = new JFrame();
            viewer.setTitle(title);
            viewer.setExtendedState(Frame.MAXIMIZED_BOTH);
            viewer.getContentPane().add(jv.getContentPane());
            viewer.setResizable(true);
            viewer.setIconImage(jv.getIconImage());
            viewer.setVisible(true);
        } catch (JRException exc) {
           System.out.println(exc.getMessage());
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_report_simpanan = new javax.swing.JLabel();
        btn_report_angsuran = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_report_anggota = new javax.swing.JLabel();
        btn_report_pinjaman = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btn_report_pengeluaran = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        GAMBAR = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(183, 233, 233));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(184, 231, 231));

        btn_report_simpanan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_report_simpanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_report_simpanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/receive_cash_120px.png"))); // NOI18N
        btn_report_simpanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_report_simpananMouseClicked(evt);
            }
        });

        btn_report_angsuran.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_report_angsuran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_report_angsuran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/instalment_120px.png"))); // NOI18N
        btn_report_angsuran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_report_angsuranMouseClicked(evt);
            }
        });

        btn_kembali.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_kembali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/exit_120px.png"))); // NOI18N
        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("KEMBALI");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SIMPANAN");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ANGSURAN");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("ANGGOTA");

        btn_report_anggota.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_report_anggota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_report_anggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/conference_120px.png"))); // NOI18N
        btn_report_anggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_report_anggotaMouseClicked(evt);
            }
        });

        btn_report_pinjaman.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_report_pinjaman.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_report_pinjaman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/budget_120px.png"))); // NOI18N
        btn_report_pinjaman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_report_pinjamanMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("PINJAMAN");

        btn_report_pengeluaran.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_report_pengeluaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_report_pengeluaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/payment_history_120px.png"))); // NOI18N
        btn_report_pengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_report_pengeluaranMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("PENGELUARAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_report_anggota)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_report_simpanan)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_pengeluaran))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_pinjaman))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_report_angsuran)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_kembali)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_report_anggota, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 930, 200));

        jLabel1.setFont(new java.awt.Font("Bookman Old Style", 1, 30)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LAPORAN DATA KOPERASI");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 440, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_business_report_70px.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 70, 70));
        jPanel1.add(GAMBAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(-110, -70, 1500, 1110));

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_report_pinjamanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_report_pinjamanMouseClicked
        JOptionPane.showMessageDialog(null, "Tunggu sebentar ... \nsedang menyiapkan data ...");
        ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\report\\pinjaman.jrxml", "Cetak Data Pinjaman");
    }//GEN-LAST:event_btn_report_pinjamanMouseClicked

    private void btn_report_anggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_report_anggotaMouseClicked
        JOptionPane.showMessageDialog(null, "Tunggu sebentar ... \nsedang menyiapkan data ...");
        ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\report\\anggota.jrxml", "Cetak Data Anggota");
    }//GEN-LAST:event_btn_report_anggotaMouseClicked

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked
        APP.RMComp(this, new Home());
    }//GEN-LAST:event_btn_kembaliMouseClicked

    private void btn_report_angsuranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_report_angsuranMouseClicked
        JOptionPane.showMessageDialog(null, "Tunggu sebentar ... \nsedang menyiapkan data ...");
        ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\report\\angsuran_pinjaman.jrxml", "Cetak Data Angsuran Pinjmanan");
    }//GEN-LAST:event_btn_report_angsuranMouseClicked

    private void btn_report_simpananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_report_simpananMouseClicked
        JOptionPane.showMessageDialog(null, "Tunggu sebentar ... \nsedang menyiapkan data ...");
        ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\report\\simpanan.jrxml", "Cetak Data Simpanan");
    }//GEN-LAST:event_btn_report_simpananMouseClicked

    private void btn_report_pengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_report_pengeluaranMouseClicked
        JOptionPane.showMessageDialog(null, "Tunggu sebentar ... \nsedang menyiapkan data ...");
        ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\report\\pengeluaran.jrxml", "Cetak Data Pengeluaran");
    }//GEN-LAST:event_btn_report_pengeluaranMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GAMBAR;
    private javax.swing.JLabel btn_kembali;
    private javax.swing.JLabel btn_report_anggota;
    private javax.swing.JLabel btn_report_angsuran;
    private javax.swing.JLabel btn_report_pengeluaran;
    private javax.swing.JLabel btn_report_pinjaman;
    private javax.swing.JLabel btn_report_simpanan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
