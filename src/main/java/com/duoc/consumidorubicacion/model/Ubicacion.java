package com.duoc.consumidorubicacion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORIAL_GPS")
@Data
@NoArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patente;
    private String fechaHora;
    private double latitud;
    private double longitud;
    
    private LocalDateTime fechaRegistroSistema = LocalDateTime.now();

    // Constructor para facilitar la creación
    public Ubicacion(String patente, String fechaHora, double latitud, double longitud) {
        this.patente = patente;
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}