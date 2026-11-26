package ie.dbs.leaguemanager.team;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public String listTeams(Model model) {
        model.addAttribute("teams", teamService.getTeams());
        return "team/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("teamForm", new TeamForm());
        return "team/form";
    }

    @PostMapping
    public String createTeam(@Valid @ModelAttribute("teamForm") TeamForm teamForm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "team/form";
        }

        teamService.createTeam(teamForm);
        return "redirect:/teams";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id);

        // wrong id or deleted team so just send them back to the list
        if (team == null) {
            return "redirect:/teams";
        }

        TeamForm teamForm = new TeamForm(team.getName(), team.getManagerName());

        model.addAttribute("teamForm", teamForm);
        model.addAttribute("teamId", id);
        model.addAttribute("squad", teamService.getSquad(id));
        model.addAttribute("availablePlayers", teamService.getAvailablePlayers(id));
        return "team/edit";
    }

    @PostMapping("/{id}")
    public String updateTeam(@PathVariable Long id,
                             @Valid @ModelAttribute("teamForm") TeamForm teamForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teamId", id);
            model.addAttribute("squad", teamService.getSquad(id));
            model.addAttribute("availablePlayers", teamService.getAvailablePlayers(id));
            return "team/edit";
        }

        teamService.updateTeam(id, teamForm);
        return "redirect:/teams";
    }

    @PostMapping("/{id}/players")
    public String assignPlayer(@PathVariable Long id, @RequestParam Long playerId) {
        teamService.assignPlayer(id, playerId);
        return "redirect:/teams/" + id + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String deleteTeam(@PathVariable Long id) {
        if (teamService.hasPlayers(id)) {
            return "redirect:/teams?deleteError";
        }

        teamService.deleteTeam(id);
        return "redirect:/teams";
    }
}