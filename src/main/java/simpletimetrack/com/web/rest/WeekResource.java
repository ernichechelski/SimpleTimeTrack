package simpletimetrack.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import simpletimetrack.com.domain.Week;

import simpletimetrack.com.repository.WeekRepository;
import simpletimetrack.com.security.AuthoritiesConstants;
import simpletimetrack.com.security.SecurityUtils;
import simpletimetrack.com.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing Week.
 */
@RestController
@RequestMapping("/api")
public class WeekResource {

    private final Logger log = LoggerFactory.getLogger(WeekResource.class);

    private static final String ENTITY_NAME = "week";

    private final WeekRepository weekRepository;

    public WeekResource(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }

    /**
     * POST  /weeks : Create a new week.
     *
     * @param week the week to create
     * @return the ResponseEntity with status 201 (Created) and with body the new week, or with status 400 (Bad Request) if the week has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/weeks")
    @Timed
    public ResponseEntity<Week> createWeek(@RequestBody Week week) throws URISyntaxException {
        log.debug("REST request to save Week : {}", week);
        if (week.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new week cannot already have an ID")).body(null);
        }
        if(!week.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin()) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new week cannot be assigned to someone else")).body(null);
        }
        Week result = weekRepository.save(week);
        return ResponseEntity.created(new URI("/api/weeks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /weeks : Updates an existing week.
     *
     * @param week the week to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated week,
     * or with status 400 (Bad Request) if the week is not valid,
     * or with status 500 (Internal Server Error) if the week couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/weeks")
    @Timed
    public ResponseEntity<Week> updateWeek(@RequestBody Week week) throws URISyntaxException {
        log.debug("REST request to update Week : {}", week);
        if (week.getId() == null) {
            return createWeek(week);
        }
        if(!week.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin()) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new week cannot be assigned to someone else")).body(null);
        }
        Week result = weekRepository.save(week);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, week.getId().toString()))
            .body(result);
    }

    /**
     * GET  /weeks : get all the weeks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of weeks in body
     */
    @GetMapping("/weeks")
    @Timed
    public List<Week> getAllWeeks() {
        log.debug("REST request to get all Weeks");

        List<Week> weeks = null;
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            weeks = weekRepository.findAll();
        }
        else {
            weeks = weekRepository.findByUserIsCurrentUser();
        }
        return weeks;
    }

    /**
     * GET  /weeks/:id : get the "id" week.
     *
     * @param id the id of the week to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the week, or with status 404 (Not Found)
     */
    @GetMapping("/weeks/{id}")
    @Timed
    public ResponseEntity<Week> getWeek(@PathVariable Long id) {
        log.debug("REST request to get Week : {}", id);
        Week week = weekRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(week));
    }

    /**
     * DELETE  /weeks/:id : delete the "id" week.
     *
     * @param id the id of the week to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/weeks/{id}")
    @Timed
    public ResponseEntity<Void> deleteWeek(@PathVariable Long id) {
        log.debug("REST request to delete Week : {}", id);
        Week week = weekRepository.findOne(id);
        if(!week.getUser().getLogin().equals(SecurityUtils.getCurrentUserLogin()) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new week cannot be assigned to someone else")).body(null);
        }
        weekRepository.delete(id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
