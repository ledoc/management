package fr.treeptik.conf;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Inject
	private DataSource dataSource;

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/*/**");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
						"Select login, motDePasse, 'true' as enabled from Administrateur where login=? ")
				.authoritiesByUsernameQuery(
						"Select login, role from Administrateur where login=?");
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
						"Select login, motDePasse, 'true' as enabled from Client where login=? ")
				.authoritiesByUsernameQuery(
						"Select login, role From Client  where login=?");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated().and().formLogin()
				.passwordParameter("j_password")
				.usernameParameter("j_username")
				.defaultSuccessUrl("/client/list")
				.loginProcessingUrl("/authentification").permitAll()
				.loginPage("/loginform.jsp").permitAll().and().csrf().disable()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}
}
