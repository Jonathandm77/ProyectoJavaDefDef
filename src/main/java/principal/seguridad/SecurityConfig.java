package principal.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import principal.modelo.Rol;
import principal.modelo.Usuario;
import principal.servicio.implementacion.UsuarioServiceImpl;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	UsuarioServiceImpl userDetailsService;
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
	@Override
    protected void configure(HttpSecurity http) throws Exception {
           
            http.authorizeRequests()
                    .antMatchers("/alumnos/**","/coches/**","/profesores/**").hasAnyRole("ADMIN","TEACHER").antMatchers("/calendario/**","/seguridad/**")
                    .hasAnyRole("ADMIN","TEACHER","USER").antMatchers("/seguridad/password#operat","/usuarios/**").hasRole("ADMIN").antMatchers("/miPerfil/**")
                    .hasRole("USER").antMatchers("/misAlumnos/**").hasRole("TEACHER")       //definimos que roles pueden acceder a cada URL      
                    .and()
                    .formLogin()
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")//si intenta acceder sin permisos se le redirige a login
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .and()
                    .logout()
                        .permitAll()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login/logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                        .and()
                   
                    .csrf().disable();
           
    }
	

	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    }
}
