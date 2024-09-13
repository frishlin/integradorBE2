package com.example.IntegradorII.service;

import com.example.IntegradorII.entity.Paciente;
import com.example.IntegradorII.entity.Turno;
import com.example.IntegradorII.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private TurnoRepository turnoRepository;

    public Turno guardarTurno(Turno turno){
        return turnoRepository.save(turno);
    }
    public List<Turno> listarTurno(){
        return turnoRepository.findAll();
    }
    public void eliminarTurno(Long id){
        turnoRepository.deleteById(id);
    }
    public Optional<Turno> buscarPorID(Long id){ return turnoRepository.findById(id);}
}
