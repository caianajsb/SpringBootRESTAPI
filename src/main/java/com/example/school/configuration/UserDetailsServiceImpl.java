package com.example.school.configuration;

import com.example.school.model.RoleModel;
import com.example.school.model.RoleName;
import com.example.school.model.UserModel;
import com.example.school.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        UserModel userModel = new UserModel();
        userModel.setPassword("$2a$10$jTmouezrspbJfnb9qSjGbOU.kSANCrgkhWdfVfKyaLlvQdhq2K77a");
        userModel.setUsername("admin");
        RoleModel roleModel = new RoleModel();
        roleModel.setRoleName(RoleName.ROLE_ADMIN);
        userModel.addRoleModel(roleModel);
        userRepository.save(userModel);

        userModel = new UserModel();
        userModel.setPassword("$2a$10$jTmouezrspbJfnb9qSjGbOU.kSANCrgkhWdfVfKyaLlvQdhq2K77a");
        userModel.setUsername("user");
        roleModel = new RoleModel();
        roleModel.setRoleName(RoleName.ROLE_USER);
        userModel.addRoleModel(roleModel);
        userRepository.save(userModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new User(userModel.getUsername(), userModel.getPassword(), true, true, true,true, userModel.getAuthorities());
    }
}