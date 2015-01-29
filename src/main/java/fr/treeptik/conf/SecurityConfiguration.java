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
		web.ignoring()
				.antMatchers("/bower_components/*/**", "i18n/**", "css/**",
						"*.css", "*.js")
				.antMatchers("/fonts/**")
				.antMatchers("/images/**")
				.antMatchers("/scripts/**")
				.antMatchers("/api-docs", "/api-docs/*", "/styles/**",
						"/user/signin", "/user/activate/userEmail/**");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
						"Select login, password, 'true' as enabled from Administrateur where login=? ")
				.authoritiesByUsernameQuery(
						"Select login, role From Administrateur where login=?");
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
						"Select login, password, 'true' as enabled from Client where login=? ")
				.authoritiesByUsernameQuery(
						"Select login, role From Client  where login=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		// .formLogin().loginPage("/login.jsp").usernameParameter("j_username")
		// .passwordParameter("j_password")
		// .defaultSuccessUrl("/client/list").failureUrl("/login.jsp")
		// .and().logout().logoutUrl("/logout")
		// .deleteCookies("JSESSIONID").permitAll().and().csrf().disable()
		// .authorizeRequests().antMatchers("/login.jsp").permitAll()
		// .antMatchers("/client/list").denyAll()
		// .antMatchers("/admininistrateur/**").hasRole("ADMIN").anyRequest()
		// .authenticated();
		// }

		// http
		// .exceptionHandling()
		// .authenticationEntryPoint(authenticationEntryPoint)
		// .and()
		// .formLogin()
		// .loginProcessingUrl("/user/authentication")
		// .successHandler(ajaxAuthenticationSuccessHandler)
		// .failureHandler(ajaxAuthenticationFailureHandler)
		// .usernameParameter("j_username")
		// .passwordParameter("j_password")
		// .permitAll()
		// .and()
		// .logout()
		// .logoutUrl("/user/logout")
		// .logoutSuccessHandler(ajaxLogoutSuccessHandler)
		// .deleteCookies("JSESSIONID")
		// .permitAll()
		// .and()
		.csrf().disable().headers().frameOptions().disable()
				.authorizeRequests().antMatchers("/messages/**")
				.hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.antMatchers("/admin/**").permitAll();
	}
}
