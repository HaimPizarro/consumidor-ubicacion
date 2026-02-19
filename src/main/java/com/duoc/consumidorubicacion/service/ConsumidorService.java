package com.duoc.consumidorubicacion.service;

import com.duoc.consumidorubicacion.model.Ubicacion;
import com.duoc.consumidorubicacion.model.UbicacionDto;
import com.duoc.consumidorubicacion.repository.UbicacionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumidorService {

    @Autowired
    private UbicacionRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "cola-ubicaciones")
    public void recibirMensaje(String mensajeJson) {
        try {
            UbicacionDto dto = objectMapper.readValue(mensajeJson, UbicacionDto.class);
            
            String accion = dto.getAccion();
            if (accion == null) accion = "CREAR"; 

            switch (accion) {
                case "CREAR":
                    Ubicacion nueva = new Ubicacion();
                    nueva.setPatente(dto.getPatente());
                    nueva.setFechaHora(dto.getFechaHora());
                    nueva.setLatitud(dto.getLatitud());
                    nueva.setLongitud(dto.getLongitud());
                    
                    Ubicacion guardada = repository.save(nueva);
                    System.out.println(">> [CREATE] Ubicación guardada con ID: " + guardada.getId());
                    break;

                case "EDITAR":
                    Optional<Ubicacion> existente = repository.findById(dto.getId());
                    if (existente.isPresent()) {
                        Ubicacion u = existente.get();
                        u.setPatente(dto.getPatente());
                        u.setFechaHora(dto.getFechaHora());
                        u.setLatitud(dto.getLatitud());
                        u.setLongitud(dto.getLongitud());
                        repository.save(u);
                        System.out.println(">> [UPDATE] Ubicación actualizada ID: " + dto.getId());
                    } else {
                        System.out.println(">> [ERROR] No existe ID " + dto.getId());
                    }
                    break;

                case "ELIMINAR":
                    if (repository.existsById(dto.getId())) {
                        repository.deleteById(dto.getId());
                        System.out.println(">> [DELETE] Ubicación eliminada ID: " + dto.getId());
                    } else {
                        System.out.println(">> [ERROR] No existe ID " + dto.getId());
                    }
                    break;

                default:
                    System.out.println(">> [WARN] Acción desconocida: " + accion);
            }
        } catch (Exception e) {
            System.out.println(">> Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}