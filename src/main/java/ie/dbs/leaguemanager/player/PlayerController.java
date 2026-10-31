package ie.dbs.leaguemanager.player;

import ie.dbs.leaguemanager.team.TeamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerController(PlayerService playerService,
                            TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping
    public String listPlayers(Model model) {
        model.addAttribute("players", playerService.getPlayers());
        return "player/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("playerForm", new PlayerForm());
        model.addAttribute("teams", teamService.getTeams());
        return "player/form";
    }

    @PostMapping
    public String createPlayer(@Valid @ModelAttribute("playerForm") PlayerForm playerForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.getTeams());
            return "player/form";
        }

        Player player = playerService.createPlayer(playerForm);

        if (player == null) {
            bindingResult.rejectValue("teamId", "team.invalid", "The selected team does not exist");
            model.addAttribute("teams", teamService.getTeams());
            return "player/form";
        }

        return "redirect:/players";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Player player = playerService.getPlayerById(id);

        if (player == null) {
            return "redirect:/players";
        }

        PlayerForm playerForm = new PlayerForm(
                player.getFirstName(),
                player.getLastName(),
                player.getPosition(),
                player.getShirtNumber(),
                player.getTeam().getId()
        );

        model.addAttribute("playerForm", playerForm);
        model.addAttribute("playerId", id);
        model.addAttribute("teams", teamService.getTeams());
        return "player/edit";
    }

    @PostMapping("/{id}")
    public String updatePlayer(@PathVariable Long id,
                               @Valid @ModelAttribute("playerForm") PlayerForm playerForm,
                               BindingResult bindingResult,
                               Model model) {
        if (playerService.getPlayerById(id) == null) {
            return "redirect:/players";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("playerId", id);
            model.addAttribute("teams", teamService.getTeams());
            return "player/edit";
        }

        Player player = playerService.updatePlayer(id, playerForm);

        if (player == null) {
            bindingResult.rejectValue("teamId", "team.invalid", "The selected team does not exist");
            model.addAttribute("playerId", id);
            model.addAttribute("teams", teamService.getTeams());
            return "player/edit";
        }

        return "redirect:/players";
    }

    @PostMapping("/{id}/delete")
    public String deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return "redirect:/players";
    }
}