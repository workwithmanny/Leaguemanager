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
    public Team createTeam(TeamForm form) {
        Team team = new Team(form.getName(), form.getManagerName());
        return teamRepository.save(team);
    }

    @Transactional
    public Team updateTeam(Long id, TeamForm form) {
        // load the whole team first or else it will have a duplicate
        Team team = teamRepository
                .findById(id)
                .orElse(null);

        if (team == null) {
            return null;
        }

        team.setName(form.getName());
        team.setManagerName(form.getManagerName());

        return teamRepository.save(team);
    }

    // returns true if the team was deleted, false if no team had that id
    @Transactional
    public boolean deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            return false;
        }

        teamRepository.deleteById(id);

        return true;
    }
}