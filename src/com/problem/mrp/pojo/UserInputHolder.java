package com.problem.mrp.pojo;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserInputHolder {
	
	private int totalNoOfEndProduct;
	private Map<String, Integer> onHandInventory = new LinkedHashMap<>();
	
	public int getTotalNoOfEndProduct() {
		return totalNoOfEndProduct;
	}
	public void setTotalNoOfEndProduct(int totalNoOfEndProduct) {
		this.totalNoOfEndProduct = totalNoOfEndProduct;
	}
	public Map<String, Integer> getOnHandInventory() {
		return onHandInventory;
	}
	public void setOnHandInventory(Map<String, Integer> onHandInventory) {
		this.onHandInventory = onHandInventory;
	}
	
	public boolean addMaterialToInventory(String materialName, Integer materialQty) {
		if(onHandInventory==null) {
			return false;
		}
		onHandInventory.put(materialName, materialQty);
		return true;
	}
}
