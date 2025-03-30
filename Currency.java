public class Currency {
    private String currencyCode;
    private String currencyName;
    private double exchangeRate;
    private String country;
    private String continent;
    private boolean isActive;
    
    // Constructor
    public Currency(String currencyCode, String currencyName, double exchangeRate, 
                   String country, String continent) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.exchangeRate = exchangeRate;
        this.country = country;
        this.continent = continent;
        this.isActive = true;
    }
    
    // Getters and Setters
    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    
    public String getCurrencyName() {
        return currencyName;
    }
    
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    
    public double getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getContinent() {
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return String.format("%-4s | %-20s | %-10.2f | %-15s | %-10s | %s",
            currencyCode, currencyName, exchangeRate, country, continent,
            isActive ? "Active" : "Inactive");
    }
} 