package com.streamers.akka.streams;

public class City {

    private String city;
    private String capitalCity;
    private String population;
    private String country;

    public City() {
    }

    public City(String city, String county, String capitalCity, String population) {
        this.capitalCity = capitalCity;
        this.city = city;
        this.country = county;
        this.population = population;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the capitalCity
     */
    public String getCapitalCity() {
        return capitalCity;
    }

    /**
     * @param capitalCity the capitalCity to set
     */
    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    /**
     * @return the population
     */
    public String getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(String population) {
        this.population = population;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "capital: " + this.capitalCity + " City: " + this.city + " country: " + this.country + " population: " + this.population;
    }

}
