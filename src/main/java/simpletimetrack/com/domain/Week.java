package simpletimetrack.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Week.
 */
@Entity
@Table(name = "week")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Week implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @Column(name = "monday")
    private Integer monday;

    @Column(name = "tuesday")
    private Integer tuesday;

    @Column(name = "wednesday")
    private Integer wednesday;

    @Column(name = "thursday")
    private Integer thursday;

    @Column(name = "friday")
    private Integer friday;

    @Column(name = "saturday")
    private Integer saturday;

    @Column(name = "sunday")
    private Integer sunday;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Week number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getMonday() {
        return monday;
    }

    public Week monday(Integer monday) {
        this.monday = monday;
        return this;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public Week tuesday(Integer tuesday) {
        this.tuesday = tuesday;
        return this;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public Week wednesday(Integer wednesday) {
        this.wednesday = wednesday;
        return this;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public Week thursday(Integer thursday) {
        this.thursday = thursday;
        return this;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public Week friday(Integer friday) {
        this.friday = friday;
        return this;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public Week saturday(Integer saturday) {
        this.saturday = saturday;
        return this;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public Integer getSunday() {
        return sunday;
    }

    public Week sunday(Integer sunday) {
        this.sunday = sunday;
        return this;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }

    public User getUser() {
        return user;
    }

    public Week user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public Week project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Week week = (Week) o;
        if (week.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), week.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Week{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", monday='" + getMonday() + "'" +
            ", tuesday='" + getTuesday() + "'" +
            ", wednesday='" + getWednesday() + "'" +
            ", thursday='" + getThursday() + "'" +
            ", friday='" + getFriday() + "'" +
            ", saturday='" + getSaturday() + "'" +
            ", sunday='" + getSunday() + "'" +
            "}";
    }
}
