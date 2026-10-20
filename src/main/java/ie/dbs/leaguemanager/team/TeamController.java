package ie.dbs.leaguemanager.team;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public String listTeams(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        return "teams";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("team", new Team());
        return "team-form";
    }

    @PostMapping
    public String saveTeam(@RequestParam String name,
                           @RequestParam String managerName) {
        teamRepository.save(new Team(name, managerName));
        return "redirect:/teams";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("team", teamRepository.findById(id).orElseThrow());
        return "team-form";
    }

    @PostMapping("/{id}")
    public String updateTeam(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String managerName) {
        Team team = teamRepository.findById(id).orElseThrow();
        team.setName(name);
        team.setManagerName(managerName);
        teamRepository.save(team);
        return "redirect:/teams";
    }

    @PostMapping("/{id}/delete")
    public String deleteTeam(@PathVariable Long id) {
        teamRepository.deleteById(id);
        return "redirect:/teams";
    }
}