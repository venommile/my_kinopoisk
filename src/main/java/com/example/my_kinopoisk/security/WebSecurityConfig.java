package com.example.my_kinopoisk.security;

//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//
//@EnableGlobalMethodSecurity(
//    securedEnabled = true,
//    jsr250Enabled = true,
//    prePostEnabled = true
//)
public class WebSecurityConfig {
//
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable()
//            .authorizeRequests()
//            .antMatchers("/").permitAll()
//            .anyRequest().authenticated()
//            .and().formLogin().permitAll()
//            .and().logout().permitAll();
//        http.headers().frameOptions().disable();
//    }

}
