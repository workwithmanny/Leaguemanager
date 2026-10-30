package ie.dbs.leaguemanager.team;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }
    // This returns null when no team has that id

    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return teamRepository
                .findById(id)
                .orElse(null);
    }

    @Transactional
    public Team createTeam(String name, String managerName) {
        Team team = new Team(name, managerName);
        return teamRepository.save(team);
    }

    // load the whole team first or else it will have a duplicate
    @Transactional
    public Team updateTeam(Long id, String name, String managerName) {
        Team team = teamRepository
                .findById(id)
                .orElse(null);

        if (team == null) {
            return null;
        }

        team.setName(name);
        team.setManagerName(managerName);

        return teamRepository.save(team);
    }
    // false means the team wasn't found so nothing got deleted.
    @Transactional
    public boolean deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            return false;
        }

        teamRepository.deleteById(id);

        return true;
    }
}