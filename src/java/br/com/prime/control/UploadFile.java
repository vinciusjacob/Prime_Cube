/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prime.control;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 *
 * @author Vinicius
 */
@ManagedBean
public class UploadFile {
    private static Part file;
    private String fileName;
    private Boolean disableButtom;

    public Boolean getDisableButtom() {
        return disableButtom;
    }

    public void setDisableButtom(Boolean disableButtom) {
        this.disableButtom = disableButtom;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @PostConstruct
    public void init(){
        this.setFileName("Nenhum arquivo selecionado.");
        this.setDisableButtom(true);
    }
    
    public Part getFile(){
        return file;
    }
    public void setFile(Part file){
        UploadFile.file = file;
        if(file == null){
            this.setFileName("Nenhum arquivo selecionado.");
            this.setDisableButtom(true);
        }else{
            this.setFileName(getHeader(UploadFile.file));
            this.setDisableButtom(false);
        }
        
    }
    
    
    public String getHeader(Part part){
        String header = part.getHeader( "content-disposition" );
        for( String tmp : header.split(";") ){
            if( tmp.trim().startsWith("filename") ){
                return tmp.substring( tmp.indexOf("=")+2 , tmp.length()-1 );
            }
        }
        return null;
    }

    public void validaCnab400(FacesContext context, UIComponent component, Object value) {
        Part arquivo = (Part) value;
        
        if(!getHeader(arquivo).contains(".REM")){
            this.setFileName("Tipo de arquivo invalido!");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Arquivo invalido", "O arquivo deve ser no formato de remessa.");
            throw new ValidatorException(msg);
        }
    }
    public void validaSerasa(FacesContext context, UIComponent component, Object value){
        Part retorno = (Part) value;
        if(retorno.getSize()>5000){
            this.setFileName("Tamanho: "+retorno.getSize());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Arquivo invalido", "O arquivo possui um tamanho superior ao retorno.");
            throw new ValidatorException(msg);
        }
    }
    
}
