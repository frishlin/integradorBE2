package com.example.IntegradorII.controller;

import com.example.IntegradorII.entity.Odontologo;
import com.example.IntegradorII.exception.BadRequestException;
import com.example.IntegradorII.exception.ResourceNotFoundException;
import com.example.IntegradorII.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class ControllerOdontologo {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty()) {
            throw new BadRequestException("Falta el número de Matrícula: el campo no puede estar vacío o nulo. ");
        } else {
            odontologoService.guardarOdontologo(odontologo);
            return ResponseEntity.ok(odontologo);
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarOdontologo(){
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado);
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarOdontologo(@PathVariable Long id, @RequestBody Odontologo nuevoOdontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoExistente = odontologoService.buscarPorID(id);
        if(odontologoExistente.isPresent()) {
            Odontologo odontologoActualizado = odontologoExistente.get();
            odontologoActualizado.setNombre(nuevoOdontologo.getNombre());
            odontologoActualizado.setApellido(nuevoOdontologo.getApellido());
            odontologoActualizado.setMatricula(nuevoOdontologo.getMatricula());
            // Actualizar otros campos si es necesario
            odontologoService.guardarOdontologo(odontologoActualizado);
            return ResponseEntity.ok("Odontologo actualizado");
        } else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado");
        }else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }
}
