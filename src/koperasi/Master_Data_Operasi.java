package koperasi;

import DB.konek;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import kelas.Const;
import kelas.operasiData;

public class Master_Data_Operasi extends javax.swing.JPanel {

    DefaultTableModel model;
    private konek koneksi;
    private operasiData odata;
    private String whereid;
    
    public Master_Data_Operasi() {
        initComponents();
        setMain();
    }
    private void setMain(){
        lb_judul.setText(Const.masterDataMenu);
        setTable();
        dataMasukKeTabel("");
        jd_users.setSize(new Dimension(600, 280));
        jd_users.setTitle("Users");
        jd_users.setLocationRelativeTo(this);
        //jd_users.setModal(true); 
        jd_pekerjaan.setSize(new Dimension(600, 280));
        jd_pekerjaan.setTitle("Pekerjaan");
        jd_pekerjaan.setLocationRelativeTo(this);
        //jd_pekerjaan.setModal(true); 
        txtf_crud_id_pekerjaan.setEnabled(false);
        jd_js.setSize(new Dimension(600, 280));
        jd_js.setTitle("Pekerjaan");
        jd_js.setLocationRelativeTo(this);
        //jd_js.setModal(true); 
        txtf_crud_id_js.setEnabled(false);
        jd_tenor.setSize(new Dimension(600, 230));
        jd_tenor.setTitle("Pekerjaan");
        jd_tenor.setLocationRelativeTo(this);
        //jd_tenor.setModal(true); 
    }
    private void setTable(){
        Object[] field = {};
        switch (Const.masterDataMenu) {
            case "USERS":
                field = new Object[]{"ID", "Username", "Password", "Masuk Pada", "Dibuat Pada", "Diupdate Pada"};
                break;
            case "PEKERJAAN":
                field = new Object[]{"ID", "Pekerjaan"};
                break;
            case "JENIS SIMPANAN":
                field = new Object[]{"ID", "Jenis Simpanan"};
                break;
            case "TENOR":
                field = new Object[]{"ID", "Tenor"};
                break;
        }
        model = new DefaultTableModel(field, 0);
        tb_master_data.setModel(model);
        tb_master_data.setRowHeight(30);
    }
    private void dataMasukKeTabel(String key){
        odata = new operasiData();
        model.getDataVector().removeAllElements();
        ResultSet rs;
        String W = "";
        
        try {
            koneksi = new konek();
            switch (Const.masterDataMenu) {
                case "USERS":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR username LIKE '%"+Const.encrypt(key)+"%' OR password LIKE '%"+Const.encrypt(key)+"%' OR "
                            + "logged_at LIKE '%"+key+"%' OR created_at LIKE '%"+key+"%' OR updated_at LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM users "+W+" ORDER BY id DESC");
                    while (rs.next()) {                
                        String decUsername = Const.decrypt(rs.getString(2));
                        String decPassword = Const.decrypt(rs.getString(3));
                        Object[] data = {rs.getString(1), decUsername, decPassword, rs.getString(4), 
                            rs.getString(5), rs.getString(6)};
                        model.addRow(data);
                    }
                    break;
                case "PEKERJAAN":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR pekerjaan LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM pekerjaan "+W+" ORDER BY id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2)};
                        model.addRow(data);
                    }
                    break;
                case "JENIS SIMPANAN":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR jenis_simpanan LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM jenis_simpanan "+W+" ORDER BY id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2)};
                        model.addRow(data);
                    }
                    break;
                case "TENOR":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR tenor LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM tenor "+W+" ORDER BY tenor DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2)};
                        model.addRow(data);
                    }
                    break;
            }
            
        } catch (SQLException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "MDO_inData : "+e);
        }
    }
    private void CRUDUser_Simpan(){
        odata = new operasiData();
        String username = txtf_crud_username.getText();
        String password = txtf_crud_password.getText();
        
        try {
            //ENKRIPSI DATA
            String encu = Const.encrypt(username);
            String encp = Const.encrypt(password);
            if(username.equals("") || password.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data users !");
            }else{
                switch(Const.masterDataCRUD){
                    case "TAMBAH":
                        odata.insert_users(encu, encp);
                        break;
                    case "EDIT":
                        odata.update_users(whereid, encu, encp);
                        break;
                    case "HAPUS":
                        odata.delete_users(whereid);
                        break;
                }
                jd_users.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_master_data.clearSelection();
        } catch (SQLException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "MDO_CRUDuser_click : "+e);
        }
    }
    private void CRUDPekerjaan_Simpan(){
        odata = new operasiData();
        String id = txtf_crud_id_pekerjaan.getText();
        String pekerjaan = txtf_crud_pekerjaan.getText();
        
        try {
            if(id.equals("") || pekerjaan.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data pekerjaan !");
            }else{
                switch(Const.masterDataCRUD){
                    case "TAMBAH":
                        odata.insert_pekerjaan(id, pekerjaan);
                        break;
                    case "EDIT":
                        odata.update_pekerjaan(whereid, pekerjaan);
                        break;
                    case "HAPUS":
                        odata.delete_pekerjaan(whereid);
                        break;
                }
                jd_pekerjaan.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_master_data.clearSelection();
        } catch (SQLException  e) {
            JOptionPane.showMessageDialog(null, "MDO_CRUDPekerjaan_click : "+e);
        }
    }
    private void CRUDjenis_simpanan_Simpan(){
        odata = new operasiData();
        String id = txtf_crud_id_js.getText();
        String jenis_simpanan = txtf_crud_js.getText();
        
        try {
            if(id.equals("") || jenis_simpanan.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data jenis_simpanan !");
            }else{
                switch(Const.masterDataCRUD){
                    case "TAMBAH":
                        odata.insert_jenis_simpanan(id, jenis_simpanan);
                        break;
                    case "EDIT":
                        odata.update_jenis_simpanan(whereid, jenis_simpanan);
                        break;
                    case "HAPUS":
                        odata.delete_jenis_simpanan(whereid);
                        break;
                }
                jd_js.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_master_data.clearSelection();
        } catch (SQLException  e) {
            JOptionPane.showMessageDialog(null, "MDO_CRUDJenis_simpanan_click : "+e);
        }
    }
    private void CRUDTenor_Simpan(){
        odata = new operasiData();
        String tenor = txtf_crud_tenor.getText();
        
        try {
            if(tenor.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data Tenor !");
            }else{
                switch(Const.masterDataCRUD){
                    case "TAMBAH":
                        odata.insert_tenor("TR"+tenor, tenor);
                        break;
                    case "EDIT":
                        odata.update_tenor(whereid, "TR"+tenor, tenor);
                        break;
                    case "HAPUS":
                        odata.delete_tenor(whereid);
                        break;
                }
                jd_tenor.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_master_data.clearSelection();
        } catch (SQLException  e) {
            JOptionPane.showMessageDialog(null, "MDO_CRUDTenor_click : "+e);
        }
    }
    private void resetFields(JTextField... f){
        for (JTextField tf : f) {
            tf.setText("");
        }
    }
    private void tampilDataUbah(int i, int row, JTextField... f){
        Object getValue;
        for (JTextField tf : f) {
            getValue = tb_master_data.getValueAt(row, i);
            tf.setText((String) getValue);
            i++;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_users = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        lb_crud_title = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtf_crud_username = new javax.swing.JTextField();
        txtf_crud_password = new javax.swing.JTextField();
        btn_crud_simpan_users = new javax.swing.JButton();
        jd_pekerjaan = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        lb_crud_title_pekerjaan = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtf_crud_id_pekerjaan = new javax.swing.JTextField();
        txtf_crud_pekerjaan = new javax.swing.JTextField();
        btn_crud_simpan_pekerjaan = new javax.swing.JButton();
        jd_js = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        lb_crud_title_js = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtf_crud_id_js = new javax.swing.JTextField();
        txtf_crud_js = new javax.swing.JTextField();
        btn_crud_simpan_js = new javax.swing.JButton();
        jd_tenor = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        lb_crud_title_tenor = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtf_crud_tenor = new javax.swing.JTextField();
        btn_crud_simpan_tenor = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lb_judul = new javax.swing.JLabel();
        Tambah = new javax.swing.JLabel();
        btn_edit = new javax.swing.JLabel();
        btn_hapus = new javax.swing.JLabel();
        txtf_Cari = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_master_data = new javax.swing.JTable();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title.setText("TITLE JD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_users.getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USERNAME ");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PASSWORD");

        btn_crud_simpan_users.setText("SIMPAN");
        btn_crud_simpan_users.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_usersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_username))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_password, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_users, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_username, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_password, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_crud_simpan_users, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jd_users.getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_pekerjaan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_pekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_pekerjaan.setText("TITLE JD");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_pekerjaan, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_pekerjaan, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_pekerjaan.getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID ");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PEKERJAAN");

        btn_crud_simpan_pekerjaan.setText("SIMPAN");
        btn_crud_simpan_pekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_pekerjaanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_id_pekerjaan))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_pekerjaan, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_pekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_id_pekerjaan, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_pekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_crud_simpan_pekerjaan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jd_pekerjaan.getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_js.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_js.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_js.setText("TITLE JD");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_js, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_js, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_js.getContentPane().add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ID ");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("JENIS SIMPANAN");

        btn_crud_simpan_js.setText("SIMPAN");
        btn_crud_simpan_js.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_jsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_id_js))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_js, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_js, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_id_js, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_js, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_crud_simpan_js, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jd_js.getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_tenor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_tenor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_tenor.setText("TITLE JD");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_tenor, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_tenor, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_tenor.getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(0, 102, 102));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TENOR");

        txtf_crud_tenor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_crud_tenorKeyTyped(evt);
            }
        });

        btn_crud_simpan_tenor.setText("SIMPAN");
        btn_crud_simpan_tenor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_tenorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_tenor, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_tenor, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_tenor, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_crud_simpan_tenor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jd_tenor.getContentPane().add(jPanel9, java.awt.BorderLayout.CENTER);

        setPreferredSize(new java.awt.Dimension(848, 610));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(848, 80));

        lb_judul.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_judul.setForeground(new java.awt.Color(0, 102, 102));
        lb_judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_judul.setText("TITLE");
        lb_judul.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        Tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        Tambah.setText("  TAMBAH");
        Tambah.setPreferredSize(new java.awt.Dimension(60, 60));
        Tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambahMouseClicked(evt);
            }
        });

        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btn_edit.setText("  EDIT");
        btn_edit.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_editMouseClicked(evt);
            }
        });

        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        btn_hapus.setText("  HAPUS");
        btn_hapus.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_hapusMouseClicked(evt);
            }
        });

        txtf_Cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtf_CariKeyReleased(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(60, 60));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_judul, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtf_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lb_judul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtf_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tb_master_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tb_master_data);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtf_CariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_CariKeyReleased
        String getValueSearch = txtf_Cari.getText();
        dataMasukKeTabel(getValueSearch); 
    }//GEN-LAST:event_txtf_CariKeyReleased

    private void TambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahMouseClicked
        Const.masterDataCRUD = "TAMBAH";
        
        resetFields(txtf_crud_username, txtf_crud_password, txtf_crud_id_pekerjaan, txtf_crud_pekerjaan);
        lb_crud_title.setText("TAMBAH USERS");
        lb_crud_title_pekerjaan.setText("TAMBAH PEKERJAAN");
        lb_crud_title_js.setText("TAMBAH JENIS SIMPANAN");
        lb_crud_title_tenor.setText("TAMBAH TENOR");
        
        txtf_crud_username.setEnabled(true);
        txtf_crud_password.setEnabled(true);
        
        txtf_crud_id_pekerjaan.setEnabled(false);
        txtf_crud_pekerjaan.setEnabled(true);
        
        txtf_crud_id_js.setEnabled(false);
        txtf_crud_js.setEnabled(true);
        
        txtf_crud_tenor.setEnabled(true);
        
        try {
            if(Const.masterDataMenu.equals("USERS")){
                jd_users.setVisible(true);
            }else if(Const.masterDataMenu.equals("PEKERJAAN")){
                jd_pekerjaan.setVisible(true);
                txtf_crud_id_pekerjaan.setText(odata.getId("pekerjaan")+"");
            }else if(Const.masterDataMenu.equals("JENIS SIMPANAN")){
                jd_js.setVisible(true);
                txtf_crud_id_js.setText("JS"+odata.getIdVarchar("jenis_simpanan"));
            }else if(Const.masterDataMenu.equals("TENOR")){
                jd_tenor.setVisible(true);
            }
        } catch (SQLException e) {
        }
        
    }//GEN-LAST:event_TambahMouseClicked

    private void btn_crud_simpan_usersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_usersActionPerformed
        CRUDUser_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_usersActionPerformed

    private void btn_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editMouseClicked
        Const.masterDataCRUD = "EDIT";
        int getRow = tb_master_data.getSelectedRow();
        
        resetFields(txtf_crud_username, txtf_crud_password, txtf_crud_id_pekerjaan, txtf_crud_pekerjaan);
        lb_crud_title.setText("UBAH USERS");
        lb_crud_title_pekerjaan.setText("UBAH PEKERJAAN");
        lb_crud_title_js.setText("UBAH JENIS SIMPANAN");
        lb_crud_title_tenor.setText("UBAH TENOR");
        txtf_crud_username.setEnabled(true);
        txtf_crud_password.setEnabled(true);
        
        txtf_crud_id_pekerjaan.setEnabled(false);
        txtf_crud_pekerjaan.setEnabled(true);
        
        txtf_crud_id_js.setEnabled(false);
        txtf_crud_js.setEnabled(true);
        
        txtf_crud_tenor.setEnabled(true);
        
        if(Const.masterDataMenu.equals("USERS")){
            if(getRow >= 0){
                jd_users.setVisible(true);
                tampilDataUbah(1, getRow, txtf_crud_username, txtf_crud_password);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }else if(Const.masterDataMenu.equals("PEKERJAAN")){
            if(getRow >= 0){
                jd_pekerjaan.setVisible(true);
                tampilDataUbah(0, getRow, txtf_crud_id_pekerjaan, txtf_crud_pekerjaan);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }else if(Const.masterDataMenu.equals("JENIS SIMPANAN")){
            if(getRow >= 0){
                jd_js.setVisible(true);
                tampilDataUbah(0, getRow, txtf_crud_id_js, txtf_crud_js);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }else if(Const.masterDataMenu.equals("TENOR")){
            if(getRow >= 0){
                jd_tenor.setVisible(true);
                tampilDataUbah(0, getRow, txtf_crud_tenor);
                txtf_crud_tenor.setText((String) tb_master_data.getValueAt(tb_master_data.getSelectedRow(), 1));
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }if(getRow < 0){JOptionPane.showMessageDialog(null, "pilih data yg dingin diubah");}
    }//GEN-LAST:event_btn_editMouseClicked

    private void btn_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_hapusMouseClicked
        Const.masterDataCRUD = "HAPUS";
        int getRow = tb_master_data.getSelectedRow();
        
        resetFields(txtf_crud_username, txtf_crud_password, txtf_crud_id_pekerjaan, txtf_crud_pekerjaan);
        lb_crud_title.setText("BENERAN HAPUS USERS ?");
        txtf_crud_username.setEnabled(false);
        txtf_crud_password.setEnabled(false);
        btn_crud_simpan_users.setText("OK");
        
        lb_crud_title_pekerjaan.setText("BENERAN HAPUS PEKERJAAN ?");
        txtf_crud_id_pekerjaan.setEnabled(false);
        txtf_crud_pekerjaan.setEnabled(false);
        btn_crud_simpan_pekerjaan.setText("OK");
        
        lb_crud_title_js.setText("BENERAN HAPUS JENIS SIMPANAN ?");
        txtf_crud_id_js.setEnabled(false);
        txtf_crud_js.setEnabled(false);
        btn_crud_simpan_js.setText("OK");
        
        lb_crud_title_tenor.setText("BENERAN HAPUS TENOR ?");
        txtf_crud_tenor.setEnabled(false);
        btn_crud_simpan_tenor.setText("OK");
        
        if(Const.masterDataMenu.equals("USERS")){
            if(getRow >= 0){
                jd_users.setVisible(true);
                tampilDataUbah(1, getRow, txtf_crud_username, txtf_crud_password);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }else if(Const.masterDataMenu.equals("PEKERJAAN")){
            if(getRow >= 0){
                jd_pekerjaan.setVisible(true);
                tampilDataUbah(0, getRow, txtf_crud_id_pekerjaan, txtf_crud_pekerjaan);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }else if(Const.masterDataMenu.equals("JENIS SIMPANAN")){
            if(getRow >= 0){
                jd_js.setVisible(true);
                tampilDataUbah(0, getRow, txtf_crud_id_js, txtf_crud_js);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }else if(Const.masterDataMenu.equals("TENOR")){
            if(getRow >= 0){
                jd_tenor.setVisible(true);
                tampilDataUbah(0, getRow, txtf_crud_tenor);
                whereid = (String)tb_master_data.getValueAt(getRow, 0);
            }
        }if(getRow < 0){JOptionPane.showMessageDialog(null, "pilih data yg dingin diubah");}
    }//GEN-LAST:event_btn_hapusMouseClicked

    private void btn_crud_simpan_pekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_pekerjaanActionPerformed
        CRUDPekerjaan_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_pekerjaanActionPerformed

    private void btn_crud_simpan_jsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_jsActionPerformed
        CRUDjenis_simpanan_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_jsActionPerformed

    private void btn_crud_simpan_tenorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_tenorActionPerformed
        CRUDTenor_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_tenorActionPerformed

    private void txtf_crud_tenorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_tenorKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_crud_tenorKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tambah;
    private javax.swing.JButton btn_crud_simpan_js;
    private javax.swing.JButton btn_crud_simpan_pekerjaan;
    private javax.swing.JButton btn_crud_simpan_tenor;
    private javax.swing.JButton btn_crud_simpan_users;
    private javax.swing.JLabel btn_edit;
    private javax.swing.JLabel btn_hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JDialog jd_js;
    private javax.swing.JDialog jd_pekerjaan;
    private javax.swing.JDialog jd_tenor;
    private javax.swing.JDialog jd_users;
    private javax.swing.JLabel lb_crud_title;
    private javax.swing.JLabel lb_crud_title_js;
    private javax.swing.JLabel lb_crud_title_pekerjaan;
    private javax.swing.JLabel lb_crud_title_tenor;
    private javax.swing.JLabel lb_judul;
    private javax.swing.JTable tb_master_data;
    private javax.swing.JTextField txtf_Cari;
    private javax.swing.JTextField txtf_crud_id_js;
    private javax.swing.JTextField txtf_crud_id_pekerjaan;
    private javax.swing.JTextField txtf_crud_js;
    private javax.swing.JTextField txtf_crud_password;
    private javax.swing.JTextField txtf_crud_pekerjaan;
    private javax.swing.JTextField txtf_crud_tenor;
    private javax.swing.JTextField txtf_crud_username;
    // End of variables declaration//GEN-END:variables
}
