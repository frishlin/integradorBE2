package com.example.IntegradorII.repository;

import com.example.IntegradorII.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    //PRofesor agrego un metodo para buscar por el correo
    Optional<Paciente> findByEmail(String correo);

    //Método para buscar pasientes asignados a un odontólogo
    List<Paciente> findByOdontologoId(Long odontologoId);

}
