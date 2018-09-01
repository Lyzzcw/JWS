package com.zc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.zc.domian.Player;
import com.zc.domian.Room;
import com.zc.jws.Client;
import com.zc.jws.ClientManager;
import com.zc.listener.MyMouseListener;
import com.zc.util.Cnst;
import com.zc.util.Draw;

public class ActionPanal {
	
	static int danPaiJiange = 8;
	static int paiNumButtonWidth = 30;
	static int paiNumButtonHeight = 30;
	static Font paiNumButtonFont = new Font("楷体", Font.PLAIN, 15);
	static int oneLinePaiNum = 15;
	
	static Map<Integer,Integer> nums = new ConcurrentHashMap<Integer, Integer>();
	static{
		nums.put(1, 0);
		nums.put(2, 0);
		nums.put(3, 0);
		nums.put(4, 0);
	}
	
	public static void removeAllChuDePais(String openId){
		Component[] all = JUI.jp.getComponents();
		List<Component> jbs = new ArrayList<Component>();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_出的牌.concat(openId))) {
						jbs.add(b);
					}
				}
			}
			if (jbs.size()>0) {
				for(int i=0;i<jbs.size();i++){
					JUI.jp.remove(jbs.get(i));
				}
			}
			JUI.jp.repaint();
			JUI.jf.repaint();
		}
	} 
	
	//断线重连才会请求
	public static void addChuList(List<Integer> chuList,String openId,Integer position,String userId){
		removeAllChuDePais(openId);//按道理调用不到这个方法，除非……DMXCL
		if (chuList!=null&&chuList.size()>0) {
			Integer[][] chus = Client.getIntegerPaisNoSort(chuList);
			for(int j=1;j<=chus.length;j++){
				JButton b = GamePanal.getPaiButton(Cnst.numToPaiMap.get(chus[j-1][0]+""), 0,0,chus[j-1][1]==1,openId);
				if (j==chus.length) {
					if (ClientManager.room.getSth("lastChuPaiUserId")!=null) {
						String lastChuPaiUserId = ClientManager.room.getSth("lastChuPaiUserId").toString();
						if (userId.equals(lastChuPaiUserId)) {
							String lastChuPaiStr = ClientManager.room.getSth("lastChuPai").toString();
							List<Integer> extras = new ArrayList<Integer>();
							extras.add(Integer.parseInt(lastChuPaiStr));
							Integer[][] lastChuPai = Client.getIntegerPais(extras);
							if (lastChuPai[0][0] == chus[j-1][0]&&lastChuPai[0][1] == chus[j-1][1]) {
								b.setBackground(GamePanal.chuPaiColor);
							}
						}
					}
				}
				JUI.jp.add(b);
				JUI.jp.repaint();
				addChuPaiButton(b);
			}
		}
	}
	
	
	
	//chu为出的牌
	public static void repaintPlayerPais(Player p,String chu){
		
		Object obj = p.getSth("pais");
		Integer[][] pais = null;
		List<Integer> finalPais = (List<Integer>) obj;
		GamePanal.removeOnePais(finalPais, chu);
		pais = Client.getIntegerPais(finalPais);
		

		String openId = p.getSth("openId").toString();
		int position = Integer.parseInt(p.getSth("position").toString());
		GamePanal.removeAllPlayerPais(openId);//先移除玩家之前的手牌
		int num = 0;
		for(int j=1;j<=pais.length;j++){//玩家paiNum
			if (pais[j-1][1]==1) {
				num++;
    	        JUI.jp.add(GamePanal.getPaiButton(Cnst.numToPaiMap.get(pais[j-1][0]+""), GamePanal.getPaiX(position,num), GamePanal.getPaiY(position,num),pais[j-1][1]==1,openId));
			}
    	}
		for(int j=1;j<=pais.length;j++){//玩家paiNum
			if (pais[j-1][1]==0) {
				num++;
    	        JUI.jp.add(GamePanal.getPaiButton(Cnst.numToPaiMap.get(pais[j-1][0]+""), GamePanal.getPaiX(position,num), GamePanal.getPaiY(position,num),pais[j-1][1]==1,openId));
			}
    	}
		p.setSth("pais", finalPais);
		JUI.jp.repaint();
	}
	
	//b为原始的牌buttuon，在出牌时，把这个牌传过来了，
	//b.getName() 的格式为：Cnst.buttonId_牌.concat(openId).concat("_").concat(text)
	public static void addChuPaiButton(JButton b){
		String openId = b.getName().split(Cnst.split)[0].replace(Cnst.buttonId_牌, "");
		Player p = ClientManager.clientManager.get(openId).getPlayer();
		int position = Integer.parseInt(p.getSth("position").toString());
		
		nums.put(position, nums.get(position)+1);
		
		b.setFont(paiNumButtonFont);
		b.setBounds(getChuPaiX(position, nums.get(position), b),getChuPaiY(position, nums.get(position), b),paiNumButtonWidth, paiNumButtonHeight);
		b.setToolTipText(null);
		b.setName(b.getName().replace(Cnst.buttonId_牌, Cnst.buttonId_出的牌));
		JUI.jp.repaint();
		
	}
	
	public static int getChuPaiX(int position,int num,JButton b){
		int x=0;
		int lineNum = num/(oneLinePaiNum+1) +1;
		int tn = num-(lineNum-1)*oneLinePaiNum;
		switch (position) {
		case 1:
			x = GamePanal.getPaiX(position, 1)+(tn-1)*paiNumButtonWidth;
			break;
		case 2:
			x = GamePanal.getPaiX(position, 1)-b.getWidth()-paiNumButtonWidth*lineNum;
			break;
		case 3:
			x = GamePanal.getPaiX(position, 1)+b.getWidth()-paiNumButtonWidth*tn;
			break;
		case 4:
			x = GamePanal.getPaiX(position, 1)+b.getWidth()+b.getWidth()+(lineNum-1)*paiNumButtonWidth;
			break;
		}
		return x;
	}
	
	public static int getChuPaiY(int position,int num,JButton b){
		int y=0;
		int lineNum = num/(oneLinePaiNum+1) +1;
		int tn = num-(lineNum-1)*oneLinePaiNum;
		switch (position) {
		case 1:
			y = GamePanal.getPaiY(position, 1)-b.getHeight()-paiNumButtonHeight*lineNum;
			break;
		case 2:
			y = GamePanal.getPaiY(position, 1)+b.getHeight()-paiNumButtonHeight*tn;
			break;
		case 3:
			y = GamePanal.getPaiY(position, 1)+b.getHeight()+b.getHeight()+(lineNum-1)*paiNumButtonHeight;
			break;
		case 4:
			y = GamePanal.getPaiY(position, 1)+paiNumButtonHeight*(tn-1);
			break;
		}
		return y;
	}
	
	static int actionButtonWidth = 70;
	static int actionButtonHeight = 70;
	static int jiange = 8;
	static Font actionButtonFont = new Font("楷体", Font.BOLD, 40);
	
	static List<Integer> actions = new ArrayList<Integer>();
	static void initActions(){
		actions = new ArrayList<Integer>();
		actions.add(-3);
		actions.add(35);
		actions.add(66);
		actions.add(99);
		actions.add(500);
		actions.add(0);
	}
	
	public static void removeAllActionButton(String openId){
		Component[] all = JUI.jp.getComponents();
		List<Component> jbs = new ArrayList<Component>();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_行为.concat(openId))||b.getName()!=null&&b.getName().contains(Cnst.buttonId_发牌.concat(openId))) {
						jbs.add(b);
					}
				}
			}
		}
		if (jbs.size()>0) {
			for(int i=0;i<jbs.size();i++){
				JUI.jp.remove(jbs.get(i));
			}
		}
		JUI.jp.repaint();
	}
	
	static String winds = "东南西北";
	
	//获取吃的字符串，吃的那张牌在最前面
	public static String getNewChiStr(String chiStrs,Integer extra){
//		if (extra>9&&extra<=18) {
//			extra = extra-9;
//		}else if(extra>18&&extra<=27){
//			extra = extra-18;
//		}
		String[] strs = chiStrs.split(" ");
		int num = 0;
		for(int m=0;m<strs.length;m++){
			if (strs[m]!=null&&strs[m].equals(extra+"")) {
				num = m;
				break;
			}
		}
		if (num!=0) {
			String temp = strs[num];
			strs[num] = strs[0];
			strs[0] = temp;
		}
		chiStrs = "";
		for(int m=0;m<strs.length;m++){
			chiStrs = chiStrs+strs[m]+" ";
		}
		return chiStrs;
	}
	
	public static void addPlayerAction(Player p,List<Integer> actions){
		removeAllActionButton(p.getSth("openId").toString());
		/*test*/
//		initActions();
//		actions = ActionPanal.actions;
		/*test*/
		boolean containsGuo = false;
		int actionNum = actions.size();
		for(int i=0;i<actions.size();i++){
			if (actions.get(i)==0) {
				actions.remove(i);
				containsGuo = true;
				break;
			}
		}
		String wind = winds.charAt(Integer.parseInt(p.getSth("position").toString())-1)+"";
		String openId = p.getSth("openId").toString();
		for(int i=0;i<actions.size();i++){
			String str = Cnst.getActionStr(actions.get(i));
			JButton b = new JButton();
			b.setBounds(
					(JUI.frameWidth-actionButtonWidth*actionNum-jiange*actionNum)/2+actionButtonWidth*i+jiange*i,
					GamePanal.paiNumButton.getY()-jiange-actionButtonHeight,
					actionButtonWidth, 
					actionButtonHeight
					);
			b.setFont(actionButtonFont);
			b.setFocusPainted(false);
			b.setFocusable(false);
			b.setToolTipText(str);
			
			
			String chiStrs = Cnst.getChiBianMa(actions.get(i));
			if (actions.get(i)>34&&actions.get(i)<=56) {
				Integer lastChuPai = Integer.parseInt(ClientManager.room.getSth("lastChuPai").toString());
				chiStrs = getNewChiStr(chiStrs, lastChuPai);
			}
			
			URL uri = JUI.class.getResource("/icon");
	        String fileName = wind+str+"."+Draw.imageSuffix;
	        String url = uri.getPath().substring(1)+"/"+fileName;
	        Draw.drawActionImg(str, wind, url,chiStrs);
	        uri = JUI.class.getResource("/icon/"+fileName);
			ImageIcon icon = new ImageIcon(uri);
			
			Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
	        icon = new ImageIcon(temp);
			
	        b.setIcon(icon);
	        
			b.setName(Cnst.buttonId_行为.concat(openId).concat(Cnst.split).concat(actions.get(i)+""));
			b.setBorder(BorderFactory.createEtchedBorder());
	        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
			JUI.jp.add(b);
		}
		
		//过的按钮
		if (containsGuo) {
			String str = Cnst.getActionStr(0);
			JButton b = new JButton();
			b.setBounds(
					(JUI.frameWidth-actionButtonWidth*actionNum-jiange*actionNum)/2+actionButtonWidth*(actionNum-1)+jiange*(actionNum+1),
					GamePanal.paiNumButton.getY()-jiange-actionButtonHeight,
					actionButtonWidth, 
					actionButtonHeight
					);
			b.setFont(actionButtonFont);
			b.setFocusPainted(false);
			b.setFocusable(false);
			b.setToolTipText(str);
			
			URL uri = JUI.class.getResource("/icon");
	        String fileName = wind+str+"."+Draw.imageSuffix;
	        String url = uri.getPath().substring(1)+"/"+fileName;
	        Draw.drawActionImg(str, wind, url,null);
	        uri = JUI.class.getResource("/icon/"+fileName);
			ImageIcon icon = new ImageIcon(uri);
			
			Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
	        icon = new ImageIcon(temp);
	        b.setIcon(icon);
			
			
			b.setName(Cnst.buttonId_行为.concat(openId).concat(Cnst.split).concat(Cnst.ACTION_TYPE_GUO+""));
			b.setBorder(BorderFactory.createEtchedBorder());
	        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
			JUI.jp.add(b);

			actions.add(Cnst.ACTION_TYPE_GUO);
		}
		
		JUI.jp.repaint();
	}
	
	//发牌按钮
	public static void addFaPaiButton(Player p,List<Integer> actions){
		removeAllActionButton(p.getSth("openId").toString());
		String wind = winds.charAt(Integer.parseInt(p.getSth("position").toString())-1)+"";
		String openId = p.getSth("openId").toString();
		
		int actionNum = 1;
		
		String str = Cnst.getActionStr(actions.get(0));
		JButton b = new JButton();
		b.setBounds(
				(JUI.frameWidth-actionButtonWidth*actionNum-jiange*actionNum)/2+actionButtonWidth*(actionNum-1)+jiange*(actionNum+1),
				GamePanal.paiNumButton.getY()-jiange-actionButtonHeight,
				actionButtonWidth, 
				actionButtonHeight
				);
		b.setFont(actionButtonFont);
		b.setFocusPainted(false);
		b.setFocusable(false);
		b.setToolTipText(str);
		
		URL uri = JUI.class.getResource("/icon");
        String fileName = wind+str+"."+Draw.imageSuffix;
        String url = uri.getPath().substring(1)+"/"+fileName;
        Draw.drawActionImg(str, wind, url,null);
        uri = JUI.class.getResource("/icon/"+fileName);
		ImageIcon icon = new ImageIcon(uri);
		
		Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
        icon = new ImageIcon(temp);
        b.setIcon(icon);
		
		
		b.setName(Cnst.buttonId_发牌.concat(openId).concat(Cnst.split).concat(Cnst.ACTION_TYPE_FAPAI+""));
		b.setBorder(BorderFactory.createEtchedBorder());
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
		JUI.jp.add(b);

		JUI.jp.repaint();
	}
	
	public static void sendFaPaiMessage(Player p){
		Map<String,String> send = Cnst.getSendMap(100201);
		send.put("action", Cnst.ACTION_FAPAI+"");
		send.put("roomId", ClientManager.room.getSth("roomId")+"");
		send.put("userId", p.getSth("userId").toString());
		String openId = p.getSth("openId").toString();
		ClientManager.clientManager.get(openId).send(send, openId);
	}
	
	//吃 碰 杠 胡 怼  等都在这里
	public static void playerAction(JButton b){
		String[] tempStr = b.getName().replace(Cnst.buttonId_行为, "").split(Cnst.split);
		String openId = tempStr[0];
		String action = tempStr[1];
		Map<String,String> send = Cnst.getSendMap(100201);
		send.put("roomId", ClientManager.room.getSth("roomId").toString());
		send.put("action", action);
		send.put("userId", ClientManager.clientManager.get(openId).getUserId());
		ClientManager.clientManager.get(openId).send(send, openId);
	}
	//TODO 补花
	public static void addHuaListButton(List<Integer> list,Player p){
		Integer position = Integer.parseInt(p.getSth("position").toString());
		String openId = p.getSth("openId").toString();

		//首先移除所有的行为list按钮
		removeAllHuaListButton(openId);
		for(int i=0;i<list.size();i++){
			getHuaButton(position, openId, list.get(i));
		}
	}
	public static void getHuaButton(int position,String openId,Integer actionStr){
		int num = getHuaListButtonNums(openId);
		num++;
		String name = actionStr.toString();
		JButton b = new JButton(Cnst.numToPaiMap.get(name));
		b.setBorder(BorderFactory.createEtchedBorder());
		int x = 0;
		int y = 0;
		switch (position) {
		case 1:
			x = GamePanal.getPaiX(position, 1) + JUI.huaWidth * (num - 1) ;
			y = GamePanal.getPaiY(position, 1) - JUI.huaHeight - 80;
			break;
		case 2:
			x = GamePanal.getPaiX(position, 1) - JUI.huaWidth - 80;
			y = GamePanal.getPaiY(position, 1) - JUI.huaHeight * (num - 1);
			break;
		case 3:
			x = GamePanal.getPaiX(position, 1) - JUI.huaWidth * (num - 1);
			y = GamePanal.getPaiY(position, 1) + JUI.huaHeight + 80;
			break;
		case 4:
			x = GamePanal.getPaiX(position, 1) + JUI.huaWidth + 80;
			y = GamePanal.getPaiY(position, 1) + JUI.huaHeight * (num - 1);
			break;
		}
		b.setBounds(x, y, JUI.huaWidth, JUI.huaHeight);
		b.setFocusPainted(false);
		b.setName(Cnst.buttonId_已经开过的花牌.concat(openId).concat(
				Cnst.numToPaiMap.get(name)));
		JUI.jp.add(b);
		JUI.jp.repaint();
	}
	public static void removeAllHuaListButton(String openId){
		Component[] all = JUI.jp.getComponents();
		List<Component> jbs = new ArrayList<Component>();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_已经开过的花牌.concat(openId))) {
						jbs.add(b);
					}
				}
			}
			if (jbs.size()>0) {
				for(int i=0;i<jbs.size();i++){
					JUI.jp.remove(jbs.get(i));
				}
			}
			JUI.jp.repaint();
		}
	}
	public static int getHuaListButtonNums(String openId){
		int num = 0;
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if(b.getName()!=null&&b.getName().contains(Cnst.buttonId_已经开过的花牌.concat(openId))){
						num++;
					}
				}
			}
		}
		return num;
	}
		
	//list非空并且size不为0  渲染玩家吃碰杠list
	public static void addActionListButton(List<Object> list,Player p){
		Integer position = Integer.parseInt(p.getSth("position").toString());
		String openId = p.getSth("openId").toString();

		//首先移除所有的行为list按钮
		removeAllActionListButton(openId);
		for(int i=0;i<list.size();i++){
			String actionStr = "";
			String paiType = "";
			Integer ex = 0;
			if (list.get(i) instanceof Map) {
				Map<String,Object> ll = (Map<String, Object>) list.get(i);
				String action = ll.get("action").toString();
				String extra = ll.get("extra").toString();
				actionStr = Cnst.getTypeFromAction(Integer.parseInt(action),extra);
				actionStr = getNewChiStr(actionStr, Integer.parseInt(extra));
				ex = Integer.parseInt(extra);
			}else{
				Integer action = Integer.parseInt(list.get(i).toString());
				actionStr = Cnst.getTypeFromAction(action,null);
				String extra = actionStr.split(" ")[0];
				actionStr = getNewChiStr(actionStr, Integer.parseInt(extra));
				ex = Integer.parseInt(extra);
			}
//			if (ex>9&&ex<=18) {
//				paiType = "饼";
//			}else if(ex>18&&ex<=27){
//				paiType = "条";
//			}else if(ex<=9){
//				paiType = "万";
//			}else{
//				paiType = Cnst.numToPaiMap.get(ex+"");
//			}
			getActionButton(position, openId, actionStr);
		}
	}
	public static void removeAllActionListButton(String openId){
		Component[] all = JUI.jp.getComponents();
		List<Component> jbs = new ArrayList<Component>();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_已经执行的行为.concat(openId))) {
						jbs.add(b);
					}
				}
			}
			if (jbs.size()>0) {
				for(int i=0;i<jbs.size();i++){
					JUI.jp.remove(jbs.get(i));
				}
			}
			JUI.jp.repaint();
		}
	}
	
	public static void getActionButton(int position,String openId,String actionStr){
		String[] temp = actionStr.split(" ");
		int num = getActionListButtonNums(openId);
		for(int i=0;i<temp.length;i++){
			num++;
			String name = temp[i];
//			if (Integer.parseInt(temp[i])>27) {
//				name = "";
//			}
			JButton b = new JButton(Cnst.numToPaiMap.get(name));
	        b.setBorder(BorderFactory.createEtchedBorder());
	        int x = 0;
	        int y = 0;
	        switch (position) {
			case 1:
				x = GamePanal.getPaiX(position, 1)+JUI.buttonWidth*(num-1);
				y = GamePanal.getPaiY(position, 1)-JUI.buttinHeight;
				break;
			case 2:
				x = GamePanal.getPaiX(position, 1)-JUI.buttonWidth;
				y = GamePanal.getPaiY(position, 1)-JUI.buttinHeight*(num-1);
				break;
			case 3:
				x = GamePanal.getPaiX(position, 1)-JUI.buttonWidth*(num-1);
				y = GamePanal.getPaiY(position, 1)+JUI.buttinHeight;
				break;
			case 4:
				x = GamePanal.getPaiX(position, 1)+JUI.buttonWidth;
				y = GamePanal.getPaiY(position, 1)+JUI.buttinHeight*(num-1);
				break;
			}
	        b.setBounds(x,y,JUI.buttonWidth,JUI.buttinHeight);
	        b.setFocusPainted(false);
	        b.setName(Cnst.buttonId_已经执行的行为.concat(openId).concat(Cnst.numToPaiMap.get(name)));
	        JUI.jp.add(b);
	        JUI.jp.repaint();
		}
	}
	
	public static int getActionListButtonNums(String openId){
		int num = 0;
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if(b.getName()!=null&&b.getName().contains(Cnst.buttonId_已经执行的行为.concat(openId))){
						num++;
					}
				}
			}
		}
		return num;
	}
	
	public static Map<String,Object> info = new ConcurrentHashMap<String, Object>();
	public static void createInfoData(){
		ClientManager.room = new Room();
		ClientManager.room.setSth("roomId", "666666");
		info.put("lastNum", "5");
		List<Map<String,Object>> userInfos = new ArrayList<Map<String,Object>>();
		
		for(int i=1;i<=4;i++){
			Map<String,Object> ui = new ConcurrentHashMap<String, Object>();
			ui.put("userId", ""+i+i+i+i+i+i);
			ui.put("duiNum", i);
			ui.put("isWin", i==2?1:0);
			ui.put("isDian", i==3?1:0);
			ui.put("score", i==2?9999:-3333);
			ui.put("pais", getSomeInfo(1,i));
			ui.put("actionList", getSomeInfo(2,i));
			ui.put("winInfo", getSomeInfo(3,i));
			userInfos.add(ui);
		}
		info.put("userInfos", userInfos);
	}
	
	public static List<Integer> getSomeInfo(int type,int wind){
		List<Integer> si = new ArrayList<Integer>();
		switch (type) {
		case 1:
			if (wind==1) {
				si.add(2);
				for(int i=0;i<3;i++){
					si.add(1);
				}
			}else if(wind==2){
				for(int i=0;i<8;i++){
					si.add(1);
				}
			}else if(wind==3){
				for(int i=0;i<10;i++){
					si.add(1);
				}
			}else if(wind==4){
				for(int i=0;i<13;i++){
					si.add(1);
				}
			}
			break;
		case 2:
			if (wind==1) {
				si.add(56);
				si.add(83);
				si.add(109);
			}else if(wind==2){
				si.add(83);
				si.add(109);
			}else if(wind==3){
				si.add(90);
			}else if(wind==4){
				
			}
			break;
		case 3:
			if (wind==1) {
				
			}else if(wind==2){
				for(int i=1;i<=5;i++){
					si.add(i);
				}
			}else if(wind==3){
				
			}else if(wind==4){
				
			}
			break;
		}
		return si;
	}

	static JDialog dialog = null;
	static int frameWidth = 1000;
	static int frameHeight = 700;
	
	static int roomIdX = 2;
	static int roomIdY = 2;
	static int roomIdFontSize = 20;
	static int roomIdWidth = 180;
	static int roomIdheight = roomIdFontSize;
	static int lastNunJianGe = 10;
	static int headImgX = 30;
	static int headImgY = roomIdY+roomIdheight+lastNunJianGe*2;
	static int headWidth = 100;
	static int headHeight = 100;
	static int headJianGe = 30;
	
	static int paiWidth = 40;
	static int paiHeight = 40;
	static int paiJianGe = 5;
	
	static int scoreJiange = 20;
	static int scoreX = 800;
	static int scoreWidth = 100;
	static int scoreHeight = 50;
	static int scoreFontSize = 40;
	
	static int winWidth = 100;
	static int winHeight = 100;
	static int winFontSize = 80;
	
	static int huInfoWidth = 80;
	
	
	public static void addXiaoJieSuanDialog(Map<String,Object> info){
//		dialog = new JDialog(JUI.jf,true);
		dialog = new JDialog(JUI.jf,true){
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				super.paint(g);
				g.setColor(Color.white);//设置第一条线的颜色 
				int i=1;
				int lineHeight = 3;
				//下面的坐标死活动态不了，我你妈
		        g.fill3DRect(0, headImgY+i++*(headHeight+headJianGe/2)+JUI.titleHeight-4, frameWidth, lineHeight,true);
		        g.fill3DRect(0, headImgY+i++*(headHeight+headJianGe/2)+JUI.titleHeight+10, frameWidth, lineHeight,true);
		        g.fill3DRect(0, headImgY+i++*(headHeight+headJianGe/2)+JUI.titleHeight+24, frameWidth, lineHeight,true);
			}
		};
		String lastNum = info.get("lastNum").toString();//剩余圈数
		List<Map<String,Object>> userInfos = (List<Map<String, Object>>) info.get("userInfo");
		
		dialog.setTitle("小结算");
		dialog.setLayout(null);
        dialog.setSize(frameWidth, frameHeight);//设置窗口大小
        
        Font f1 = new Font("楷体", Font.PLAIN, roomIdFontSize);
    	JLabel l = new JLabel("房间号："+ClientManager.room.getSth("roomId").toString());
		l.setBounds(roomIdX,roomIdY,roomIdWidth,roomIdheight);
		l.setFont(f1);
		dialog.getContentPane().add(l);
        
        f1 = new Font("楷体", Font.PLAIN, roomIdFontSize);
    	l = new JLabel("剩余圈数："+lastNum);
		l.setBounds(roomIdX+roomIdWidth+lastNunJianGe,roomIdY,roomIdWidth,roomIdheight);
		l.setFont(f1);
		dialog.getContentPane().add(l);
        
        for(int i=0;i<userInfos.size();i++){
        	Map<String,Object> map = userInfos.get(i);
        	String userId = map.get("userId").toString();
//        	String openId = "wsw-"+(i+1);//测试数据
        	String openId = ClientManager.playerMap.get(userId).getSth("openId").toString();
        	int currscore = Integer.parseInt(map.get("score").toString());
        	Player p = ClientManager.clientManager.get(openId)!=null?ClientManager.clientManager.get(openId).getPlayer():null;
        	if (p!=null) {
				Integer score = Integer.parseInt(p.getSth("score").toString());
				score = score+currscore;
				p.setSth("score", score);
			}
        	JButton b = new JButton();
            b.setFocusPainted(false);
            b.setBounds(headImgX, headImgY+i*(headHeight+headJianGe), headWidth, headHeight);
            b.setBorder(BorderFactory.createEtchedBorder());
            URL uri = JUI.class.getResource("/icon");
            String fileName = userId+"."+Draw.imageSuffix;
            String url = uri.getPath().substring(1)+"/"+fileName;
            Draw.draw(userId, openId, url,false,i==3?true:false,null,true,null);
            uri = JUI.class.getResource("/icon/"+fileName);
    		ImageIcon icon = new ImageIcon(uri);
    		
    		Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
            icon = new ImageIcon(temp);
            b.setIconTextGap(-10);
            b.setIcon(icon);
            dialog.getContentPane().add(b);
            
            List<Integer> pais = (List<Integer>) map.get("pais");
            List<Integer> actionList = (List<Integer>) map.get("actionList");
            
            int actionPainums = 0;
            int jianges = 0;
            if (actionList!=null&&actionList.size()>0) {
            	for(int m=0;m<actionList.size();m++){
                	Object o = actionList.get(m);
                	int action = 0;
                	if (o instanceof Map) {
    					action = Integer.parseInt(((Map) o).get("action").toString());
    				}else if(o instanceof String||o instanceof Integer){
    					action = Integer.parseInt(o.toString());
    				}
                	int[] ps = null;
                	if (action>34&&action<=56) {
            			ps = Cnst.bianmaMap.get(action);
            		}else if (action>56&&action<=90) {
            			ps = new int[]{action-56,action-56,action-56};
            		}else if (action>90&&action<=126) {
            			ps = new int[]{action-90,action-90,action-90,action-90};
            		}else if(action==-2){//暗杠
            			Integer extra = Integer.parseInt(((Map) o).get("extra").toString());
            			ps = new int[]{extra,extra,extra,extra};
            		}
                	for(int n=0;n<ps.length;n++){
                		b = new JButton(Cnst.numToPaiMap.get(ps[n]+""));
                        b.setBounds(headImgX+headWidth+actionPainums*paiWidth+jianges*paiJianGe, headImgY+headHeight-paiHeight+(headJianGe+headHeight)*i, paiWidth, paiHeight);
                        b.setFocusPainted(false);
                        b.setFont(JUI.mf);
                        b.setBorder(BorderFactory.createEtchedBorder());
                        b.setBackground(Color.LIGHT_GRAY);
                        dialog.getContentPane().add(b);
                		actionPainums++;
                	}
                	jianges++;
                }
			}
            
            
            boolean hu = map.get("isWin").toString().equals("1")?true:false;
            
            if (pais!=null&&pais.size()>0) {
            	Integer last = null;
            	if (hu) {
					last = new Integer(pais.remove(pais.size()-1));
				}
            	Integer[] ps = new Integer[pais.size()];
				pais.toArray(ps);
				Arrays.sort(ps);
				pais = Arrays.asList(ps);
				
				List<Integer> arrList = new ArrayList<Integer>(pais);
				pais = arrList;
				if (last!=null) {
					pais.add(last);
				}
				
            	for(int m=0;m<pais.size();m++){
                	b = new JButton(Cnst.numToPaiMap.get(pais.get(m)+""));
                	if (hu&&m==pais.size()-1) {
                    	jianges++;
    				}
                    b.setBounds(headImgX+headWidth+actionPainums*paiWidth+jianges*paiJianGe, headImgY+headHeight-paiHeight+(headJianGe+headHeight)*i, paiWidth, paiHeight);
                    b.setFocusPainted(false);
                    b.setFont(JUI.mf);
                    b.setBorder(BorderFactory.createEtchedBorder());

                    dialog.getContentPane().add(b);
            		actionPainums++;
                }
            }
            
            
            String score = map.get("score").toString();
            f1 = new Font("楷体", Font.PLAIN, scoreFontSize);
        	l = new JLabel(score);
        	//headImgX+headWidth+actionPainums*paiWidth+jianges*paiJianGe+scoreJiange
    		l.setBounds(scoreX,headImgY+i*(headHeight+headJianGe)+i*(headHeight-scoreHeight)/2,scoreWidth,scoreHeight);
    		l.setFont(f1);
    		dialog.getContentPane().add(l);
            
    		
    		boolean isDian = map.get("isDian").toString().equals("1")?true:false;
    		boolean isWin = map.get("isWin").toString().equals("1")?true:false;
    		if (isDian||isWin) {
    			f1 = new Font("楷体", Font.PLAIN, winFontSize);
            	l = new JLabel(isDian?"点":isWin?"胡":"");
            	//headImgX+headWidth+actionPainums*paiWidth+jianges*paiJianGe+scoreJiange
        		l.setBounds(scoreX+scoreWidth,headImgY+i*(headHeight+headJianGe)+i*(headHeight-winHeight)/2,winWidth,winHeight);
        		l.setFont(f1);
//        		l.setOpaque(true);
//        		l.setBackground(Color.red);
        		l.setForeground(Color.red);
        		dialog.getContentPane().add(l);
			}
    		if (isWin) {
    			List<Integer> huInfo = (List<Integer>) map.get("winInfo");
    			for(int m=0;m<huInfo.size();m++){
    				f1 = new Font("楷体", Font.PLAIN, roomIdFontSize);
    		    	l = new JLabel(Cnst.huInfoStr.get(huInfo.get(m)));
    				l.setBounds(headImgX+headWidth+m*huInfoWidth, headImgY+headHeight-paiHeight+(headJianGe+headHeight)*i-roomIdheight-paiJianGe,huInfoWidth,roomIdheight);
    				l.setFont(f1);
    				l.setForeground(Color.red);
    				dialog.getContentPane().add(l);
    			}
			}
        }
        
        JButton b = new JButton("下一局");
        b.setFocusPainted(false);
        b.setBounds(nextX, nextY, frameWidth, nextHeight);
        b.setBorder(BorderFactory.createEtchedBorder());

    	Font temp = new Font("楷体", Font.PLAIN, 50);

        b.setFont(temp);
        b.setToolTipText("点击之后，四个人后台轮流请求准备");
        b.setName(Cnst.buttonId_下一局);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        dialog.getContentPane().add(b);
        
        dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.validate();
        dialog.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        dialog.setVisible(true);
		dialog.repaint();
        
	}
	
	static int nextHeight = 100;
	static int nextX = 0;
	static int nextY = frameHeight-nextHeight-JUI.titleHeight;

	
	public static void nextJu(){
		GamePanal.hasAddRuleButton = false;
		dialog.removeAll();
		dialog.repaint();
		dialog.dispose();
		if (ClientManager.clientManager==null||ClientManager.clientManager.size()!=4) {
			JOptionPane.showMessageDialog(JUI.jf,"当前有玩家未上线，点击确定之后，仅让在线玩家发送准备","警告",JOptionPane.PLAIN_MESSAGE);
		}
		JUI.jp.removeAll();
		JUI.jp.repaint();
		for(String key:ClientManager.clientManager.keySet()){
			GamePanal.zhunBei(ClientManager.clientManager.get(key).getUserId(), key, ClientManager.room.getSth("roomId").toString());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
}
