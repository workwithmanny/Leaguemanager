package ie.dbs.leaguemanager.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserAccount> findAll() {
        return repository.findAll();
    }

    @Transactional
    public UserAccount createUser(CreateUserForm form) {
        String username = form.getUsername().trim().toLowerCase(Locale.ROOT);

        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        String passwordHash = passwordEncoder.encode(form.getPassword());

        UserAccount account = new UserAccount(username, passwordHash, form.getRole());
        return repository.save(account);
    }

    @Transactional
    public void setEnabled(Long id, boolean enabled) {
        UserAccount account = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (account.getRole() == UserRole.ADMIN) {
            throw new IllegalArgumentException("Administrator accounts cannot be disabled");
        }

        account.setEnabled(enabled);
    }
}