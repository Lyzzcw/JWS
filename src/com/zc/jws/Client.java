package com.zc.jws;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zc.domian.Player;
import com.zc.domian.Room;
import com.zc.listener.MyMouseListener;
import com.zc.ui.ActionPanal;
import com.zc.ui.GamePanal;
import com.zc.ui.HallPanal;
import com.zc.ui.JUI;
import com.zc.util.Cnst;

@SuppressWarnings("unchecked")
public class Client {

	private WebSocketClient wsClient;
	
	private Player player;
	
	private String roomId;
	
	public void connect(String openId,Client client) {
		try {
			String url = "ws://"+Cnst.minaHost+":"+Cnst.minaPort+"/mj";
			wsClient = new WebSocketClient(new URI(url), new Draft_17()) {
				@Override
				public void onOpen(ServerHandshake arg0) {
					System.out.println(openId+"：打开链接");
					JUI.appendLog(openId+"："+url+"\t打开链接");
					ClientManager.clientManager.put(openId, client);
					MyMouseListener.changeButton(openId,Color.green);
				}

				@SuppressWarnings("unchecked")
				@Override
				public void onMessage(String arg0) {
					System.out.println(openId+"：接收的数据："+arg0);
					try {
						JSONObject message = JSON.parseObject(arg0);
						message = Cnst.getNewObj(message);
						//格式化输出转义之后的json
//						System.out.println(openId+"：接收的数据："+formatJson(JSON.toJSONString(message)));
						
						JUI.executeThread.execute(new receiveTask(message, openId, wsClient, "onMessage"));
					} catch (Exception e) {
						JUI.appendLog(openId+"：onMessage数据解析有误！");
						e.printStackTrace();
					} 
				}

				@Override
				public void onError(Exception arg0) {
					arg0.printStackTrace();
//					ClientManager.clientManager.remove(openId);
//					if (isInRoom()) {
//						player.setSth("state", 0);
//						ClientManager.clientManager.remove(openId);
//						GamePanal.changePlayerImageState(player, false);
//						MyMouseListener.changeButton(openId,Color.red);
//						JUI.appendLog(openId+"：发生错误，玩家掉线");
//					}else{
//						ClientManager.openIdToUserId.remove(openId);
//						ClientManager.playerMap.remove(player.getSth("userId").toString());
//						MyMouseListener.changeButton(openId,null);
//						JUI.appendLog(openId+"：发生错误已关闭");
//					}

					JUI.executeThread.execute(new receiveTask(null, openId, wsClient, "onError"));
				}

				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
//					ClientManager.clientManager.remove(openId);
//					if (isInRoom()) {
//						player.setSth("state", 0);
//						GamePanal.changePlayerImageState(player, false);
//						MyMouseListener.changeButton(openId,Color.red);
//						JUI.appendLog(openId+"：玩家掉线");
//					}else{
//						ClientManager.openIdToUserId.remove(openId);
//						ClientManager.playerMap.remove(player.getSth("userId").toString());
//						MyMouseListener.changeButton(openId,null);
//						JUI.appendLog(openId+"：链接已关闭");
//					}
					
					JUI.executeThread.execute(new receiveTask(null, openId, wsClient, "onClose"));
				}
			};
			wsClient.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class receiveTask implements Runnable{
		private JSONObject message;
		private String openId;
		private WebSocketClient wsClient;
		private String type;
		
		public receiveTask(JSONObject message,String openId,WebSocketClient wsClient,String type) {
			this.message = message;
			this.openId = openId;
			this.wsClient = wsClient;
			this.type = type;
		}

		@Override
		public void run() {
			if (type.equals("onMessage")) {
				if (message.containsKey("state")&&message.get("state").toString().equals("1")) {
					String interfaceId = message.getString("interfaceId");
					switch (interfaceId) {
					case "100100":
						i_100100(message, openId, wsClient);
						break;
					case "100007":
						i_100007(message, openId);
						break;
					case "100008":
						i_100008(message, openId, wsClient);
						break;
					case "100200":
						i_100200(message,openId);
						break;
					case "100106":
						i_100106(message, openId);
						break;
					case "100104":
						i_100104(message, openId);
						break;
					case "100102":
						i_100102(message, openId, wsClient);
						break;
					default:
						break;
					}
				}else{
					JUI.appendLog(openId+"：发生错误,"+message.toJSONString());
				}
			}else if(type.equals("onError")){
				onError();
			}else if(type.equals("onClose")){
				onClose();
			}
			
			
			
		}
		
		public void onError(){
			ClientManager.clientManager.remove(openId);
			if (isInRoom()) {
				player.setSth("state", 0);
				ClientManager.clientManager.remove(openId);
				GamePanal.changePlayerImageState(player, false);
				MyMouseListener.changeButton(openId,Color.red);
				JUI.appendLog(openId+"：发生错误，玩家掉线");
			}else{
				if (player!=null) {
					ClientManager.openIdToUserId.remove(openId);
					ClientManager.playerMap.remove(player.getSth("userId").toString());
					MyMouseListener.changeButton(openId,null);
					JUI.appendLog(openId+"：发生错误已关闭");
				}
			}
		}
		
		public void onClose(){
			ClientManager.clientManager.remove(openId);
			if (isInRoom()) {
				player.setSth("state", 0);
				GamePanal.changePlayerImageState(player, false);
				MyMouseListener.changeButton(openId,Color.red);
				JUI.appendLog(openId+"：玩家掉线");
			}else{
				if (player!=null) {
					ClientManager.openIdToUserId.remove(openId);
					ClientManager.playerMap.remove(player.getSth("userId").toString());
					MyMouseListener.changeButton(openId,null);
					JUI.appendLog(openId+"：链接已关闭");
				}
			}
		}
		
	}
	
	public boolean isInRoom(){
		return player!=null&&!player.getSth("playStatus").toString().equals(Cnst.PLAYER_STATE_DATING)&&
				(ClientManager.room!=null&&roomId!=null&&roomId.equals(ClientManager.room.getSth("roomId").toString()));
	}
	
	public static Integer[][] getIntegerPais(List<Integer> pais){
		Integer hunPai;
		if(ClientManager.room.getSth("hunPai") == null){
			hunPai = Integer.parseInt(ClientManager.room.getSth("dingHunPai").toString());
			if (hunPai == 28 || hunPai == 29 || hunPai == 30 || hunPai == 31 || hunPai == 32 || hunPai == 33 || hunPai == 34 ) {
				
			} else if(hunPai == 127 ||hunPai == 128 ){
				hunPai = 28;
			}else if(hunPai == 129 ||hunPai == 130 ){
				hunPai = 29;
			}else if(hunPai == 131 ||hunPai == 132 ){
				hunPai = 30;
			}else if(hunPai == 133 ||hunPai == 134 ){
				hunPai = 31;
			}else if (hunPai % 9 == 0) {// 上滚定混
				hunPai = hunPai - 8;
			} else { // 正常混牌
				hunPai = hunPai + 1;
			}
		}else{
			hunPai = Integer.parseInt(ClientManager.room.getSth("hunPai").toString());
		}
		
		
		Integer[][] result = new Integer[pais.size()][2];
		if (pais!=null&&pais.size()>0) {
			Integer[] ps = new Integer[pais.size()];
			Arrays.sort(pais.toArray(ps));
			for(int i=0;i<ps.length;i++){
				int pn = ps[i];
				int isHun = hunPai.equals(pn)?1:0;
				result[i][0] = pn;
				result[i][1] = isHun;
			}
		}
		return result;
	}
	public static Integer[][] getIntegerPaisNoSort(List<Integer> pais){
		Integer hunPai;
		if(ClientManager.room.getSth("hunPai") == null){
			hunPai = Integer.parseInt(ClientManager.room.getSth("dingHunPai").toString());
			if (hunPai == 28 || hunPai == 29 || hunPai == 30 || hunPai == 31 || hunPai == 32 || hunPai == 33 || hunPai == 34 ) {
				
			} else if(hunPai == 127 ||hunPai == 128 ){
				hunPai = 28;
			}else if(hunPai == 129 ||hunPai == 130 ){
				hunPai = 29;
			}else if(hunPai == 131 ||hunPai == 132 ){
				hunPai = 30;
			}else if(hunPai == 133 ||hunPai == 134 ){
				hunPai = 31;
			}else if (hunPai % 9 == 0) {// 上滚定混
				hunPai = hunPai - 8;
			} else { // 正常混牌
				hunPai = hunPai + 1;
			}
		}else{
			hunPai = Integer.parseInt(ClientManager.room.getSth("hunPai").toString());
		}
		
		
		Integer[][] result = new Integer[pais.size()][2];
		if (pais!=null&&pais.size()>0) {
			Integer[] ps = new Integer[pais.size()];
			pais.toArray(ps);
			for(int i=0;i<ps.length;i++){
				int pn = ps[i];
				int isHun = hunPai.equals(pn)?1:0;
				result[i][0] = pn;
				result[i][1] = isHun;
			}
		}
		return result;
	}
	
	public void i_100100(JSONObject message,String openId,WebSocketClient wsClient){
		Map<String,Object> info = (Map<String,Object>)message.toJavaObject(Map.class).get("info");
		JUI.appendLog(openId+"：接收"+100100+"的数据："+formatJson(JSON.toJSONString(info)));
		Player p = new Player();
		if (info.containsKey("roomInfo")) {
			if (ClientManager.room==null) {
				ClientManager.room = new Room();
				ClientManager.room.addT((Map<String,Object>)(info).get("roomInfo"));
				if (ClientManager.room.getSth("currMJNum")!=null) {
					GamePanal.changePaiNumButton(ClientManager.room.getSth("currMJNum").toString());
				}
				GamePanal.addPlayer(player);
				roomId = ClientManager.room.getSth("roomId").toString();
			}else{
				String temprid = ((Map<String,Object>)(info.get("roomInfo"))).get("roomId").toString();
				roomId = temprid;
				String crid = String.valueOf(ClientManager.room.getSth("roomId"));
				if (temprid.equals(crid)) {
//					ClientManager.room = new Room();
					ClientManager.room.addT((Map<String,Object>)(info).get("roomInfo"));
//					GamePanal.addPlayer(player);
					if (ClientManager.room.getSth("currMJNum")!=null) {
						GamePanal.changePaiNumButton(ClientManager.room.getSth("currMJNum").toString());
					}
				}else{
					JOptionPane.showMessageDialog(JUI.jf, Cnst.reqStateMap.get(Cnst.REQ_STATE_3),"提示",JOptionPane.PLAIN_MESSAGE);
					wsClient.close();
				}
			}
		}else if(info.containsKey("currentUser")){
			p.addT((Map<String,Object>)(info).get("currentUser"));
			p.setSth("openId", openId);
			player = p;
			
			if (!player.getSth("playStatus").toString().equals(String.valueOf(Cnst.PLAYER_STATE_DATING))) {
				//刷新玩家界面
				if (ClientManager.room==null) {//如果没有房间信息，就渲染当前玩家的房间即可
					//先不做处理
				}else{
					if(ClientManager.room.getSth("currActionUserId") != null && 
							ClientManager.room.getSth("currActionUserId").equals(player.getSth("userId"))){
						player.setSth("currAction", ClientManager.room.getSth("currAction"));
					}
					GamePanal.addPlayer(player);
				}
			}else{
				if (ClientManager.room!=null) {
					Map<String,String> send = Cnst.getSendMap(100008);
					send.put("roomId", ClientManager.room.getSth("roomId").toString());
					send.put("userId", player.getSth("userId").toString());
					ClientManager.clientManager.get(openId).send(send,openId);
				}else{
					HallPanal.addPlayerInfo(p);
					ClientManager.openIdToUserId.clear();
					ClientManager.openIdToUserId.put(openId,p.getSth("userId").toString());
					for(String oid:ClientManager.clientManager.keySet()){
						if (!oid.equals(openId)) {
							ClientManager.clientManager.get(oid).disConnect();
						}
					}
				}
			}
		}else if(info.containsKey("anotherUsers")){
			List<Map<String,Object>> anotherUsers = (List<Map<String, Object>>) info.get("anotherUsers");
			if (anotherUsers!=null&&anotherUsers.size()>0) {
				if (ClientManager.room!=null&&ClientManager.room.getSth("state").toString().equals(Cnst.ROOM_STATE_CREATED+"")) {
					int prepareNum = 0;
					for(int i=0;i<anotherUsers.size();i++){
						Map<String,Object> tp = anotherUsers.get(i);
						Integer playStatus = Integer.parseInt(tp.get("playStatus").toString());
						if (playStatus==Cnst.PLAYER_STATE_PREPARED) {
							prepareNum++;
						}
					}
					if (prepareNum==3) {
						GamePanal.addKaiJuButton(openId);
					}
				}
			}
			
		}
	}
	public void i_100007(JSONObject message,String openId){
		Map<String,Object> info = (Map<String,Object>)message.toJavaObject(Map.class).get("info");
		JUI.appendLog(openId+"：接收"+100007+"的数据："+formatJson(JSON.toJSONString(info)));
		int reqState = (int) info.get("reqState");
		if (reqState==Cnst.REQ_STATE_1) {
			ClientManager.room = new Room();
			ClientManager.room.addT((Map<String,Object>)info.get("roomInfo"));
			Map<String,Object> userInfo = (Map<String, Object>) info.get("userInfo");
			if (userInfo!=null&&userInfo.size()>0) {
				for(String key:userInfo.keySet()){
					player.setSth(key, userInfo.get(key));
					if (key.equals("position")&&userInfo.get("position").toString().equals("1")) {
						ClientManager.room.setSth("zhuangPlayer", player.getSth("userId"));
					}
				}
			}
			if (Cnst.roomType1.equals(ClientManager.room.getSth("roomType").toString())) {//房主模式
				roomId = ClientManager.room.getSth("roomId").toString();
				player.setSth("score", 0);
				GamePanal.addPlayer(player);
			}else{//代开模式
				
			}
		}else{
			JOptionPane.showMessageDialog(JUI.jf, Cnst.reqStateMap.get(reqState),"提示",JOptionPane.PLAIN_MESSAGE);
		}
	}
	public void i_100008(JSONObject message,String openId,WebSocketClient wsClient){
		Map<String,Object> info = (Map<String,Object>)message.toJavaObject(Map.class).get("info");
		JUI.appendLog(openId+"：接收"+100008+"的数据："+formatJson(JSON.toJSONString(info)));
		if (info.containsKey("reqState")&&!info.get("reqState").toString().equals(Cnst.REQ_STATE_1+"")) {
			JOptionPane.showMessageDialog(JUI.jf, Cnst.reqStateMap.get(Integer.parseInt(info.get("reqState").toString())),"提示",JOptionPane.PLAIN_MESSAGE);
			wsClient.close();
		}else{
			List<Map<String,Object>> userInfo = (List<Map<String, Object>>) info.get("userInfo");
			if (userInfo!=null&&userInfo.size()>0) {
				for(Map<String,Object> one:userInfo){
					if (one.get("userId").toString().equals(player.getSth("userId").toString())) {
						for(String key:one.keySet()){
							player.setSth(key, one.get(key));
						}
						if (one.get("position").toString().equals("1")) {
							ClientManager.room.setSth("zhuangPlayer", player.getSth("userId"));
						}
					}
				}
				
			}
			GamePanal.addPlayer(player);
		}
	}
	public void i_100200(JSONObject message,String openId){
		Map<String,Object> info = (Map<String,Object>)message.toJavaObject(Map.class).get("info");
		JUI.appendLog(openId+"：接收"+100200+"的数据："+formatJson(JSON.toJSONString(info)));
		int prepareNum = 0;
		List<Map<String,Object>> userInfo = (List<Map<String, Object>>) info.get("userInfo");
		Map<String,Object> roomInfo = (Map<String, Object>) info.get("roomInfo");
		if (userInfo!=null&&userInfo.size()>0) {
			for(int i=0;i<userInfo.size();i++){
				Player tp = ClientManager.playerMap.get(userInfo.get(i).get("userId").toString());
				if (tp!=null) {
					tp.setSth("playStatus", userInfo.get(i).get("playStatus").toString());
					if (tp.getSth("openId").toString().equals(openId)) {
						GamePanal.changePlayerImageState(tp, true);
					}
					if (userInfo.get(i).get("playStatus").toString().equals(Cnst.PLAYER_STATE_PREPARED+"")) {
						prepareNum++;
					}
				}
			}
		}
		if (player.getSth("fangzhu")!=null) {
			if (prepareNum==3) {
				GamePanal.addKaiJuButton(openId);
			}
		}
		if (roomInfo!=null&&roomInfo.size()>0) {
			for(String key:roomInfo.keySet()){
				ClientManager.room.setSth(key, roomInfo.get(key));
			}
		}
		
		roomId = ClientManager.room.getSth("roomId").toString();
		
		if (roomInfo.get("state").toString().equals(Cnst.ROOM_STATE_GAMIING+"")) {//游戏中了，需要接收玩家数据
			
			List<Integer> extra = (List<Integer>) info.get("extra");
			String nextActionUserId = info.get("nextActionUserId").toString();
			List<Integer> nextAction = (List<Integer>) info.get("nextAction");
			Integer hunPai = Integer.parseInt(info.get("dingHunPai").toString());
			if (hunPai == 28 || hunPai == 29 || hunPai == 30 || hunPai == 31 || hunPai == 32 || hunPai == 33 || hunPai == 34 ) {
				
			} else if(hunPai == 127 ||hunPai == 128 ){
				hunPai = 28;
			}else if(hunPai == 129 ||hunPai == 130 ){
				hunPai = 29;
			}else if(hunPai == 131 ||hunPai == 132 ){
				hunPai = 30;
			}else if(hunPai == 133 ||hunPai == 134 ){
				hunPai = 31;
			}else if (hunPai % 9 == 0) {// 上滚定混
				hunPai = hunPai - 8;
			} else { // 正常混牌
				hunPai = hunPai + 1;
			}
			ClientManager.room.setSth("hunPai", hunPai.toString());
			//没有传
//			String dingHunPai = info.get("dingHunPai").toString();
//			ClientManager.room.setSth("dingHunPai", dingHunPai);
//			Integer[][] pais = getIntegerPais(extra,hunPai);
			ClientManager.room.setSth("nextActionUserId", nextActionUserId);
			ClientManager.room.setSth("nextAction", nextAction);
			
			if (nextActionUserId.equals(player.getSth("userId").toString())) {//下一个行为人是自己
				player.setSth("currAction", nextAction);
			}
			
			player.setSth("pais", extra);
			GamePanal.changePaiNumButton(String.valueOf(info.get("currMJNum")));
			GamePanal.addPlayer(player);
			
			GamePanal.changePlayerImageState(player, true);
			GamePanal.addJieSanButton(openId);
		}
	}
	
	public void i_100104(JSONObject message,String openId){
		Map<String,Object> info = (Map<String,Object>)message.toJavaObject(Map.class).get("info");
		JUI.appendLog(openId+"：接收"+100104+"的数据："+formatJson(JSON.toJSONString(info)));
		
		String operateUserId = info.get("userId").toString();
		Integer action = Integer.parseInt(info.get("action").toString());
		String currentUserId = player.getSth("userId").toString();
		
		for(Player temp:ClientManager.playerMap.values()){
			temp.removeKey("currAction");
			temp.removeKey("lastFaPai");
		}
		if (currentUserId.equals(operateUserId)) {//只处理当前人的行为
			//根据动作渲染视图
			if (action>0&&action<=34) {//出牌
				if (player.getSth("chuList")!=null) {
					List<Integer> chuList = (List<Integer>) player.getSth("chuList");
					chuList.add(action);
				}else{
					List<Integer> chuList = new ArrayList<Integer>();
					chuList.add(action);
					player.setSth("chuList", chuList);
					
				}
				ClientManager.room.setSth("lastChuPai", action);
				ClientManager.room.setSth("lastChuPaiOpenId", openId);
				currentChuPai.removeMouseListener(chuPaiListener);
				ActionPanal.addChuPaiButton(currentChuPai);
				ActionPanal.repaintPlayerPais(player, action+"");
				GamePanal.cleanAllPaiBackgrounds();
			}else if(action>=127 && action<=134){//补花
				if (player.getSth("huaList")!=null) {
					List<Integer> chuList = (List<Integer>) player.getSth("huaList");
					chuList.add(action);
				}else{
					List<Integer> chuList = new ArrayList<Integer>();
					chuList.add(action);
					player.setSth("huaList", chuList);
					
				}
				ClientManager.room.setSth("lastChuPai", action);
				ClientManager.room.setSth("lastChuPaiOpenId", openId);
				currentChuPai.removeMouseListener(chuPaiListener);
				ActionPanal.addChuPaiButton(currentChuPai);
				ActionPanal.repaintPlayerPais(player, action+"");
				GamePanal.cleanAllPaiBackgrounds();
				GamePanal.addPlayer(player);
			}else if (action>34&&action<=56) {//吃
				ActionPanal.removeAllActionButton(openId);
				String extra = info.get("extra").toString();
				//以下三条只是设想……
//				1、移除手牌
//				2、添加吃的牌到出牌区
//				3、出牌人出的牌移除或者变换背景色
				List<Object> actionList = null;
				if (player.getSth("actionList")==null) {
					actionList = new ArrayList<Object>();
				}else{
					actionList = (List<Object>) player.getSth("actionList");
				}
				Map<String,String> chi = new ConcurrentHashMap<String, String>();
				chi.put("action", action+"");
				chi.put("extra", extra);
				actionList.add(chi);
				player.setSth("actionList", actionList);
				

				ClientManager.room.removeKey("lastFaPai");
				ClientManager.room.removeKey("lastFaPaiUserId");
				
				removeActionPaiFromPais(player, action+"", extra);

				GamePanal.addPlayer(player);
			}else if (action>56&&action<=90) {//碰
				ActionPanal.removeAllActionButton(openId);
				List<Object> actionList = null;
				if (player.getSth("actionList")==null) {
					actionList = new ArrayList<Object>();
				}else{
					actionList = (List<Object>) player.getSth("actionList");
				}
				actionList.add(action);
				player.setSth("actionList", actionList);
				removeActionPaiFromPais(player, action+"", "-3");
				ClientManager.room.removeKey("lastFaPai");
				ClientManager.room.removeKey("lastFaPaiUserId");
				GamePanal.addPlayer(player);
			}else if (action>90&&action<=126) {//杠
				ActionPanal.removeAllActionButton(openId);
				List<Object> actionList = null;
				if (player.getSth("actionList")==null) {
					actionList = new ArrayList<Object>();
				}else{
					actionList = (List<Object>) player.getSth("actionList");
				}
				actionList.add(action);
				player.setSth("actionList", actionList);
				removeActionPaiFromPais(player, action+"", "-4");
				ClientManager.room.removeKey("lastFaPai");
				ClientManager.room.removeKey("lastFaPaiUserId");
				GamePanal.addPlayer(player);
			}else if (action==Cnst.ACTION_TYPE_HU) {//胡
				ClientManager.room.removeKey("lastFaPai");
				ClientManager.room.removeKey("lastFaPaiUserId");
				Map<String,String> send = Cnst.getSendMap(100102);
				send.put("roomId", ClientManager.room.getSth("roomId").toString());
				send(send, openId);
				return;
			}else if (action==Cnst.ACTION_TYPE_GUO) {//过
				ActionPanal.removeAllActionButton(openId);
			}else if (action==Cnst.ACTION_TYPE_DUI) {//怼
				String hunPai = ClientManager.room.getSth("hunPai").toString();
				if (player.getSth("chuList")!=null) {
					List<Integer> chuList = (List<Integer>) player.getSth("chuList");
					chuList.add(Integer.parseInt(hunPai));
				}else{
					List<Integer> chuList = new ArrayList<Integer>();
					chuList.add(Integer.parseInt(hunPai));
					player.setSth("chuList", chuList);
					
				}
				GamePanal.cleanAllPaiBackgrounds();
				currentChuPai.removeMouseListener(chuPaiListener);
				ActionPanal.addChuPaiButton(currentChuPai);
				ActionPanal.repaintPlayerPais(player, hunPai);
			}else if (action==Cnst.ACTION_TYPE_FAPAI) {//请求发牌回应
				ActionPanal.removeAllActionButton(openId);
				String e = info.get("extra").toString();
//				List<Integer> extras = new ArrayList<Integer>();
//				extras.add(Integer.parseInt(e));
//				String hunPai = ClientManager.room.getSth("hunPai").toString();
//				Integer[][] lastFaPai = getIntegerPais(extras,hunPai);
				player.setSth("lastFaPai",e);
				ClientManager.room.setSth("lastFaPai", e);
				ClientManager.room.setSth("lastFaPaiUserId", getUserId());
				GamePanal.changePaiNumButton(""+(Integer.parseInt(GamePanal.paiNumButton.getText())-1));//更新牌数信息
				GamePanal.addPlayer(player);
			}
		}
		
		//处理接下来的行为
		if (info.containsKey("nextActionUserId")) {//自己是下一个操作人
			String nextActionUserId = info.get("nextActionUserId").toString();
			if (nextActionUserId.equals(currentUserId)) {
				//补花直接补发牌
				List<Integer> nextAction = (List<Integer>) info.get("nextAction");
				if(nextAction != null && nextAction.size() == 1 && (nextAction.get(0) >= 127 && nextAction.get(0) <= 134)){
					Map<String,String> send = Cnst.getSendMap(100201);
					Client client = ClientManager.clientManager.get(openId);
					send.put("userId", client.getUserId());
					send.put("roomId", ClientManager.room.getSth("roomId").toString());
					send.put("action", nextAction.get(0).toString());
					client.send(send, openId);
				}else{
					ClientManager.room.setSth("nextActionUserId", nextActionUserId);
					ClientManager.room.setSth("nextAction", nextAction);
					player.setSth("currAction", nextAction);
					GamePanal.addPlayer(player);
				}				
			}
		}
		
	}

	public void i_100102(JSONObject message,String openId,WebSocketClient wsClient){
		Map<String,Object> info = (Map<String,Object>)message.toJavaObject(Map.class).get("info");
		JUI.appendLog(openId+"：接收"+100102+"的数据："+formatJson(JSON.toJSONString(info)));
		ActionPanal.addXiaoJieSuanDialog(info);
	}
	
	
	public void removeActionPaiFromPais(Player p,String action,String extra){
		List<Integer> list = (List<Integer>) p.getSth("pais");
		if (extra.equals("-3")) {//碰
			Integer pai = Integer.parseInt(action)-56;
			for(int i=0;i<2;i++){
				for(int j=0;j<list.size();j++){
					if (list.get(j)==pai) {
						list.remove(j);
						break;
					}
				}
			}
		}else if (extra.equals("-4")) {//杠
			Integer pai = Integer.parseInt(action)-90;
			for(int i=0;i<3;i++){
				for(int j=0;j<list.size();j++){
					if (list.get(j)==pai) {
						list.remove(j);
						break;
					}
				}
			}
		}else {//吃
			int[] chis = Cnst.bianmaMap.get(Integer.parseInt(action));
			Integer ex = Integer.parseInt(extra);
			for(int i=0;i<chis.length;i++){
				if (ex!=chis[i]) {
					for(int m=0;m<list.size();m++){
						if (list.get(m)==chis[i]) {
							list.remove(m);
							break;
						}
					}
				}
			}
		}
		p.setSth("pais", list);
	}
	
	
	public void i_100106(JSONObject message,String openId){
		JUI.appendLog(openId+"：接收"+100106+"的数据："+formatJson(JSON.toJSONString(message)));
	}
	
	public boolean isConnect(){
		boolean isConnect = wsClient.getConnection().isOpen();
		return isConnect;
	}
	
	public void disConnect(){
		wsClient.close();
	}
	public void reConnect(){
		wsClient.connect();
	}
	
	public void send(Map<String,String> sendMap,String openId) {
		String sendStr = sendMessageConvert(sendMap);
//		System.out.println("发送的数据："+formatJson(JSON.toJSONString(sendMap)));
		JUI.appendLog(openId+"：发送的数据："+JSON.toJSONString(sendMap));
		System.out.println(openId+"：发送的数据："+sendStr);
		wsClient.send(sendStr);
	}
	
	private String sendMessageConvert(Map<String,String> sendMap){
		if (sendMap!=null&&sendMap.size()>0) {
			Map<String,String> convertMap = new ConcurrentHashMap<String, String>();
			for(String key:sendMap.keySet()){
				convertMap.put(Cnst.ROUTE_MAP.get(key), sendMap.get(key));
			}
			return JSON.toJSONString(convertMap);
		}
    	return null;
    }
	
	public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr))
            return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
            case '"':
                                if (last != '\\'){
                    isInQuotationMarks = !isInQuotationMarks;
                                }
                sb.append(current);
                break;
            case '{':
            case '[':
                sb.append(current);
                if (!isInQuotationMarks) {
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                }
                break;
            case '}':
            case ']':
                if (!isInQuotationMarks) {
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                }
                sb.append(current);
                break;
            case ',':
                sb.append(current);
                if (last != '\\' && !isInQuotationMarks) {
                    sb.append('\n');
                    addIndentBlank(sb, indent);
                }
                break;
            default:
                sb.append(current);
            }
        }

        return sb.toString();
    }
	
	private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append(' ').append(' ').append(' ');
        }
    }
	
	public Player getPlayer(){
		return player;
	}
	
	public String getUserId(){
		return player==null?null:player.getSth("userId").toString();
	}
	
	private JButton currentChuPai;
	private MouseListener chuPaiListener;
	public void setCurrentChuPaiButton(JButton b){
		this.currentChuPai = b;
	}
	public void setChuPaiMouseListener(MouseListener chuPaiListener){
		this.chuPaiListener = chuPaiListener;	
	}
	
}