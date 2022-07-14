package com.bf.app.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RechargeLog {
	
	private int pkId;
	private long pid;
	private String uid;
	private int sid;
	private int cid;
	private String orderId;
	private int rmb;
	private int trePlus;
	private int treStar;
	private int lv;
	private String platform;
	private String accType;
	private int vip;
	private int sum;
	private String goodsID;
	private String currency;
	private Date date;

}
