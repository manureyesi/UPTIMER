package com.mundomanu.uptimer.config.vo;

import java.io.Serializable;
import java.util.Date;
import org.springframework.http.HttpMethod;

public class ConfigServiceUptimerVO implements Serializable {
    
    private String nombre;
    private String url;
    private long puerto;
    private String protocolo;
    private HttpMethod method;
    private Boolean comprobarCambio;
    private String campoBusqueda;
    private String ultimoRegistro;
    private String ultimoRegistroStatus;
    private Date ultimaActualizacion;
    private Long tiempoConsultaSegundos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPuerto() {
        return puerto;
    }

    public void setPuerto(long puerto) {
        this.puerto = puerto;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Boolean getComprobarCambio() {
        return comprobarCambio;
    }

    public void setComprobarCambio(Boolean comprobarCambio) {
        this.comprobarCambio = comprobarCambio;
    }

    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    public String getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(String ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }

    public String getUltimoRegistroStatus() {
        return ultimoRegistroStatus;
    }

    public void setUltimoRegistroStatus(String ultimoRegistroStatus) {
        this.ultimoRegistroStatus = ultimoRegistroStatus;
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public Long getTiempoConsultaSegundos() {
        return tiempoConsultaSegundos;
    }

    public void setTiempoConsultaSegundos(Long tiempoConsultaSegundos) {
        this.tiempoConsultaSegundos = tiempoConsultaSegundos;
    }
    
}
