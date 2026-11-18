package ie.dbs.leaguemanager.home;

import ie.dbs.leaguemanager.player.PlayerService;
import ie.dbs.leaguemanager.team.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public HomeController(TeamService teamService,
                          PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("teamCount", teamService.getTeams().size());
        model.addAttribute("playerCount", playerService.getPlayers().size());
        return "index";
    }
}