package in.ankit.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.ankit.entity.RoleEntity;
import in.ankit.repository.RoleRepository;
import in.ankit.repository.UserRepository;
import in.ankit.service.UserService;


@Configuration
public class DefaultDataConfig {

    private static final String DEFAULT_USER_ROLE = "ROLE_USER";
    private static final String DEFAULT_ADMIN_ROLE = "ROLE_ADMIN";

    @Bean
    public CommandLineRunner setUpDefaultData(RoleRepository roleRepository, UserRepository userRepository,
                                       UserService userService) {
        return args -> {
            if (roleRepository.findByRole(DEFAULT_ADMIN_ROLE) == null)
                roleRepository.save(new RoleEntity(DEFAULT_ADMIN_ROLE));

            if (userRepository.findByUsername("admin") == null)
                userService.createDefaultUser("admin", DEFAULT_ADMIN_ROLE);

            if (roleRepository.findByRole(DEFAULT_USER_ROLE) == null)
                roleRepository.save(new RoleEntity(DEFAULT_USER_ROLE));

            if (userRepository.findByUsername("user") == null) {
                userService.createDefaultUser("user", DEFAULT_USER_ROLE);
            }
        };
    }

}
