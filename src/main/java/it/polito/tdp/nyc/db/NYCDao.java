package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {


	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}


		return result;
	}


	public List <String> getAllProvider(){
		String sql="SELECT distinct provider "
				+ "FROM nyc_wifi_hotspot_locations";

		List <String> result= new ArrayList <>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new String(res.getString("Provider")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;

	}
	/*
	 * public List<City> getCities(String provider) {
String sql = "SELECT DISTINCT City, AVG(Latitude) AS Lat, AVG(Longitude) AS Lng, COUNT(*) AS NUM "
+ "FROM nyc_wifi_hotspot_locations "
+ "WHERE Provider= ? "
+ "GROUP BY City "
+ "ORDER BY City";

Connection conn = DBConnect.getConnection();
try {
PreparedStatement st = conn.prepareStatement(sql);
st.setString(1, provider);
ResultSet res = st.executeQuery() ;
List<City> result = new ArrayList<City>() ;
while(res.next()) {
result.add(new City( res.getString("City"),
new LatLng(res.getDouble("Lat"), res.getDouble("Lng")),
res.getInt("NUM")
));
}
conn.close();
return result;
} catch (SQLException e) {
e.printStackTrace();
return null;
}
}
	 */

	public List <String> getAllQuartieri(String h){
		String sql="SELECT distinct city "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE provider = ?";
		List <String> result= new ArrayList <>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, h);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new String(res.getString("City")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;


	}

	public List <Adiacenza> getArchi(String provider){
		String sql="SELECT distinct n1.City AS c1, n2.City AS c2, AVG(n1.Latitude) AS lat1 , AVG(n2.Latitude) AS lat2,  AVG(n1.Longitude) AS longi1,  AVG(n2.Longitude) AS longi2 "
				+ "from nyc_wifi_hotspot_locations n1, nyc_wifi_hotspot_locations n2 "
				+ "WHERE n1.City>n2.City and n1.Provider=n2.Provider AND n1.Provider=? "
				+ "GROUP BY  n1.City, n2.City";
		//SE IO METTO ORDER BY NELLA QUERY HO GIA ORDINE ALFABETICO
		List <Adiacenza> result= new ArrayList <>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				LatLng d1= new LatLng(res.getDouble("lat1"), res.getDouble("longi1"));
				LatLng d2= new LatLng(res.getDouble("lat2"), res.getDouble("longi2"));

				double distance=LatLngTool.distance(d1, d2, LengthUnit.KILOMETER);


				result.add(new Adiacenza(res.getString("c1"), res.getString("c2"), distance));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;

	}

}
