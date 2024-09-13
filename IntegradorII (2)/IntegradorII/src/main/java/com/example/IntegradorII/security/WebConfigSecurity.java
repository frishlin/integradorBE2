package com.example.IntegradorII.security;

import com.example.IntegradorII.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.rmi.server.ExportException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Activa seguridad
public class WebConfigSecurity {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean // Metodo que nos ayuda con autenticación
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz)->authz
                        //USER y ADMIN pueden acceder a la página de inicio
                        .requestMatchers("/", "/index.html").hasAnyRole("USER", "ADMIN")
                        //USER y ADMIN pueden gestionar turnos
                        .requestMatchers("/get_turnos.html", "/post_turnos.html", "/turno/**").hasAnyRole("USER", "ADMIN")
                        //USER y ADMIN acceder a archivos estáticos
                        .requestMatchers("/js/**", "/css/**", "/img/**").permitAll()
                        //ADMIN controla la gestión de pacientes y odontólogos
                        .requestMatchers("/get_pacientes.html", "/post_pacientes.html", "/get_odontologos.html", "/post_odontologos.html").hasRole("ADMIN")
                        //Cualquier ruta será accedida por el ADMIN
                        .requestMatchers("/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(withDefaults())
                .logout(withDefaults());
        return http.build();
    }
}
