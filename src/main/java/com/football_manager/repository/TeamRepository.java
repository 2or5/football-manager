package com.football_manager.repository;

import com.football_manager.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Method get all teams.
     *
     * @return list of {@link Team}.
     */
    public List<Team> getAllTeams() {
        return entityManager.createQuery("SELECT t FROM Team t LEFT JOIN FETCH t.players", Team.class)
                .getResultList();
    }

    /**
     * Method get optional team by id.
     *
     * @param id {@link Integer}
     * @return {@link Team}.
     */
    public Optional<Team> getTeamById(Integer id) {
        List<Team> results = entityManager.createQuery("SELECT t FROM Team t WHERE t.id = :id", Team.class)
                .setParameter("id", id)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    /**
     * Method for create new team.
     *
     * @param team {@link Team}
     * @return {@link Team}.
     */
    public Team saveTeam(Team team) {
        entityManager.persist(team);
        return team;
    }

    /**
     * Method for update team.
     *
     * @param team {@link Team}
     * @return {@link Team}.
     */
    public Team updateTeam(Team team) {
        return entityManager.merge(team);
    }

    /**
     * Method for delete team by id.
     *
     * @param id {@link Integer}
     * @return {@link Integer}.
     */
    public Integer deleteTeam(Integer id) {
        entityManager.createQuery("UPDATE Player p SET p.team.id = NULL WHERE p.team.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        return entityManager.createQuery("DELETE FROM Team t WHERE t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
