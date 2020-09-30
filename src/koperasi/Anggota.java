package koperasi;

import DB.konek;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import kelas.Const;
import kelas.operasiData;

public class Anggota extends javax.swing.JPanel {

    DefaultTableModel model;
    private operasiData odata;
    private konek koneksi;
    private String whereid;
    Date tanggal = new Date();
    
    public Anggota() {
        initComponents();
        setMain();
    }
    public void setMain(){
        setPanelUtama(false, "", "#fff", 909, 100);
        setTable();
        dataMasukTabel("");
        tambahCmb();
        JTextFieldDateEditor ed = (JTextFieldDateEditor) jdate_tgl_lahir.getDateEditor();
        ed.setEditable(false);
    }
    public void setPanelUtama(boolean tf, String title, String colorHex, int w, int h){
        lb_sembunyikan.setVisible(tf);
        panel_crud.setVisible(tf);
        panel_utama.setPreferredSize(new Dimension(w, h));
        lb_title_crud.setText(title);
        Color c = Color.decode(colorHex);
        rb_l.setBackground(c);
        rb_p.setBackground(c);
        panel_crud.setBackground(c);
    }
    private void setTable(){
        Object[] field = new Object[]{"ID", "Nama", "NIK", "lahir", "JK", "ID Pekerjaan", "No Hp", "Alamat", 
                    "Email","Dibuat", "Diubah"};
        model = new DefaultTableModel(field, 0);
        tb_anggota.setModel(model);
        tb_anggota.setRowHeight(30);
    }
    private void dataMasukTabel(String key){
        odata = new operasiData();
        model.getDataVector().removeAllElements();
        ResultSet rs;
        String W = "";
        try {
            koneksi = new konek();
            if(!key.equals("")){
                W = "WHERE id LIKE '%"+key+"%' OR nama LIKE '%"+key+"%' OR nik LIKE '%"+Const.encrypt(key)+"%' "
                        + "OR tgl_lahir LIKE '%"+key+"%' OR jk LIKE '%"+key+"%' OR pekerjaan LIKE '%"+key+"%' OR no_hp LIKE '%"+Const.encrypt(key)+"%' "
                        + "OR alamat LIKE '%"+Const.encrypt(key)+"%' OR email LIKE'%"+Const.encrypt(key)+"%' "
                        + "OR created_at LIKE '%"+key+"%' OR updated_at LIKE '%"+key+"%'";
            }
            rs = koneksi.eksekusiSelect("SELECT * FROM anggota "+W+" ORDER BY id DESC");
            while(rs.next()){
                String decNik = Const.decrypt(rs.getString(3));
                String decNo_hp = Const.decrypt(rs.getString(7));
                String decAlamat = Const.decrypt(rs.getString(8));
                String decEmail = Const.decrypt(rs.getString(9));
                Object[] data = {rs.getString(1), rs.getString(2), decNik, rs.getString(4), 
                    rs.getString(5), rs.getString(6), decNo_hp, decAlamat, decEmail, 
                    rs.getString(10), rs.getString(11)};
                model.addRow(data);
            }
        } catch (SQLException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "dataMasukTabel : "+e);
        }
    }
    public void tambahCmb(){
        cmb_pekerjaan.removeAllItems();
        cmb_pekerjaan.addItem("Pilih Pekerjaan");
        try {
            koneksi = new konek();
            ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM pekerjaan");
            while(rs.next()){
                cmb_pekerjaan.addItem(rs.getString(2));
            }
        } catch (SQLException e) {
        }
    }
    private void resetFields(JTextField... f){
        for (JTextField tf : f) {
            tf.setText("");
        }
        buttonGroup1.clearSelection();
        txta_alamat.setText("");
        cmb_pekerjaan.setSelectedIndex(0);
        jdate_tgl_lahir.setDate(null);
        tb_anggota.clearSelection();
    }
    private void tampilDataUbah(int i, int row, JTextField... f){
        try {
            f[0].setText((String) tb_anggota.getValueAt(row, 0));
            f[1].setText((String) tb_anggota.getValueAt(row, 1));
            f[2].setText((String) tb_anggota.getValueAt(row, 2));
            f[3].setText((String) tb_anggota.getValueAt(row, 6));
            f[4].setText((String) tb_anggota.getValueAt(row, 8));
            if(tb_anggota.getValueAt(row, 4) == "L"){
                rb_l.setSelected(true);
            }else{
                rb_p.setSelected(true);
            }
            txta_alamat.setText((String) tb_anggota.getValueAt(row, 7));
            int objectParseInt  = Integer.parseInt(model.getValueAt(row, 5).toString());
            cmb_pekerjaan.setSelectedIndex(objectParseInt);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) tb_anggota.getValueAt(row, 3));
            jdate_tgl_lahir.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(Anggota.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void SimpanDataAnggota(){
        odata = new operasiData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        String id = txtf_id.getText();
        String nama = txtf_nama.getText();
        String nik = txtf_nik.getText();
        String tgl_lahir = format.format(jdate_tgl_lahir.getDate());
        String jk = rb_l.isSelected() ? "L" : "P"; 
        int pekerjaan = cmb_pekerjaan.getSelectedIndex();
        String no_hp = txtf_no_hp.getText();
        String alamat = txta_alamat.getText();
        String email = txtf_email.getText();
        
        try {
            if(id.equals("") || nama.equals("") || nik.equals("") || jk.equals("") || pekerjaan==0 || 
                    no_hp.equals("") || alamat.equals("") || email.equals("") ){
                JOptionPane.showMessageDialog(null, "Lengkapi data anggota dengan benar !");
            }else{
                switch(Const.AnggotaCRUD){
                    case "TAMBAH":
                        odata.insert_anggota(id, nama, Const.encrypt(nik), tgl_lahir, jk, pekerjaan, Const.encrypt(no_hp), 
                        Const.encrypt(alamat), Const.encrypt(email));
                        break;
                    case "EDIT":
                        odata.update_anggota(id, nama, Const.encrypt(nik), tgl_lahir, jk, pekerjaan, Const.encrypt(no_hp), 
                        Const.encrypt(alamat), Const.encrypt(email));
                        odata.updateNamaAngg_transaksi_simpanan(id, nama);
                        odata.updateNamaAngg_transaksi_pinjaman_and_angsuran(id, nama);
                        break;
                }
                setPanelUtama(false, "", "#fff", 909, 100);
            }
            dataMasukTabel("");
            tb_anggota.clearSelection();
        } catch (SQLException | UnsupportedEncodingException  e) {
            JOptionPane.showMessageDialog(null, "MDO_SimpanDataAnggota_click : "+e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        panel_utama = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lb_tambah = new javax.swing.JLabel();
        lb_edit = new javax.swing.JLabel();
        lb_hapus = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtf_cari = new javax.swing.JTextField();
        lb_sembunyikan = new javax.swing.JLabel();
        panel_crud = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtf_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtf_nama = new javax.swing.JTextField();
        txtf_nik = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jdate_tgl_lahir = new com.toedter.calendar.JDateChooser();
        txtf_no_hp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rb_l = new javax.swing.JRadioButton();
        rb_p = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        cmb_pekerjaan = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txta_alamat = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        txtf_email = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        lb_title_crud = new javax.swing.JLabel();
        lb_home = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_anggota = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        panel_utama.setPreferredSize(new java.awt.Dimension(909, 360));
        panel_utama.setRequestFocusEnabled(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATA ANGGOTA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lb_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        lb_tambah.setText(" TAMBAH");
        lb_tambah.setPreferredSize(new java.awt.Dimension(41, 50));
        lb_tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_tambahMouseClicked(evt);
            }
        });

        lb_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        lb_edit.setText(" EDIT");
        lb_edit.setPreferredSize(new java.awt.Dimension(0, 50));
        lb_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_editMouseClicked(evt);
            }
        });

        lb_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        lb_hapus.setText(" HAPUS");
        lb_hapus.setPreferredSize(new java.awt.Dimension(0, 50));
        lb_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_hapusMouseClicked(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cari.png"))); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(25, 50));

        txtf_cari.setPreferredSize(new java.awt.Dimension(6, 30));
        txtf_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtf_cariKeyReleased(evt);
            }
        });

        lb_sembunyikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png"))); // NOI18N
        lb_sembunyikan.setText(" SEMBUNYIKAN");
        lb_sembunyikan.setPreferredSize(new java.awt.Dimension(0, 50));
        lb_sembunyikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_sembunyikanMouseClicked(evt);
            }
        });

        panel_crud.setBackground(new java.awt.Color(51, 0, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ID");
        jLabel2.setPreferredSize(new java.awt.Dimension(34, 30));

        txtf_id.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NAMA");
        jLabel3.setPreferredSize(new java.awt.Dimension(34, 30));

        txtf_nik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_nikKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("NIK");
        jLabel4.setPreferredSize(new java.awt.Dimension(34, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TANGGAL LAHIR");
        jLabel6.setPreferredSize(new java.awt.Dimension(34, 30));

        txtf_no_hp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_no_hpKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NO HANDPHONE");
        jLabel7.setPreferredSize(new java.awt.Dimension(34, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("JENIS KELAMIN");
        jLabel8.setPreferredSize(new java.awt.Dimension(34, 30));

        rb_l.setBackground(new java.awt.Color(51, 0, 51));
        buttonGroup1.add(rb_l);
        rb_l.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rb_l.setForeground(new java.awt.Color(255, 255, 255));
        rb_l.setText("Laki - Laki");

        rb_p.setBackground(new java.awt.Color(51, 0, 51));
        buttonGroup1.add(rb_p);
        rb_p.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rb_p.setForeground(new java.awt.Color(255, 255, 255));
        rb_p.setText("Perempuan");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("PEKERJAAN");
        jLabel9.setPreferredSize(new java.awt.Dimension(34, 30));

        cmb_pekerjaan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txta_alamat.setColumns(20);
        txta_alamat.setRows(5);
        jScrollPane2.setViewportView(txta_alamat);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ALAMAT");
        jLabel10.setPreferredSize(new java.awt.Dimension(34, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("EMAIL");
        jLabel11.setPreferredSize(new java.awt.Dimension(34, 30));

        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        lb_title_crud.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_title_crud.setForeground(new java.awt.Color(255, 255, 255));
        lb_title_crud.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_title_crud.setText("TITLE CRUD");
        lb_title_crud.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lb_title_crud.setPreferredSize(new java.awt.Dimension(34, 30));

        javax.swing.GroupLayout panel_crudLayout = new javax.swing.GroupLayout(panel_crud);
        panel_crud.setLayout(panel_crudLayout);
        panel_crudLayout.setHorizontalGroup(
            panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_crudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtf_id, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_title_crud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtf_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtf_nik, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jdate_tgl_lahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rb_l, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(rb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmb_pekerjaan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_crudLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_email, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_simpan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panel_crudLayout.setVerticalGroup(
            panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_crudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtf_no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtf_id, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_title_crud, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtf_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtf_nik, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jdate_tgl_lahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rb_l, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rb_p, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmb_pekerjaan, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel_crudLayout.createSequentialGroup()
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_crudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtf_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))))
        );

        lb_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/home.png"))); // NOI18N
        lb_home.setText(" HOME");
        lb_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_homeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel_utamaLayout = new javax.swing.GroupLayout(panel_utama);
        panel_utama.setLayout(panel_utamaLayout);
        panel_utamaLayout.setHorizontalGroup(
            panel_utamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_utamaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_home, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_sembunyikan, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtf_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panel_crud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel_utamaLayout.setVerticalGroup(
            panel_utamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_utamaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_utamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panel_utamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtf_cari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_sembunyikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_crud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
        );

        add(panel_utama, java.awt.BorderLayout.PAGE_START);

        tb_anggota.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_anggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_anggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_anggota);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void lb_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_tambahMouseClicked
        jdate_tgl_lahir.setDate(tanggal);
        try {
            koneksi = new konek();
            Const.AnggotaCRUD = "TAMBAH";
            setPanelUtama(true, "TAMBAH DATA", "#330033", 909, 360);
            resetFields(txtf_id,txtf_nama,txtf_nik,txtf_no_hp,txtf_email);
            odata = new operasiData();
            txtf_id.setText(odata.getIdSubstringAnggota("anggota"));
        } catch (SQLException ex) {
            
        }
    }//GEN-LAST:event_lb_tambahMouseClicked

    private void lb_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_editMouseClicked
        Const.AnggotaCRUD = "EDIT";
        int getRow = tb_anggota.getSelectedRow();
        resetFields(txtf_id,txtf_nama,txtf_nik,txtf_no_hp,txtf_email);
        if(getRow >= 0){
            setPanelUtama(true, "EDIT DATA", "#003366", 909, 360);
            tampilDataUbah(0, getRow, txtf_id,txtf_nama,txtf_nik,txtf_no_hp,txtf_email);
            whereid = (String)tb_anggota.getValueAt(getRow, 0);
        }else{
            setPanelUtama(false, "EDIT DATA", "#003366", 909, 100);
            JOptionPane.showMessageDialog(null, "Pilih dulu yang mau diedit");
        }
    }//GEN-LAST:event_lb_editMouseClicked

    private void lb_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_hapusMouseClicked
        setPanelUtama(false, "", "#fff", 909, 100);
        int getRow = tb_anggota.getSelectedRow();
        if(getRow >= 0){
            try {
                int pilih = JOptionPane.showConfirmDialog(null, "Hapus anggota ? ");
                switch(pilih){
                    case JOptionPane.YES_OPTION:
                        whereid = (String)tb_anggota.getValueAt(getRow, 0);
                        odata.delete_anggota(whereid);
                        JOptionPane.showMessageDialog(null, "Penghapusan berhasil");
                        dataMasukTabel("");        
                        break;
                    default:
                        break;
                }
                tb_anggota.clearSelection();
            } catch (SQLException ex) { JOptionPane.showMessageDialog(null, "Tidak dapat terhapus, "
                    + "anggota memiliki beberapa transaksi di koperasi ini");}
        }else{
            JOptionPane.showMessageDialog(null, "Pilih dulu yang mau dihapus");
        }
    }//GEN-LAST:event_lb_hapusMouseClicked

    private void txtf_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_cariKeyReleased
        dataMasukTabel(txtf_cari.getText());
    }//GEN-LAST:event_txtf_cariKeyReleased

    private void lb_sembunyikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_sembunyikanMouseClicked
        setPanelUtama(false, "", "#fff", 909, 100);
    }//GEN-LAST:event_lb_sembunyikanMouseClicked

    private void lb_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_homeMouseClicked
        APP.RMComp(this, new Home());
    }//GEN-LAST:event_lb_homeMouseClicked

    private void tb_anggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_anggotaMouseClicked
        if(tb_anggota.getSelectedRow() >= 0 && Const.AnggotaCRUD == "EDIT"){
            setPanelUtama(false, "", "#fff", 909, 100);
        }
    }//GEN-LAST:event_tb_anggotaMouseClicked

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        SimpanDataAnggota();
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void txtf_nikKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_nikKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_nikKeyTyped

    private void txtf_no_hpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_no_hpKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_no_hpKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_simpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmb_pekerjaan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdate_tgl_lahir;
    private javax.swing.JLabel lb_edit;
    private javax.swing.JLabel lb_hapus;
    private javax.swing.JLabel lb_home;
    private javax.swing.JLabel lb_sembunyikan;
    private javax.swing.JLabel lb_tambah;
    private javax.swing.JLabel lb_title_crud;
    private javax.swing.JPanel panel_crud;
    private javax.swing.JPanel panel_utama;
    private javax.swing.JRadioButton rb_l;
    private javax.swing.JRadioButton rb_p;
    private javax.swing.JTable tb_anggota;
    private javax.swing.JTextArea txta_alamat;
    private javax.swing.JTextField txtf_cari;
    private javax.swing.JTextField txtf_email;
    private javax.swing.JTextField txtf_id;
    private javax.swing.JTextField txtf_nama;
    private javax.swing.JTextField txtf_nik;
    private javax.swing.JTextField txtf_no_hp;
    // End of variables declaration//GEN-END:variables
}
