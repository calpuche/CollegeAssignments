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
public class Term {
	private Integer termId;
	private String termSemester;
	private Integer termYear;
	private Boolean termActive;
	private Boolean termOpenForRegistration;
	
	public static final class termMapper implements RowMapper<Term>{
		public Term mapRow(ResultSet rs, int rowNum) throws SQLException{
			Term term = new Term(rs.getInt("termId"),
					rs.getString("termSemester"),
					rs.getInt("termYear"),
					rs.getBoolean("termActive"),
					rs.getBoolean("termOpenForRegistration"));
			return term;
		}
	}
	
	
}
