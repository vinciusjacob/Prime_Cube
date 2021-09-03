package br.com.prime.control;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author u090
 */
@ManagedBean
@SessionScoped
public class Pages implements java.io.Serializable {
    private static final long serialVersionUID = -5284902038223218258L;
    
    private String pageName;
    
    @PostConstruct
    public void init(){
        if(pageName == null || pageName.equals("null")){
            pageName="financeiro/principal";
        }
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
