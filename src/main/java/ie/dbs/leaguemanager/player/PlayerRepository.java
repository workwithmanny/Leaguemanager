package ie.dbs.leaguemanager.player;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    boolean existsByTeamId(Long teamId);
    List<Player> findByTeamId(Long teamId);
    List<Player> findByTeamIdNot(Long teamId);
}