package simpletimetrack.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simpletimetrack.com.web.rest.util.HeaderUtil;

import java.util.Calendar;
import java.util.Date;



/*
* General helper andpoint with useful data
* Created by: Ernest Chechelski
* */
@RestController
@RequestMapping("/helper")
public class HelperResource {

    private final Logger log = LoggerFactory.getLogger(HelperResource.class);
    private final String TAG = HelperResource.class.getSimpleName();

    /**
     * GET  /weeks/current : get current week number
     *
     * @return the ResponseEntity with status 200 (OK) and the current week number in body
     */
    @GetMapping("/week/number")
    @Timed
    public ResponseEntity<Long> getCurrentWeekNumber() {
        log.debug("REST request to get current week number");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Long number = Long.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
        if(number == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(TAG,"numbererror","Cannot retrieve current week number")).body(null);
        }
        return ResponseEntity.ok()
            .body(number);

    }

    /**
     * GET  /weeks/current : get current week year
     *
     * @return the ResponseEntity with status 200 (OK) and the current week year in body
     */
    @GetMapping("/week/year")
    @Timed
    public ResponseEntity<Long> getCurrentWeekYear() {
        log.debug("REST request to get current week number");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Long number = Long.valueOf(cal.get(Calendar.YEAR));
        if(number == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(TAG,"numbererror","Cannot retrieve current week year")).body(null);
        }
        return  ResponseEntity.ok().body(number);

    }
}
