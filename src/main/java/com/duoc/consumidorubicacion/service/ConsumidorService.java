package com.duoc.consumidorubicacion.service;

import com.duoc.consumidorubicacion.model.Ubicacion;
import com.duoc.consumidorubicacion.model.UbicacionDto;
import com.duoc.consumidorubicacion.repository.UbicacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorService {

    @Autowired
    private UbicacionRepository repositorio;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "cola-ubicaciones")
    public void recibirMensaje(String mensajeJson) {
        try {
            System.out.println("Mensaje recibido: " + mensajeJson);

            //Convierte JSON a Objeto DTO
            UbicacionDto dto = objectMapper.readValue(mensajeJson, UbicacionDto.class);

            //Crea la Entidad para la Base de Datos
            Ubicacion nuevaUbicacion = new Ubicacion(
                dto.getPatente(),
                dto.getFechaHora(),
                dto.getLatitud(),
                dto.getLongitud()
            );

            //Guarda en Oracle
            repositorio.save(nuevaUbicacion);
            System.out.println(">> Ubicación guardada en Oracle con ID: " + nuevaUbicacion.getId());

        } catch (Exception e) {
            System.err.println("Error procesando mensaje: " + e.getMessage());
        }
    }
}