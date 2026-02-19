package com.duoc.consumidorubicacion.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL_GPS")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;

    @Column(name = "fecha_hora")
    private String fechaHora;

    @Column(name = "fecha_registro_sistema")
    private LocalDateTime fechaRegistroSistema;

    private double latitud;
    private double longitud;

    public Ubicacion() {
    }

    public Ubicacion(String patente, String fechaHora, double latitud, double longitud) {
        this.patente = patente;
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaRegistroSistema = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    
    public LocalDateTime getFechaRegistroSistema() { return fechaRegistroSistema; }
    public void setFechaRegistroSistema(LocalDateTime fechaRegistroSistema) { this.fechaRegistroSistema = fechaRegistroSistema; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
}