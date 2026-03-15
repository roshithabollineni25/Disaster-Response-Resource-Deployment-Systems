import java.sql.*;
import java.util.*;

public class HistoricalDataDAO {

    public List<HistoricalPattern> getAllPatterns(){

        List<HistoricalPattern> list = new ArrayList<>();

        String sql = "SELECT h.*, v.name as location_name FROM HistoricalData h JOIN VillageLocations v ON h.location_id = v.id";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                HistoricalPattern hp = new HistoricalPattern();

                hp.setId(rs.getInt("id"));
                hp.setLocationId(rs.getInt("location_id"));
                hp.setLocationName(rs.getString("location_name"));
                hp.setDisasterType(rs.getString("disaster_type"));
                hp.setFrequency(rs.getInt("frequency"));
                hp.setSeasonPattern(rs.getString("season_pattern"));
                hp.setRiskLevel(rs.getString("risk_level"));

                list.add(hp);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
}