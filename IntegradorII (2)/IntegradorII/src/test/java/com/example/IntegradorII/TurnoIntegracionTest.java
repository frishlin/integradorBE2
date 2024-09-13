package com.example.IntegradorII;

import com.example.IntegradorII.entity.Domicilio;
import com.example.IntegradorII.entity.Odontologo;
import com.example.IntegradorII.entity.Paciente;
import com.example.IntegradorII.entity.Turno;
import com.example.IntegradorII.service.OdontologoService;
import com.example.IntegradorII.service.PacienteService;
import com.example.IntegradorII.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.mockito.internal.handler.MockHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnoIntegracionTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private MockMvc mockMvc;

    public void cargarTurnos(){
        Paciente paciente1 = pacienteService.guardarPaciente(new Paciente("Joel","Huenupi","188888888", LocalDate.of(2024,9,12),new Domicilio("Calle Salvador",111,"Santiago","La reina"),"joelhuenupi@gmail.com"));
        Odontologo odontologo1 = odontologoService.guardarOdontologo(new Odontologo("Marisol","Juarez","4444444444"));
        Turno turno1 = turnoService.guardarTurno(new Turno(paciente1,odontologo1,LocalDate.of(2024,9,12)));
    }


    @Test
    public void listarTurnos() throws Exception{
        cargarTurnos();
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                .get("/turno")
                .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
