package com.example.IntegradorII.service;

import com.example.IntegradorII.entity.Odontologo;
import com.example.IntegradorII.entity.Paciente;
import com.example.IntegradorII.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }
    public List<Paciente> listarPaciente(){
        return pacienteRepository.findAll();
    }
    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }
    public Optional<Paciente> buscarPorID(Long id){ return pacienteRepository.findById(id);}
}
