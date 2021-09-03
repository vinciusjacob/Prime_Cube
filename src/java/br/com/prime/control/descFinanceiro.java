/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.control;

import br.com.prime.conect.sqlServer;
import br.com.prime.model.Descontos;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import br.com.prime.model.Biblioteca;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Vinicius
 */
@ManagedBean
@ViewScoped
public class descFinanceiro {

    private List<Descontos> descontos;
    private List<Descontos> filteredDesc;
    private String msg;
    private Biblioteca b = new Biblioteca();

    public List<Descontos> getFilteredDesc() {
        return filteredDesc;
    }

    public void setFilteredDesc(List<Descontos> filteredDesc) {
        this.filteredDesc = filteredDesc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Descontos> getDescontos() {
        return descontos;
    }

    public void setDescontos(List<Descontos> descontos) {
        this.descontos = descontos;
    }

    public Set<String> getObs() {
        List<String> lista = new ArrayList<>();
        for (Descontos desc : descontos) {
            lista.add(desc.getObs());
        }
        Set<String> outra = new HashSet<>(lista);
        return outra;
    }
    public Set<String> getEmpresas() {
        List<String> lista = new ArrayList<String>();
        for (Descontos desc : descontos) {
            lista.add(desc.getEmpresa());
        }
        Set<String> outra = new HashSet<String>(lista);
        return outra;
    }
    public String[] getSupervisor() {
        List<String> lista = new ArrayList<String>();
        for (Descontos desc : descontos) {
            lista.add(desc.getSupervisor());
        }
        Set<String> outra = new HashSet<String>(lista);
        String[] noutra = outra.toArray(new String[outra.size()]);
        Arrays.sort(noutra);
        return noutra;
    }
    public String[] getVendedor() {
        List<String> lista = new ArrayList<String>();
        for (Descontos desc : descontos) {
            lista.add(desc.getVendedor());
        }
        Set<String> outra = new HashSet<String>(lista);
        String[] noutra = outra.toArray(new String[outra.size()]);
        Arrays.sort(noutra);
        return noutra;
    }
    public Set<String> getPedidos() {
        List<String> lista = new ArrayList<String>();
        for (Descontos desc : descontos) {
            lista.add(desc.getPedido());
        }
        Set<String> outra = new HashSet<String>(lista);
        return outra;
    }
    public Integer[] getProdutos() {
        List<Integer> lista = new ArrayList();
        for (Descontos desc : descontos) {
            lista.add(Integer.parseInt(desc.getCodProduto()));
        }
        Set<Integer> outra = new HashSet(lista);
        Integer[] noutra = outra.toArray(new Integer[outra.size()]);
        Arrays.sort(noutra);
        return noutra;
    }
    public String[] getDescricao() {
        List<String> lista = new ArrayList<String>();
        for (Descontos desc : descontos) {
            lista.add(desc.getDescPro());
        }
        Set<String> outra = new HashSet<String>(lista);
        String[] noutra = outra.toArray(new String[outra.size()]);
        Arrays.sort(noutra);
        return noutra;
    }
    public Set<String> getPrUn() {
        List<String> lista = new ArrayList<>();
        for (Descontos desc : descontos) {
            lista.add(desc.getVlrun());
        }
        Set<String> outra = new HashSet<>(lista);
        return outra;
    }

    @PostConstruct
    public void init() {
        sqlServer sql = new sqlServer();
        Descontos desc;
        this.descontos = new ArrayList();
        String query = "SELECT * FROM TBL_MOVIMENTO AS M "
                + " INNER JOIN TBL_PRODUTOS AS PRO ON PRO.COD_PRODUTO=M.COD_PRODUTO AND PRO.CODEMPRESA=M.EMPPRESA "
                + " LEFT JOIN TBL_VENDEDORES AS V ON V.CODVENDEDOR = M.CODVENDEDOR "
                + " LEFT JOIN TBL_DESCBANCOUN AS D ON D.CODPROD=M.COD_PRODUTO AND D.NUMPED=M.NUM_PEDIDO"
                + " WHERE FONTE = 'PEDIDOS' ORDER BY NUM_PEDIDO,M.COD_PRODUTO";
        try {
            sql.SqlQuery(query);
            while (sql.rs.next()) {
                desc = new Descontos();
                desc.setEmpresa(sql.rs.getString("EMPPRESA"));
                String sup = "000"+sql.rs.getString("CODSUPERVISOR");
                desc.setSupervisor(sup.substring(sup.length()-3)+" - "+sql.rs.getString("NOMESUPERVISOR"));
                String vend = "000"+sql.rs.getString("CODVENDEDOR");
                desc.setVendedor(vend.substring(vend.length()-3)+" - "+sql.rs.getString("NOMEVENDEDOR"));
                desc.setCodProduto(sql.rs.getString("COD_PRODUTO"));
                String descPro = "000"+sql.rs.getString("COD_PRODUTO");
                desc.setDescPro(descPro.substring(descPro.length()-3)+" - "+sql.rs.getString("DESCRICAO_PROD"));
                desc.setObs(b.isNull(sql.rs.getString("OBS")));
                desc.setVlrun(b.isMoney(sql.rs.getString("PRUNIT")), null);
                desc.setPedido(sql.rs.getString("NUM_PEDIDO"));
                this.descontos.add(desc);
            }
            sql.desconecta();
            this.setMsg("Desconto Financeiro");
        } catch (Exception ex) {
            this.setMsg("Error: " + ex.toString());
        }
    }
     public void is(FacesContext context, UIComponent component, Object value) {
        
        if(!b.isNumeric(value.toString())){
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Arquivo invalido", "O arquivo deve ser no formato de remessa.");
            throw new ValidatorException(msg);
        }
    }

}
