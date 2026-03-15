import java.sql.*;
import java.util.*;

public class DisasterService {

    private IncidentDAO incidentDAO = new IncidentDAO();
    private RescueTeamDAO rescueTeamDAO = new RescueTeamDAO();
    private HistoricalDataDAO historicalDataDAO = new HistoricalDataDAO();

    private int getLocationId(String locationName){

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM VillageLocations WHERE name=?")){

            ps.setString(1, locationName);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return rs.getInt("id");

        }catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    public int reportIncident(String location,String type,String severity,String reporter,String desc){

        int locId = getLocationId(location);

        Incident inc = new Incident();

        inc.setLocationId(locId);
        inc.setType(type);
        inc.setSeverity(severity);
        inc.setReportedBy(reporter);
        inc.setDescription(desc);

        return incidentDAO.insertIncident(inc);
    }

    public String findNearestTeam(String disasterType){

        List<RescueTeam> teams = rescueTeamDAO.getAllTeams();

        for(RescueTeam team : teams){

            if(team.getSpecialty().toLowerCase().contains(disasterType.toLowerCase()))
                return team.getTeamName();
        }

        return teams.size()>0 ? teams.get(0).getTeamName() : "No Team Available";
    }

    public String generateAlert(String location,String type,String severity,String reporter,String desc){

        reportIncident(location,type,severity,reporter,desc);

        String team = findNearestTeam(type);

        return "Disaster Detected: "+type+
                "\nLocation: "+location+
                "\nSeverity: "+severity+
                "\nReported By: "+reporter+
                "\nNearest Response Team: "+team+
                "\nAction: Team alerted and dispatched.";
    }

    public List<RescueTeam> getAllTeams(){
        return rescueTeamDAO.getAllTeams();
    }

    public List<HistoricalPattern> getAllPatterns(){
        return historicalDataDAO.getAllPatterns();
    }
}