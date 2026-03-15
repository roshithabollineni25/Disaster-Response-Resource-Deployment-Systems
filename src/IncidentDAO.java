import java.sql.*;

public class IncidentDAO {

    public int insertIncident(Incident inc){

        String sql = "INSERT INTO DisasterReports(location_id,type,severity,reported_by,description) VALUES(?,?,?,?,?)";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, inc.getLocationId());
            ps.setString(2, inc.getType());
            ps.setString(3, inc.getSeverity());
            ps.setString(4, inc.getReportedBy());
            ps.setString(5, inc.getDescription());

            return ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}