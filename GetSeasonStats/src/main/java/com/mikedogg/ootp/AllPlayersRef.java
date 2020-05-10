package com.mikedogg.ootp;

public class AllPlayersRef {
	
	private String playerId;
	private String owner;
	private String fullName;
	private String team;
	private int ab;
	private int r;
	private int h;
	private int hr;
	private int rbi;
	private int sb;
	private int w;
	private int sv;
	private int hold;
	private float ip;
	private int er;
	private int so;
	private int bb;
	
	
	public AllPlayersRef(String playerId, String owner, String fullName, String team, int ab, int r, int h, int hr, int rbi,
			int sb, int w, int sv, int hold, float ip, int er, int so, int bb) {
		super();
		this.playerId = playerId;
		this.owner = owner;
		this.fullName = fullName;
		this.team = team;
		this.ab = ab;
		this.r = r;
		this.h = h;
		this.hr = hr;
		this.rbi = rbi;
		this.sb = sb;
		this.w = w;
		this.sv = sv;
		this.hold = hold;
		this.ip = ip;
		this.er = er;
		this.so = so;
		this.bb = bb;
	}
	public int getAb() {
		return ab;
	}
	public void setAb(int ab) {
		this.ab = ab;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	public int getRbi() {
		return rbi;
	}
	public void setRbi(int rbi) {
		this.rbi = rbi;
	}
	public int getSb() {
		return sb;
	}
	public void setSb(int sb) {
		this.sb = sb;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getSv() {
		return sv;
	}
	public void setSv(int sv) {
		this.sv = sv;
	}
	public int getHold() {
		return hold;
	}
	public void setHold(int hold) {
		this.hold = hold;
	}
	public float getIp() {
		return ip;
	}
	public void setIp(float ip) {
		this.ip = ip;
	}
	public int getEr() {
		return er;
	}
	public void setEr(int er) {
		this.er = er;
	}
	public int getSo() {
		return so;
	}
	public void setSo(int so) {
		this.so = so;
	}
	public int getBb() {
		return bb;
	}
	public void setBb(int bb) {
		this.bb = bb;
	}
	
	public AllPlayersRef addPicherStats (int w, int sv, int hold, float ip, int er, int so, int bb) {
		
		this.w = w;
		this.sv = sv;
		this.hold = hold;
		this.ip = ip;
		this.er = er;
		this.so = so;
		this.bb = bb;

		return this;
	}

}
