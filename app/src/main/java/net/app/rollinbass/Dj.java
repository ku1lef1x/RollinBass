package net.app.rollinbass;

import java.io.Serializable;

public class Dj implements Serializable {

    private int id;
    private String nombre;
    private String apellidos;
    private String generoMusical;
    private String urlFoto;
    private String alias;
    private String descripcion;


    //constructor con parametros
    public Dj(int id,String nombre, String apellidos, String generoMusical,
              String urlFoto, String alias, String descripcion){
        this.id=id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.generoMusical=generoMusical;
        this.urlFoto=urlFoto;
        this.alias=alias;
        this.descripcion=descripcion;
    }
    //constructor vacio
    public Dj(){
    }

    //getter y setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public String getUrlFoto() { return urlFoto; }

    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public String getAlias() { return alias; }

    public void setAlias(String alias) { this.alias = alias; }

    public String getDescripcion() { return  descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Dj{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", generoMusical='" + generoMusical + '\'' +
                ", urlFoto='" + urlFoto + '\'' +
                ", alias='" + alias + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
