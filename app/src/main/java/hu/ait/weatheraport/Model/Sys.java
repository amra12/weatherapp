package hu.ait.weatheraport.Model;

/**
 * Created by rohit on 10/15/15.
 */
public class Sys {

    private Integer type;
    private Long id;
    private double message;
    private String country;
    private Long sunrise;
    private Long sunset;

    public Integer getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
}
