package net.app.rollinbass;

import java.io.Serializable;

public class Sugerencia implements Serializable {

    //atributos
    private String titulo;
    private String mail;
    private String sugerencia;

    //constructores
    public Sugerencia(String titulo, String mail, String sugerencia) {
        this.titulo = titulo;
        this.mail = mail;
        this.sugerencia = sugerencia;
    }

    public Sugerencia(){};

    //getter y setter
    public String getTituto() { return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

    public String getSugerencia() {return sugerencia;}

    public void setSugerencia(String sugerencia) {this.sugerencia = sugerencia;}
}
