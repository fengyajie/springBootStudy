package com.example.configuration.annotation;

public class SystemLogModel {

	private String LogAction; 
	private String LogContent; 
	private String FlagID; 
	private String FlagName; 
	private String LogIP; 
	private String TimeFlag; 
	private int ModuleID;
	public String getLogAction() {
		return LogAction;
	}
	public void setLogAction(String logAction) {
		LogAction = logAction;
	}
	public String getLogContent() {
		return LogContent;
	}
	public void setLogContent(String logContent) {
		LogContent = logContent;
	}
	public String getFlagID() {
		return FlagID;
	}
	public void setFlagID(String flagID) {
		FlagID = flagID;
	}
	public String getFlagName() {
		return FlagName;
	}
	public void setFlagName(String flagName) {
		FlagName = flagName;
	}
	public String getLogIP() {
		return LogIP;
	}
	public void setLogIP(String logIP) {
		LogIP = logIP;
	}
	public String getTimeFlag() {
		return TimeFlag;
	}
	public void setTimeFlag(String timeFlag) {
		TimeFlag = timeFlag;
	}
	public int getModuleID() {
		return ModuleID;
	}
	public void setModuleID(int moduleID) {
		ModuleID = moduleID;
	}
}
