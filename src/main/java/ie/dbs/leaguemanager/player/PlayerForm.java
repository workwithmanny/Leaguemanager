package ie.dbs.leaguemanager.player;

import jakarta.validation.constraints.*;

public class PlayerForm {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Please select a position")
    private String position;

    @NotNull(message = "Shirt number is required")
    @Min(value = 1, message = "Shirt number must be at least 1")
    @Max(value = 99, message = "Shirt number cannot be more than 99")
    private Integer shirtNumber;

    @NotNull(message = "A team must be selected")
    private Long teamId;

    public PlayerForm() {
    }

    public PlayerForm(String firstName, String lastName, String position, Integer shirtNumber, Long teamId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.shirtNumber = shirtNumber;
        this.teamId = teamId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(Integer shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}