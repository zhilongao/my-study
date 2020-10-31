oauth2.0 授权服务  提供获取token 验证token的服务
授权码模式
    1.获取授权码
        http://localhost:8080/oauth/authorize?client_id=test1&client_secret=secret&response_type=code
    2.获取token
        http://localhost:8080/oauth/token?client_id=test1&client_secret=secret&grant_type=authorization_code&code=cG3Ipz
        
简化模式
        http://localhost:8080/oauth/authorize?client_id=test1&client_secret=secret&response_type=token    
        
密码模式
        http://localhost:8080/oauth/token?client_id=test1&client_secret=secret&grant_type=password&username=gao&password=123456
      
客户端模式        
        http://localhost:8080/oauth/token?client_id=test1&client_secret=secret&grant_type=client_credentials                