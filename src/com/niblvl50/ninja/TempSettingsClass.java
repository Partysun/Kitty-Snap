package com.niblvl50.ninja;

public class TempSettingsClass {
	
	private static TempSettingsClass tempSettingsClass= null;
	
	private float speedofartifact = 50f;

	public static TempSettingsClass getInstance() {
		if(tempSettingsClass == null)
			tempSettingsClass = new TempSettingsClass();
		return tempSettingsClass;
	}	
	
	public float getSpeedofartifact() {
		return speedofartifact;
	}

	public void setSpeedofartifact(float speedofartifact) {
		this.speedofartifact = speedofartifact;
	}
	
}
