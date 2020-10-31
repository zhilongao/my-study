package com.example.service.impl;


import com.example.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/10/27 19:05
 * @since v1.0.0001
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!"zhang".equals(s)) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_ROOT");
        SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_CREATE");
        authorities.add(auth1);
        authorities.add(auth2);
        // 密码
        String passWord = "$2a$10$MP.R5ft3FdCKOe0bDX.Qh.F23FitE.FS3I4Tg9AsNsHqotZbiVVwq";
        // 用户是否可用
        boolean enable = true;
        // 账号是否失效(未失效:true)
        boolean accountNonExpired = true;
        // 密钥是否失效(未失效:true)
        boolean credentialsNonExpired = true;
        // 账号是否锁定(未锁定:true)
        boolean accountNonLocked = true;
        UserDetails user = new User(s
                ,passWord
                ,enable
                ,accountNonExpired
                ,credentialsNonExpired
                ,accountNonLocked
                ,authorities);
        return user;
    }
}
