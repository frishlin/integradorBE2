package com.example.IntegradorII;

import com.example.IntegradorII.entity.Odontologo;
import com.example.IntegradorII.service.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OdontologoIntegracionTest {
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private MockMvc mockMvc;

    public void cargarOdontologo(){
        Odontologo odontologo1 = odontologoService.guardarOdontologo(new Odontologo("joel","Huenupi","151515fgfgfg"));
    }

    @Test
    public void listarOdontologo() throws Exception{
        cargarOdontologo();
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/odontologo")
                        .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String contenidoRespuesta = resultado.getResponse().getContentAsString();

        // Consultar al profe si seguimos unsando logger
        System.out.println("Odontologo agregado: " + contenidoRespuesta);
    }
}
