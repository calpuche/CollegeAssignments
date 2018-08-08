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
public class AssignmentGroup {
	private Integer groupId;
	private Integer sectionId;
	private String groupName;
	private Float groupWeight;
	public static final class AssignmentGroupMapper implements RowMapper<AssignmentGroup>{
		public AssignmentGroup mapRow(ResultSet rs, int rowNum) throws SQLException{
			AssignmentGroup asgGroup = new AssignmentGroup(rs.getInt("groupId"),
					rs.getInt("sectionId"),
					rs.getString("groupName"),
					rs.getFloat("groupWeight"));
			return asgGroup;
		}
	}
}
