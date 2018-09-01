package com.zc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.zc.domian.Player;
import com.zc.jws.ClientManager;
import com.zc.listener.MyMouseListener;
import com.zc.util.Cnst;
import com.zc.util.Draw;

/**
 * 大厅视图
 * @Title:  HallPanal     
 * @Description:    TODO  
 * @author: zc    
 * @date:   2018年4月9日 下午5:58:16   
 * @version V1.0 
 * @Copyright: 2018 云智通 All rights reserved.
 */
public class HallPanal {
	
	static int headimgWidth = 100;
	static int headimgHeight = 100;
	
	static int headimgX = 20;
	static int headimgY = 20;
	
	static int idOidMoneyX = headimgX+headimgWidth+20;
	static int idOidMoneyY = headimgY;
	static int lableWidth = 300;
	static int lableHeight = 25;
	
	
	
	public static void addHeadImg(String userId,String openId){
		JButton b = new JButton();
        b.setFocusPainted(false);
        b.setBounds(headimgX, headimgY, headimgWidth, headimgHeight);
        b.setBorder(BorderFactory.createEtchedBorder());
        URL uri = JUI.class.getResource("/icon");
        String fileName = userId+"."+Draw.imageSuffix;
        String url = uri.getPath().substring(1)+"/"+fileName;
        Draw.draw(userId, openId, url,false,false,null,true,null);
        uri = JUI.class.getResource("/icon/"+fileName);
		ImageIcon icon = new ImageIcon(uri);
		
		Image temp = icon.getImage().getScaledInstance(b.getWidth(),b.getHeight(), icon.getImage().SCALE_AREA_AVERAGING);  
        icon = new ImageIcon(temp);
        b.setIconTextGap(-10);
        b.setIcon(icon);

		JUI.jp.add(b);
		JUI.jp.repaint();
	}

	static Font mf = new Font("楷体", Font.PLAIN, 15);
	public static void addOpenIdAndUserIdAndMoney(String userId,String userName,String money,String notice){
		JLabel l = new JLabel("昵称："+userName);
		l.setBounds(idOidMoneyX,idOidMoneyY, lableWidth, lableHeight);
		l.setFont(mf);
//		l.setForeground(Color.red);
//		l.setOpaque(true);
//		l.setBackground(Color.red);
		JUI.jp.add(l);
		
		l = new JLabel("ID："+userId);
		l.setBounds(idOidMoneyX,idOidMoneyY+40, lableWidth, lableHeight);
		l.setFont(mf);
		JUI.jp.add(l);
		
		l = new JLabel("房卡："+money);
		l.setBounds(idOidMoneyX,idOidMoneyY+80, lableWidth, lableHeight);
		l.setFont(mf);
//		l.setOpaque(true);
//		l.setBackground(Color.red);
		JUI.jp.add(l);
		
		l = new JLabel(notice);
		l.setBounds(30,idOidMoneyY+120, lableWidth+300, lableHeight);
		l.setFont(mf);
		
		JUI.jp.add(l);
		JUI.jp.repaint();
	}
	

	public static Font buttonFont = new Font("楷体", Font.PLAIN, 40);
	static Font redioFont = new Font("楷体", Font.PLAIN, 20);
	static int jiange = 30;
	static int bwidth = 200;
	static int bheight = 200;
	static int buttonX = 180;
	//创建房间、加入房间、俱乐部、战绩、代开、代开历史
	public static void addButton(String  userId,String openId){
		JButton b = new JButton("创建房间");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((JUI.jp.getWidth()-(bwidth+jiange)*3)/2, buttonX, bwidth, bheight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_创建房间.concat(openId).concat(Cnst.split).concat(userId));
        JUI.jp.add(b);
        
        b = new JButton("加入房间");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((JUI.jp.getWidth()-(bwidth+jiange)*3)/2+jiange+bwidth, buttonX, bwidth, bheight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_加入房间.concat(openId).concat(Cnst.split).concat(userId));
        JUI.jp.add(b);
        
        b = new JButton("俱乐部");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((JUI.jp.getWidth()-(bwidth+jiange)*3)/2+jiange+bwidth+jiange+bwidth, buttonX, bwidth, bheight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_俱乐部.concat(openId).concat(Cnst.split).concat(userId));
        JUI.jp.add(b);
        
        b = new JButton("战绩");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((JUI.jp.getWidth()-(bwidth+jiange)*3)/2, buttonX+bheight+jiange, bwidth, bheight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_战绩.concat(openId).concat(Cnst.split).concat(userId));
        JUI.jp.add(b);
        
        b = new JButton("代开列表");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((JUI.jp.getWidth()-(bwidth+jiange)*3)/2+jiange+bwidth, buttonX+bheight+jiange, bwidth, bheight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_代开列表.concat(openId).concat(Cnst.split).concat(userId));
        JUI.jp.add(b);
        
        b = new JButton("代开历史");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((JUI.jp.getWidth()-(bwidth+jiange)*3)/2+jiange+bwidth+jiange+bwidth, buttonX+bheight+jiange, bwidth, bheight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_代开历史.concat(openId).concat(Cnst.split).concat(userId));
        JUI.jp.add(b);
        
		JUI.jp.repaint();
	}
	
	public static void addPlayerInfo(Player p){
		if (p!=null) {
			JUI.jp.removeAll();
			JUI.jp.repaint();
			addHeadImg(p.getSth("userId").toString(), p.getSth("openId").toString());
			addOpenIdAndUserIdAndMoney(p.getSth("userId").toString(), p.getSth("userName").toString(),p.getSth("money").toString(),p.getSth("notice").toString());
			addButton(p.getSth("userId").toString(), p.getSth("openId").toString());
		}
	}

	
	public static void createRoom(String userId,String openId){
		dialog = new JDialog(JUI.jf,true);
		dialog.setTitle("创建房间");
		dialog.setLayout(null);
        dialog.setSize(frameWidth, frameHeight);//设置窗口大小
        
        int i=0;
        for(String key:Cnst.createRoomParamsType.keySet()){
        	JLabel l = new JLabel(Cnst.createRoomParamsStr.get(key)+"：");
            l.setFont(redioFont);
    		l.setBounds(redioSX,redioSY+redisJiangeY*i,redioWidth,redisHeight);
    		
    		l.setOpaque(false);
    		l.setBackground(Color.red);
    		
    		dialog.getContentPane().add(l);
    		ButtonGroup bg = new ButtonGroup();
    		List<String> values = Cnst.createRoomParamsSChooseValues.get(key);
    		
    		int y=0;
    		for(String va:values){
                y++;
    			JRadioButton r1 = new JRadioButton();
    			String text = "";
    			if (Cnst.createRoomParamsType.get(key)==Cnst.paramsDataType_是否) {
            		text = va.equals(Cnst.params_是)?"是":"否";
    			}else if(Cnst.createRoomParamsType.get(key)==Cnst.paramsDataType_直接取值){
            		text = va;
    			}else if(Cnst.createRoomParamsType.get(key)==Cnst.paramsDataType_有中文含义){
            		text = Cnst.userDefinedStr.get(key).get(va);
    			}
    			r1.setText(text);
                r1.setBounds(redioSX+(redioWidth+redioJiange)*y, redioSY+redisJiangeY*i, redioWidth, redisHeight);//如果设置父级的layout为null，每个组件必须这只这四个参数才能显示
                r1.setFont(redioFont);
                bg.add(r1);
                r1.setName(key.concat(Cnst.split).concat(va));
                boolean defaultable = y==1;
                r1.setSelected(defaultable); //设置默认
                dialog.getContentPane().add(r1);
    		}
    		i++;
        }
        
        
        JButton b = new JButton("确定");
        b.setFont(buttonFont);
        b.setBounds((frameWidth-sureButtonWidth)/2, redioSY+redisJiangeY*++i, sureButtonWidth, sureButtonHeight);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        b.setName(Cnst.buttonId_确定.concat(openId).concat(Cnst.split).concat(userId));
        dialog.getContentPane().add(b);
        
        
        dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.validate();
        dialog.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        dialog.setVisible(true);
		dialog.repaint();
	}

	

	static JDialog dialog = new JDialog(JUI.jf,true);
	static int frameHeight = 500;
	static int frameWidth = 600;
	static int redioWidth = 130;
	static int redisHeight = 50;
//	static int redioSX = 160;
	static int redioSX = 20;
	
	static int redioSY = 20;
	static int redioJiange = 20;
	static int redisJiangeY = redioFont.getSize()*2;
	static int sureButtonWidth = 200;
	static int sureButtonHeight = 100;
	
	//创建房间的参数获取
	public static void getDialogParam(String userId,String openId){
		Map<String,String> send = Cnst.getSendMap(100007);
		Component[] all = dialog.getContentPane().getComponents();
		if (all!=null&&all.length>0) {
			for(Component r:all){
				if (r instanceof JRadioButton&&((JRadioButton) r).isSelected()) {
					String[] value = r.getName().split(Cnst.split);
					send.put(value[0], value[1]);
				}
			}
		}
		send.put("userId", userId);
		System.out.println(send);
		dialog.removeAll();
		dialog.repaint();
		dialog.dispose();
		ClientManager.clientManager.get(openId).send(send,openId);
	}



	static int pleaseButtonWidth = 50;
	static int pleaseButtonHeight = 50;
	static int pleaseButtonJG = 10;
	static int pleaseButtonY = 20; 
	static String pleaseStr = "请输入房间号";
	static int numWidth = 100;
	static int nunHeight = 100;
	public static void addPleaseButton(int num){
		JButton b = new JButton(pleaseStr.charAt(num-1)+"");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(redioFont);
        b.setEnabled(false);
        b.setBackground(Color.lightGray);
        b.setBounds((frameWidth-(pleaseButtonWidth+pleaseButtonJG)*6)/2+(num-1)*(pleaseButtonJG+pleaseButtonWidth), pleaseButtonY, pleaseButtonWidth, pleaseButtonHeight);
        b.setName(Cnst.roomSnButtonPrefix+num);
        dialog.add(b);
	}
	
	
	static String num1 = "12345";
	static String num2 = "67890";
	public static void addNumButton(int num){
		JButton b = new JButton(num+"");
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        if (num>=1&&num<=5) {
        	b.setBounds((frameWidth-(numWidth+pleaseButtonJG)*5)/2+num1.indexOf(num+"")*(pleaseButtonJG+numWidth), pleaseButtonY+pleaseButtonJG*4+pleaseButtonHeight, numWidth, nunHeight);
		}else{
        	b.setBounds((frameWidth-(numWidth+pleaseButtonJG)*5)/2+num2.indexOf(num+"")*(pleaseButtonJG+numWidth), pleaseButtonY+pleaseButtonJG*6+pleaseButtonHeight+nunHeight, numWidth, nunHeight);
    	}
        b.setName(Cnst.numButtonPrefix+num);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        dialog.add(b);
	}
	static String[] operateStr = {"重输","退格","加入"};
	static int opeWidth = 150;
	static int opeHeight = 100;
	static int optJiange = 30;
	public static void addOperateButton(int num){
		JButton b = new JButton(operateStr[num-1]);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setFont(buttonFont);
        b.setBounds((frameWidth-(opeWidth*3+optJiange*2))/2+(num-1)*(opeWidth+optJiange), frameHeight-opeHeight-30-JUI.titleHeight, opeWidth, opeHeight);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        if (num==1) {
            b.setName(Cnst.opt_重输);
		}else if(num==2){
            b.setName(Cnst.opt_退格);
		}else if(num==3){
            b.setName(Cnst.opt_确定加入);
		}
        dialog.add(b);
	}

	
	public static void jiaRuRoom(){
		dialog = new JDialog(JUI.jf,true);
		dialog.setTitle("加入房间");
		dialog.setLayout(null);
        dialog.setSize(frameWidth, frameHeight);                       // 设置窗口大小
        
        for(int i=1;i<=6;i++){
            addPleaseButton(i);
        }
        
        for(int i=0;i<=9;i++){
        	addNumButton(i);
        }
        
        for(int i=1;i<=3;i++){
        	addOperateButton(i);
        }
        
        dialog.setResizable(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.validate();
        dialog.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        dialog.setVisible(true);
		dialog.repaint();
		JUI.jf.repaint();
	}

	

	
	static int allNum = 0;
	public static void cacuRoonSn(JButton jb){
		if (allNum<6) {
			Component[] all = dialog.getContentPane().getComponents();
			if (all!=null&&all.length>0) {
				for(Component b:all){
					if (b instanceof JButton) {
						if (b.getName()!=null&&b.getName().equals(Cnst.roomSnButtonPrefix+(allNum+1))) {
							allNum++;
							((JButton) b).setText(jb.getText());
							b.setBackground(Color.white);
							break;
						}
					}
				}
				dialog.repaint();
			}
		}
	}
	
	public static void tuiGe(){
		if (allNum>6) {
			allNum=6;
		}
		if (allNum>0) {
			allNum--;
		}
		Component[] all = dialog.getContentPane().getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().equals(Cnst.roomSnButtonPrefix+(allNum+1))) {
						((JButton) b).setText(pleaseStr.charAt(allNum)+"");
						b.setBackground(Color.lightGray);
						break;
					}
				}
			}
			dialog.repaint();
		}
	}
	public static void chongShu(){
		allNum = 0;
		Component[] all = dialog.getContentPane().getComponents();
		for(int i=0;i<pleaseStr.length();i++){
			if (all!=null&&all.length>0) {
				for(Component b:all){
					if (b instanceof JButton) {
						if (b.getName()!=null&&b.getName().equals(Cnst.roomSnButtonPrefix+(i+1))) {
							((JButton) b).setText(pleaseStr.charAt(i)+"");
							b.setBackground(Color.lightGray);
							break;
						}
					}
				}
				dialog.repaint();
			}
		}
		
	}
	public static void jiaRu(){
		if (allNum<6) {
			return;
		}
		if (allNum>6) {
			allNum=6;
		}
		String roomSn = "";
		Component[] all = dialog.getContentPane().getComponents();
		for(int i=0;i<allNum;i++){
			if (all!=null&&all.length>0) {
				boolean add = false;
				for(Component b:all){
					if (b instanceof JButton) {
						if (b.getName()!=null&&b.getName().equals(Cnst.roomSnButtonPrefix+(i+1))) {
							roomSn = roomSn+((JButton)b).getText();
							add = true;
							break;
						}
					}
				}
			}
		}
		System.out.println(roomSn);
		
	}

	
	
	
}
