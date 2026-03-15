public class HistoricalPattern {

    private int id;
    private int locationId;
    private String locationName;
    private String disasterType;
    private int frequency;
    private String seasonPattern;
    private String riskLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }


    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    public String getSeasonPattern() {
        return seasonPattern;
    }

    public void setSeasonPattern(String seasonPattern) {
        this.seasonPattern = seasonPattern;
    }


    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}