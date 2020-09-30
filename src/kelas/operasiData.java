package kelas;

import DB.konek;
import java.sql.ResultSet;
import java.sql.SQLException;
public class operasiData {
    private konek koneksi;
    private String sql;
    
    //MANAGEMENT DATA USERS
    public void insert_users(String username, String password) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO users VALUES('"+getId("users")+"', '"+username+"','"+password+"', NULL, NOW(), NULL)";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_users(String id, String username, String password) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE users SET username='"+username+"', password='"+password+"', logged_at=NULL, updated_at=NOW() "
                                + "WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_users(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM users WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA PEKERJAAN
    public void insert_pekerjaan(String id, String pekerjaan) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO pekerjaan VALUES('"+id+"','"+pekerjaan+"')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_pekerjaan(String id, String pekerjaan) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE pekerjaan SET pekerjaan='"+pekerjaan+"' WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_pekerjaan(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM pekerjaan WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA JENIS SIMPANAN
    public void insert_jenis_simpanan(String id, String jenis_simpanan) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO jenis_simpanan VALUES('"+id+"','"+jenis_simpanan+"')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_jenis_simpanan(String id, String jenis_simpanan) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE jenis_simpanan SET jenis_simpanan='"+jenis_simpanan+"' WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_jenis_simpanan(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM jenis_simpanan WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA TENOR
    public void insert_tenor(String id, String tenor) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO tenor VALUES('"+id+"','"+tenor+"')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_tenor(String idygdiubah, String id, String tenor) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE tenor SET id='"+id+"', tenor='"+tenor+"' WHERE id='"+idygdiubah+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_tenor(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM tenor WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA ANGGOTA
    public void insert_anggota(String id, String nama, String nik, String tgl_lahir, 
            String jk, int pekerjaan, String no_hp, String alamat, String email) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO anggota VALUES('"+id+"', '"+nama+"', '"+nik+"', '"+tgl_lahir+"', '"+jk+"', "
                + "'"+pekerjaan+"', '"+no_hp+"', '"+alamat+"', '"+email+"', NOW(), NULL)";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_anggota(String id, String nama, String nik, String tgl_lahir, 
            String jk, int pekerjaan, String no_hp, String alamat, String email) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE anggota SET nama='"+nama+"', nik='"+nik+"', tgl_lahir='"+tgl_lahir+"', jk='"+jk+"', "
                + "pekerjaan='"+pekerjaan+"', no_hp='"+no_hp+"', alamat='"+alamat+"', email='"+email+"', "
                + "updated_at=NOW() WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_anggota(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM anggota WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA TRANSAKSI SIMPANAN
    public void insert_transaksi_simpanan(String id, String id_angg, String nama_angg, String jml_simpan, 
            String jns_simpan, String tgl_simpan, String ket) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO simpanan VALUES('"+id+"', '"+id_angg+"', '"+nama_angg+"', '"+jml_simpan+"', '"+jns_simpan+"', "
                + "'"+tgl_simpan+"', '"+ket+"')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_transaksi_simpanan(String idubah, String id, String id_angg, String nama_angg, String jml_simpan, 
            String jns_simpan, String tgl_simpan, String ket) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE simpanan SET id='"+id+"', id_angg='"+id_angg+"', nama_angg='"+nama_angg+"', jml_simpan='"+jml_simpan+"', "
                + "jns_simpan='"+jns_simpan+"', tgl_simpan='"+tgl_simpan+"', ket='"+ket+"' WHERE id='"+idubah+"'";
        
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_transaksi_simpanan(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM simpanan WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void updateNamaAngg_transaksi_simpanan(String id, String nama) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE simpanan SET nama_angg='"+nama+"' WHERE id_angg='"+id+"'";
        
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA TRANSAKSI PENGELUARAN
    public void insert_transaksi_pengeluaran(String id, String jml_pengl, String tgl_pengl, String ket) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO pengeluaran VALUES('"+id+"', '"+jml_pengl+"', '"+tgl_pengl+"', '"+ket+"')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_transaksi_pengeluaran(String idubah, String id, String jml_pengl, String tgl_pengl, String ket) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE pengeluaran SET id='"+id+"', jml_pengl='"+jml_pengl+"', tgl_pengl='"+tgl_pengl+"', "
                + "ket='"+ket+"' WHERE id='"+idubah+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_transaksi_pengeluaran(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM pengeluaran WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void updateNamaAngg_transaksi_pinjaman_and_angsuran(String id, String nama) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE pinjaman SET nama_angg='"+nama+"' WHERE id_angg='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        ResultSet rs = koneksi.eksekusiSelect("SELECT id FROM pinjaman WHERE id_angg='"+id+"'");
        while(rs.next()){
            koneksi.eksekusiUpdate("UPDATE angsuran_pinjaman SET nama_angg='"+nama+"' WHERE id_pinjam='"+rs.getString(1)+"'");
        }
        koneksi.tutupKoneksi();
    }
//    public void updateNamaAngg_transaksi_angsuran(String id, String nama) throws SQLException{
//        koneksi = new konek();
//        ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM pinjaman WHERE id='"+id+"'");
//        sql = "UPDATE angsuran SET nama_angg='"+nama+"' WHERE id_pinjam='"+id+"'";
//        koneksi.eksekusiUpdate(sql);
//        koneksi.tutupKoneksi();
//    }
    
    
    
    //MANAGEMENT DATA TRANSAKSI PINJAMAN
    public void insert_transaksi_pinjaman(String id, String id_angg, String nama_angg, String jml_pinjam, 
            String tenor, float bunga, String angsuran, String jml_keseluruhan, String tgl_pinjam, String ket) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO pinjaman VALUES('"+id+"', '"+id_angg+"', '"+nama_angg+"', '"+jml_pinjam+"', "
                + "'"+tenor+"', '"+bunga+"', '"+angsuran+"', '"+jml_keseluruhan+"', '"+tgl_pinjam+"', 'Berjalan','"+ket+"')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_transaksi_pinjaman(String idubah, String id, String id_angg, String nama_angg, String jml_pinjam, 
            String tenor, float bunga, String angsuran, String jml_keseluruhan, String tgl_pinjam, String ket) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE pinjaman SET id='"+id+"', id_angg='"+id_angg+"', nama_angg='"+nama_angg+"', jml_pinjam='"+jml_pinjam+"', "
                + "tenor='"+tenor+"', bunga='"+bunga+"', angsuran='"+angsuran+"', jml_keseluruhan='"+jml_keseluruhan+"', "
                + "tgl_pinjam='"+tgl_pinjam+"', ket='"+ket+"' WHERE id='"+idubah+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void lunas_transaksi_pinjaman(String id_pinjam, String status) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE pinjaman SET status_pinjam='"+status+"' WHERE id='"+id_pinjam+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_transaksi_pinjaman(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM pinjaman WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void delete_transaksi_pinjaman_with_angsuran_pinjaman(String id_pinjam) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM angsuran_pinjaman WHERE id_pinjam='"+id_pinjam+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    
    //MANAGEMENT DATA TRANSAKSI ANGSURAN PINJAMAN
    public void insert_transaksi_angsuran_pinjaman(String id, String id_pinjam, String nama_anggota, String angsur_ke, 
            String jml_angsur, String sisa_angsur, String tgl_angsur, String denda, String tgl_japo) throws SQLException{
        koneksi = new konek();
        sql = "INSERT INTO angsuran_pinjaman VALUES('"+id+"', '"+id_pinjam+"', "
                + "'"+nama_anggota+"', '"+angsur_ke+"', '"+jml_angsur+"', '"+sisa_angsur+"', '"+tgl_angsur+"', "
                + "'"+denda+"', '"+tgl_japo+"', 'On')";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void update_transaksi_angsuran_pinjaman(String id_pinjam, String nama_anggota, 
            int sisa_angsur, String tgl_angsur) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE angsuran_pinjaman SET nama_angg='"+nama_anggota+"', ='"+nama_anggota+"' "
                + "WHERE id_pinjam='"+id_pinjam+"' AND "
                + "status_angsur='On'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void lunas_transaksi_angsuran_pinjaman(String id_pinjam) throws SQLException{
        koneksi = new konek();
        sql = "UPDATE angsuran_pinjaman SET status_angsur='Off' WHERE id_pinjam='"+id_pinjam+"' AND "
                + "status_angsur='On'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public void max_transaksi_angsuran_pinjaman(String id_pinjam) throws SQLException{
        koneksi = new konek();
        ResultSet rs = koneksi.eksekusiSelect("SELECT MAX(angsur_ke) FROM angsuran_pinjaman WHERE id_pinjam='"+id_pinjam+"'");
        if(rs.next()){
            sql = "UPDATE angsuran_pinjaman SET status_angsur='on' WHERE id_pinjam='"+id_pinjam+"' AND angsur_ke='"+rs.getString(1)+"'";
            koneksi.eksekusiUpdate(sql);
        }
        koneksi.tutupKoneksi();
    }
    public void delete_transaksi_angsuran_pinjaman(String id) throws SQLException{
        koneksi = new konek();
        sql = "DELETE FROM angsuran_pinjaman WHERE id='"+id+"'";
        koneksi.eksekusiUpdate(sql);
        koneksi.tutupKoneksi();
    }
    public String tgl_japo_transaksi_angsuran_pinjamanForDenda(int max_angsuran, String id_pinjam) throws SQLException{
        koneksi = new konek();
        String tgl_japo_terakhir = "";
        ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM angsuran_pinjaman WHERE id_pinjam='"+id_pinjam+"' AND angsur_ke='"+max_angsuran+"'");
        if(rs.next()){
            tgl_japo_terakhir = rs.getString("tgl_tempo");
        }
        koneksi.tutupKoneksi();
        return tgl_japo_terakhir;
    }
    
    
    public int getId(String table) throws SQLException{
        koneksi = new konek();
        int id = 0;
        ResultSet rs = koneksi.eksekusiSelect("SELECT MAX(id) FROM "+table+"");
        while (rs.next()) { 
            id = rs.getInt(1)+1; 
            //RESET ID TERAKHIR DITAMBAHKAN
            koneksi.eksekusiUpdate("ALTER TABLE "+table+" AUTO_INCREMENT="+id);
        }koneksi.tutupKoneksi();
        return id;
    }
    public int getIdVarchar(String table) throws SQLException{
        koneksi = new konek();
        int id = 0;
        ResultSet rs = koneksi.eksekusiSelect(""
                + "SELECT MAX(CAST(SUBSTRING(id, 3, length(id)-2) AS UNSIGNED)) FROM "+table+"");
        while (rs.next()) { 
            id = rs.getInt(1)+1; 
        }koneksi.tutupKoneksi();
        return id;
    }
    public String getIdSubstringAnggota(String table) throws SQLException{
        koneksi = new konek();
        String id = "";
        ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM "+table+"");
        if(!rs.isBeforeFirst()){
            id = "AG0001";
        }
        while (rs.next()) { 
            String no = rs.getString(1).substring(2);
            String AN = "" +(Integer.parseInt(no)+1);
            String Nol = "";
            if(AN.length()==1){Nol="000";}
            else if(AN.length()==2){Nol="00";}
            else if(AN.length()==3){Nol="0";}
            else if(AN.length()==4){Nol="";}
            id = "AG" + Nol + AN;
            
        }koneksi.tutupKoneksi();
        return id;
    }
    public String getIdSubstringTransaksi(String table, String str) throws SQLException{
        koneksi = new konek();
        String id = "";
        ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM "+table+"");
        if(!rs.isBeforeFirst()){
            id = str+"00001";
        }
        while (rs.next()) { 
            String no = rs.getString(1).substring(3);
            String AN = "" +(Integer.parseInt(no)+1);
            String Nol = "";
            if(AN.length()==1){Nol="0000";}
            else if(AN.length()==2){Nol="000";}
            else if(AN.length()==3){Nol="00";}
            else if(AN.length()==4){Nol="0";}
            else if(AN.length()==5){Nol="";}
            id = str + Nol + AN;
        }koneksi.tutupKoneksi();
        return id;
    }
    public String cariAPUntukUbahP(String id_pinjam){
        try {
            koneksi = new konek();
            ResultSet rs = koneksi.eksekusiSelect("SELECT * FROM angsuran_pinjaman WHERE id_pinjam='"+id_pinjam+"'");
            while(rs.next()){
                return "ada";
            }
        } catch (SQLException ex) { }
        return "";
    }
    
    
    
    
    //SALDO
    public String saldoSimpananAnggota(String id_anggota){
        try {
            koneksi = new konek();
            ResultSet rs = koneksi.eksekusiSelect("SELECT SUM(jml_simpan) FROM simpanan WHERE id_angg='"+id_anggota+"'");
            while(rs.next()){
                if(rs.getString(1) == null){
                    return "";
                }else{
                    return rs.getString(1);
                }
            }
        } catch (SQLException ex) { }
        return "";
    }
    public String itemSimpananAnggota(String id_anggota, String jns_simpan){
        try {
            koneksi = new konek();
            ResultSet rs = koneksi.eksekusiSelect("SELECT SUM(jml_simpan) "
                    + "FROM simpanan WHERE id_angg='"+id_anggota+"' AND jns_simpan='"+jns_simpan+"'");
            
            while(rs.next()){
                if(rs.getString(1) == null){
                    return "Tidak ada";
                }else{
                    return rs.getString(1);
                }
            }
        } catch (SQLException ex) { }
        return "Tidak ada";
    }
}
