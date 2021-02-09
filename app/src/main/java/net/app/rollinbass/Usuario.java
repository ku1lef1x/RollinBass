package net.app.rollinbass;

public class Usuario {

    private String user;
    private String pass;
    private String nombreCompleto;
    private String mail;
    private long fechaNac;

    //constructores
    public Usuario(String user, String pass, String nombreCompleto, String mail, long fechaNac) {
        this.user = user;
        this.pass = pass;
        this.nombreCompleto = nombreCompleto;
        this.mail = mail;
        this.fechaNac = fechaNac;
    }

    public Usuario(){};

    //getter y setter
    public String getUser() { return user;}

    public void setUser(String user) {this.user = user;}

    public String getPass() { return pass; }

    public void setPass(String pass) {this.pass = pass;}

    public String getNombreCompleto() { return nombreCompleto; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getMail() { return mail;}

    public void setMail(String mail) { this.mail = mail;}

    public long getFechaNac() { return fechaNac;}

    public void setFechaNac(long fechaNac) { this.fechaNac = fechaNac; }

    //toString();
    @Override
    public String toString() {
        return "Usuario{" +
                " user='" + user + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", pass='" + pass + '\'' +
                ", mail='" + mail + '\'' +
                ", fechaNac=" + fechaNac +
                '}';
    }
}
