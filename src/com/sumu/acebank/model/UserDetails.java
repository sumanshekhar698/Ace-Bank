package com.sumu.acebank.model;

public class UserDetails {
	
	private int account1;
	private int account2;
	private int withdraw;
	private int deposit;
	private int balance;
	private int id;
	
	public UserDetails() {
		
	}
	
	public UserDetails(int account1, int account2, int withdraw, int deposit, int balance) {
		this.account1 = account1;
		this.account2 = account2;
		this.withdraw = withdraw;
		this.deposit = deposit;
		this.balance = balance;
	}
	
	public UserDetails(int account1, int account2, int withdraw, int deposit, int balance, int id) {
		this.account1 = account1;
		this.account2 = account2;
		this.withdraw = withdraw;
		this.deposit = deposit;
		this.balance = balance;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getAccount1() {
		return account1;
	}

	public void setAccount1(int account1) {
		this.account1 = account1;
	}

	public int getAccount2() {
		return account2;
	}

	public void setAccount2(int account2) {
		this.account2 = account2;
	}

	public int getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
}
