package com.trade.security.service;

import com.trade.common.exception.BusinessException;
import com.trade.common.enums.ResultCodeEnum;
// import com.trade.user.feign.UserFeignClient; // 假设存在用户服务的Feign客户端
// import com.trade.user.dto.UserDTO; // 假设用户服务返回的用户DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户详细信息服务实现类
 * <p>
 * 该类负责从用户服务加载用户详细信息，用于Spring Security的认证过程。
 * </p>
 *
 * @author Trade Team
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // @Autowired
    // private UserFeignClient userFeignClient; // 注入用户服务的Feign客户端，用于远程调用获取用户信息

    /**
     * 根据用户名加载用户详细信息。
     *
     * @param username 用户名
     * @return UserDetails 用户详细信息对象
     * @throws UsernameNotFoundException 如果用户未找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟通过Feign客户端调用用户服务获取用户信息
        // 在实际项目中，这里会调用 userFeignClient.getUserByUsername(username) 等方法
        // UserDTO userDTO = userFeignClient.getUserByUsername(username).getData(); // 假设Feign接口返回Result<UserDTO>

        // ---- 以下为模拟数据，实际项目中应替换为真实的Feign调用 ----
        com.trade.security.model.User mockUser = findMockUserByUsername(username);
        if (mockUser == null) {
            throw new UsernameNotFoundException(ResultCodeEnum.USER_NOT_EXIST.getMessage());
        }
        // ---- 模拟数据结束 ----

        // if (userDTO == null) {
        //     throw new UsernameNotFoundException(ResultCodeEnum.USER_NOT_EXIST.getMessage());
        // }

        // 获取用户权限信息
        // Set<String> permissions = userFeignClient.getUserPermissions(userDTO.getId()).getData(); // 假设Feign接口返回Result<Set<String>>
        // Set<GrantedAuthority> authorities = permissions.stream()
        //         .map(SimpleGrantedAuthority::new)
        //         .collect(Collectors.toSet());

        // ---- 以下为模拟权限数据 ----
        Set<GrantedAuthority> authorities = new HashSet<>();
        if ("admin".equals(mockUser.getUsername())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("user:list"));
            authorities.add(new SimpleGrantedAuthority("user:create"));
            authorities.add(new SimpleGrantedAuthority("user:update"));
            authorities.add(new SimpleGrantedAuthority("user:delete"));
        } else if ("user".equals(mockUser.getUsername())){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("order:create"));
            authorities.add(new SimpleGrantedAuthority("order:list"));
        }
        // ---- 模拟权限数据结束 ----


        return new User(
                mockUser.getUsername(),
                mockUser.getPassword(),
                mockUser.isEnabled(),
                mockUser.isAccountNonExpired(),
                mockUser.isCredentialsNonExpired(),
                mockUser.isAccountNonLocked(),
                authorities
        );
    }

    /**
     * 模拟根据用户名查找用户（实际项目中应通过数据库或用户服务获取）
     *
     * @param username 用户名
     * @return 模拟的用户对象，如果未找到则返回null
     */
    private com.trade.security.model.User findMockUserByUsername(String username) {
        // 实际项目中，这里应该调用
        // com.trade.user.mapper.UserMapper.selectOne(new QueryWrapper<com.trade.user.model.User>().eq("username", username));
        // 或者通过 Feign Client 调用用户微服务
        if ("admin".equals(username)) {
            com.trade.security.model.User adminUser = new com.trade.security.model.User();
            adminUser.setId(1L);
            adminUser.setUsername("admin");
            // 密码 "password" 使用 BCryptPasswordEncoder 加密后的结果, 在实际应用中，数据库存储的应该是加密后的密码
            // 可以使用 new BCryptPasswordEncoder().encode("password") 生成
            adminUser.setPassword("$2a$10$EipD5Q5X.YR4V2/A9A7hUuL9gN.o0g8f21nQn2N1n.B0g8f21nQn"); // 假设这是 "password" 加密后的值
            adminUser.setEnabled(true);
            adminUser.setAccountNonExpired(true);
            adminUser.setCredentialsNonExpired(true);
            adminUser.setAccountNonLocked(true);
            return adminUser;
        } else if ("user".equals(username)) {
            com.trade.security.model.User normalUser = new com.trade.security.model.User();
            normalUser.setId(2L);
            normalUser.setUsername("user");
            normalUser.setPassword("$2a$10$EipD5Q5X.YR4V2/A9A7hUuL9gN.o0g8f21nQn2N1n.B0g8f21nQn"); // 假设这是 "password" 加密后的值
            normalUser.setEnabled(true);
            normalUser.setAccountNonExpired(true);
            normalUser.setCredentialsNonExpired(true);
            normalUser.setAccountNonLocked(true);
            return normalUser;
        }
        return null;
    }
}