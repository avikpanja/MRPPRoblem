package com.problem.mrp.dao;

import java.util.Map;

public interface BaseDAO {

	public Map<String, Integer> getMaterialsForOneProduct() throws Exception;
}
