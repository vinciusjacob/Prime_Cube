/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.model;

import java.text.DecimalFormat;

/**
 *
 * @author Vinicius
 */
public class Biblioteca {
    
    public String isNull(String texto){
        if(texto == null){
            texto = "";
        }
        return texto;
    }
    
    public String isMoney(String money){
        if (money == null || money.equals("")){
            money = "";
        }else{
            DecimalFormat df = new DecimalFormat("0.00");
            money = df.format(Double.parseDouble(money));
        }
        return money;
    }
    public boolean isNumeric(String valor){
        try{
            Double.parseDouble(valor);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
