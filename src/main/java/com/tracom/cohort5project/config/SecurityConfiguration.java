package com.tracom.cohort5project.config;


import com.meeting.planner.User;
import com.meeting.planner.UserRepository;
import com.tracom.cohort5project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	private UserRepository userRepository;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				 "/registration**",
	                "/js/**",
	                "/css/**",
	                "/img/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}


	@GetMapping("/list_users")
	public String viewUsersList(Model model){

		List<User> listUsers = userRepository.findAll();
		model.addAttribute("listUsers", listUsers);

		return "list_users";
	}

	@GetMapping("/add_users")
	public String addUser(Model model){

		model.addAttribute("user", new User());

		return "add_users";
	}

	@RequestMapping("/delete_user/{id}")
	public String deleteUser(@PathVariable(name = "id") int id){
		userRepository.deleteById(id);
		return "list_users";
	}

	/**
	 * Create a POSTMAPPING function to save users
	 */

	@PostMapping("/save_user")
	public String addNewUser(User web_user){
		userRepository.save(web_user);
		return "list_users";
	}

	/**
	 * Edit users
	 */

	@RequestMapping("/edit_user/{id}")
	public ModelAndView showEditUserForm(@PathVariable(name = "id") int id){

		ModelAndView mnv = new ModelAndView("edit_users");

		//User object
		User user = userRepository.getById(id);
		mnv.addObject("editUser", user);

		return mnv;
	}

}
