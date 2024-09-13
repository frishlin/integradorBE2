package com.example.IntegradorII.controller;

import com.example.IntegradorII.entity.Odontologo;
import com.example.IntegradorII.entity.Paciente;
import com.example.IntegradorII.entity.Turno;
import com.example.IntegradorII.exception.BadRequestException;
import com.example.IntegradorII.exception.ResourceNotFoundException;
import com.example.IntegradorII.service.OdontologoService;
import com.example.IntegradorII.service.PacienteService;
import com.example.IntegradorII.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class ControllerTurno {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Turno> registrarTurno(@RequestBody Turno turno) throws BadRequestException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorID(turno.getOdontologo().getId());
        if(pacienteBuscado.isPresent()){
            if(odontologoBuscado.isPresent()){
                return ResponseEntity.ok(turnoService.guardarTurno( turno));
            }else{
                throw new BadRequestException("Odontologo no encontrado");}
        }else{
            throw new BadRequestException("Paciente no encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurno());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Turno>> buscarTurnoId(@PathVariable Long id) throws ResourceNotFoundException{
        Optional<Turno> turnoExistente = turnoService.buscarPorID(id);
        if(turnoExistente.isPresent()) {
            return ResponseEntity.ok(turnoService.buscarPorID(id));
        }else{
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarTurno(@PathVariable Long id, @RequestBody Turno nuevoTurno) throws ResourceNotFoundException {
        Optional<Turno> turnoExistente = turnoService.buscarPorID(id);
        Optional<Paciente> pacienteNuevo = pacienteService.buscarPorID(nuevoTurno.getPaciente().getId());
        Optional<Odontologo> odontologoNuevo = odontologoService.buscarPorID(nuevoTurno.getOdontologo().getId());
        if(turnoExistente.isPresent()) {
            Turno turnoActualizado = turnoExistente.get();
            if(pacienteNuevo.isPresent()) { // Verificar si el nuevo paciente existe
                turnoActualizado.setPaciente(pacienteNuevo.get());
            } else {
                throw new ResourceNotFoundException("Paciente no encontrado");
            }
            if(odontologoNuevo.isPresent()) {// Verificar si el nuevo odontólogo existe
                turnoActualizado.setOdontologo(odontologoNuevo.get());
            } else {
                throw new ResourceNotFoundException("Odontólogo no encontrado");
            }
            turnoActualizado.setFecha(nuevoTurno.getFecha());// Actualizar otros campos del turno si es necesario
            turnoService.guardarTurno(turnoActualizado);
            return ResponseEntity.ok("Turno actualizado correctamente con nuevo paciente y odontólogo");
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Turno> turnoBuscado= turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");
        }else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }
}
