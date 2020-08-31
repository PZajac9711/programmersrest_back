package pl.programmersrest.blog.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.programmersrest.blog.authentication.entity.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    @Query("SELECT u FROM RefreshTokenEntity u WHERE LOWER(u.username) = LOWER(?1)")
    Optional<RefreshTokenEntity> findRefreshTokenEntitiesByUsername(String username);
}
