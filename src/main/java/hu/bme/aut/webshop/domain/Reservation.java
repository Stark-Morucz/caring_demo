package hu.bme.aut.webshop.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String bookerName;
    private String fromDestination;
    private String toDestination;
    private Date travelDate;

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookerName(String bookerName) {
        this.bookerName = bookerName;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public Long getId() {
        return id;
    }

    public String getBookerName() {
        return bookerName;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public Date getTravelDate() {
        return travelDate;
    }

}
