package util;

import java.io.Serializable;

/**
 * enum that takes care of all
 * actions that possibly can be
 * sent to the server
 * 
 * @author Magnus
 */
public enum SendableAction implements Serializable{
	LOGIN,
	LOGOUT,
	SEARCH_PLAYER,
	SIGN_UP,
	PLACE_WORD,
	START_GAME,
	QUIT_GAME,
	PASS,
	SWAP
}