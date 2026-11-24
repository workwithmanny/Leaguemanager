package ie.dbs.leaguemanager.team;

import ie.dbs.leaguemanager.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import ie.dbs.leaguemanager.player.Player;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository,
                       PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
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

    @Transactional(readOnly = true)
    public boolean hasPlayers(Long id) {
        return playerRepository.existsByTeamId(id);
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

    @Transactional(readOnly = true)
    public List<Player> getSquad(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    @Transactional(readOnly = true)
    public List<Player> getAvailablePlayers(Long teamId) {
        return playerRepository.findByTeamIdNot(teamId);
    }

    // moves the player onto this team, returns false if the team or player doesn't exist
    @Transactional
    public boolean assignPlayer(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        Player player = playerRepository.findById(playerId).orElse(null);

        if (team == null || player == null) {
            return false;
        }

        player.setTeam(team);
        playerRepository.save(player);
        return true;
    }

}