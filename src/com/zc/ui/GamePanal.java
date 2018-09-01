package com.zc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.alibaba.fastjson.JSON;
import com.zc.domian.Player;
import com.zc.domian.Room;
import com.zc.jws.Client;
import com.zc.jws.ClientManager;
import com.zc.listener.MyMouseListener;
import com.zc.util.Cnst;
import com.zc.util.Draw;

public class GamePanal {
	
	//openId-button
	static Map<String,JButton> headButtons = new ConcurrentHashMap<String, JButton>();
	public static JButton paiNumButton = null;
	
	
	public static boolean hasAddRuleButton = false;
	
	public synchronized static void addRuleButton(){
		if (hasAddRuleButton) {
			return;
		}
		hasAddRuleButton = true;
		JButton b = new JButton("规则");
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setBounds(0,0,50,50);
        b.setFocusPainted(false);
        b.setToolTipText("点击查看房间规则");
        b.setName(Cnst.buttonId_房间规则);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        
        JUI.jp.add(b);
        JUI.jp.repaint();
	}
	public static void addKaiJuButton(String openId){
		// 移除解散
		Component[] all = JUI.jp.getComponents();
		if (all != null && all.length > 0) {
			for (Component b : all) {
				if (b instanceof JButton) {
					if (b.getName() != null
							&& b.getName().contains(
									Cnst.buttonId_解散.concat(openId))) {
						JUI.jp.remove(b);
					}
				}
			}
			JUI.jp.repaint();
		}	
		JButton b = new JButton("开局");
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setBounds(50,0,50,50);
        b.setFocusPainted(false);
        b.setToolTipText("点击开局");
        b.setName(Cnst.buttonId_开局.concat(openId));
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        
        JUI.jp.add(b);
        JUI.jp.repaint();
	}
	
	public static void addJieSanButton(String openId){
		//移除开局
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_开局.concat(openId))) {
						JUI.jp.remove(b);
					}
				}
			}
			JUI.jp.repaint();
		}
		JButton b = new JButton("解散");
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setBounds(50,0,50,50);
        b.setFocusPainted(false);
        b.setToolTipText("点击解散");
        b.setName(Cnst.buttonId_解散.concat(openId));
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        
        JUI.jp.add(b);
        JUI.jp.repaint();
	}
	
	public static void showRules1(){
		dialog = new JDialog(JUI.jf,false);
		dialog.setTitle("房间规则");
		dialog.setLayout(null);
        dialog.setSize(frameWidth, frameHeight);//设置窗口大小
        String userId = ClientManager.room.getSth("userId").toString();
        String userName = ClientManager.room.getSth("userName").toString();
        String roomId = ClientManager.room.getSth("roomId").toString();
        String state = ClientManager.room.getSth("state").toString();
        String lastNum = ClientManager.room.getSth("lastNum").toString();
        String totalNum = ClientManager.room.getSth("totalNum").toString();
        
        JLabel l = new JLabel("房间号："+roomId);
		l.setBounds(firseLableX,firseLableY, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        try {
			l = new JLabel("房主昵称："+URLDecoder.decode(userName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		l.setBounds(firseLableX,firseLableY+ruleFontSize+jiange, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("房主id："+userId);
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*2, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("房间状态："+Cnst.roomStateStr.get(state));
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*3, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("圈数信息："+(Integer.parseInt(totalNum)-Integer.parseInt(lastNum)+1)+"/"+totalNum+"圈");
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*4, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        int i = 5;
        for(String key:Cnst.createRoomParamsType.keySet()){
        	if (ClientManager.room.getSth(key)!=null) {
        		String va = ClientManager.room.getSth(key).toString();
                l = new JLabel();
                String text = "";
                if (Cnst.createRoomParamsType.get(key)==Cnst.paramsDataType_是否) {
            		text = va.equals(Cnst.params_是)?"是":"否";
    			}else if(Cnst.createRoomParamsType.get(key)==Cnst.paramsDataType_直接取值){
            		text = va;
    			}else if(Cnst.createRoomParamsType.get(key)==Cnst.paramsDataType_有中文含义){
            		text = Cnst.userDefinedStr.get(key).get(va);
    			}
                l.setText(Cnst.createRoomParamsStr.get(key)+"："+text);
        		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*i++, lableWidth, lableHeight);
        		l.setFont(mf);
                dialog.add(l);
			}
        }
        
        dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.validate();
        dialog.setLocationRelativeTo(null);//把窗口位置设置到屏幕中心
        dialog.setVisible(true);
		dialog.repaint();
        
	}

	static JDialog dialog = null;
	static int frameHeight = 500;
	static int frameWidth = 500;
	static int ruleFontSize = 30;
	static Font mf = new Font("楷体", Font.PLAIN, ruleFontSize);
	static int firseLableX = 90;
	static int firseLableY = 20;
	static int lableWidth = 300;
	static int lableHeight = 40;
	static int jiange = 20;
	public static void showRules(){
		dialog = new JDialog(JUI.jf,false);
		dialog.setTitle("房间规则");
		dialog.setLayout(null);
        dialog.setSize(frameWidth, frameHeight);//设置窗口大小
        String roomType = ClientManager.room.getSth("roomType").toString();
        String scoreType = ClientManager.room.getSth("scoreType").toString();
        String userId = ClientManager.room.getSth("userId").toString();
        String userName = ClientManager.room.getSth("userName").toString();
        String roomId = ClientManager.room.getSth("roomId").toString();
        String state = ClientManager.room.getSth("state").toString();
        String lastNum = ClientManager.room.getSth("lastNum").toString();
        String totalNum = ClientManager.room.getSth("totalNum").toString();
        
        JLabel l = new JLabel("房间号："+roomId);
		l.setBounds(firseLableX,firseLableY, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        try {
			l = new JLabel("房主昵称："+URLDecoder.decode(userName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		l.setBounds(firseLableX,firseLableY+ruleFontSize+jiange, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("房主id："+userId);
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*2, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("房间状态："+Cnst.roomStateStr.get(state));
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*3, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("房间类型："+(roomType.equals(Cnst.roomType1+"")?"房主模式":"代开"));
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*4, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("计分规则："+(scoreType.equals(Cnst.scoreType1+"")?"点炮三家付":"点炮包三家"));
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*5, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        l = new JLabel("圈数信息："+(Integer.parseInt(totalNum)-Integer.parseInt(lastNum)+1)+"/"+totalNum+"圈");
		l.setBounds(firseLableX,firseLableY+(ruleFontSize+jiange)*6, lableWidth, lableHeight);
		l.setFont(mf);
        dialog.add(l);
        
        
        dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.validate();
        dialog.setLocationRelativeTo(null);//把窗口位置设置到屏幕中心
        dialog.setVisible(true);
		dialog.repaint();
        
	}
	
	public static void zhunBei(String userId,String openId,String roomId){
		//当前玩家需要请求准备
		Map<String,String> send = Cnst.getSendMap(100200);
		send.put("userId", userId);
		send.put("roomId", roomId);
		ClientManager.clientManager.get(openId).send(send, openId);
	}
	
	
	public static void changePlayerImageState(Player p,boolean inline){
		String openId = p.getSth("openId").toString();
		String userId = p.getSth("userId").toString();
		String score = p.getSth("score").toString();
		Integer position = Integer.parseInt(p.getSth("position").toString()); 
		boolean fangzhu = p.getSth("fangzhu")!=null;
		boolean zhuang = p.getSth("zhuang")!=null;
		String playStatus = p.getSth("playStatus").toString();
		JButton b = headButtons.get(openId);
		JUI.jp.remove(b);
		JUI.jp.add(getHeadButton(userId,openId,getHeadimgX(position),getHeadimgY(position),position,fangzhu,zhuang,playStatus,inline,score));
		JUI.jp.repaint();
	}
	
	public static void removeOnePais(List<Integer> finalPais,String pai){
		if (finalPais!=null&&finalPais.size()>0) {
			for(int i=0;i<finalPais.size();i++){
				if (Integer.parseInt(pai)==finalPais.get(i)) {
					finalPais.remove(i);
					break;
				}
			}
		}
	}
	
	//刷新玩家视图
	@SuppressWarnings("unchecked")
	public static void addPlayer(Player p){
		String openId = p.getSth("openId").toString();
		if (ClientManager.room==null) {
			return;
		}
		
		String fangzhuId = ClientManager.room.getSth("userId").toString();
		String userId = p.getSth("userId").toString();
		ClientManager.playerMap.put(userId, p);
		Object zhuang = ClientManager.room.getSth("zhuangPlayer");
		boolean isZhuang = false;
		String cuid = p.getSth("userId").toString();
		if (zhuang!=null&&zhuang.toString().equals(cuid)) {
			isZhuang = true;
			p.setSth("zhuang",true);
		}
		boolean fangzhu = false;
		if (fangzhuId.equals(cuid)) {
			fangzhu = true;
			p.setSth("fangzhu", true);
		}
		if (JUI.checkPanal()==1) {
			JUI.jp.removeAll();
		}
		try {
			Integer position = (Integer) p.getSth("position");
			if (p.getSth("pais")!=null) {
				Object obj = p.getSth("pais");
				List<Integer> finalPais = (List<Integer>) obj;
				Integer[][] pais = null;
				pais = Client.getIntegerPaisNoSort((List)obj);
//				if (obj instanceof List) {
//					pais = Client.getIntegerPaisNoSort((List)obj, ClientManager.room.getSth("hunPai").toString());
//				}else{
//					pais = (Integer[][]) obj;
//				}
				removeAllPlayerPais(openId);//先移除玩家之前的手牌
				
				Integer num0 = null;
				Integer num1 = null;
				boolean addedFaPai = false;
				if (pais.length==14||pais.length==11||pais.length==8||pais.length==5||pais.length==2) {
					addedFaPai = true;
					if (addedFaPai) {//lastFaPai以这个字段为准
						String roomLF = String.valueOf(finalPais.get(finalPais.size()-1));
						removeOnePais(finalPais, roomLF);
						List<Integer> temp = new ArrayList<Integer>();
						temp.add(Integer.parseInt(roomLF));
						Integer[][] roomLastFaPai = Client.getIntegerPais(temp);
						num0 = roomLastFaPai[0][0];
						num1 = roomLastFaPai[0][1];
//						pais = removePaiFromPais(pais, new Integer[][]{{num0,num1}});
//						removeOnePais(finalPais, roomLF);
						p.setSth("pais", finalPais);
					}else{
						num0 = pais[pais.length-1][0];
						num1 = pais[pais.length-1][1];
					}
					pais = getNewIntegerArray(pais, pais.length-1);
				}else{
					pais = getNewIntegerArray(pais, pais.length);
				}
				
				int num = 0;
				for(int j=1;j<=pais.length;j++){//玩家paiNum
					if (pais[j-1][1]==1) {
						num++;
		    	        JUI.jp.add(getPaiButton(Cnst.numToPaiMap.get(pais[j-1][0]+""), getPaiX(position,num), getPaiY(position,num),pais[j-1][1]==1,openId));
					}
	        	}
				for(int j=1;j<=pais.length;j++){//玩家paiNum
					if (pais[j-1][1]==0) {
						num++;
		    	        JUI.jp.add(getPaiButton(Cnst.numToPaiMap.get(pais[j-1][0]+""), getPaiX(position,num), getPaiY(position,num),pais[j-1][1]==1,openId));
					}
	        	}
				//No such child: 64  报错就在下面if里的几行
				if (p.getSth("chuList")!=null) {//toAdd
					
					List<Integer> chuList = (List<Integer>) p.getSth("chuList");
					
					
					if (ActionPanal.nums.get(position)!=chuList.size()) {
						
						
						ActionPanal.addChuList(chuList,openId,position,userId);
					}
				}
				
				boolean needClean = false;//是否需要清理上个玩家最后出的牌的背景色
				boolean needChange = false;//是否需要改变当前出牌玩家的手牌背景色
				//在玩家牌的button渲染完之后
				//需要对当前行为解析，是否有动作
				//如果有动作，就保留刚出的牌的背景色，如果没有吃碰杠等，就去掉最新出牌的背景色
				if (p.getSth("currAction")!=null) {
					List<Integer> actions = (List<Integer>) p.getSth("currAction");
					if (actions.size()>0) {
						if (actions.contains(Cnst.ACTION_CHUPAI)) {
							needClean = true;
							needChange = true;
						}else if(actions.contains(Cnst.ACTION_FAPAI)){//需要添加发牌按钮
							addedFaPai = true;
							needClean = true;
							if (Cnst.shouFaPaiButton) {//展示发牌button
								ActionPanal.addFaPaiButton(p, actions);
							}else{//不展示发牌button，直接发牌
								ActionPanal.sendFaPaiMessage(p);
							}
						}else{
							if (ClientManager.room.getSth("lastFaPaiUserId")!=null) {
								String lastFaPaiUserId = ClientManager.room.getSth("lastFaPaiUserId").toString();
								if (!lastFaPaiUserId.equals(userId)) {
									addedFaPai = true;
								}
								
							}
							ActionPanal.addPlayerAction(p, actions);
						}
					}
					
				}else{
					addedFaPai = true;
				}
				
				if (!addedFaPai) {
					if(p.getSth("lastFaPai")!=null){
						Object lf = p.getSth("lastFaPai");
						if (lf instanceof String||lf instanceof Integer) {
							List<Integer> extras = new ArrayList<Integer>();
							extras.add(Integer.parseInt(lf.toString()));
							Integer[][] lastFaPai = Client.getIntegerPais(extras);
							num0 = lastFaPai[0][0];
							num1 = lastFaPai[0][1];
						}else if(lf instanceof Integer[][]){
							Integer[][] lastFaPai = (Integer[][]) p.getSth("lastFaPai");
							num0 = lastFaPai[0][0];
							num1 = lastFaPai[0][1];
						}
						//在渲染lastFaPai之后，和手牌之后，悄悄的把lastFaPai加入手牌里，这时候实际视图没有，但是数据里有了
						//目的是为了在重新整理手牌的时候，不少牌，在接收行为回应的时候会清理掉lastFaPai，所以不能等那时候再处理
						finalPais.add(Integer.parseInt(lf.toString()));
						p.setSth("pais", finalPais);
					}
				}
				
				//添加单独的牌
				if (num0!=null) {
					if (position==1) {
						JUI.jp.add(getPaiButton(Cnst.numToPaiMap.get(num0+""), getPaiX(position,pais.length+1)+danPaiJiange, getPaiY(position,pais.length+1),num1==1,openId));
					}else if(position==2){
						JUI.jp.add(getPaiButton(Cnst.numToPaiMap.get(num0+""), getPaiX(position,pais.length+1), getPaiY(position,pais.length+1)-danPaiJiange,num1==1,openId));
					}else if(position==3){
						JUI.jp.add(getPaiButton(Cnst.numToPaiMap.get(num0+""), getPaiX(position,pais.length+1)-danPaiJiange, getPaiY(position,pais.length+1),num1==1,openId));
					}else if(position==4){
						JUI.jp.add(getPaiButton(Cnst.numToPaiMap.get(num0+""), getPaiX(position,pais.length+1), getPaiY(position,pais.length+1)+danPaiJiange,num1==1,openId));
					}
				}
				
				
				if (needChange) {
					GamePanal.changeCurrentChuPlayerPaisBackground(openId);
				} 
				if (needClean) {
					GamePanal.cleanCurrentChuPaiBackground();
				}
				
				
			}
			String playStatus = p.getSth("playStatus").toString();
			//添加玩家头像
			JButton b = headButtons.get(openId);
			if (b!=null) {
				JUI.jp.remove(b);
			}

			if (p.getSth("score")!=null) {
				String score = p.getSth("score").toString();
				JUI.jp.add(getHeadButton(p.getSth("userId").toString(),openId,getHeadimgX(position),getHeadimgY(position),position,fangzhu,isZhuang,playStatus,true,score));
			}
			
			//添加actionList视图
			if (p.getSth("actionList")!=null) {
				List<Object> actionList = (List<Object>) p.getSth("actionList");
				if (actionList.size()>0) {
					ActionPanal.addActionListButton(actionList,p);
				}
			}
			//添加huaList视图
			if(p.getSth("huaList") != null ){
				List<Integer> huaList = (List<Integer>) p.getSth("huaList");
				if (huaList.size()>0) {
					ActionPanal.addHuaListButton(huaList,p);
				}
			}
			JUI.jp.repaint();
			addRuleButton();
			if(p.getSth("playStatus").equals(Cnst.PLAYER_STATE_GAME)){
				addJieSanButton(openId);
			}
			next(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Integer[][] removePaiFromPais(Integer[][] pais,Integer[][] pai){
		Integer[][] newPais = new Integer[pais.length-1][2];
		List<String> list = new ArrayList<String>();
		for(int i=0;i<pais.length;i++){
			list.add(pais[i][0]+Cnst.split+pais[i][1]);
		}
		for(int i=0;i<list.size();i++){
			if ((pai[0][0]+Cnst.split+pai[0][1]).equals(list.get(i))) {
				list.remove(i);
				break;
			}
		}
		for(int i=0;i<list.size();i++){
			String[] temp = list.get(i).split(Cnst.split);
			newPais[i][0] = Integer.parseInt(temp[0]);
			newPais[i][1] = Integer.parseInt(temp[1]);
		}

		Arrays.sort(newPais, new Comparator<Integer[]>(){//二维数组按照某列进行排序,你也可以采用Map
            public int compare(Integer[] o1,Integer[] o2) {//任何多维数组可看成一个一维数组,一维数组中每个元素是一个一维数组
                return o1[0].compareTo(o2[0]);//比较：大于0则表示升序,这里的[]中的0代表按照第几列为标准排序，可以修改
            }
        });
		return newPais;
	}
	
	//向手牌中加入faPai
	public static Integer[][] addPaiToPais(Integer[][] pais,Integer[][] pai){
		Integer[][] newPais = new Integer[pais.length+1][2];
		
		for(int i=0;i<pais.length;i++){
			newPais[i][0] = pais[i][0];
			newPais[i][1] = pais[i][1];
		}

		newPais[pais.length][0] = pai[0][0];
		newPais[pais.length][1] = pai[0][1];
		Arrays.sort(newPais, new Comparator<Integer[]>(){//二维数组按照某列进行排序,你也可以采用Map
            public int compare(Integer[] o1,Integer[] o2) {//任何多维数组可看成一个一维数组,一维数组中每个元素是一个一维数组
                return o1[0].compareTo(o2[0]);//比较：大于0则表示升序,这里的[]中的0代表按照第几列为标准排序，可以修改
            }
        });
		return newPais;
	}
	
	
	
	static int danPaiJiange = 8;
	static int paiNumButtonWidth = 50;
	static int paiNumButtonHeight = 50;
	static Font paiNumButtonFont = new Font("楷体", Font.PLAIN, 35);
	
	public synchronized static void changePaiNumButton(String paiNum){
		if (paiNumButton==null) {
			paiNumButton = new JButton();
			paiNumButton.setBounds((JUI.frameWidth-paiNumButtonWidth)/2, (JUI.frameHeight-paiNumButtonHeight)/2, paiNumButtonWidth, paiNumButtonHeight);
			paiNumButton.setFont(paiNumButtonFont);
			paiNumButton.setFocusPainted(false);
			paiNumButton.setFocusable(false);
			paiNumButton.setToolTipText("房间当前牌数");
			paiNumButton.setBackground(new Color(238, 238, 238));
			paiNumButton.setBorder(BorderFactory.createEtchedBorder());
			JUI.jp.add(paiNumButton);
		}
		paiNumButton.setText(paiNum);

		JUI.jp.repaint();
	}
	
	public static Integer[][] getNewIntegerArray(Integer[][] pais,int num){
		Integer[][] newPais = new Integer[num][2];
		for(int i=0;i<num;i++){
			newPais[i][0] = pais[i][0];
			newPais[i][1] = pais[i][1];
		}
		Arrays.sort(newPais, new Comparator<Integer[]>(){//二维数组按照某列进行排序,你也可以采用Map
            public int compare(Integer[] o1,Integer[] o2) {//任何多维数组可看成一个一维数组,一维数组中每个元素是一个一维数组
                return o1[0].compareTo(o2[0]);//比较：大于0则表示升序,这里的[]中的0代表按照第几列为标准排序，可以修改
            }
        });
		return newPais;
	}
	
	//移除玩家头像
	public static void removePlayerButton(String openId){
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if(b.getName()!=null&&b.getName().equals(Cnst.buttonId_头像.concat(openId))){
						JUI.jp.remove(b);
						break;
					}
				}
			}
			JUI.jp.repaint();
		}
	
		
	}
	
	//清理最新出的牌的背景色
	public static void cleanCurrentChuPaiBackground(){
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_出的牌)) {
						b.setBackground(JUI.defaultButton.getBackground());
					}
				}
			}
			JUI.jp.repaint();
		}
	}
	//清理当前出牌人的手牌的背景色
	public static void cleanAllPaiBackgrounds(){
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_牌)) {
						b.setBackground(JUI.defaultButton.getBackground());
					}
				}
			}
			JUI.jp.repaint();
		}
	}
	
	public static Color chuPaiColor = new Color(250, 220, 148);
	//当前出牌人，要更换所有牌的背景色
	public static void changeCurrentChuPlayerPaisBackground(String openId){
		cleanAllPaiBackgrounds();
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_牌.concat(openId))) {
						b.setBackground(chuPaiColor);
					}
				}
			}
			JUI.jp.repaint();
		}
	}
	//移除玩家所有手牌
	public static void removeAllPlayerPais(String openId){
		Component[] all = JUI.jp.getComponents();
		List<Component> jbs = new ArrayList<Component>();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_牌.concat(openId))) {
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
	
	//渲染完玩家，需要对玩家状态做判断，做下一步操作，前提是玩家一定在这个房间里面，playStatus不是1
	public static void next(Player p){
//		Integer playStatus = Integer.parseInt(p.getSth("playStatus").toString());
		Room r = ClientManager.room;
		Integer userId = Integer.parseInt(p.getSth("userId").toString());
		Integer roomState = Integer.parseInt(r.getSth("state").toString());
		String roomId = r.getSth("roomId").toString();
		String openId = p.getSth("openId").toString();
		String playStatus = p.getSth("playStatus").toString();
		if (p.getSth("fangzhu")==null) {//不是房主的主动请求准备
			if (roomState==Cnst.ROOM_STATE_CREATED||roomState.equals(Cnst.ROOM_STATE_XJS)) {
				if (!playStatus.equals(Cnst.PLAYER_STATE_PREPARED+"")) {
					zhunBei(userId+"", openId, roomId);
				}
			}else{//游戏中
				//toAdd
			}
		}
	}
	
	public static JButton getHeadButton(String userId,String openId,int x,int y,int wind,boolean fangzhu,boolean zhuang,String playStatus,boolean inline,String score){
		removePlayerButton(openId);
        JButton b = new JButton();
        b.setFocusPainted(false);
        b.setBounds(0, 0, JUI.headimgWidth,JUI.headimgHeight);
        b.setBorder(BorderFactory.createEtchedBorder());
        URL uri = JUI.class.getResource("/icon");
        String fileName = userId+"."+Draw.imageSuffix;
        String url = uri.getPath().substring(1)+"/"+fileName;
        Draw.draw(userId, openId, url,fangzhu,zhuang,playStatus,inline,score);
        uri = JUI.class.getResource("/icon/"+fileName);
		ImageIcon icon = new ImageIcon(uri);
		
		Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
        icon = new ImageIcon(temp);
        b.setIconTextGap(-10);
        b.setToolTipText("千万不要点击！");
        b.setLocation(x, y);
        b.setIcon(icon);
        b.setName(Cnst.buttonId_头像.concat(openId));
        headButtons.put(openId, b);
        return b;
	}
	//创建牌的button
	public static JButton getPaiButton(String text,int x,int y,boolean hun,String openId){
		//jbutton
        JButton b = new JButton();
        b.setBounds(x, y, JUI.buttonWidth, JUI.buttinHeight);
        b.setFocusPainted(false);
        b.setFont(JUI.mf);
        b.setToolTipText("单击右键出牌");
        b.setBorder(BorderFactory.createEtchedBorder());
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        if (hun) {
//        	Image temp = JUI.hunIcon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), JUI.hunIcon.getImage().SCALE_AREA_AVERAGING);  
//        	Icon icon = new ImageIcon(temp);
        	
        	URL uri = JUI.class.getResource("/icon");
	        String fileName = text+"."+Draw.imageSuffix;
	        String url = uri.getPath().substring(1)+"/"+fileName;
	        Draw.drawHunPai(text, url);
	        uri = JUI.class.getResource("/icon/"+fileName);
			ImageIcon icon = new ImageIcon(uri);
			
			Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
	        icon = new ImageIcon(temp);
        	
        	b.setIcon(icon);
        	b.setIconTextGap(-30);
		}else{
			if (text.contains("万")) {
				b.setForeground(new Color(242, 45, 17));
			}else if(text.contains("饼")){
				b.setForeground(new Color(66, 140, 241));
			}else if(text.contains("条")){
				b.setForeground(new Color(52, 220, 125));
			}
			b.setText(text);
		}
        b.setName(Cnst.buttonId_牌.concat(openId).concat(Cnst.split).concat(text));
        return b;
	}
	
	//给出玩家风向,返回牌的X坐标
	public static int getPaiX(int wind,int paiNum){
		int x = 0;
		switch (wind) {
		case 1:
			x = (JUI.frameWidth-JUI.buttonWidth*13)/2+JUI.buttonWidth*(paiNum-1);
			break;
		case 2:
			x = JUI.frameWidth-JUI.buttonWidth-5;
			break;
		case 3:
			x = JUI.frameWidth-(JUI.frameWidth-JUI.buttonWidth*13)/2-JUI.buttonWidth*(paiNum);
			break;
		case 4:
			x = 0;
			break;
		}
		return x;
	}
	//给出玩家风向,返回牌的X坐标
	public static int getPaiY(int wind,int paiNum){
			int y = 0;
			switch (wind) {
			case 1:
				y = JUI.frameHeight-JUI.buttinHeight-JUI.titleHeight;
				break;
			case 2:
				y = JUI.frameHeight-(JUI.frameHeight-JUI.buttinHeight*13)/2-JUI.buttinHeight*(paiNum);
				break;
			case 3:
				y = 0;
				break;
			case 4:
				y = (JUI.frameHeight-JUI.buttinHeight*13)/2+JUI.buttinHeight*(paiNum-1);
				break;
			}
			return y;
		}
	//获取头像的X坐标
	public static int getHeadimgX(int wind){
		int x = 0;
		switch (wind) {
		case 1:
			x = (JUI.frameWidth-JUI.buttonWidth*13)/2-JUI.headimgWidth;
			break;
		case 2:
			x = JUI.frameWidth-JUI.headimgWidth-5;
			break;
		case 3:
			x = JUI.frameWidth - (JUI.frameWidth-JUI.buttonWidth*13)/2;
			break;
		case 4:
			break;
		}
		return x;
	}
	
	//获取头像的Y坐标
	public static int getHeadimgY(int wind){
		int y = 0;
		switch (wind) {
		case 1:
			y = JUI.frameHeight - JUI.headimgHeight - JUI.titleHeight;
			break;
		case 2:
			y = JUI.frameHeight-(JUI.frameHeight-JUI.buttinHeight*13)/2;
			break;
		case 3:
			
			break;
		case 4:
			y = (JUI.frameWidth-JUI.buttonWidth*13)/2-JUI.headimgHeight;
			break;
		}
		return y;
	}

}
