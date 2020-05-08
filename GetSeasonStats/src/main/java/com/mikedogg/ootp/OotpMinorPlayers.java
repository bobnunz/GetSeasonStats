package com.mikedogg.ootp;

public class OotpMinorPlayers {
	
	private String owner;
	private String bxscPlayer;
	private String ootpPlayerId;
	
	public OotpMinorPlayers(String owner, String bxscPlayer, String ootpPlayerId) {
		super();
		this.owner = owner;
		this.bxscPlayer = bxscPlayer;
		this.ootpPlayerId = ootpPlayerId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBxscPlayer() {
		return bxscPlayer;
	}

	public void setBxscPlayer(String bxscPlayer) {
		this.bxscPlayer = bxscPlayer;
	}

	public String getOotpPlayerId() {
		return ootpPlayerId;
	}

	public void setOotpPlayerId(String ootpPlayerId) {
		this.ootpPlayerId = ootpPlayerId;
	}
	
}
