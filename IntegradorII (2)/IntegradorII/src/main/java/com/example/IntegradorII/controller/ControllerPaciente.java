package com.example.IntegradorII.controller;

import com.example.IntegradorII.entity.Paciente;
import com.example.IntegradorII.exception.BadRequestException;
import com.example.IntegradorII.exception.ResourceNotFoundException;
import com.example.IntegradorII.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class ControllerPaciente {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente (@RequestBody Paciente paciente) throws BadRequestException {
        if (paciente.getEmail() == null || paciente.getEmail().trim().isEmpty()) {
            throw new BadRequestException("Falta el email del paciente: el campo no puede estar vacío o nulo.");
        } else {
            pacienteService.guardarPaciente(paciente);
            return ResponseEntity.ok(paciente);
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(){
        return ResponseEntity.ok(pacienteService.listarPaciente());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Paciente>> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado);
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente nuevoPaciente) {
        Optional<Paciente> pacienteExistente = pacienteService.buscarPorID(id);
        if(pacienteExistente.isPresent()) {
            Paciente pacienteActualizado = pacienteExistente.get();
            pacienteActualizado.setNombre(nuevoPaciente.getNombre());
            pacienteActualizado.setApellido(nuevoPaciente.getApellido());
            pacienteActualizado.setDomicilio(nuevoPaciente.getDomicilio());
            pacienteActualizado.setOdontologo(nuevoPaciente.getOdontologo());
            // Actualizar otros campos si es necesario
            pacienteService.guardarPaciente(pacienteActualizado);
            return ResponseEntity.ok("Paciente actualizado");
        } else {
            return ResponseEntity.badRequest().body("Paciente no encontrado");
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Paciente eliminado");
        }else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}
