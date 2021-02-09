package net.app.rollinbass;

import java.io.Serializable;

public class Links implements Serializable {

    //atributos
    private int id;
    private String alias;
    private String urlInstagram;
    private String urlSoundCloud;
    private String urlYoutube;
    private String urlCancion;

    //constructores
    public Links(int id, String alias, String urlInstagram, String urlSoundCloud, String urlYoutube, String urlCancion) {
        this.id = id;
        this.alias = alias;
        this.urlInstagram = urlInstagram;
        this.urlSoundCloud = urlSoundCloud;
        this.urlYoutube = urlYoutube;
        this.urlCancion = urlCancion;
    }
    public Links() {
    }

    //getter y setter
    public int getId() { return id; }

    public void setId(int id) {this.id = id; }

    public String getAlias() {return alias;}

    public void setAlias(String alias) {this.alias = alias;}

    public String getUrlInstagram() { return urlInstagram; }

    public void setUrlInstagram(String urlInstagram) { this.urlInstagram = urlInstagram; }

    public String getUrlSoundCloud() { return urlSoundCloud; }

    public void setUrlSoundCloud(String urlSoundCloud) {this.urlSoundCloud = urlSoundCloud;}

    public String getUrlYoutube() {return urlYoutube;}

    public void setUrlYoutube(String urlYoutube) {this.urlYoutube = urlYoutube; }

    public String getUrlCancion() {return urlCancion;}

    public void setUrlCancion(String urlCancion) {this.urlCancion = urlCancion;}

    //tostring
    @Override
    public String toString() {
        return "Links{" +
                "urlInstagram='" + urlInstagram + '\'' +
                ", urlSoundCloud='" + urlSoundCloud + '\'' +
                ", urlYoutube='" + urlYoutube + '\'' +
                ", urlCancion='" + urlCancion + '\'' +
                '}';
    }
}
