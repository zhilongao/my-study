/*
package com.example.config;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import javax.sql.DataSource;

*/
/**
 * 写点注释吧
 * @Configuration 配置类
 * @EnableWebSecurity 开启security服务
 * @EnableGlobalMethodSecurity 开启全局security注解
 *
 * @author gaozhilong
 * @date 2020/10/27 19:04
 * @since v1.0.0001
 *//*

// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)// 支持spring表达式注解
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private SessionInformationExpiredStrategy expiredSessionStrategy;
    */
/**
     * 自定义认证管理器
     * @param auth
     * @throws Exception
     *//*

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        */
/*
        auth.inMemoryAuthentication()
                .withUser("zhang")
                .password("{noop}111")
                .roles("USER");
        *//*

        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
    //


    */
/**
     * 自定义过滤器链
     * @param http
     * @throws Exception
     *//*

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        // 设置哪些页面可以直接访问，哪些需要验证
        http.authorizeRequests()
                // 放过
                .antMatchers("/login.html","/error.html").permitAll()
                // 针对单独的url授权
                .antMatchers("/order/order1").hasRole("USER")
                .antMatchers("/order/order2", "/order/order3").hasRole("CREATE")
                // 剩下的所有的地址都是需要在认证状态下才可以访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 指定指定要的登录页面
                .loginPage("/login.html")
                // 处理认证路径的请求
                .loginProcessingUrl("/login.do")
                .defaultSuccessUrl("/home.html")
                .failureForwardUrl("/error.html")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                // 开启remember me功能
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository)
                // 禁用csrf
                //.and().csrf().disable()
                ;
        // 单用户登陆
        // http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
        // http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    */
/**
     * 持久化token
     * 在security中，默认使用的是PersistentTokenRepository的子类InMemoryTokenRepositoryImpl，将token放入到内存中
     * 若是使用JdbcTokenRepositoryImpl，会创建表 persistent_logins,将token持久化到数据库中
     * @return
     *//*

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public SessionInformationExpiredStrategy expiredSessionStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/logout");
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
*/
