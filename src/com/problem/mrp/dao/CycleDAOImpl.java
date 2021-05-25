package com.problem.mrp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.problem.mrp.constants.MRPConstants;

public class CycleDAOImpl extends BaseDAOAbstract {

	@Override
	public Map<String, Integer> getMaterialsForOneProduct() throws Exception {
		Map<String, Integer> materials = new LinkedHashMap<>();
		Connection con = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = this.getConnection();
			ps = con.prepareStatement("SELECT Item, ReqQty FROM " + MRPConstants.SCHEMA_NAME + ".material_cycle");
			rs = ps.executeQuery();
			while(rs.next()) {
				materials.put(rs.getString("Item"), rs.getInt("ReqQty"));
			}
		} catch(NullPointerException e) {
			System.out.println("NullPointerException occurred in getMaterialsForOneProduct(), Connection: " + con);
			throw e;
		} catch(SQLException e) {
			System.out.println("SQLException occurred in getMaterialsForOneProduct()");
			throw e;
		} finally {
			this.releaseResource(rs, ps, con);
		}
		return materials;
	}
}
