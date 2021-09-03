package br.com.prime.control;

import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;

/**
 *
 * @author u090
 */
@ManagedBean
public class Menu {

    private String visible;

    @PostConstruct
    public void init() {
        visible = "true";
    }

    public void mostrarmenu() {
        this.setVisible("false");

    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }
}
