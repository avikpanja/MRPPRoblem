package com.problem.mrp.processor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.problem.mrp.pojo.UserInputHolder;
import com.problem.mrp.constants.MRPConstants;
import com.problem.mrp.dao.BaseDAO;
import com.problem.mrp.dao.CycleDAOImpl;
import com.problem.mrp.util.MRPUtil;

public class MRPReportProcessor {

	private String inputFilePath;
	private String reportFilePath;

	public MRPReportProcessor(String inputFilePath, String reportFilePath) {
		this.inputFilePath = inputFilePath;
		this.reportFilePath = reportFilePath;
	}
	
	public String getInputFilePath() {
		return inputFilePath;
	}
	public String getReportFilePath() {
		return reportFilePath;
	}
	/* */
	public void process() throws Exception {
		System.out.println("MRPReportPrcessor.process - Started");
		
		if(inputFilePath==null) {
			throw new Exception("User input file path is missing!");
		}
		if(reportFilePath==null) {
			throw new Exception("Report file path is missing!");
		}
		
		/* Read data from user input*/
		UserInputHolder userInput = MRPUtil.readInputFile(inputFilePath);
		
		Map<String, Integer> totalMaterialMap = calculateTotalRequiredMaterial(userInput.getTotalNoOfEndProduct());
	
		if(totalMaterialMap!=null && !totalMaterialMap.isEmpty()) {
			//MRPUtil.deleteFile(reportFilePath);
			
			String heading = MRPConstants.HEADING_TOTAL_MATERIAL_REQUIRED.replace("<value>", ""+userInput.getTotalNoOfEndProduct());
			MRPUtil.writeDataToFile(reportFilePath, heading, totalMaterialMap);
			
			Map<String, Integer> requiredMaterial = calculateTotalDueMaterial(totalMaterialMap, userInput.getOnHandInventory());
			
			heading = MRPConstants.HEADING_MATERIAL_TO_PURCHASE.replace("<value>", ""+userInput.getTotalNoOfEndProduct());
			MRPUtil.writeDataToFile(reportFilePath, heading, requiredMaterial);
		} else {
			throw new Exception("Total Required Material is empty or null!");
		}
		System.out.println("MRPReportPrcessor.process - Ended");
	}
	
	/**
	 * calculates total material required for each individual item
	 * to build the end products
	 * 
	 * */
	private static Map<String, Integer> calculateTotalRequiredMaterial(int noOfEndProduct) throws Exception {
		if(noOfEndProduct<=0) {
			throw new Exception("No of end product should not be less or equal to Zero(0)");
		}
		
		BaseDAO dao = new CycleDAOImpl();
		Map<String, Integer> materialForOneCycle = null;
		Map<String, Integer> totalMaterialMap = new LinkedHashMap<>();
		
		try {
			materialForOneCycle = dao.getMaterialsForOneProduct();
			if(materialForOneCycle!=null && !materialForOneCycle.isEmpty()) {
				Set<String> keySet = materialForOneCycle.keySet();
				for(String key: keySet) {
					totalMaterialMap.put(key, materialForOneCycle.get(key)*noOfEndProduct);
				}
			}
		} catch(Exception e) {
			throw e;
		}
		
		return totalMaterialMap;
	}
	
	/**
	 * calculates total due material for each individual item
	 * to build the end products
	 * 
	 * */
	private static Map<String, Integer> calculateTotalDueMaterial(Map<String, Integer> totalMate, Map<String, Integer> availableMate) throws Exception {
		Map<String, Integer> requiredMate = new LinkedHashMap<>();
		Set<String> keySet = totalMate.keySet();
		for(String key: keySet) {
			int qty = availableMate.get(key)!=null
					? totalMate.get(key)-availableMate.get(key) > 0
							? totalMate.get(key)-availableMate.get(key) 
							: 0
					: availableMate.get(key);
			requiredMate.put(key, qty);
		}
		return requiredMate;
	}
}
