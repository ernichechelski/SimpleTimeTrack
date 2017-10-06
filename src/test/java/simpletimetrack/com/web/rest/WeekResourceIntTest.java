package simpletimetrack.com.web.rest;

import simpletimetrack.com.SimpleTimeTrackApp;

import simpletimetrack.com.domain.Week;
import simpletimetrack.com.repository.WeekRepository;
import simpletimetrack.com.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WeekResource REST controller.
 *
 * @see WeekResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleTimeTrackApp.class)
public class WeekResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_MONDAY = 1;
    private static final Integer UPDATED_MONDAY = 2;

    private static final Integer DEFAULT_TUESDAY = 1;
    private static final Integer UPDATED_TUESDAY = 2;

    private static final Integer DEFAULT_WEDNESDAY = 1;
    private static final Integer UPDATED_WEDNESDAY = 2;

    private static final Integer DEFAULT_THURSDAY = 1;
    private static final Integer UPDATED_THURSDAY = 2;

    private static final Integer DEFAULT_FRIDAY = 1;
    private static final Integer UPDATED_FRIDAY = 2;

    private static final Integer DEFAULT_SATURDAY = 1;
    private static final Integer UPDATED_SATURDAY = 2;

    private static final Integer DEFAULT_SUNDAY = 1;
    private static final Integer UPDATED_SUNDAY = 2;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWeekMockMvc;

    private Week week;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WeekResource weekResource = new WeekResource(weekRepository);
        this.restWeekMockMvc = MockMvcBuilders.standaloneSetup(weekResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Week createEntity(EntityManager em) {
        Week week = new Week()
            .year(DEFAULT_YEAR)
            .number(DEFAULT_NUMBER)
            .monday(DEFAULT_MONDAY)
            .tuesday(DEFAULT_TUESDAY)
            .wednesday(DEFAULT_WEDNESDAY)
            .thursday(DEFAULT_THURSDAY)
            .friday(DEFAULT_FRIDAY)
            .saturday(DEFAULT_SATURDAY)
            .sunday(DEFAULT_SUNDAY);
        return week;
    }

    @Before
    public void initTest() {
        week = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeek() throws Exception {
        int databaseSizeBeforeCreate = weekRepository.findAll().size();

        // Create the Week
        restWeekMockMvc.perform(post("/api/weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(week)))
            .andExpect(status().isCreated());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeCreate + 1);
        Week testWeek = weekList.get(weekList.size() - 1);
        assertThat(testWeek.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testWeek.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testWeek.getMonday()).isEqualTo(DEFAULT_MONDAY);
        assertThat(testWeek.getTuesday()).isEqualTo(DEFAULT_TUESDAY);
        assertThat(testWeek.getWednesday()).isEqualTo(DEFAULT_WEDNESDAY);
        assertThat(testWeek.getThursday()).isEqualTo(DEFAULT_THURSDAY);
        assertThat(testWeek.getFriday()).isEqualTo(DEFAULT_FRIDAY);
        assertThat(testWeek.getSaturday()).isEqualTo(DEFAULT_SATURDAY);
        assertThat(testWeek.getSunday()).isEqualTo(DEFAULT_SUNDAY);
    }

    @Test
    @Transactional
    public void createWeekWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weekRepository.findAll().size();

        // Create the Week with an existing ID
        week.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekMockMvc.perform(post("/api/weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(week)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWeeks() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        // Get all the weekList
        restWeekMockMvc.perform(get("/api/weeks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(week.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].monday").value(hasItem(DEFAULT_MONDAY)))
            .andExpect(jsonPath("$.[*].tuesday").value(hasItem(DEFAULT_TUESDAY)))
            .andExpect(jsonPath("$.[*].wednesday").value(hasItem(DEFAULT_WEDNESDAY)))
            .andExpect(jsonPath("$.[*].thursday").value(hasItem(DEFAULT_THURSDAY)))
            .andExpect(jsonPath("$.[*].friday").value(hasItem(DEFAULT_FRIDAY)))
            .andExpect(jsonPath("$.[*].saturday").value(hasItem(DEFAULT_SATURDAY)))
            .andExpect(jsonPath("$.[*].sunday").value(hasItem(DEFAULT_SUNDAY)));
    }

    @Test
    @Transactional
    public void getWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);

        // Get the week
        restWeekMockMvc.perform(get("/api/weeks/{id}", week.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(week.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.monday").value(DEFAULT_MONDAY))
            .andExpect(jsonPath("$.tuesday").value(DEFAULT_TUESDAY))
            .andExpect(jsonPath("$.wednesday").value(DEFAULT_WEDNESDAY))
            .andExpect(jsonPath("$.thursday").value(DEFAULT_THURSDAY))
            .andExpect(jsonPath("$.friday").value(DEFAULT_FRIDAY))
            .andExpect(jsonPath("$.saturday").value(DEFAULT_SATURDAY))
            .andExpect(jsonPath("$.sunday").value(DEFAULT_SUNDAY));
    }

    @Test
    @Transactional
    public void getNonExistingWeek() throws Exception {
        // Get the week
        restWeekMockMvc.perform(get("/api/weeks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);
        int databaseSizeBeforeUpdate = weekRepository.findAll().size();

        // Update the week
        Week updatedWeek = weekRepository.findOne(week.getId());
        updatedWeek
            .year(UPDATED_YEAR)
            .number(UPDATED_NUMBER)
            .monday(UPDATED_MONDAY)
            .tuesday(UPDATED_TUESDAY)
            .wednesday(UPDATED_WEDNESDAY)
            .thursday(UPDATED_THURSDAY)
            .friday(UPDATED_FRIDAY)
            .saturday(UPDATED_SATURDAY)
            .sunday(UPDATED_SUNDAY);

        restWeekMockMvc.perform(put("/api/weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeek)))
            .andExpect(status().isOk());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeUpdate);
        Week testWeek = weekList.get(weekList.size() - 1);
        assertThat(testWeek.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testWeek.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testWeek.getMonday()).isEqualTo(UPDATED_MONDAY);
        assertThat(testWeek.getTuesday()).isEqualTo(UPDATED_TUESDAY);
        assertThat(testWeek.getWednesday()).isEqualTo(UPDATED_WEDNESDAY);
        assertThat(testWeek.getThursday()).isEqualTo(UPDATED_THURSDAY);
        assertThat(testWeek.getFriday()).isEqualTo(UPDATED_FRIDAY);
        assertThat(testWeek.getSaturday()).isEqualTo(UPDATED_SATURDAY);
        assertThat(testWeek.getSunday()).isEqualTo(UPDATED_SUNDAY);
    }

    @Test
    @Transactional
    public void updateNonExistingWeek() throws Exception {
        int databaseSizeBeforeUpdate = weekRepository.findAll().size();

        // Create the Week

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWeekMockMvc.perform(put("/api/weeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(week)))
            .andExpect(status().isCreated());

        // Validate the Week in the database
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWeek() throws Exception {
        // Initialize the database
        weekRepository.saveAndFlush(week);
        int databaseSizeBeforeDelete = weekRepository.findAll().size();

        // Get the week
        restWeekMockMvc.perform(delete("/api/weeks/{id}", week.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Week> weekList = weekRepository.findAll();
        assertThat(weekList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Week.class);
        Week week1 = new Week();
        week1.setId(1L);
        Week week2 = new Week();
        week2.setId(week1.getId());
        assertThat(week1).isEqualTo(week2);
        week2.setId(2L);
        assertThat(week1).isNotEqualTo(week2);
        week1.setId(null);
        assertThat(week1).isNotEqualTo(week2);
    }
}
