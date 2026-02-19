package com.duoc.consumidorubicacion.controller;

import com.duoc.consumidorubicacion.model.Ubicacion;
import com.duoc.consumidorubicacion.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crud")
@CrossOrigin(origins = "*") 
public class UbicacionController {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @GetMapping("/listar")
    public List<Ubicacion> listarUbicaciones() {
        return ubicacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> obtenerPorId(@PathVariable Long id) {
        Optional<Ubicacion> ubicacion = ubicacionRepository.findById(id);
        
        if (ubicacion.isPresent()) {
            return ResponseEntity.ok(ubicacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Ubicacion> editarUbicacion(@PathVariable Long id, @RequestBody Ubicacion detalles) {
        Optional<Ubicacion> ubicacionOptional = ubicacionRepository.findById(id);

        if (ubicacionOptional.isPresent()) {
            Ubicacion ubicacionExistente = ubicacionOptional.get();
            
            ubicacionExistente.setPatente(detalles.getPatente());
            ubicacionExistente.setLatitud(detalles.getLatitud());
            ubicacionExistente.setLongitud(detalles.getLongitud());
            ubicacionExistente.setFechaHora(detalles.getFechaHora());
            
            Ubicacion actualizada = ubicacionRepository.save(ubicacionExistente);
            return ResponseEntity.ok(actualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        if (ubicacionRepository.existsById(id)) {
            ubicacionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}