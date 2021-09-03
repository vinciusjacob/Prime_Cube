/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.conect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Vinicius
 */
public class sqlGest {
    public final String bd = "GESTPLUS_AEQ_DISTRIBUIDORA";
    private String usuario = "consulta";
    private String senha = "AtendeTi16";
    private String servidor = "SRV01007:1433";
    private Connection con;
    public Statement st;
    public ResultSet rs;
    
    
    public void Conecta() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection("jdbc:sqlserver://"+servidor+";DatabaseName="+bd, usuario, senha);
        st = con.createStatement();
    }
    public void desconecta() throws SQLException{
        if(con!=null){
            con.close();
        }
    }
    /**
     * Execulta comandos sql: inserir/alterar/excluir
     * @param sql 
     * @throws java.lang.Exception 
     */
    public void SqlUpdate(String sql) throws Exception{
        Conecta();
        st.executeUpdate(sql);
        desconecta();
    }
    /**
     * Execulta consultas sql, e retorna um ResultSet 
     * @param sql
     * @throws java.lang.Exception
     */
    public void SqlQuery(String sql) throws Exception{
        Conecta();
        rs = st.executeQuery(sql);
    }
    /**
     * Obtém o número de linhas da um resultado
     * @param sql
     * @return 
     */
    public int SqlNumRows(String sql) throws Exception{
        int linha = 0;
        Conecta();
        rs = st.executeQuery(sql);
        while (rs.next()){
            linha++;
        }
        desconecta();
        return linha;
    }
}
