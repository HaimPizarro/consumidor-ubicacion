package com.duoc.consumidorubicacion.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class UbicacionDto implements Serializable {
    private String patente;
    private String fechaHora;
    private double latitud;
    private double longitud;
}