package koperasi;

import DB.konek;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import kelas.Const;
import kelas.operasiData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Transaksi_Operasi extends javax.swing.JPanel {

    DefaultTableModel model;
    private konek koneksi;
    private operasiData odata;
    private String whereid;
    private String tenorBuatPenanda;
    Date tanggal = new Date();
    
    public Transaksi_Operasi() {
        initComponents();
        setMain();
    }
    private void disableType(JDateChooser a){
        JTextFieldDateEditor ed = (JTextFieldDateEditor) a.getDateEditor();
        ed.setEditable(false);
    }
    private void setMain(){
        btn_edit.setVisible(true);
        lb_judul.setText(Const.transaksiMenu);
        setTable();
        dataMasukKeTabel("");
        jd_simpanan.setSize(new Dimension(650, 540));
        jd_simpanan.setTitle("Simpanan");
        jd_simpanan.setLocationRelativeTo(this);
        cmbJenisSimpanan();
        disableType(jdate_crud_tgl_simpanan);
        
        
        jd_pengeluaran.setSize(new Dimension(600, 440));
        jd_pengeluaran.setTitle("Pengeluaran");
        jd_pengeluaran.setLocationRelativeTo(this);
        txtf_crud_id_pengeluaran.setEnabled(false);
        disableType(jdate_crud_tgl_pengeluaran);
        
        
        jd_pinjaman.setSize(new Dimension(654, 630));
        jd_pinjaman.setTitle("Pinjaman");
        jd_pinjaman.setLocationRelativeTo(this);
        txtf_crud_id_pinjaman.setEnabled(false);
        cmbPinjaman();
        disableType(jdate_crud_tgl_pinjaman);
        
        
        jd_angsuran.setSize(new Dimension(654, 480));
        jd_angsuran.setTitle("Angsuran Pinjaman");
        jd_angsuran.setLocationRelativeTo(this);
        txtf_crud_id_angsuran.setEnabled(false);
        disableType(jdate_crud_tgl_angsuran);
        
        if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
            btn_edit.setVisible(false);
        }
    }
    private void setTable(){
        Object[] field = {};
        switch (Const.transaksiMenu) {
            case "SIMPANAN":
                field = new Object[]{"ID", "Id Anggota", "Nama Anggota", "Jumlah Simpan", "Jenis Simpan",
                    "Tanggal Simpan", "Keterangan"};
                break;
            case "PENGELUARAN":
                field = new Object[]{"ID", "Jumlah Pengeluaran", "Tanggal Pengeluaran", "Keterangan"};
                break;
            case "PINJAMAN":
                field = new Object[]{"ID", "Id Anggota", "Nama Anggota", "Jml Pinjam", "Tenor", "Bunga", "Angsuran",
                    "Jumlah Keseluruhan", "Tgl Pinjam", "Status", "Keterangan"};
                break;
            case "ANGSURAN PINJAMAN":
                field = new Object[]{"ID", "Id Pinjaman", "Nama Anggota", "Angsuran Ke", "Jml Angsuran", "Sisa Angsuran", 
                    "Tanggal Angsuran", "Denda", "Tanggal Jatuh Tempo", "Status"};
                break;
        }
        model = new DefaultTableModel(field, 0);
        tb_transaksi.setModel(model);
        tb_transaksi.setRowHeight(30);
    }
    private void dataMasukKeTabel(String key){
        odata = new operasiData();
        model.getDataVector().removeAllElements();
        ResultSet rs;
        String W = "";
        try {
            koneksi = new konek();
            switch (Const.transaksiMenu) {
                case "SIMPANAN":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '"+key+"' OR id_angg LIKE '"+key+"' OR nama_angg LIKE '"+key+"' OR jml_simpan LIKE '"+key+"' OR "
                                + "jns_simpan LIKE '"+key+"' OR tgl_simpan LIKE '"+key+"' OR ket LIKE '"+key+"'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM simpanan "+W+" ORDER BY id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
                            rs.getString(5), rs.getString(6), rs.getString(7)};
                        model.addRow(data);
                    }
                    break;
                case "PENGELUARAN":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR jml_pengl LIKE '%"+key+"%' OR ket LIKE '"+key+"'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM pengeluaran "+W+" ORDER BY id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                        model.addRow(data);
                    }
                    break;
                case "PINJAMAN":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR id_angg LIKE '%"+key+"%' OR nama_angg LIKE '%"+key+"%' OR "
                                + "jml_pinjam LIKE '%"+key+"%' OR tenor LIKE '%"+key+"%' OR bunga LIKE '%"+key+"%' OR "
                                + "angsuran LIKE '%"+key+"%' OR jml_keseluruhan LIKE '%"+key+"%' OR tgl_pinjam LIKE '%"+key+"%' OR status_pinjam LIKE '%"+key+"%' OR "
                                + "ket LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM pinjaman "+W+" ORDER BY id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
                            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)};
                        model.addRow(data);
                    }
                    break;
                case "ANGSURAN PINJAMAN":
                    if(!key.equals("")){
                        W = "WHERE id LIKE '%"+key+"%' OR id_pinjam LIKE '%"+key+"%' OR nama_angg LIKE '%"+key+"%' OR "
                                + "angsur_ke LIKE '%"+key+"%' OR jml_angsur LIKE '%"+key+"%' OR sisa_angsur LIKE '%"+key+"%' OR "
                                + "tgl_angsur LIKE '%"+key+"%' OR denda LIKE '%"+key+"%' OR tgl_tempo LIKE '%"+key+"%' OR status_angsur LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM angsuran_pinjaman "+W+" ORDER BY status_angsur, id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
                            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)};
                        model.addRow(data);
                    }
                    break;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "MDO_inData : "+e);
        }
    }
    //KHUSUSON UNTUK FUNCTION SIMPANAN
    private void CRUDSimpanan_Simpan(){
        odata = new operasiData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String id = txtf_crud_id_simpanan.getText();
        String id_angg = txtf_crud_id_angg_simpanan.getText();
        String nama_angg = txtf_crud_nama_angg_simpanan.getText();
        String jml_simpan = txtf_crud_jml_simpanan.getText();
        String jns_simpan = (String)cmb_crud_jenis_simpanan.getSelectedItem();
        String tgl_simpan = format.format(jdate_crud_tgl_simpanan.getDate());
        String ket = txta_crud_ket_simpanan.getText();
        
        try {
            if(id.equals("") || id_angg.equals("") || nama_angg.equals("") || 
                    jml_simpan.equals("") || jns_simpan.equals("Pilih Jenis Simpan") ||
                    ket.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data simpanan !");
            }else{
                //AFTER SUBSTRING BLABLABLA
                //jns_simpan = jns_simpan.substring(jns_simpan.lastIndexOf("-")+1);
                //BEFORE SUBSTRING BLABLABLA
                jns_simpan = jns_simpan.substring(0, jns_simpan.indexOf("-"));
                
                switch(Const.transaksiCRUD){
                    case "TAMBAH":
                        odata.insert_transaksi_simpanan(id, id_angg, nama_angg, jml_simpan, jns_simpan, tgl_simpan, ket);
                        break;
                    case "EDIT":
                        odata.update_transaksi_simpanan(whereid, id, id_angg, nama_angg, jml_simpan, jns_simpan, tgl_simpan, ket);
                        break;
                    case "HAPUS":
                        odata.delete_transaksi_simpanan(whereid);
                        break;
                }
                jd_simpanan.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_transaksi.clearSelection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "CRUDSimpanan_Simpan : "+e);
        }
    }
    private void tampilDataUbahSimpanan(int i, int row, JTextField... f){
        Object getValue;
        for (JTextField tf : f) {
            if(i!=4 || i!=5 || i!=6){
                getValue = tb_transaksi.getValueAt(row, i);
                tf.setText((String) getValue);
                i++;
            }
        }
        try {
            String id_jns_simpan = (String)tb_transaksi.getValueAt(row, 4);
            ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM jenis_simpanan WHERE id='"+id_jns_simpan+"'");
            if(rs.next()){ cmb_crud_jenis_simpanan.setSelectedItem(id_jns_simpan+"-"+rs.getString(2)); }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) tb_transaksi.getValueAt(row, 5));
            jdate_crud_tgl_simpanan.setDate(date);
            txta_crud_ket_simpanan.setText((String)tb_transaksi.getValueAt(row, 6));
        } catch (SQLException | ParseException ex) { }
        
    }
    private void resetFieldsSimpanan(JTextField... f){
        for (JTextField tf : f) {
            tf.setText("");
        }
        txta_crud_ket_simpanan.setText("");
        cmb_crud_jenis_simpanan.setSelectedIndex(0);
    }
    private void setEnableFieldSimpanan(boolean a,boolean b,boolean c,boolean d,boolean e,boolean f,boolean g,boolean  h){
        txtf_crud_id_simpanan.setEnabled(a);
        txtf_crud_id_angg_simpanan.setEnabled(b);
        txtf_crud_nama_angg_simpanan.setEnabled(c);
        txtf_crud_jml_simpanan.setEnabled(d);
        cmb_crud_jenis_simpanan.setEnabled(e);
        jdate_crud_tgl_simpanan.setEnabled(f);
        txta_crud_ket_simpanan.setEnabled(g);
        btn_crud_cari_angg.setEnabled(h);
    }
    public void cmbJenisSimpanan(){
        cmb_crud_jenis_simpanan.removeAllItems();
        cmb_crud_jenis_simpanan.addItem("Pilih Jenis Simpan");
        try {
            koneksi = new konek();
            ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM jenis_simpanan");
            while(rs.next()){
                cmb_crud_jenis_simpanan.addItem(rs.getString(1)+"-"+rs.getString(2));
            }
        } catch (SQLException e) { }
    }
    //KHUSUSON UNTUK FUNCTION PENGELUARAN
    private void CRUDPengeluaran_Simpan(){
        odata = new operasiData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String id = txtf_crud_id_pengeluaran.getText();
        String jml_pengeluaran = txtf_crud_jml_pengeluaran.getText();
        String tgl_pengeluaran = format.format(jdate_crud_tgl_pengeluaran.getDate());
        String ket = txta_crud_ket_pengeluaran.getText();
        
        try {
            if(id.equals("") || jml_pengeluaran.equals("") || ket.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data pengeluaran !");
            }else{
                switch(Const.transaksiCRUD){
                    case "TAMBAH":
                        odata.insert_transaksi_pengeluaran(id, jml_pengeluaran, tgl_pengeluaran, ket);
                        break;
                    case "EDIT":
                        odata.update_transaksi_pengeluaran(whereid, id, jml_pengeluaran, tgl_pengeluaran, ket);
                        break;
                    case "HAPUS":
                        odata.delete_transaksi_pengeluaran(whereid);
                        break;
                }
                jd_pengeluaran.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_transaksi.clearSelection();
        } catch (SQLException  e) {
            JOptionPane.showMessageDialog(null, "transaksiPengeluaran_click : "+e);
        }
    }
    private void setEnableFieldPengeluaran(boolean a,boolean b,boolean c){
        txtf_crud_id_pengeluaran.setEnabled(a);
        txtf_crud_jml_pengeluaran.setEnabled(b);
        txta_crud_ket_pengeluaran.setEnabled(c);
    }
    private void resetFieldsPengeluaran(JTextField... f){
        for (JTextField tf : f) {
            tf.setText("");
        }
        txta_crud_ket_pengeluaran.setText("");
    }
    private void tampilDataUbahPengeluaran(int i, int row, JTextField... f){
        Object getValue;
        for (JTextField tf : f) {
            if(i!=2 || i!=3){
                getValue = tb_transaksi.getValueAt(row, i);
                tf.setText((String) getValue);
                i++;
            }
        }
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) tb_transaksi.getValueAt(row, 2));
            jdate_crud_tgl_pengeluaran.setDate(date);
            txta_crud_ket_pengeluaran.setText((String)tb_transaksi.getValueAt(row, 3));
        } catch (ParseException ex) { }
        
    }
    //KHUSUSON UNTUK FUNCTION PINJAMAN
    private void CRUDPinjaman_Simpan(){
        odata = new operasiData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String id = txtf_crud_id_pinjaman.getText();
        String id_angg = txtf_crud_id_angg_pinjaman.getText();
        String nama_angg = txtf_crud_nama_angg_pinjaman.getText();
        String jml_pinjam = txtf_crud_jml_pinjaman.getText();
        String tenor = (String)cmb_crud_tenor_pinjaman.getSelectedItem();
        Float bunga = Float.parseFloat(txtf_crud_bunga_pinjaman.getText());
        String angsuran = txtf_crud_angsur_pinjaman.getText();
        String jml_k = txtf_crud_jml_keseluruhan_pinjaman.getText();
        String tgl_pinjam = format.format(jdate_crud_tgl_pinjaman.getDate());
        String ket = txta_crud_ket_pinjaman.getText();
        try {
            if(id.equals("") || id_angg.equals("") || nama_angg.equals("") || jml_pinjam.equals("") || 
                    tenor.equals("") || bunga==0 || angsuran.equals("") || tgl_pinjam.equals("") || ket.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data pinjaman !");
            }else{
                tenor = tenor.substring(0, tenor.indexOf("-"));
                String subCombo = (String)cmb_crud_tenor_pinjaman.getSelectedItem();
                subCombo = subCombo.substring(subCombo.lastIndexOf("-")+1);
                int jmlPinjamWithBunga = (Integer.parseInt(angsuran)*Integer.parseInt(subCombo));
                
                switch(Const.transaksiCRUD){
                    case "TAMBAH":
                        odata.insert_transaksi_pinjaman(id, id_angg, nama_angg, jml_pinjam, tenor, bunga, 
                                angsuran, jml_k, tgl_pinjam, ket);
                        break;
                    case "EDIT":
                        odata.update_transaksi_pinjaman(whereid, id, id_angg, nama_angg, jml_pinjam, tenor, bunga, 
                                angsuran, jml_k, tgl_pinjam, ket);
                        break;
                    case "HAPUS":
                        odata.delete_transaksi_pinjaman_with_angsuran_pinjaman(whereid);
                        odata.delete_transaksi_pinjaman(whereid);
                        break; 
                }
                jd_pinjaman.setVisible(false);
            }
            dataMasukKeTabel("");
            tb_transaksi.clearSelection();
        } catch (SQLException  e) {
            JOptionPane.showMessageDialog(null, "transaksiPinjaman_click : "+e);
        }
    }
    public void cmbPinjaman(){
        cmb_crud_tenor_pinjaman.removeAllItems();
        cmb_crud_tenor_pinjaman.addItem("Pilih Tenor");
        try {
            koneksi = new konek();
            ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM tenor");
            while(rs.next()){
                cmb_crud_tenor_pinjaman.addItem(rs.getString(1)+"-"+rs.getString(2));
            }
        } catch (SQLException e) { }
    }
    private void setEnableFieldPinjaman(boolean a,boolean b,boolean c,boolean d,boolean e,boolean f){
        txtf_crud_id_pinjaman.setEnabled(a);
        txtf_crud_jml_pinjaman.setEnabled(b);
        cmb_crud_tenor_pinjaman.setEnabled(c);
        txtf_crud_bunga_pinjaman.setEnabled(d);
        txta_crud_ket_pinjaman.setEnabled(e);
        btn_crud_cari_angg_pinjaman.setEnabled(f);
    }
    private void resetFieldsPinjaman(JTextField... f){
        for (JTextField tf : f) {
            tf.setText("");
        }
        txta_crud_ket_pinjaman.setText("");
        cmb_crud_tenor_pinjaman.setSelectedIndex(0);
    }
    private void tampilDataUbahPinjaman(int i, int row, JTextField... f){
        f[0].setText((String)tb_transaksi.getValueAt(row, 0));
        f[1].setText((String)tb_transaksi.getValueAt(row, 1));
        f[2].setText((String)tb_transaksi.getValueAt(row, 2));
        f[3].setText((String)tb_transaksi.getValueAt(row, 3));
        f[4].setText((String)tb_transaksi.getValueAt(row, 5));
        f[5].setText((String)tb_transaksi.getValueAt(row, 6));
        f[6].setText((String)tb_transaksi.getValueAt(row, 7));
        try {
            String id_tenor = (String)tb_transaksi.getValueAt(row, 4);
            ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM tenor WHERE id='"+id_tenor+"'");
            if(rs.next()){ cmb_crud_tenor_pinjaman.setSelectedItem(id_tenor+"-"+rs.getString(2)); }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) tb_transaksi.getValueAt(row, 8));
            jdate_crud_tgl_pinjaman.setDate(date);
            txta_crud_ket_pinjaman.setText((String)tb_transaksi.getValueAt(row, 10));
            
        } catch (SQLException | ParseException ex) { }
        
    }
    
    
    
    
    //KHUSUSON UNTUK FUNCTION ANGSURAN PINJAMAN
    private void CRUDAngsuran_pinjaman_Simpan(){
        odata = new operasiData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String id = txtf_crud_id_angsuran.getText();
        String id_pinjam = txtf_crud_id_pinjaman_angsuran.getText();
        String nama_angg = txtf_crud_nama_angg_angsuran.getText();
        String angsur_ke = txtf_crud_angsuran_ke.getText();
        String jml_angsur = txtf_crud_jml_angsuran.getText();
        String sisa_angsur = txtf_crud_sisa_angsuran.getText();
        String tgl_angsur = format.format(jdate_crud_tgl_angsuran.getDate());
        String tgl_japo = txtf_crud_tgl_japo_angsuran.getText();
        String denda = txtf_crud_denda_angsuran.getText();
        Map<String, Object> id_angsuran = new HashMap<>();
        try {
            if(id.equals("") || id_pinjam.equals("") || nama_angg.equals("") || angsur_ke.equals("") || 
                    jml_angsur.equals("") || tgl_angsur==null || sisa_angsur.equals("") || 
                    tgl_japo.equals("") || denda.equals("")){
                JOptionPane.showMessageDialog(null, "Lengkapi data Angsuran Pinjaman !");
            }else{
                switch(Const.transaksiCRUD){
                    case "TAMBAH":
                        odata.lunas_transaksi_angsuran_pinjaman(id_pinjam);
                        odata.insert_transaksi_angsuran_pinjaman(id, id_pinjam, nama_angg, angsur_ke, jml_angsur, 
                                sisa_angsur, tgl_angsur, denda, tgl_japo);
                        if(tenorBuatPenanda.equals(angsur_ke) && sisa_angsur.equals("0")){
                            odata.lunas_transaksi_angsuran_pinjaman(id_pinjam);
                            odata.lunas_transaksi_pinjaman(id_pinjam,"Lunas");
                        }
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Cetak kuitansi ? ", 
                            "Kuitansi", JOptionPane.OK_CANCEL_OPTION);
                        if(dialogResult == JOptionPane.OK_OPTION){
                            JOptionPane.showMessageDialog(null, "Tunggu Sebentar");
                            id_angsuran.put("id", id);
                            ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\kuitansi\\angsuran.jrxml", 
                                     id_angsuran, "Kuitansi Angsuran");
                        }
                        break;
                    case "HAPUS":
                        odata.delete_transaksi_angsuran_pinjaman(whereid);
                        odata.lunas_transaksi_pinjaman(id_pinjam,"Berjalan");
                        odata.max_transaksi_angsuran_pinjaman(id_pinjam);
                        break;
                }
                jd_angsuran.setVisible(false);
            }
        } catch (SQLException  e) {
            JOptionPane.showMessageDialog(null, "MDO_CRUDTenor_click : "+e);
        }
        dataMasukKeTabel("");
        tb_transaksi.clearSelection();
    }
    private void setEnableFieldAngsuran(boolean a,boolean b,boolean c,boolean d,boolean e){
        txtf_crud_id_angsuran.setEnabled(a);
        txtf_crud_angsuran_ke.setEnabled(b);
        txtf_crud_jml_angsuran.setEnabled(c);
        txtf_crud_sisa_angsuran.setEnabled(d);
        btn_crud_cari_id_angg_angsuran.setEnabled(e);
    }
    private void resetFieldsAngsuran(JTextField... f){
        for (JTextField tf : f) {
            tf.setText("");
        }
    }
    private void tampilDataUbahAngsuran(int i, int row, JTextField... f){
        f[0].setText((String)tb_transaksi.getValueAt(row, 0));
        f[1].setText((String)tb_transaksi.getValueAt(row, 1));
        f[2].setText((String)tb_transaksi.getValueAt(row, 2));
        f[3].setText((String)tb_transaksi.getValueAt(row, 3));
        f[4].setText((String)tb_transaksi.getValueAt(row, 4));
        f[5].setText((String)tb_transaksi.getValueAt(row, 5));
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) tb_transaksi.getValueAt(row, 6));
            jdate_crud_tgl_angsuran.setDate(date);
            txtf_crud_denda_angsuran.setText((String)tb_transaksi.getValueAt(row, 7));
            txtf_crud_tgl_japo_angsuran.setText((String)tb_transaksi.getValueAt(row, 8));
        } catch (ParseException ex) { }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_simpanan = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        lb_crud_title_simpanan = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtf_crud_id_simpanan = new javax.swing.JTextField();
        txtf_crud_id_angg_simpanan = new javax.swing.JTextField();
        btn_crud_simpan_simpanan = new javax.swing.JButton();
        txtf_crud_nama_angg_simpanan = new javax.swing.JTextField();
        btn_crud_cari_angg = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtf_crud_jml_simpanan = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmb_crud_jenis_simpanan = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jdate_crud_tgl_simpanan = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txta_crud_ket_simpanan = new javax.swing.JTextArea();
        jd_pengeluaran = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        lb_crud_title_pengeluaran = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtf_crud_id_pengeluaran = new javax.swing.JTextField();
        txtf_crud_jml_pengeluaran = new javax.swing.JTextField();
        btn_crud_simpan_pengeluaran = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txta_crud_ket_pengeluaran = new javax.swing.JTextArea();
        jdate_crud_tgl_pengeluaran = new com.toedter.calendar.JDateChooser();
        jd_pinjaman = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        lb_crud_title_pinjaman = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtf_crud_id_pinjaman = new javax.swing.JTextField();
        txtf_crud_jml_pinjaman = new javax.swing.JTextField();
        btn_crud_simpan_pinjaman = new javax.swing.JButton();
        txtf_crud_id_angg_pinjaman = new javax.swing.JTextField();
        txtf_crud_nama_angg_pinjaman = new javax.swing.JTextField();
        btn_crud_cari_angg_pinjaman = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cmb_crud_tenor_pinjaman = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtf_crud_bunga_pinjaman = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtf_crud_angsur_pinjaman = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jdate_crud_tgl_pinjaman = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txta_crud_ket_pinjaman = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        txtf_crud_jml_keseluruhan_pinjaman = new javax.swing.JTextField();
        jd_angsuran = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        lb_crud_title_angsuran = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtf_crud_id_angsuran = new javax.swing.JTextField();
        txtf_crud_id_pinjaman_angsuran = new javax.swing.JTextField();
        btn_crud_simpan_angsuran = new javax.swing.JButton();
        txtf_crud_nama_angg_angsuran = new javax.swing.JTextField();
        btn_crud_cari_id_angg_angsuran = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        txtf_crud_angsuran_ke = new javax.swing.JTextField();
        txtf_crud_jml_angsuran = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jdate_crud_tgl_angsuran = new com.toedter.calendar.JDateChooser();
        txtf_crud_sisa_angsuran = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtf_crud_tgl_japo_angsuran = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtf_crud_denda_angsuran = new javax.swing.JTextField();
        frame_table_cari = new javax.swing.JFrame();
        jPanel10 = new javax.swing.JPanel();
        txtf_frameCariAnggota = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lb_judul = new javax.swing.JLabel();
        Tambah = new javax.swing.JLabel();
        btn_edit = new javax.swing.JLabel();
        btn_hapus = new javax.swing.JLabel();
        txtf_Cari = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btn_cetak = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_transaksi = new javax.swing.JTable();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_simpanan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_simpanan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_simpanan.setText("TITLE JD");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_simpanan, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_simpanan, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_simpanan.getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("ID");

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText(" ANGGOTA");

        txtf_crud_id_angg_simpanan.setEditable(false);

        btn_crud_simpan_simpanan.setText("SIMPAN");
        btn_crud_simpan_simpanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_simpananActionPerformed(evt);
            }
        });

        txtf_crud_nama_angg_simpanan.setEditable(false);

        btn_crud_cari_angg.setText("...");
        btn_crud_cari_angg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_cari_anggActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("JUMLAH SIMPANAN");

        txtf_crud_jml_simpanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_crud_jml_simpananKeyTyped(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(51, 51, 51));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("RP. ");

        jLabel11.setBackground(new java.awt.Color(51, 51, 51));
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("JENIS SIMPANAN");

        cmb_crud_jenis_simpanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setBackground(new java.awt.Color(51, 51, 51));
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("TANGGAL SIMPAN");

        jLabel14.setBackground(new java.awt.Color(51, 51, 51));
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("KETERANGAN");

        txta_crud_ket_simpanan.setColumns(20);
        txta_crud_ket_simpanan.setRows(5);
        jScrollPane2.setViewportView(txta_crud_ket_simpanan);

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
                        .addComponent(txtf_crud_id_simpanan))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(txtf_crud_id_angg_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtf_crud_nama_angg_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_crud_cari_angg, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtf_crud_jml_simpanan))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_crud_simpan_simpanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jdate_crud_tgl_simpanan, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                                    .addComponent(cmb_crud_jenis_simpanan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_id_simpanan, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtf_crud_id_angg_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtf_crud_nama_angg_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_crud_cari_angg, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_jml_simpanan)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_crud_jenis_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdate_crud_tgl_simpanan, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btn_crud_simpan_simpanan, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_simpanan.getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_pengeluaran.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_pengeluaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_pengeluaran.setText("TITLE JD");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_pengeluaran.getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("ID ");

        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("JUMLAH PENGELUARAN");

        txtf_crud_jml_pengeluaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_crud_jml_pengeluaranKeyTyped(evt);
            }
        });

        btn_crud_simpan_pengeluaran.setText("SIMPAN");
        btn_crud_simpan_pengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_pengeluaranActionPerformed(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("TANGGAL PENGELUARAN");

        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("KETERANGAN");

        txta_crud_ket_pengeluaran.setColumns(20);
        txta_crud_ket_pengeluaran.setRows(5);
        jScrollPane4.setViewportView(txta_crud_ket_pengeluaran);

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
                        .addComponent(txtf_crud_id_pengeluaran))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_jml_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdate_crud_tgl_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_id_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_jml_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jdate_crud_tgl_pengeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_crud_simpan_pengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jd_pengeluaran.getContentPane().add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_pinjaman.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_pinjaman.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_pinjaman.setText("TITLE JD");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_pinjaman.getContentPane().add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("ID ");

        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("JUMLAH PINJAMAN");

        txtf_crud_jml_pinjaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtf_crud_jml_pinjamanKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_crud_jml_pinjamanKeyTyped(evt);
            }
        });

        btn_crud_simpan_pinjaman.setText("SIMPAN");
        btn_crud_simpan_pinjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_pinjamanActionPerformed(evt);
            }
        });

        txtf_crud_id_angg_pinjaman.setEditable(false);

        txtf_crud_nama_angg_pinjaman.setEditable(false);

        btn_crud_cari_angg_pinjaman.setText("...");
        btn_crud_cari_angg_pinjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_cari_angg_pinjamanActionPerformed(evt);
            }
        });

        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText(" ANGGOTA");

        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("TENOR");

        cmb_crud_tenor_pinjaman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_crud_tenor_pinjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_crud_tenor_pinjamanActionPerformed(evt);
            }
        });

        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("BUNGA");

        txtf_crud_bunga_pinjaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtf_crud_bunga_pinjamanKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtf_crud_bunga_pinjamanKeyTyped(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("%");

        txtf_crud_angsur_pinjaman.setEditable(false);

        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("ANGSURAN PINJAMAN");

        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("TANGGAL PINJAMAN");

        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("KETERANGAN");

        txta_crud_ket_pinjaman.setColumns(20);
        txta_crud_ket_pinjaman.setRows(5);
        jScrollPane5.setViewportView(txta_crud_ket_pinjaman);

        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("JUMLAH KESELURUHAN");

        txtf_crud_jml_keseluruhan_pinjaman.setEditable(false);

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
                        .addComponent(txtf_crud_id_pinjaman))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_jml_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(txtf_crud_id_angg_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtf_crud_nama_angg_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_crud_cari_angg_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmb_crud_tenor_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_bunga_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_angsur_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdate_crud_tgl_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtf_crud_jml_keseluruhan_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_id_pinjaman, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtf_crud_id_angg_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtf_crud_nama_angg_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_crud_cari_angg_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_jml_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmb_crud_tenor_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtf_crud_bunga_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_angsur_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_jml_keseluruhan_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdate_crud_tgl_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_crud_simpan_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jd_pinjaman.getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(637, 70));

        lb_crud_title_angsuran.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lb_crud_title_angsuran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_crud_title_angsuran.setText("TITLE JD");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_crud_title_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jd_angsuran.getContentPane().add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("ID ");

        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("PINJAMAN");

        txtf_crud_id_pinjaman_angsuran.setEditable(false);
        txtf_crud_id_pinjaman_angsuran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtf_crud_id_pinjaman_angsuranKeyReleased(evt);
            }
        });

        btn_crud_simpan_angsuran.setText("SIMPAN");
        btn_crud_simpan_angsuran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_simpan_angsuranActionPerformed(evt);
            }
        });

        txtf_crud_nama_angg_angsuran.setEditable(false);

        btn_crud_cari_id_angg_angsuran.setText("...");
        btn_crud_cari_id_angg_angsuran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_crud_cari_id_angg_angsuranActionPerformed(evt);
            }
        });

        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("ANGSURAN KE");

        txtf_crud_angsuran_ke.setEditable(false);

        txtf_crud_jml_angsuran.setEditable(false);

        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("JUMLAH ANGSUR");

        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("TANGGAL ANGSURAN");

        jdate_crud_tgl_angsuran.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdate_crud_tgl_angsuranPropertyChange(evt);
            }
        });

        txtf_crud_sisa_angsuran.setEditable(false);

        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("SISA ANGSURAN");

        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText("TANGGAL JATUH TEMPO");

        txtf_crud_tgl_japo_angsuran.setEditable(false);

        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("DENDA BERAPA HARI");

        txtf_crud_denda_angsuran.setEditable(false);
        txtf_crud_denda_angsuran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtf_crud_denda_angsuranMouseClicked(evt);
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
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_id_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_crud_simpan_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtf_crud_id_pinjaman_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtf_crud_nama_angg_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_crud_cari_id_angg_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_angsuran_ke, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtf_crud_jml_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtf_crud_sisa_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdate_crud_tgl_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(txtf_crud_tgl_japo_angsuran))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtf_crud_denda_angsuran))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_id_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_id_pinjaman_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtf_crud_nama_angg_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_crud_cari_id_angg_angsuran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtf_crud_angsuran_ke, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtf_crud_jml_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtf_crud_sisa_angsuran)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdate_crud_tgl_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtf_crud_tgl_japo_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtf_crud_denda_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(btn_crud_simpan_angsuran, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );

        jd_angsuran.getContentPane().add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel10.setPreferredSize(new java.awt.Dimension(752, 50));

        txtf_frameCariAnggota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtf_frameCariAnggotaKeyReleased(evt);
            }
        });

        jLabel15.setText("CARI");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(477, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtf_frameCariAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtf_frameCariAnggota, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        frame_table_cari.getContentPane().add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        frame_table_cari.getContentPane().add(jScrollPane3, java.awt.BorderLayout.CENTER);

        setPreferredSize(new java.awt.Dimension(848, 610));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(848, 80));

        lb_judul.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_judul.setForeground(new java.awt.Color(51, 51, 51));
        lb_judul.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_judul.setText("TITLE");
        lb_judul.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        Tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_list_45px.png"))); // NOI18N
        Tambah.setText(" TAMBAH");
        Tambah.setPreferredSize(new java.awt.Dimension(60, 60));
        Tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambahMouseClicked(evt);
            }
        });

        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_45px.png"))); // NOI18N
        btn_edit.setText(" EDIT");
        btn_edit.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_editMouseClicked(evt);
            }
        });

        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_delete_45px.png"))); // NOI18N
        btn_hapus.setText(" HAPUS");
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

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_45px_1.png"))); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(60, 60));

        btn_cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_print_45px.png"))); // NOI18N
        btn_cetak.setText("CETAK KWITANSI");
        btn_cetak.setPreferredSize(new java.awt.Dimension(60, 60));
        btn_cetak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cetakMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_judul, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
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
                                .addComponent(txtf_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_cetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        tb_transaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tb_transaksi);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtf_CariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_CariKeyReleased
        String getValueSearch = txtf_Cari.getText();
        dataMasukKeTabel(getValueSearch); 
    }//GEN-LAST:event_txtf_CariKeyReleased

    private void TambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahMouseClicked
        Const.transaksiCRUD = "TAMBAH";
        odata = new operasiData();
        
        jdate_crud_tgl_simpanan.setDate(tanggal);
        lb_crud_title_simpanan.setText("TAMBAH SIMPANAN");
        setEnableFieldSimpanan(false, false, false, true, true, true, true, true);
        resetFieldsSimpanan(txtf_crud_id_simpanan, txtf_crud_id_angg_simpanan, txtf_crud_nama_angg_simpanan, 
                txtf_crud_jml_simpanan);
        
        jdate_crud_tgl_pengeluaran.setDate(tanggal);
        lb_crud_title_pengeluaran.setText("TAMBAH PENGELUARAN");
        setEnableFieldPengeluaran(false, true, true);
        resetFieldsPengeluaran(txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
        
        jdate_crud_tgl_pinjaman.setDate(tanggal);
        lb_crud_title_pinjaman.setText("TAMBAH PINJAMAN");
        setEnableFieldPinjaman(false, true, true, true, true, true);
        resetFieldsPinjaman(txtf_crud_id_pinjaman, txtf_crud_id_angg_pinjaman, txtf_crud_nama_angg_pinjaman, 
                txtf_crud_jml_pinjaman, txtf_crud_bunga_pinjaman, txtf_crud_angsur_pinjaman);
        
        jdate_crud_tgl_angsuran.setDate(tanggal);
        lb_crud_title_angsuran.setText("TAMBAH ANGSURAN");
        setEnableFieldAngsuran(false, false, false, false, true);
        resetFieldsAngsuran(txtf_crud_id_angsuran, txtf_crud_id_pinjaman_angsuran, txtf_crud_nama_angg_angsuran, 
                txtf_crud_angsuran_ke, txtf_crud_jml_angsuran, txtf_crud_sisa_angsuran, 
                txtf_crud_tgl_japo_angsuran, txtf_crud_denda_angsuran);
        
        try {
            if(Const.transaksiMenu.equals("SIMPANAN")){
                txtf_crud_id_simpanan.setText(odata.getIdSubstringTransaksi("simpanan","SMP"));
                jd_simpanan.setVisible(true);
            }else if(Const.transaksiMenu.equals("PENGELUARAN")){
                txtf_crud_id_pengeluaran.setText(odata.getIdSubstringTransaksi("pengeluaran","PNG"));
                jd_pengeluaran.setVisible(true);
            }else if(Const.transaksiMenu.equals("PINJAMAN")){
                jd_pinjaman.setVisible(true);
                txtf_crud_id_pinjaman.setText(odata.getIdSubstringTransaksi("pinjaman","PJM"));
            }else if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
                jd_angsuran.setVisible(true);
                txtf_crud_id_angsuran.setText(""+odata.getId("angsuran_pinjaman"));
            }
        } catch (SQLException e) {
        }
        
    }//GEN-LAST:event_TambahMouseClicked

    private void btn_crud_simpan_simpananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_simpananActionPerformed
        CRUDSimpanan_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_simpananActionPerformed

    private void btn_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editMouseClicked
        Const.transaksiCRUD = "EDIT";
        odata = new operasiData();
        int getRow = tb_transaksi.getSelectedRow();
        
        //SIMPANAN
        resetFieldsSimpanan(txtf_crud_id_simpanan, txtf_crud_id_angg_simpanan, txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
        lb_crud_title_simpanan.setText("UBAH SIMPANAN");
        setEnableFieldSimpanan(true, false, false, true, true, true, true, true);
        
        //PENGELUARAN
        resetFieldsPengeluaran(txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
        lb_crud_title_pengeluaran.setText("UBAH PENGELUARAN");
        setEnableFieldPengeluaran(true, true, true);
        
        //PINJAMAN
        resetFieldsPinjaman(txtf_crud_id_pinjaman, txtf_crud_id_angg_pinjaman, txtf_crud_nama_angg_pinjaman, 
                txtf_crud_jml_pinjaman, txtf_crud_bunga_pinjaman, txtf_crud_angsur_pinjaman);
        lb_crud_title_pinjaman.setText("UBAH PINJAMAN");
        setEnableFieldPinjaman(true, true, true, true, true, true);
        
        if(Const.transaksiMenu.equals("SIMPANAN")){
            if(getRow >= 0){
                jd_simpanan.setVisible(true);
                tampilDataUbahSimpanan(0, getRow, txtf_crud_id_simpanan, txtf_crud_id_angg_simpanan, 
                        txtf_crud_nama_angg_simpanan, txtf_crud_jml_simpanan);
                whereid = (String)tb_transaksi.getValueAt(getRow, 0);
            }
        }else if(Const.transaksiMenu.equals("PENGELUARAN")){
            if(getRow >= 0){
                jd_pengeluaran.setVisible(true);
                tampilDataUbahPengeluaran(0, getRow, txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
                whereid = (String)tb_transaksi.getValueAt(getRow, 0);
            }
        }else if(Const.transaksiMenu.equals("PINJAMAN")){
            if(getRow >= 0){
                if(odata.cariAPUntukUbahP((String)tb_transaksi.getValueAt(getRow, 0)).equals("")){
                     jd_pinjaman.setVisible(true);
                     tampilDataUbahPinjaman(0, getRow, txtf_crud_id_pinjaman, txtf_crud_id_angg_pinjaman, txtf_crud_nama_angg_pinjaman, 
                        txtf_crud_jml_pinjaman, txtf_crud_bunga_pinjaman, txtf_crud_angsur_pinjaman, txtf_crud_jml_keseluruhan_pinjaman);
                    whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                }else{
                    JOptionPane.showMessageDialog(null, "Tidak dapat diedit, Pinjaman sudah memiliki pembayaran angsuran");
                }
            }
        }
        if(getRow < 0){JOptionPane.showMessageDialog(null, "pilih data yg dingin diubah");}
    }//GEN-LAST:event_btn_editMouseClicked

    private void btn_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_hapusMouseClicked
        Const.transaksiCRUD = "HAPUS";
        int getRow = tb_transaksi.getSelectedRow();
        
        //SIMPANAN
        resetFieldsSimpanan(txtf_crud_id_simpanan, txtf_crud_id_angg_simpanan, txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
        lb_crud_title_simpanan.setText("BENERAN HAPUS SIMPANAN ?");
        setEnableFieldSimpanan(false, false, false, false, false, false, false, false);
        btn_crud_simpan_simpanan.setText("OK");
        
        //PENGELUARAN
        resetFieldsPengeluaran(txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
        lb_crud_title_pengeluaran.setText("BENERAN HAPUS DATA PENGELUARAN ? ");
        setEnableFieldPengeluaran(false, false, false);
        btn_crud_simpan_pengeluaran.setText("OK");
        
        //PINJAMAN
        resetFieldsAngsuran(txtf_crud_id_pinjaman, txtf_crud_id_angg_pinjaman, txtf_crud_nama_angg_pinjaman, 
                txtf_crud_jml_pinjaman, txtf_crud_bunga_pinjaman, txtf_crud_angsur_pinjaman);
        lb_crud_title_pinjaman.setText("BENERAN HAPUS DATA PINJAMAN ?");
        setEnableFieldPinjaman(false, false, false, false, false, false);
        btn_crud_simpan_pinjaman.setText("OK");
        
        //ANGSURAN PINJAMAN
        resetFieldsAngsuran(txtf_crud_id_angsuran, txtf_crud_id_pinjaman_angsuran, txtf_crud_nama_angg_angsuran, 
                txtf_crud_angsuran_ke, txtf_crud_jml_angsuran, txtf_crud_sisa_angsuran,
                txtf_crud_tgl_japo_angsuran, txtf_crud_denda_angsuran);
        lb_crud_title_angsuran.setText("BENERAN HAPUS DATA ANGSURAN PINJAMAN ?");
        setEnableFieldAngsuran(false, false, false, false,false);
        btn_crud_simpan_angsuran.setText("OK");
        
        if(Const.transaksiMenu.equals("SIMPANAN")){
            if(getRow >= 0){
                jd_simpanan.setVisible(true);
                tampilDataUbahSimpanan(0, getRow, txtf_crud_id_simpanan, txtf_crud_id_angg_simpanan, 
                        txtf_crud_nama_angg_simpanan, txtf_crud_jml_simpanan);
                whereid = (String)tb_transaksi.getValueAt(getRow, 0);
            }
        }else if(Const.transaksiMenu.equals("PENGELUARAN")){
            if(getRow >= 0){
                jd_pengeluaran.setVisible(true);
                tampilDataUbahPengeluaran(0, getRow, txtf_crud_id_pengeluaran, txtf_crud_jml_pengeluaran);
                whereid = (String)tb_transaksi.getValueAt(getRow, 0);
            }
        }else if(Const.transaksiMenu.equals("PINJAMAN")){
            if(getRow >= 0){
                if(odata.cariAPUntukUbahP((String)tb_transaksi.getValueAt(getRow, 0)).equals("")){
                    jd_pinjaman.setVisible(true);
                    tampilDataUbahPinjaman(0, getRow, txtf_crud_id_pinjaman, txtf_crud_id_angg_pinjaman, txtf_crud_nama_angg_pinjaman, 
                        txtf_crud_jml_pinjaman, txtf_crud_bunga_pinjaman, txtf_crud_angsur_pinjaman, txtf_crud_jml_keseluruhan_pinjaman);
                    whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                }else{
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Pinjaman sudah memiliki pembayaran angsuran, "
                            + "Jika anda memilih menghapus pinjaman ini, data angsuran akan dihapus juga", 
                            "Hapus ? ", JOptionPane.OK_CANCEL_OPTION);
                    if(dialogResult == JOptionPane.OK_OPTION){
                        jd_pinjaman.setVisible(true);
                        tampilDataUbahPinjaman(0, getRow, txtf_crud_id_pinjaman, txtf_crud_id_angg_pinjaman, txtf_crud_nama_angg_pinjaman, 
                        txtf_crud_jml_pinjaman, txtf_crud_bunga_pinjaman, txtf_crud_angsur_pinjaman, txtf_crud_jml_keseluruhan_pinjaman);
                        whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                    }
                }
            }
        }else if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
            if(getRow >= 0){
                try {
                    koneksi = new konek();
                    ResultSet rs = koneksi.eksekusiSelect("SELECT MAX(angsur_ke) FROM "
                            + "angsuran_pinjaman WHERE id_pinjam='"+(String)tb_transaksi.getValueAt(getRow, 1)+"'");
                    String s = (String)tb_transaksi.getValueAt(getRow, 3);
                    if(rs.next()){
                        if(s.equals(rs.getString(1))){
                            jd_angsuran.setVisible(true);
                            tampilDataUbahAngsuran(0, getRow, txtf_crud_id_angsuran, txtf_crud_id_pinjaman_angsuran, txtf_crud_nama_angg_angsuran,
                                        txtf_crud_angsuran_ke, txtf_crud_jml_angsuran, txtf_crud_sisa_angsuran);
                            whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                        }else{
                            JOptionPane.showMessageDialog(null,
                                "Anda hanya dapat menghapus transaksi angsuran yang terakhir dilunasi pada kolom ANGSURAN KE");
                        }   
                    }
                } catch (SQLException ex) {
                    
                }
            }
        }
        if(getRow < 0){JOptionPane.showMessageDialog(null, "pilih data yg dingin dihapus");}
    }//GEN-LAST:event_btn_hapusMouseClicked

    private void btn_crud_simpan_pengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_pengeluaranActionPerformed
        CRUDPengeluaran_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_pengeluaranActionPerformed

    private void btn_crud_simpan_pinjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_pinjamanActionPerformed
        CRUDPinjaman_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_pinjamanActionPerformed

    private void btn_crud_cari_anggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_cari_anggActionPerformed
        setTableFrame_cari();
        dataMasukTabelFrame_cari("");
        frame_table_cari.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame_table_cari.setVisible(true);
    }//GEN-LAST:event_btn_crud_cari_anggActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        txtf_crud_id_angg_simpanan.setText(""+jTable1.getValueAt(jTable1.getSelectedRow(), 0));
        txtf_crud_nama_angg_simpanan.setText(""+jTable1.getValueAt(jTable1.getSelectedRow(), 1));
        txtf_crud_id_angg_pinjaman.setText(""+jTable1.getValueAt(jTable1.getSelectedRow(), 0));
        txtf_crud_nama_angg_pinjaman.setText(""+jTable1.getValueAt(jTable1.getSelectedRow(), 1));
        if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            txtf_crud_id_pinjaman_angsuran.setText(""+jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            txtf_crud_nama_angg_angsuran.setText(""+jTable1.getValueAt(jTable1.getSelectedRow(), 2));
            try {
                koneksi = new konek();
                odata = new operasiData();
                String id_pinjam = txtf_crud_id_pinjaman_angsuran.getText();
                ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM angsuran_pinjaman WHERE id_pinjam='"+id_pinjam+"' AND "
                        + "status_angsur='On'");
               
                if(!rs.isBeforeFirst()){
                    String jml_angsur = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 6);
                    String jml_k = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 7);
                    double sisa = Integer.parseInt(jml_k)-Integer.parseInt(jml_angsur);
                    txtf_crud_angsuran_ke.setText("1");
                    txtf_crud_jml_angsuran.setText(""+jml_angsur); 
                    txtf_crud_sisa_angsuran.setText(stringFormat(sisa));
                    
                    String tgl_pinjam = (String)jTable1.getValueAt(jTable1.getSelectedRow(), 8);
                    c.setTime(s.parse(tgl_pinjam));
                    c.add(Calendar.MONTH, 1);
                    txtf_crud_tgl_japo_angsuran.setText(s.format(c.getTime()));
                }
                if(rs.next()){
                    txtf_crud_angsuran_ke.setText(""+(rs.getInt(4)+1)); 
                    txtf_crud_jml_angsuran.setText(""+rs.getInt(5));
                    txtf_crud_sisa_angsuran.setText(""+(rs.getInt("sisa_angsur")-rs.getInt("jml_angsur")));
                    String tgl_japo_terakhir = odata.tgl_japo_transaksi_angsuran_pinjamanForDenda(rs.getInt(4), id_pinjam);
                    c.setTime(s.parse(tgl_japo_terakhir));
                    c.add(Calendar.MONTH, 1);
                    txtf_crud_tgl_japo_angsuran.setText(s.format(c.getTime()));
                }
                String a = (String)jTable1.getValueAt(jTable1.getSelectedRow(),4);
                tenorBuatPenanda = a.substring(a.lastIndexOf("R")+1);
            } catch (SQLException | ParseException e) {System.out.println(e);}
        }
        frame_table_cari.setVisible(false);
    }//GEN-LAST:event_jTable1MouseClicked

    private void txtf_frameCariAnggotaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_frameCariAnggotaKeyReleased
        dataMasukTabelFrame_cari(txtf_frameCariAnggota.getText());
    }//GEN-LAST:event_txtf_frameCariAnggotaKeyReleased

    private void btn_crud_cari_angg_pinjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_cari_angg_pinjamanActionPerformed
        setTableFrame_cari();
        dataMasukTabelFrame_cari("");
        frame_table_cari.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame_table_cari.setVisible(true);
    }//GEN-LAST:event_btn_crud_cari_angg_pinjamanActionPerformed

    private void txtf_crud_jml_pinjamanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_jml_pinjamanKeyReleased
        otomatisasiPenghitungan();
    }//GEN-LAST:event_txtf_crud_jml_pinjamanKeyReleased

    private void txtf_crud_bunga_pinjamanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_bunga_pinjamanKeyReleased
        otomatisasiPenghitungan();
    }//GEN-LAST:event_txtf_crud_bunga_pinjamanKeyReleased

    private void cmb_crud_tenor_pinjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_crud_tenor_pinjamanActionPerformed
        otomatisasiPenghitungan();
    }//GEN-LAST:event_cmb_crud_tenor_pinjamanActionPerformed

    private void btn_crud_simpan_angsuranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_simpan_angsuranActionPerformed
        CRUDAngsuran_pinjaman_Simpan();
    }//GEN-LAST:event_btn_crud_simpan_angsuranActionPerformed

    private void btn_crud_cari_id_angg_angsuranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_crud_cari_id_angg_angsuranActionPerformed
        setTableFrame_cari();
        dataMasukTabelFrame_cari("");
        frame_table_cari.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame_table_cari.setVisible(true);
    }//GEN-LAST:event_btn_crud_cari_id_angg_angsuranActionPerformed

    private void txtf_crud_id_pinjaman_angsuranKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_id_pinjaman_angsuranKeyReleased
        
    }//GEN-LAST:event_txtf_crud_id_pinjaman_angsuranKeyReleased

    private void txtf_crud_denda_angsuranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtf_crud_denda_angsuranMouseClicked
        hitungDenda();
    }//GEN-LAST:event_txtf_crud_denda_angsuranMouseClicked

    private void jdate_crud_tgl_angsuranPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdate_crud_tgl_angsuranPropertyChange
        Date date;
        if("date".equals(evt.getPropertyName())){
            date = (Date)evt.getNewValue();
            if(date != null){
                hitungDenda();
            }
        }
        
    }//GEN-LAST:event_jdate_crud_tgl_angsuranPropertyChange

    private void btn_cetakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cetakMouseClicked
        try {
            int getRow = tb_transaksi.getSelectedRow();
            Map<String, Object> id = new HashMap<>();
            koneksi = new konek();
            
            if(Const.transaksiMenu.equals("SIMPANAN")){
                if(getRow >= 0){
                    JOptionPane.showMessageDialog(null, "Tunggu sebentar, sedang dalam proses ..  ");
                    whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                    String jns_simpan = (String)tb_transaksi.getValueAt(getRow, 4);
                    ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM jenis_simpanan WHERE id='"+jns_simpan+"'");
                    if(rs.next()){
                        jns_simpan = rs.getString(2);
                    }
                    id.put("jns_simpan", jns_simpan);
                    id.put("id", whereid);
                    System.out.println(id);
                    ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\kuitansi\\simpanan.jrxml",
                            id, "Kuitansi Simpanan");
                }
            }else if(Const.transaksiMenu.equals("PENGELUARAN")){
                if(getRow >= 0){
                    JOptionPane.showMessageDialog(null, "Tunggu sebentar, sedang dalam proses ..  ");
                    whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                    id.put("id", whereid);
                    ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\kuitansi\\pengeluaran.jrxml",
                            id, "Kuitansi Pengeluaran Koperasi");
                }
            }else if(Const.transaksiMenu.equals("PINJAMAN")){
                if(getRow >= 0){
                    JOptionPane.showMessageDialog(null, "Tunggu sebentar, sedang dalam proses ..  ");
                    whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                    id.put("id", whereid);
                    ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\kuitansi\\pinjaman.jrxml",
                            id, "Kuitansi Pinjaman Dana");
                }
            }else if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
                if(getRow >= 0){
                    JOptionPane.showMessageDialog(null, "Tunggu sebentar, sedang dalam proses .. ");
                    whereid = (String)tb_transaksi.getValueAt(getRow, 0);
                    id.put("id", whereid);
                    ireport("E:\\2 JAVA_IT\\smstr 4 UAS Koperasi Simpan Pinjam\\koperasi\\src\\kuitansi\\angsuran.jrxml",
                            id, "Kuitansi Angsuran");
                }
            }
            if(getRow < 0){JOptionPane.showMessageDialog(null, "pilih data yg dingin dicetak kwitansi");}
        } catch (SQLException ex) {
            Logger.getLogger(Transaksi_Operasi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_cetakMouseClicked

    private void txtf_crud_jml_simpananKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_jml_simpananKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_crud_jml_simpananKeyTyped

    private void txtf_crud_jml_pengeluaranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_jml_pengeluaranKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_crud_jml_pengeluaranKeyTyped

    private void txtf_crud_jml_pinjamanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_jml_pinjamanKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_crud_jml_pinjamanKeyTyped

    private void txtf_crud_bunga_pinjamanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtf_crud_bunga_pinjamanKeyTyped
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtf_crud_bunga_pinjamanKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tambah;
    private javax.swing.JLabel btn_cetak;
    private javax.swing.JButton btn_crud_cari_angg;
    private javax.swing.JButton btn_crud_cari_angg_pinjaman;
    private javax.swing.JButton btn_crud_cari_id_angg_angsuran;
    private javax.swing.JButton btn_crud_simpan_angsuran;
    private javax.swing.JButton btn_crud_simpan_pengeluaran;
    private javax.swing.JButton btn_crud_simpan_pinjaman;
    private javax.swing.JButton btn_crud_simpan_simpanan;
    private javax.swing.JLabel btn_edit;
    private javax.swing.JLabel btn_hapus;
    private javax.swing.JComboBox<String> cmb_crud_jenis_simpanan;
    private javax.swing.JComboBox<String> cmb_crud_tenor_pinjaman;
    private javax.swing.JFrame frame_table_cari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JDialog jd_angsuran;
    private javax.swing.JDialog jd_pengeluaran;
    private javax.swing.JDialog jd_pinjaman;
    private javax.swing.JDialog jd_simpanan;
    private com.toedter.calendar.JDateChooser jdate_crud_tgl_angsuran;
    private com.toedter.calendar.JDateChooser jdate_crud_tgl_pengeluaran;
    private com.toedter.calendar.JDateChooser jdate_crud_tgl_pinjaman;
    private com.toedter.calendar.JDateChooser jdate_crud_tgl_simpanan;
    private javax.swing.JLabel lb_crud_title_angsuran;
    private javax.swing.JLabel lb_crud_title_pengeluaran;
    private javax.swing.JLabel lb_crud_title_pinjaman;
    private javax.swing.JLabel lb_crud_title_simpanan;
    private javax.swing.JLabel lb_judul;
    private javax.swing.JTable tb_transaksi;
    private javax.swing.JTextArea txta_crud_ket_pengeluaran;
    private javax.swing.JTextArea txta_crud_ket_pinjaman;
    private javax.swing.JTextArea txta_crud_ket_simpanan;
    private javax.swing.JTextField txtf_Cari;
    private javax.swing.JTextField txtf_crud_angsur_pinjaman;
    private javax.swing.JTextField txtf_crud_angsuran_ke;
    private javax.swing.JTextField txtf_crud_bunga_pinjaman;
    private javax.swing.JTextField txtf_crud_denda_angsuran;
    private javax.swing.JTextField txtf_crud_id_angg_pinjaman;
    private javax.swing.JTextField txtf_crud_id_angg_simpanan;
    private javax.swing.JTextField txtf_crud_id_angsuran;
    private javax.swing.JTextField txtf_crud_id_pengeluaran;
    private javax.swing.JTextField txtf_crud_id_pinjaman;
    private javax.swing.JTextField txtf_crud_id_pinjaman_angsuran;
    private javax.swing.JTextField txtf_crud_id_simpanan;
    private javax.swing.JTextField txtf_crud_jml_angsuran;
    private javax.swing.JTextField txtf_crud_jml_keseluruhan_pinjaman;
    private javax.swing.JTextField txtf_crud_jml_pengeluaran;
    private javax.swing.JTextField txtf_crud_jml_pinjaman;
    private javax.swing.JTextField txtf_crud_jml_simpanan;
    private javax.swing.JTextField txtf_crud_nama_angg_angsuran;
    private javax.swing.JTextField txtf_crud_nama_angg_pinjaman;
    private javax.swing.JTextField txtf_crud_nama_angg_simpanan;
    private javax.swing.JTextField txtf_crud_sisa_angsuran;
    private javax.swing.JTextField txtf_crud_tgl_japo_angsuran;
    private javax.swing.JTextField txtf_frameCariAnggota;
    // End of variables declaration//GEN-END:variables

    DefaultTableModel modelFrameCari;
    private void setTableFrame_cari(){
        Object[] field = {};
        if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
            field = new Object[]{"ID", "Id Anggota", "Nama Anggota", "Jml Pinjam", "Tenor", "Bunga", "Angsuran", "Jumlah Keseluruhan",
                    "Tgl Pinjam", "Status", "Keterangan"};
        }else{
            field = new Object[]{"ID", "Nama", "NIK", "lahir", "JK", "ID Pekerjaan", "No Hp", "Alamat", 
                    "Email","Dibuat", "Diubah"};
        }
        modelFrameCari = new DefaultTableModel(field, 0);
        jTable1.setModel(modelFrameCari);
        jTable1.setRowHeight(30);
    }
    private void dataMasukTabelFrame_cari(String key){
        odata = new operasiData();
        modelFrameCari.getDataVector().removeAllElements();
        ResultSet rs;
        String W = "";
        try {
            koneksi = new konek();
            if(Const.transaksiMenu.equals("ANGSURAN PINJAMAN")){
                if(!key.equals("")){
                        W = "AND id LIKE '%"+key+"%' OR id_angg LIKE '%"+key+"%' OR nama_angg LIKE '%"+key+"%' OR "
                                + "jml_pinjam LIKE '%"+key+"%' OR tenor LIKE '%"+key+"%' OR bunga LIKE '%"+key+"%' OR "
                                + "angsuran LIKE '%"+key+"%' OR jml_keseluruhan LIKE '%"+key+"%' OR tgl_pinjam LIKE '%"+key+"%' OR status_pinjam LIKE '%"+key+"%' OR "
                                + "ket LIKE '%"+key+"%'";
                    }
                    rs = koneksi.eksekusiSelect("SELECT * FROM pinjaman WHERE status_pinjam='Berjalan' "+W+" ORDER BY id DESC");
                    while (rs.next()) {
                        Object[] data = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
                            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)};
                        modelFrameCari.addRow(data);
                    }
            }else{
                if(!key.equals("")){
                    W = "WHERE id LIKE '%"+key+"%' OR nama LIKE '%"+key+"%' OR nik LIKE '%"+Const.encrypt(key)+"%' "
                            + "OR tgl_lahir LIKE '%"+key+"%' OR jk LIKE '%"+key+"%' OR pekerjaan LIKE '%"+key+"%' OR no_hp LIKE '%"+Const.encrypt(key)+"%' "
                            + "OR alamat LIKE '%"+Const.encrypt(key)+"%' OR email LIKE'%"+Const.encrypt(key)+"%' "
                            + "OR created_at LIKE '%"+key+"%' OR updated_at LIKE '%"+key+"%'";
                }
                rs = koneksi.eksekusiSelect("SELECT * FROM anggota "+W+"");
                while(rs.next()){
                    String decNik = Const.decrypt(rs.getString(3));
                    String decNo_hp = Const.decrypt(rs.getString(7));
                    String decAlamat = Const.decrypt(rs.getString(8));
                    String decEmail = Const.decrypt(rs.getString(9));
                    Object[] data = {rs.getString(1), rs.getString(2), decNik, rs.getString(4), 
                        rs.getString(5), rs.getString(6), decNo_hp, decAlamat, decEmail, 
                        rs.getString(10), rs.getString(11)};
                    modelFrameCari.addRow(data);
                }
            }
        } catch (SQLException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "dataMasukTabel : "+e);
        }
    }
    private void otomatisasiPenghitungan(){
        String jml_pinjam = txtf_crud_jml_pinjaman.getText();
        String bunga = txtf_crud_bunga_pinjaman.getText();
        jml_pinjam = jml_pinjam.equals("") ? jml_pinjam="0" : jml_pinjam;
        bunga = bunga.equals("") ? bunga="0" : bunga;
        
        double jml_pinjam1 = Double.parseDouble(jml_pinjam);
        double bunga1 = Double.parseDouble(bunga);
        if (cmb_crud_tenor_pinjaman.getSelectedIndex() > 0) {
            String subCombo = (String)cmb_crud_tenor_pinjaman.getSelectedItem();
            subCombo = subCombo.substring(subCombo.lastIndexOf("-")+1);
            double tenor = Double.parseDouble(subCombo);
            double cicilan = (jml_pinjam1/tenor);
            double hasilBunga = jml_pinjam1*(bunga1/100)/tenor;
            double bayarPerbulan = cicilan+hasilBunga;
            txtf_crud_angsur_pinjaman.setText(stringFormat(bayarPerbulan));
            double jml_k = bayarPerbulan*tenor;
            txtf_crud_jml_keseluruhan_pinjaman.setText(stringFormat(jml_k));
        }
    }
    public String stringFormat(double b){
        return String.format("%.0f", b);
    }
    public void hitungDenda(){
        try {
            if(!txtf_crud_tgl_japo_angsuran.getText().equals("")){
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(txtf_crud_tgl_japo_angsuran.getText());
                Date endDate = jdate_crud_tgl_angsuran.getDate();
                long duration = endDate.getTime() - startDate.getTime();
                long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
                if(diffInDays >= 0){
                    txtf_crud_denda_angsuran.setText(""+diffInDays);
                }else{
                    txtf_crud_denda_angsuran.setText("0");
                }
            }
        } catch (ParseException ex) {
            
        }
    }
    private void ireport(String path, Map<String, Object> param, String title){
        try {
            koneksi = new konek();
            File reportFile = new File(path);
            JasperDesign jasperDesign = JRXmlLoader.load(reportFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, koneksi.getConnection());
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
}

