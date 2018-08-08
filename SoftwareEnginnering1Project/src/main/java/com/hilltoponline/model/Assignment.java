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
public class Assignment {
	private Integer asgId;
	private Integer groupId;
	private String asgName;
	private Float asgWeight;
	public static final class AssignmentMapper implements RowMapper<Assignment>{
		public Assignment mapRow(ResultSet rs, int rowNum) throws SQLException{
			Assignment asg = new Assignment(rs.getInt("asgId"),
					rs.getInt("groupId"),
					rs.getString("asgName"),
					rs.getFloat("asgWeight"));
			return asg;
		}
	}
}
