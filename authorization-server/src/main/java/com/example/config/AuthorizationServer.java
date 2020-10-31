package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 写点注释吧
 * @Configuration 表示是一个配置类
 * @EnableAuthorizationServer 表示是一个授权服务
 *
 * @author gaozhilong
 * @date 2020/10/31 10:18
 * @since v1.0.0001
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 客户端的配置信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端编号
                .withClient("test1")
                // 客户端的密码
                .secret(bCryptPasswordEncoder.encode("secret"))
                // 重定向地址，携带授权码或token信息
                .redirectUris("http://www.baidu.com")
                // 用户自己选择是否授权
                .autoApprove(false)
                // 作用域
                .scopes("all")
                // 授权服务支持的类型(授权码模式 密码模式 客户端模式 简化模式 刷新token)
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")
                // 要访问资源的编号
                .resourceIds("c1");
    }

    /**
     * 授权服务的权限配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 资源服务发送的检查token是合法的请求,放过
                .checkTokenAccess("permitAll()")
                // 客户端发送的获取token的请求，放过
                .tokenKeyAccess("permitAll()")
                // 支持客户端的表单提交
                .allowFormAuthenticationForClients();
    }

    /**
     * 访问服务的配置信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 关联对应的认证器
        endpoints
                .authenticationManager(authenticationManagerBean)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
                // 设置token相关的服务
                .tokenServices(tokenServices());
    }

    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 存储方式
        tokenServices.setTokenStore(new InMemoryTokenStore());
        // 支持刷新
        tokenServices.setSupportRefreshToken(true);
        // token对应的客户端信息
        tokenServices.setClientDetailsService(clientDetailsService);
        // 默认的有效期
        tokenServices.setAccessTokenValiditySeconds(7200);
        return tokenServices;
    }
}
