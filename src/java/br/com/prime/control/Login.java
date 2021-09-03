package br.com.prime.control;

import br.com.prime.model.Usuario;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jacob
 */
@ManagedBean
public class Login {

    private String login;
    private String senha;
    private String modulo;
    //private Usuario usuario;

    public String getLogin() {
        return login;
    }
    public Usuario getUsuario(){
        Usuario usuario = new Usuario();
        usuario = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return this.senha;
    }

    public String getModulo() {
        return this.modulo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public void valida(){
        try{
            Usuario usuario = new Usuario();
        if (usuario.isValido(login, senha)) {
            if (this.modulo.equals("BackOffice") && usuario.getModulos().contains("B")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                FacesContext.getCurrentInstance().getExternalContext().redirect("backoffice.xhtml");
            } else if (this.modulo.equals("Financeiro") && usuario.getModulos().contains("F")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
                FacesContext.getCurrentInstance().getExternalContext().redirect("financeiro.xhtml");
            } else {
                addMessage("Acesso negado!", "Falha");
            }
        } else {
            addMessage("Usuario/Senha invalidos!", "Falha");
        }
        }catch(Exception ex){
            addMessage("Erro: "+ex.toString(), "Error");
        }
    }
    public void Logof() throws Exception{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
    
    public void ProtegePagina(String modulo) throws Exception{
        Usuario usuario = new Usuario();
        usuario = (Usuario)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if(usuario == null){
            addMessage("Acesso negado!", "Falha");
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
        if(!usuario.getModulos().contains(modulo)){
            this.Logof();
            addMessage("Acesso negado!", "Falha");
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    @PostConstruct
    public void init() {

    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
