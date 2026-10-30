package ie.dbs.leaguemanager.team;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "teams";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("team", new Team());
        return "team-form";
    }

    @PostMapping
    public String createTeam(@RequestParam String name,
                             @RequestParam String managerName) {
        teamService.createTeam(name, managerName);
        return "redirect:/teams";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Team team = teamService.getTeamById(id);

        if (team == null) {
            return "redirect:/teams";
        }

        model.addAttribute("team", team);
        return "team-form";
    }

    @PostMapping("/{id}")
    public String updateTeam(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String managerName) {
        teamService.updateTeam(id, name, managerName);
        return "redirect:/teams";
    }

    @PostMapping("/{id}/delete")
    public String deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return "redirect:/teams";
    }
}