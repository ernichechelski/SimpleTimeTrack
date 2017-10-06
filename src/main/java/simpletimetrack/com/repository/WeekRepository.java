package simpletimetrack.com.repository;

import simpletimetrack.com.domain.Week;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Week entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekRepository extends JpaRepository<Week,Long> {

    @Query("select week from Week week where week.user.login = ?#{principal.username}")
    List<Week> findByUserIsCurrentUser();

    @Override
    long count();
}
