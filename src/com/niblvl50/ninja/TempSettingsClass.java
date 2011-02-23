package com.niblvl50.ninja;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class TempSettingsClass {
	
	private static TempSettingsClass tempSettingsClass= null;
	
	private Context context;
	private float speedofartifact = 50f;
	private int speedofspwantime = 5;
	// Частота появления бонуса "Щита".
	private int shieldFrequency = 5;
	// Частота появления бонуса "Бонус".
	private int bonusFrequency = 50;
	// Частота появления бонуса "Враг/Наковальня".
	private int enemyFrequency = 30;
	// Частота появления бонуса "Ром".
	private int romFrequency = 5;
	// Частота появления бонуса "Замедление времени".
	private int slowFrequency = 5;
	// Частота появления бонуса "Ускорение".
	private int speedFrequency = 5;
	// Скорость персонажа
	private int defaultSpeed = 150;
	// Sound 
	private boolean isSound = true;

	public static TempSettingsClass getInstance() {
		if(tempSettingsClass == null)
			tempSettingsClass = new TempSettingsClass();
		return tempSettingsClass;
	}	
	
	public TempSettingsClass setContext(Context baseContext) {
		this.context = baseContext;
		return this;
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
	
	/**
	 * @param slowFrequency the slowFrequency to set
	 */
	public void setSlowFreq(int slowFrequency) {
		this.slowFrequency = slowFrequency;
	}

	/**
	 * @return the slowFrequency
	 */
	public int getSlowFreq() {
		return slowFrequency;
	}

	/**
	 * @param speedFrequency the speedFrequency to set
	 */
	public void setSpeedFreq(int speedFrequency) {
		this.speedFrequency = speedFrequency;
	}

	/**
	 * @return the speedFrequency
	 */
	public int getSpeedFreq() {
		return speedFrequency;
	}

	/**
	 * @param romFrequency the romFrequency to set
	 */
	public void setRomFreq(int romFrequency) {
		this.romFrequency = romFrequency;
	}

	/**
	 * @return the romFrequency
	 */
	public int getRomFreq() {
		return romFrequency;
	}

	public void getPref() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this.context);

		String speedPreference = prefs.getString("artifactSpeed", "50");
		String spawnPreference = prefs.getString("artifactSpawnTime", "10");
		String bonusFreq = prefs.getString("bonusFreq", "10");
		String enemyFreq = prefs.getString("enemyFreq", "10");
		String shieldFreq = prefs.getString("shieldFreq", "10");
		String romFreq = prefs.getString("romFreq", "5");
		String slowFreq = prefs.getString("slowFreq", "5");
		String speedFreq = prefs.getString("speedFreq", "5");
		String defaultSpeed = prefs.getString("defaultSpeed", "150");
		
		boolean isSound = prefs.getBoolean("soundPref", true);

		try {
			this.setSpeedofartifact(Float.valueOf(speedPreference));
			this.setSpawnTime(Integer.parseInt(spawnPreference));
			this.setBonusFreq(Integer.parseInt(bonusFreq));
			this.setEnemyFreq(Integer.parseInt(enemyFreq));
			this.setShieldFreq(Integer.parseInt(shieldFreq));
			this.setSlowFreq(Integer.parseInt(slowFreq));
			this.setRomFreq(Integer.parseInt(romFreq));
			this.setSpeedFreq(Integer.parseInt(speedFreq));
			this.setDefaultSpeed(Integer.parseInt(defaultSpeed));
			this.setSound(isSound);
		} catch (Exception e) {
			Log.e("PREFERENCE", e.getMessage());
		}
	}

	/**
	 * @param defaultSpeed the defaultSpeed to set
	 */
	public void setDefaultSpeed(int defaultSpeed) {
		this.defaultSpeed = defaultSpeed;
	}

	/**
	 * @return the defaultSpeed
	 */
	public int getDefaultSpeed() {
		return defaultSpeed;
	}

	/**
	 * @param isSound the isSound to set
	 */
	public void setSound(boolean isSound) {
		this.isSound = isSound;
	}

	/**
	 * @return the isSound
	 */
	public boolean isSound() {
		return isSound;
	}


}
