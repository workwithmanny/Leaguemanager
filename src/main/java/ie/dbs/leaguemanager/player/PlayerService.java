package ie.dbs.leaguemanager.player;

import ie.dbs.leaguemanager.team.Team;
import ie.dbs.leaguemanager.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository,
                         TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player getPlayerById(Long id) {
        return playerRepository
                .findById(id)
                .orElse(null);
    }

    @Transactional
    public Player createPlayer(PlayerForm form) {
        Team team = teamRepository
                .findById(form.getTeamId())
                .orElse(null);

        if (team == null) {
            return null;
        }

        Player player = new Player(
                form.getFirstName(),
                form.getLastName(),
                form.getPosition(),
                form.getShirtNumber(),
                team
        );

        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(Long id, PlayerForm form) {
        Player player = playerRepository
                .findById(id)
                .orElse(null);

        if (player == null) {
            return null;
        }

        Team team = teamRepository
                .findById(form.getTeamId())
                .orElse(null);

        if (team == null) {
            return null;
        }

        player.setFirstName(form.getFirstName());
        player.setLastName(form.getLastName());
        player.setPosition(form.getPosition());
        player.setShirtNumber(form.getShirtNumber());
        player.setTeam(team);

        return playerRepository.save(player);
    }

    @Transactional
    public boolean deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            return false;
        }

        playerRepository.deleteById(id);

        return true;
    }
}