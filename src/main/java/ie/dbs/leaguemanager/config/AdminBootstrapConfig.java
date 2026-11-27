package ie.dbs.leaguemanager.config;

import ie.dbs.leaguemanager.user.UserAccount;
import ie.dbs.leaguemanager.user.UserAccountRepository;
import ie.dbs.leaguemanager.user.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    public CommandLineRunner createInitialAdmin(
            UserAccountRepository repository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap.admin-password:}") String initialPassword) {
        return args -> {
            if (repository.existsByRole(UserRole.ADMIN)) {
                return;
            }

            if (initialPassword == null || initialPassword.isBlank()) {
                throw new IllegalStateException(
                        "No administrator exists. Set APP_BOOTSTRAP_ADMIN_PASSWORD.");
            }

            UserAccount admin = new UserAccount(
                    "admin",
                    passwordEncoder.encode(initialPassword),
                    UserRole.ADMIN);

            repository.save(admin);
        };
    }
}