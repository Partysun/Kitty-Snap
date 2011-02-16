package com.niblvl50.ninja;

public class TempSettingsClass {
	
	private static TempSettingsClass tempSettingsClass= null;
	
	private float speedofartifact = 50f;
	private int speedofspwantime = 5;
	// Частота появления бонуса "Щита".
	private int shieldFrequency = 10;
	// Частота появления бонуса "Бонус".
	private int bonusFrequency = 50;
	// Частота появления бонуса "Враг/Наковальня".
	private int enemyFrequency = 30;

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

	public void setSpawnTime(int speedofspwantime) {
		this.speedofspwantime = speedofspwantime;
	}

	public int getSpeedofspwantime() {
		return speedofspwantime;
	}

	public int getShieldFreq() {
		return shieldFrequency;
	}

	public int getBonusFreq() {
		return bonusFrequency;
	}

	public int getEnemyFreq() {
		return enemyFrequency;
	}
	
	public void setShieldFreq(int sf) {
		shieldFrequency = sf;
	}

	public void setBonusFreq(int bf) {
		bonusFrequency = bf;
	}

	public void setEnemyFreq(int ef) {
		enemyFrequency = ef;
	}
	
}
