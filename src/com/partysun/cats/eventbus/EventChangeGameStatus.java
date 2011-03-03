package com.partysun.cats.eventbus;

import com.partysun.cats.entities.RyuHayabusa;

/**
 * Возвращает игровой статус.
 *  GAMEOVER
 *  PAUSE
 *  RMK
 * @author yura
 *
 */
public class EventChangeGameStatus {

	public static final String GAMEOVER = "GAMEOVER";
	public static final String PAUSE = "PAUSE";
	
	private final String gameState;
	private final RyuHayabusa ninja;
	
	public EventChangeGameStatus(String gameState, RyuHayabusa ninja)
	{
		this.gameState = gameState;		
		this.ninja = ninja;
	}


	/**
	 * @return the gameState
	 */
	public String getGameState() {
		return gameState;
	}

	/**
	 * @return the ninja
	 */
	public RyuHayabusa getNinja() {
		return ninja;
	}
		
}
