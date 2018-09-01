package com.zc.listener;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.zc.domian.Player;
import com.zc.jws.Client;
import com.zc.jws.ClientManager;
import com.zc.ui.ActionPanal;
import com.zc.ui.GamePanal;
import com.zc.ui.HallPanal;
import com.zc.ui.JUI;
import com.zc.util.Cnst;

public class MyMouseListener {
	
	public static ButtomMouseListener getButtomMouseListener(JButton b){
		return new MyMouseListener().new ButtomMouseListener(b);
	}
	class ButtomMouseListener implements MouseListener{
		private JButton b;
		public ButtomMouseListener(JButton b) {
			this.b = b;
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3){//单击右键
				if (b.getName().contains(Cnst.buttonId_牌)) {
					if (b.getBackground().equals(GamePanal.chuPaiColor)) {
						//出牌发送消息
						String openId = b.getName().split(Cnst.split)[0].replace(Cnst.buttonId_牌, "");
						String paiStr = b.getName().split(Cnst.split)[1];
						Map<String,String> send = Cnst.getSendMap(100201);
						Client client = ClientManager.clientManager.get(openId);
						client.setCurrentChuPaiButton(b);
						client.setChuPaiMouseListener(this);
						send.put("userId", client.getUserId());
						send.put("roomId", ClientManager.room.getSth("roomId").toString());
						send.put("action", Cnst.getPaiRealNum(paiStr));
						client.send(send, openId);
					}
					
				}
			}else if(e.getButton() == MouseEvent.BUTTON2){//单击滚轮
//				JOptionPane.showMessageDialog(jf, "BUTTON2");
			}else if(e.getButton() == MouseEvent.BUTTON1){//单击左键
//				JOptionPane.showMessageDialog(jf, "BUTTON1");
				if (!b.getName().contains(Cnst.buttonId_牌)) {
					if (b.getName().contains(Cnst.buttonId_添加人员)) {
						
						if(!ClientManager.clientManager.containsKey(b.getName().replace(Cnst.buttonId_添加人员,""))){//如果在线玩家包含了当前玩家就不用处理
							if (ClientManager.room==null) {//没有房间，人员正常登录
								addPlayer(b);
							}else if(ClientManager.clientManager.size()<4){//有房间，但是人未满
								addPlayer(b);
							}else{//房间已经四个人了
								JOptionPane.showMessageDialog(JUI.jf,"当前有房间存在，请新开程序！","提示",JOptionPane.PLAIN_MESSAGE);
							}
						}else{//让玩家掉线操作
							if (ClientManager.room!=null) {
								Client c = ClientManager.clientManager.get(b.getName().replace(Cnst.buttonId_添加人员,""));
								if (c.isConnect()) {
									c.disConnect();
								}
							}else{
								JUI.jp.removeAll();
								JUI.jp.repaint();
								b.setBackground(null);
								Client c = ClientManager.clientManager.get(b.getName().replace(Cnst.buttonId_添加人员,""));
								c.disConnect();
								changeButton(b.getName().replace(Cnst.buttonId_添加人员,""), null);
								JUI.jf.repaint();
							}
						}
					}else if (b.getName().contains(Cnst.buttonId_创建房间)) {
						String str = b.getName().replace(Cnst.buttonId_创建房间, "");
						String[] t = str.split(Cnst.split);//openId和userId
						HallPanal.createRoom(t[1],t[0]);
					}else if (b.getName().contains(Cnst.buttonId_确定)) {//创建房间确定
						String str = b.getName().replace(Cnst.buttonId_确定, "");
						String[] t = str.split(Cnst.split);//openId和userId
						HallPanal.getDialogParam(t[1],t[0]);
					}else if (b.getName().contains(Cnst.buttonId_加入房间)) {//加入房间
						HallPanal.jiaRuRoom();
					}else if (b.getName().contains(Cnst.numButtonPrefix)) {//点击数字
						HallPanal.cacuRoonSn(b);
					}else if (b.getName().contains(Cnst.opt_退格)) {//退格
						HallPanal.tuiGe();
					}else if (b.getName().contains(Cnst.opt_重输)) {//重输
						HallPanal.chongShu();
					}else if (b.getName().contains(Cnst.opt_确定加入)) {//加入
						HallPanal.jiaRu();
					}else if (b.getName().contains(Cnst.opt_清理日志)) {//清理日志
						JUI.cleanLog();
					}else if (b.getName().contains(Cnst.buttonId_房间规则)) {//点击房间规则
//						JOptionPane.showMessageDialog(JUI.jf, "查看房间规则","提示",JOptionPane.PLAIN_MESSAGE);
						GamePanal.showRules1();
					}else if (b.getName().contains(Cnst.buttonId_开局)) {//点击开局按钮
//						JOptionPane.showMessageDialog(JUI.jf, "房主开局","提示",JOptionPane.PLAIN_MESSAGE);
						String openId = b.getName().replace(Cnst.buttonId_开局,"");
						String userId = null;
						if (ClientManager.clientManager.containsKey(openId)) {
							userId = ClientManager.clientManager.get(openId).getUserId();
						}
						if (userId!=null) {
							GamePanal.zhunBei(userId,openId,ClientManager.room.getSth("roomId").toString());
						}else{
							JOptionPane.showMessageDialog(JUI.jf, "没有获取到userId，玩家可能不在线","提示",JOptionPane.PLAIN_MESSAGE);
						}
					}else if (b.getName().contains(Cnst.buttonId_俱乐部)) {
//						ActionPanal.createInfoData();//造加数据
//						ActionPanal.addXiaoJieSuanDialog(ActionPanal.info);
						JOptionPane.showMessageDialog(JUI.jf, "这个功能是假的！","提示",JOptionPane.PLAIN_MESSAGE);
					}else if (b.getName().contains(Cnst.buttonId_行为)) {//玩家行为
						ActionPanal.playerAction(b);
					}else if (b.getName().contains(Cnst.buttonId_下一局)) {//下一局按钮
//						JOptionPane.showMessageDialog(JUI.jf, b.getName(),"提示",JOptionPane.PLAIN_MESSAGE);
						ActionPanal.nextJu();
					}else if (b.getName().contains(Cnst.buttonId_发牌)) {//请求发牌
						String openId = b.getName().split(Cnst.split)[0].replace(Cnst.buttonId_发牌, "");
						ActionPanal.sendFaPaiMessage(ClientManager.clientManager.get(openId).getPlayer());
					}else if (b.getName().contains(Cnst.buttonId_出的牌)) {//点击玩家出的牌
//						JOptionPane.showMessageDialog(JUI.jf, b.getName(),"提示",JOptionPane.PLAIN_MESSAGE);
					}else if(b.getName().contains(Cnst.buttonId_解散)){
						String openId = b.getName().split(Cnst.split)[0].replace(Cnst.buttonId_解散, "");
						Map<String,String> send = Cnst.getSendMap(10086);
						send.put("roomId", ClientManager.room.getSth("roomId")+"");
						ClientManager.clientManager.get(openId).send(send, openId);
					}else{
						JOptionPane.showMessageDialog(JUI.jf, b.getText(),"提示",JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
	}
	
	public static void addPlayer(JButton b){
		Client client = new Client();
		client.connect(b.getName().replace(Cnst.buttonId_添加人员,""), client);
		while(!client.isConnect()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String,String> map = Cnst.getSendMap(100100);
		map.put("openId", b.getName().replace(Cnst.buttonId_添加人员,""));
		client.send(map,map.get("openId"));
	}
	
	//改变openId按钮的状态用的
	public static void changeButton(Color color){
		Component[] all = JUI.jf.getContentPane().getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&ClientManager.clientManager.containsKey(b.getName().replace(Cnst.buttonId_添加人员,""))) {
						b.setBackground(color);
					}else{
						b.setBackground(null);
					}
				}
			}
			JUI.jf.repaint();
		}
	}
	public static void changeButton(String openId,Color color){
		Component[] all = JUI.jf.getContentPane().getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&openId.equals(b.getName().replace(Cnst.buttonId_添加人员,""))) {
						b.setBackground(color);
					}
				}
			}
			JUI.jf.repaint();
		}
	}
}
