import java.sql.*;
import java.util.*;

public class RescueTeamDAO {

    public List<RescueTeam> getAllTeams(){

        List<RescueTeam> list = new ArrayList<>();

        String sql = "SELECT e.*, v.name as location FROM EmergencyTeams e LEFT JOIN VillageLocations v ON e.base_location_id = v.id";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                RescueTeam t = new RescueTeam();

                t.setId(rs.getInt("id"));
                t.setTeamName(rs.getString("team_name"));
                t.setSpecialty(rs.getString("specialty"));
                t.setContactNumber(rs.getString("contact_number"));
                t.setBaseLocationName(rs.getString("location"));

                list.add(t);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
}