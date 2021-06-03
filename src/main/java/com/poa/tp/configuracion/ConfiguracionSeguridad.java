package com.poa.tp.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.poa.tp.seguridad.ConstantesSeguridad;
import com.poa.tp.seguridad.JWTFiltro;
import com.poa.tp.servicios.TokenService;

@EnableWebSecurity
@Configuration
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.addFilterAfter(new JWTFiltro(tokenService), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, ConstantesSeguridad.RUTA_LOGIN.getValor()).permitAll()
			.antMatchers(HttpMethod.POST, ConstantesSeguridad.RUTA_SIGN_UP.getValor()).permitAll()
			.anyRequest().authenticated();
			authenticationManager();
	}

}
