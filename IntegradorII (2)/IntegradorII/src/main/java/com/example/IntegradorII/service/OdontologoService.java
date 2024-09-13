package com.example.IntegradorII.service;
import com.example.IntegradorII.entity.Paciente;
import com.example.IntegradorII.exception.BadRequestException;
import com.example.IntegradorII.entity.Domicilio;
import com.example.IntegradorII.entity.Odontologo;
import com.example.IntegradorII.repository.OdontologoRepository;
import com.example.IntegradorII.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }
    public List<Odontologo> listarOdontologos(){
        return odontologoRepository.findAll();
    }
    public void eliminarOdontologo(Long id) throws BadRequestException {
        // Verifica si el odontólogo tiene pacientes asignados
        List<Paciente> pacientes = pacienteRepository.findByOdontologoId(id);
        if (!pacientes.isEmpty()) {
            throw new BadRequestException("No se puede eliminar el odontólogo, tiene pacientes asignados.");
        }

        // Si no tiene pacientes, el odontólogo puede ser eliminado
        odontologoRepository.deleteById(id);
    }
    public Optional<Odontologo> buscarPorID(Long id){ return odontologoRepository.findById(id);}
}
