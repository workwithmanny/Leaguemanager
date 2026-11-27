package ie.dbs.leaguemanager.user;

import jakarta.persistence.*;

@Entity
@Table(
        name = "user_account",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_account_username",
                columnNames = "username"
        )
)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    // the system stores the scrambled password not the real one and its safe if the database is seen
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;
    // lets the admin turn an account off without deleting it, disabled users cant login
    @Column(nullable = false)
    private boolean enabled = true;

    protected UserAccount() {
    }

    public UserAccount(String username, String passwordHash, UserRole role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}