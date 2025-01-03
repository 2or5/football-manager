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

    public List<Team> getAllTeams() {
        return entityManager.createQuery("SELECT t FROM Team t", Team.class).getResultList();
    }

    public Optional<Team> getTeamById(Integer id) {
        List<Team> results = entityManager.createQuery("SELECT t FROM Team t WHERE t.id = :id", Team.class)
                .setParameter("id", id)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Team saveTeam(Team team) {
        entityManager.persist(team);
        return team;
    }

    public Team updateTeam(Team team) {
        return entityManager.merge(team);
    }

    public Integer deleteTeam(Integer id) {
        return entityManager.createQuery("DELETE FROM Team t WHERE t.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
