package com.parkingcontrol.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl serviceImpl;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.httpBasic()
			.and()
//			.authorizeRequests(auth -> auth.anyRequest().permitAll()); //permitir el acceso de cualquier suario
			.authorizeHttpRequests()
			.antMatchers(HttpMethod.GET,"/parking-spot/**").permitAll()
			.antMatchers(HttpMethod.POST,"/parking-spot/create").hasRole("USER")
			.antMatchers(HttpMethod.DELETE,"/parking-spot/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.csrf().disable(); //requiere loguearse
		
	}
	
	

	//para usar con un usuario estatico
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth
//			.inMemoryAuthentication()
//			.withUser("nian")
//			.password(passwordEncoder().encode("8203"))
//			.roles("USER");
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
			.userDetailsService(serviceImpl)
			.passwordEncoder(passwordEncoder());
	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
}
