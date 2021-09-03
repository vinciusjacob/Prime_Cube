package br.com.prime.model;

import br.com.prime.conect.sqlServer;

/**
 *
 * @author Jacob
 */
public class Usuario {

    private String id;
    private String nome;
    private String login;
    private String modulos; //Permissão dos modulos 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getModulos() {
        return modulos;
    }

    public void setModulos(String modulos) {
        this.modulos = modulos;
    }

    public Boolean isValido(String login, String senha) throws Exception {
        sqlServer sql = new sqlServer();
        sql.SqlQuery("SELECT * FROM TBL_USUARIOS WHERE LOGIN = '" + login + "' AND SENHA = '" + senha + "' ");
        while (sql.rs.next()) {
            this.setId(sql.rs.getString("ID"));
            this.setLogin(sql.rs.getString("LOGIN"));
            this.setModulos(sql.rs.getString("MODULO"));
            this.setNome(sql.rs.getString("NOME"));
        }
        if (this.id == null) {
            return false;
        } else {
            return true;
        }
    }

}
