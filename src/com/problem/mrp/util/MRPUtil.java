package com.problem.mrp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.problem.mrp.constants.MRPConstants;
import com.problem.mrp.pojo.UserInputHolder;

public class MRPUtil {
	
	
	
//	private static Map<String, Integer> materialsForOneCycle;
//	
//	public static Map<String, Integer> getMaterialMapForOneCycle() {
//		
//		if(materialsForOneCycle==null) {
//			materialsForOneCycle = new LinkedHashMap<String, Integer>();
//			materialsForOneCycle.put("Seats", 1);
//			materialsForOneCycle.put("Frames", 1);
//			materialsForOneCycle.put("Brake sets", 2);
//			materialsForOneCycle.put("Brake paddles", 2);
//			materialsForOneCycle.put("Brake cables", 2);
//			materialsForOneCycle.put("Levers", 2);
//			materialsForOneCycle.put("Brake Shoes", 4);
//			materialsForOneCycle.put("Handlebars", 1);
//			materialsForOneCycle.put("Wheels", 2);
//			materialsForOneCycle.put("Tires", 2);
//			materialsForOneCycle.put("Chains", 1);
//			materialsForOneCycle.put("Crank set", 1);
//			materialsForOneCycle.put("Paddles", 2);
//		}
//		return materialsForOneCycle;
//	}

	public static UserInputHolder readInputFile(String filePath) throws Exception {
		UserInputHolder input = new UserInputHolder();
		if(filePath==null || filePath.isEmpty()) {
			throw new Exception("filePath can't be null or empty");
		}
		File file = new File(filePath);
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			input.setTotalNoOfEndProduct(Integer.parseInt(line));
			while((line=br.readLine())!=null) {
				String[] material = line.trim().split("=");
				input.addMaterialToInventory(material[0].trim(), Integer.parseInt(material[1].trim()));
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		};
		return input;
	}
	
	public static void writeDataToFile(String filePath, String heading, Map<String, Integer> data) throws Exception {
		if(filePath==null || filePath.isEmpty()) {
			throw new Exception("Report File Path can't be null or empty");
		}
		File reportFile = new File(filePath);
		try(PrintWriter pw = new PrintWriter(new FileWriter(reportFile, true))) {
			pw.println(heading);
			Set<String> keySet = data.keySet();
			for(String key: keySet) {
				pw.println(key+"="+data.get(key));
			}
		}
	}
	
	public static void deleteFile(String filePath) {
		if(filePath!=null) {
			File file = new File(filePath);
			if(file.exists()) file.delete();
		}
	}
	
	public static String getValueFromProperties(String propertiesPath, String key) {
		String value = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(propertiesPath));
			value = prop.getProperty(key);
		} catch (IOException e) {
			System.out.println(e);
		}
		return value;
	}
}
