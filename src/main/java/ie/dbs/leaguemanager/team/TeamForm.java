package ie.dbs.leaguemanager.team;

import jakarta.validation.constraints.*;

public class TeamForm {

    @NotBlank(message = "Team name is required")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Manager name is required")
    @Size(min = 2, max = 50, message = "Manager name must be between 2 and 50 characters")
    private String managerName;

    public TeamForm() {
    }

    public TeamForm(String name, String managerName) {
        this.name = name;
        this.managerName = managerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}