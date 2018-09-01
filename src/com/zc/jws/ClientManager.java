package com.zc.jws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zc.domian.Player;
import com.zc.domian.Room;

public class ClientManager {
	
	public static Map<String,Client> clientManager = new ConcurrentHashMap<String, Client>();//openId-client
	
	public static Map<String,Player> playerMap = new ConcurrentHashMap<String, Player>();//userId-player
	
	public static Map<String,String> openIdToUserId = new ConcurrentHashMap<String, String>();//openId-userId
	
	public static Room room = null;
//	static {room = new Room();}

}
