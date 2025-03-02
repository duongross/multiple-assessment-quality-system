package com.vgu.sqm.questionnaire;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/programs")
public class DataTable {
	
	@Path("/table")
	@GET
	public JsonArray getProgramsCount() throws SQLException, NamingException   {
		
		Connection db = Configuration.getAcademiaConnection();
		try {
			JsonArrayBuilder jsonArray =Json.createArrayBuilder();
			PreparedStatement st = db.prepareStatement(
					"call getTableOfData(NULL,NULL,NULL,NULL,NULL,NULL,NULL)"
					);
			ResultSet rs = st.executeQuery();
			List<Classes> data = new ArrayList<Classes>();
			while (rs.next()) {
		        data.add(new Classes(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getString(7)));
		        jsonArray.add((javax.json.JsonValue) Json.createObjectBuilder().add("academic_year", rs.getString(1).substring(0,rs.getString(1).length()-1))
		        													.add("Faculty", rs.getString(2).substring(0,rs.getString(2).length()-1))
		        													.add("Lecturer", rs.getString(3).substring(0,rs.getString(3).length()-1))
		        													.add("Module", rs.getString(4).substring(0,rs.getString(4).length()))
		        													.add("Semester", rs.getString(5).substring(0,rs.getString(5).length()))
		        													.add("Class", rs.getString(6).substring(0,rs.getString(6).length()))
		        													.add("Programm", rs.getString(7).substring(0,rs.getString(7).length())).build());
		        }
			JsonArray retArray = jsonArray.build();
			System.out.println(retArray);
		return retArray;
		
		}
		finally {
			db.close();
		}
	}
}