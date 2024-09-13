package com.example.IntegradorII.repository;

import com.example.IntegradorII.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {
    //PRofesor agrego un metodo para buscar por le correo
    Optional<Odontologo> findByMatricula(String matricula);
}
