package com.kruczek.wallet.controller;

import java.util.ArrayList;
import java.util.List;

import com.kruczek.wallet.bill.model.BillDTO;

public class WalletWrapperDTO {

	private String username;
	private List<BillDTO> standardBills = new ArrayList<>();
	private List<BillDTO> extraBills = new ArrayList<>();

	public WalletWrapperDTO() {
	}

	public WalletWrapperDTO(String username, List<BillDTO> standardBills, List<BillDTO> extraBills) {
		this.username = username;
		this.standardBills = standardBills;
		this.extraBills = extraBills;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<BillDTO> getStandardBills() {
		return standardBills;
	}

	public void setStandardBills(List<BillDTO> standardBills) {
		this.standardBills = standardBills;
	}

	public List<BillDTO> getExtraBills() {
		return extraBills;
	}

	public void setExtraBills(List<BillDTO> extraBills) {
		this.extraBills = extraBills;
	}
}
