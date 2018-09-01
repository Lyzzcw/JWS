package com.zc.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Draw {
	
	static int height = 200;
	static int width = 200;
	public static String imageSuffix = "jpg";

	public static void draw(String userId,String openId,String url,boolean fangzhu,boolean zhuang,String playStatus,boolean inLine,String score) {
		try {
			BufferedImage image = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setBackground(Color.WHITE);     
	        g.clearRect(0, 0, width, height);   
	        
	        if (fangzhu) {
	        	Font t = new Font("楷体", Font.BOLD,40);
		        Color c = Color.orange;
		        g.setColor(c);
		        g.setFont(t);
		        g.drawString("房主", 0, 40);
			}
	        if (zhuang) {
	        	Font t = new Font("楷体", Font.BOLD,40);
		        Color c = Color.orange;
		        g.setColor(c);
		        g.setFont(t);
		        g.drawString("庄", width-40, 40);
			}
	        if (playStatus!=null) {
				int ps = Integer.parseInt(playStatus);
				String text = "";
				switch (ps) {
				case Cnst.PLAYER_STATE_IN:
					text = "房间中";
					break;
				case Cnst.PLAYER_STATE_GAME:
					text = "游戏中";
					break;
				case Cnst.PLAYER_STATE_PREPARED:
					text = "已准备";
					break;
				default:
					break;
				}
	        	Font t = new Font("楷体", Font.BOLD,40);
		        Color c = Color.orange;
		        g.setColor(c);
		        g.setFont(t);
		        g.drawString(text, 0, height-8);
			}
	        
	        int fountSize = 48;
	        Font f = new Font("宋体",Font.BOLD,fountSize);  
	        Color mycolor = Color.green;
	        if (!inLine) {
				mycolor = Color.red;
			}
	        g.setColor(mycolor);
	        g.setFont(f);
	        g.drawString(openId, 5, 70);
	        g.drawString(userId, 5, fountSize+70);

	        if (score!=null) {
		        fountSize = 35;
		        f = new Font("宋体",Font.BOLD,fountSize); 
		        g.setFont(f); 
		        g.drawString(score, 5, fountSize+70+43);
			}
	        
	        g.dispose();
	        
			ImageIO.write(image, imageSuffix, new File(url)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void drawHunPai(String paiName,String url){
		try {
			BufferedImage image = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setBackground(Color.WHITE);     
	        g.clearRect(0, 0, width, height);     
	        
	        int fountSize = 60;
	        Font f = new Font("楷体",Font.PLAIN,fountSize);  
	        Color mycolor = Color.red;
	        g.setColor(mycolor);
	        g.setFont(f);
	        g.drawString("混", 0, fountSize);

	        fountSize = 90;
	        f = new Font("楷体",Font.BOLD,fountSize);  
	        g.setColor(Color.black);
	        g.setFont(f);
	        g.drawString(paiName, (width-paiName.length()*fountSize)/2+fountSize/4, 150);
	        
	        g.dispose();
			ImageIO.write(image, "jpg", new File(url)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void drawActionImg(String action,String position,String url,String nums){
		try {
			BufferedImage image = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setBackground(Color.WHITE);     
	        g.clearRect(0, 0, width, height);     
	        
	        int fountSize = 60;
	        Font f = new Font("楷体",Font.PLAIN,fountSize);  
	        Color mycolor = Color.red;
	        g.setColor(mycolor);
	        g.setFont(f);
	        g.drawString(position, 0, fountSize);

	        fountSize = 90;
	        f = new Font("楷体",Font.BOLD,fountSize);  
	        g.setColor(Color.black);
	        g.setFont(f);
	        g.drawString(action, (width-action.length()*fountSize)/2, 150);
	        
	        if (nums!=null) {//吃或者杠的时候，处理多个结果用的
	        	fountSize = 50;
		        f = new Font("楷体",Font.BOLD,fountSize);  
		        g.setColor(Color.black);
		        g.setFont(f);
		        g.drawString(nums, 5,height-5);
			}
	        
	        g.dispose();
			ImageIO.write(image, "jpg", new File(url)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void draw(String userId,String openId,String url) {
		try {
			BufferedImage image = new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setBackground(Color.WHITE);     
	        g.clearRect(0, 0, width, height);     
	        
	        int fountSize = 48;
	        Font f = new Font("宋体",Font.BOLD,fountSize);  
	        Color mycolor = Color.red;
	        g.setColor(mycolor);
	        g.setFont(f);
	        g.drawString(openId, 5, 70);

	        f = new Font("宋体",Font.BOLD,fountSize);  
	        
	        g.drawString(userId, 5, fountSize+100);
	        g.dispose();
	        
			ImageIO.write(image, "jpg", new File(url)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
