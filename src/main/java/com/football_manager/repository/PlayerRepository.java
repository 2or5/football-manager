package com.football_manager.repository;

import com.football_manager.entity.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Player> getAllPlayers() {
        return entityManager.createQuery("SELECT p from Player p", Player.class).getResultList();
    }

    public Optional<Player> getPlayerById(Integer id) {
        List<Player> results = entityManager.createQuery("SELECT p FROM Player p WHERE p.id = :id", Player.class)
                .setParameter("id", id)
                .getResultList();

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Player savePlayer(Player player) {
        entityManager.persist(player);
        return player;
    }

    public Player updatePlayer(Player player) {
        return entityManager.merge(player);
    }

    public Integer deletePlayer(Integer id) {
        return entityManager.createQuery("DELETE FROM Player p WHERE p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}