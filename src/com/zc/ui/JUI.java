package com.zc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.zc.listener.MyMouseListener;
import com.zc.util.Cnst;

public class JUI {

	public static ExecutorService executeThread = Executors.newSingleThreadExecutor();
	
	public static void main(String[] args) {
		// 显示应用 GUI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				t0();
				t3();
				t4();
			}
		});
	}
	
	public static void cleanPanal(){
		jf.getContentPane().removeAll();
		jf.repaint();
	}
	static int operateHeight = 100;
	static int fixLineWidth = 2;
	static int logWidth = 600;
	static int frameWidth = 800;
	static int frameHeight = 800;
	static int buttonWidth = 40;
	static int buttinHeight = 40; 
	static int hunButtonHeight = 15;
	static int hunButtonWidth = 15;
	static int titleHeight = 28;
	static int headimgWidth = 80;
	static int headimgHeight = 80;
	static int huaWidth = 30;
	static int huaHeight = 30;
	
//	public static Color defaultButtonColor = new Color(238,238,238);
	public static JButton defaultButton = new JButton();
	
	
	static Font mf = new Font("楷体", Font.PLAIN, 15);
	static Font hunFont = new Font("楷体", Font.PLAIN, 10);

	static Font openIdFont = new Font("楷体", Font.PLAIN, 20);

	static URL hunImgUrl = JUI.class.getResource("/icon/hun.jpg");
//	static ImageIcon hunIcon = new ImageIcon(hunImgUrl);
	
	static int openIdButtonWidth = 80;
	static int openIdButtonhtight = 80;
	
	//操作区人的button
	public static JButton getOperateUserButton(String text,int index){
		//jbutton
        JButton b = new JButton(text);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setBounds((index-1)*openIdButtonWidth, jf.getHeight()-titleHeight-openIdButtonhtight, openIdButtonWidth, openIdButtonhtight);
        b.setFocusPainted(false);
        b.setFont(openIdFont);
        b.setToolTipText("点击登录当前人");
        b.setName(Cnst.buttonId_添加人员+b.getText());
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
        return b;
	}
	//添加日志分割线
	public static JButton getFixLineButton(){
        JButton b = new JButton();
        b.setBounds(frameWidth, 0, fixLineWidth, frameHeight+operateHeight);
        b.setFocusable(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setBackground(Color.red);
        return b;
        
	}
	//添加操作区分割线
	public static JButton getOperateAreaButton(){
        JButton b = new JButton();
        b.setBounds(0, frameHeight-titleHeight, frameWidth, fixLineWidth);
        b.setFocusable(false);
        b.setFocusPainted(false);
        b.setBackground(Color.red);
        b.setBorder(BorderFactory.createEtchedBorder());
        return b;
        
	}
	
	public static JPanel jp = null;
	public static JPanel getPaiZhunPanal(){
		jp = new JPanel();
		jp.setLayout(null);
		jp.setBounds(0, 0, frameWidth, frameHeight-titleHeight);
		jp.setBackground(Color.LIGHT_GRAY);
		return jp;
	}
	
	

	public static JFrame jf = new JFrame("zc-右键出牌！！！");
	//创建frame
	public static void t0(){
		jf.setLayout(null);
        jf.setSize(frameWidth+logWidth+fixLineWidth, frameHeight+operateHeight);                       // 设置窗口大小
        jf.setResizable(false);
        jf.getContentPane().add(getPaiZhunPanal());//创建牌桌的panal
        
		 //添加日志分割线
        jf.getContentPane().add(getFixLineButton());
        //添加操作区分割线
        jf.getContentPane().add(getOperateAreaButton());
        jf.validate();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setVisible(true);
	}
	

	//操作区
	public static void t3(){
		for(int i=0;i<Cnst.openIds.length;i++){
			jf.getContentPane().add(getOperateUserButton(Cnst.openIds[i], i+1));	
		}
		jf.repaint();
	}
	public static JTextArea log = new JTextArea();
	static JScrollPane  rizhiPanal = new JScrollPane(log);
	static JPanel rp = new JPanel();
	
	
	public static JButton getCleanLogButton(){
		JButton b = new JButton("清理日志");
        b.setBorder(BorderFactory.createEtchedBorder());
        b.setBounds(frameWidth+fixLineWidth+10,frameWidth+fixLineWidth+10,logWidth-25,50);
        b.setFocusPainted(false);
        b.setFont(HallPanal.buttonFont);
        b.setToolTipText("点击清理当前日志");
        b.setName(Cnst.opt_清理日志);
        b.addMouseListener(MyMouseListener.getButtomMouseListener(b));
		return b;
	}
	
	//日志区
	public static void t4(){
		rp.setLayout(null);
		rp.setBounds(frameWidth+fixLineWidth, 0, logWidth-5, frameHeight+operateHeight-titleHeight);
		rp.setBackground(new Color(216, 228, 241));
		rp.setBorder(BorderFactory.createTitledBorder("日志区"));
		
		rizhiPanal.setBounds(10, 20, logWidth-25, frameHeight+operateHeight-titleHeight*4);
		//自动出现滚动条
		rizhiPanal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		rizhiPanal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
//		log.setFocusable(false);
		log.setLineWrap (true);
		log.setMargin(new Insets(2, 5, 0, 5));
		log.setBounds(10, 20, logWidth-25, frameHeight+operateHeight-titleHeight*4);
		
		rizhiPanal.setViewportView(log);
		
		rp.add(rizhiPanal);
		jf.add(getCleanLogButton());
		jf.getContentPane().add(rp);
		jf.repaint();
	}
	
	public static int checkPanal(){
		int panal = 2;
		Component[] all = JUI.jp.getComponents();
		if (all!=null&&all.length>0) {
			for(Component b:all){
				if (b instanceof JButton) {
					if (b.getName()!=null&&b.getName().contains(Cnst.buttonId_创建房间)) {
						panal = 1;
						break;
					}
				}
			}
		}
		return panal;
	}
	
	public synchronized static void appendLog(String logStr){
		log.append(logStr);
		log.append("\r\n---------------------------------------------------------------------------------------------------------------------------------\r\n");
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public static void cleanLog(){
		log.setText("");
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	
	
	
	
	
}