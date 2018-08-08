package com.hilltoponline.model;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section {
	
	private Integer sectionId;
	private Integer courseId;
	private Integer termId;
	private Integer userId;
	private String sectionDay;
	private String sectionTime;
	
	
	public static final class SectionMapper implements RowMapper<Section>{
		public Section mapRow(ResultSet rs, int rowNum) throws SQLException {
			Section section = new Section(rs.getInt("sectionId"), 
					rs.getInt("courseId"), 
					rs.getInt("termId"),
					rs.getInt("userId"),
					rs.getString("sectionDay"), 
					rs.getString("sectionTime"));
			return section;
		}
	}
}
