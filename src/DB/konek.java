package DB;
// IREPORT NYA PAKE V 5.6.0

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

public class konek {
    private Connection conn;
    private Statement st;
    
    public konek() throws SQLException, SQLTimeoutException{
        bukaKoneksi();
    }
    public void bukaKoneksi() throws SQLException, SQLTimeoutException{
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phbjava2_koperasi?", "root", "");
        st = conn.createStatement();
    }
    public ResultSet eksekusiSelect(String sql) throws SQLException, SQLTimeoutException{
        bukaKoneksi();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }
    public int eksekusiUpdate(String sql) throws SQLException, SQLTimeoutException{
        bukaKoneksi();
        int result = st.executeUpdate(sql);
        return result;
    }
    public void tutupKoneksi() throws SQLException, SQLTimeoutException{
        conn.close();
    }
    public Connection getConnection(){
        Connection con = null;
        if(con == null){
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/phbjava2_koperasi?", "root", "");
            } catch (SQLException e) {
                System.exit(0);
            }
        }
        return con;
    }
}
