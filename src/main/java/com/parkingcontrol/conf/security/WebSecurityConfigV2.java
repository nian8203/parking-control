package com.parkingcontrol.conf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigV2 {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.httpBasic()
		.and()
//		.authorizeRequests(auth -> auth.anyRequest().permitAll()); //permitir el acceso de cualquier suario
		.authorizeHttpRequests()
		//comentar para implementar la seugridad a traves de anotaciones en el controlador y add @EnableGlobalMethodSecurity
//		.antMatchers(HttpMethod.GET,"/parking-spot/**").permitAll()
//		.antMatchers(HttpMethod.POST,"/parking-spot/create").hasRole("USER")
//		.antMatchers(HttpMethod.DELETE,"/parking-spot/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
		.csrf().disable(); //requiere loguearse
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
}
