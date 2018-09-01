package com.zc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

public class Cnst {
	
	public static int minaPort = Integer.parseInt(ProjectInfoPropertyUtil.getProperty("mina.port"));
	public static int cid = Integer.parseInt(ProjectInfoPropertyUtil.getProperty("cid"));
	public static String minaHost = ProjectInfoPropertyUtil.getProperty("mina.host");
	public static String[] openIds = null;;
	static{
		String oids = ProjectInfoPropertyUtil.getProperty("openIds");
		openIds = oids.split(",");
	}
	
	public static Map<String,String> numToPaiMap = new ConcurrentHashMap<String, String>();
	static{
		for(int i=1;i<=9;i++){
			numToPaiMap.put(i+"", i+"万");
		}
		for(int i=10;i<=18;i++){
			numToPaiMap.put(i+"", (i-9)+"饼");
		}
		for(int i=19;i<=27;i++){
			numToPaiMap.put(i+"", (i-18)+"条");
		}
		numToPaiMap.put("28", "东");
		numToPaiMap.put("29", "南");
		numToPaiMap.put("30", "西");
		numToPaiMap.put("31", "北");
		numToPaiMap.put("32", "中");
		numToPaiMap.put("33", "发");
		numToPaiMap.put("34", "白");
		numToPaiMap.put("127", "春");
		numToPaiMap.put("128", "夏");
		numToPaiMap.put("129", "秋");
		numToPaiMap.put("130", "冬");
		numToPaiMap.put("131", "梅");
		numToPaiMap.put("132", "兰");
		numToPaiMap.put("133", "竹");
		numToPaiMap.put("134", "菊");
	}
	
	//返回完整的行为的牌
	public static String getTypeFromAction(Integer action,String extra){
		String str = "";
		if (action>34&&action<=56) {//吃
			int[] nums = bianmaMap.get(action);
			for(int i=0;i<nums.length;i++){
				str = str+nums[i]+" ";
			}
		}else if (action>56&&action<=90) {//碰
			str = (action-56)+"";
			str = str+" "+str+" "+str+" ";
		}else if (action>90&&action<=126) {//杠
			str = (action-90)+"";
			str = str+" "+str+" "+str+" "+str+" ";
		}else if(action==-2){//暗杠
			str = (Integer.parseInt(extra)-90)+"";
			str = str+" "+str+" "+str+" "+str+" ";
		}
		return str;
	}
	
	
	
	
	public static Map<String,String> ROUTE_MAP = new ConcurrentHashMap<String, String>();
    static{
    	ROUTE_MAP.put("a","interfaceId");
    	ROUTE_MAP.put("b","state");
    	ROUTE_MAP.put("c","message");
    	ROUTE_MAP.put("d","info");
    	ROUTE_MAP.put("e","others");
    	ROUTE_MAP.put("f","page");
    	ROUTE_MAP.put("g","infos");
    	ROUTE_MAP.put("h","pages");
    	ROUTE_MAP.put("i","connectionInfo");
    	ROUTE_MAP.put("j","help");
    	ROUTE_MAP.put("k","userId");
    	ROUTE_MAP.put("l","content");
    	ROUTE_MAP.put("m","tel");
    	ROUTE_MAP.put("n","roomType");
    	ROUTE_MAP.put("o","type");
    	ROUTE_MAP.put("p","clubId");
    	ROUTE_MAP.put("q","clubName");
    	ROUTE_MAP.put("r","clubUserName");
    	ROUTE_MAP.put("s","allNums");
    	ROUTE_MAP.put("t","maxNums");
    	ROUTE_MAP.put("u","freeStart");
    	ROUTE_MAP.put("v","freeEnd");
    	ROUTE_MAP.put("w","clubMoney");
    	ROUTE_MAP.put("x","cardQuota");
    	ROUTE_MAP.put("y","juNum");
    	ROUTE_MAP.put("z","used");
    	ROUTE_MAP.put("A","roomId");
    	ROUTE_MAP.put("B","reqState");
    	ROUTE_MAP.put("C","playerNum");
    	ROUTE_MAP.put("D","money");
    	ROUTE_MAP.put("E","playStatus");
    	ROUTE_MAP.put("F","position");
    	ROUTE_MAP.put("G","userInfo");
    	ROUTE_MAP.put("H","wsw_sole_main_id");
    	ROUTE_MAP.put("I","wsw_sole_action_id");
    	ROUTE_MAP.put("J","roomInfo");
    	ROUTE_MAP.put("K","lastNum");
    	ROUTE_MAP.put("L","totalNum");
    	ROUTE_MAP.put("M","roomIp");
    	ROUTE_MAP.put("N","ip");
    	ROUTE_MAP.put("O","xjst");
    	ROUTE_MAP.put("P","score");
    	ROUTE_MAP.put("Q","userName");
    	ROUTE_MAP.put("R","userImg");
    	ROUTE_MAP.put("S","joinIndex");
    	ROUTE_MAP.put("T","gender");
    	ROUTE_MAP.put("U","createTime");
    	ROUTE_MAP.put("V","circleNum");
    	ROUTE_MAP.put("W","playerInfo");
    	ROUTE_MAP.put("X","openId");
    	ROUTE_MAP.put("Y","cId");
    	ROUTE_MAP.put("Z","currentUser");
    	ROUTE_MAP.put("aa","anotherUsers");
    	ROUTE_MAP.put("ab","version");
    	ROUTE_MAP.put("ac","userAgree");
    	ROUTE_MAP.put("ad","notice");
    	ROUTE_MAP.put("ae","actNum");
    	ROUTE_MAP.put("af","exState");
    	ROUTE_MAP.put("ag","pais");
    	ROUTE_MAP.put("ah","xiaoJuNum");
    	ROUTE_MAP.put("ai","zhuangPlayer");
    	ROUTE_MAP.put("aj","dissolveTime");
    	ROUTE_MAP.put("ak","othersAgree");
    	ROUTE_MAP.put("al","dissolveRoom");
    	ROUTE_MAP.put("am","continue");
    	ROUTE_MAP.put("an","nextAction");
    	ROUTE_MAP.put("ao","nextActionUserId");
    	ROUTE_MAP.put("ap","agree");
    	ROUTE_MAP.put("aq","idx");
    	ROUTE_MAP.put("ar", "rule");
    	ROUTE_MAP.put("as", "finalScore");
    	ROUTE_MAP.put("at", "date");
    	ROUTE_MAP.put("au", "extra");
    	ROUTE_MAP.put("av", "extraType");
    	
    	ROUTE_MAP.put("aw", "scoreType");
    	ROUTE_MAP.put("ax", "chuList");
    	ROUTE_MAP.put("ay", "playType");
    	ROUTE_MAP.put("az", "actionList");
    	ROUTE_MAP.put("ba", "currMJNum");
    	ROUTE_MAP.put("bb", "hunPai");
    	ROUTE_MAP.put("bc", "lastAction");
    	ROUTE_MAP.put("bd", "lastActionUserId");
    	ROUTE_MAP.put("be", "currAction");
    	ROUTE_MAP.put("bf", "currActionUserId");
    	ROUTE_MAP.put("bg", "action");
    	ROUTE_MAP.put("bh", "huaList");
    	ROUTE_MAP.put("bi", "zhuangNum");
    	ROUTE_MAP.put("bj", "gangNum");
    	ROUTE_MAP.put("bk", "huScore");
    	ROUTE_MAP.put("bl", "chiType");
    	ROUTE_MAP.put("bm", "huNum");
    	ROUTE_MAP.put("bn", "dianNum");
    	ROUTE_MAP.put("bo", "isWin");
    	ROUTE_MAP.put("bp", "isDian");
    	ROUTE_MAP.put("bq", "winInfo");
    	ROUTE_MAP.put("br", "dingHunPai");
    	ROUTE_MAP.put("bs", "isCreated");
    	ROUTE_MAP.put("bt", "lastFaPai");
    	ROUTE_MAP.put("bu", "toUserId");
    	ROUTE_MAP.put("bv", "startPosition");
    	
    	ROUTE_MAP.put("ca", "x_index");
    	ROUTE_MAP.put("cb", "y_index");
    	ROUTE_MAP.put("cc", "distance");
    	//////////////////////////////////////////////////////
    	ROUTE_MAP.put("interfaceId","a");
    	ROUTE_MAP.put("state","b");
    	ROUTE_MAP.put("message","c");
    	ROUTE_MAP.put("info","d");
    	ROUTE_MAP.put("others","e");
    	ROUTE_MAP.put("page","f");
    	ROUTE_MAP.put("infos","g");
    	ROUTE_MAP.put("pages","h");
    	ROUTE_MAP.put("connectionInfo","i");
    	ROUTE_MAP.put("help","j");
    	ROUTE_MAP.put("userId","k");
    	ROUTE_MAP.put("content","l");
    	ROUTE_MAP.put("tel","m");
    	ROUTE_MAP.put("roomType","n");
    	ROUTE_MAP.put("type","o");
    	ROUTE_MAP.put("clubId","p");
    	ROUTE_MAP.put("clubName","q");
    	ROUTE_MAP.put("clubUserName","r");
    	ROUTE_MAP.put("allNums","s");
    	ROUTE_MAP.put("maxNums","t");
    	ROUTE_MAP.put("freeStart","u");
    	ROUTE_MAP.put("freeEnd","v");
    	ROUTE_MAP.put("clubMoney","w");
    	ROUTE_MAP.put("cardQuota","x");
    	ROUTE_MAP.put("juNum","y");
    	ROUTE_MAP.put("used","z");
    	ROUTE_MAP.put("roomId","A");
    	ROUTE_MAP.put("reqState","B");
    	ROUTE_MAP.put("playerNum","C");
    	ROUTE_MAP.put("money","D");
    	ROUTE_MAP.put("playStatus","E");
    	ROUTE_MAP.put("position","F");
    	ROUTE_MAP.put("userInfo","G");
    	ROUTE_MAP.put("wsw_sole_main_id","H");
    	ROUTE_MAP.put("wsw_sole_action_id","I");
    	ROUTE_MAP.put("roomInfo","J");
    	ROUTE_MAP.put("lastNum","K");
    	ROUTE_MAP.put("totalNum","L");
    	ROUTE_MAP.put("roomIp","M");
    	ROUTE_MAP.put("ip","N");
    	ROUTE_MAP.put("xjst","O");
    	ROUTE_MAP.put("score","P");
    	ROUTE_MAP.put("userName","Q");
    	ROUTE_MAP.put("userImg","R");
    	ROUTE_MAP.put("joinIndex","S");
    	ROUTE_MAP.put("gender","T");
    	ROUTE_MAP.put("createTime","U");
    	ROUTE_MAP.put("circleNum","V");
    	ROUTE_MAP.put("playerInfo","W");
    	ROUTE_MAP.put("openId","X");
    	ROUTE_MAP.put("cId","Y");
    	ROUTE_MAP.put("currentUser","Z");
    	ROUTE_MAP.put("anotherUsers","aa");
    	ROUTE_MAP.put("version","ab");
    	ROUTE_MAP.put("userAgree","ac");
    	ROUTE_MAP.put("notice","ad");
    	ROUTE_MAP.put("actNum","ae");
    	ROUTE_MAP.put("exState","af");
    	ROUTE_MAP.put("pais","ag");
    	ROUTE_MAP.put("xiaoJuNum","ah");
    	ROUTE_MAP.put("zhuangPlayer","ai");
    	ROUTE_MAP.put("dissolveTime","aj");
    	ROUTE_MAP.put("othersAgree","ak");
    	ROUTE_MAP.put("dissolveRoom","al");
    	ROUTE_MAP.put("continue","am");
    	ROUTE_MAP.put("nextAction","an");
    	ROUTE_MAP.put("nextActionUserId","ao");
    	ROUTE_MAP.put("agree","ap");
    	ROUTE_MAP.put("idx","aq");
    	ROUTE_MAP.put("rule", "ar");
    	ROUTE_MAP.put("finalScore", "as");
    	ROUTE_MAP.put("date", "at");
    	ROUTE_MAP.put("extra", "au");
    	ROUTE_MAP.put("extraType", "av");
    	
    	
    	ROUTE_MAP.put("scoreType", "aw");
    	ROUTE_MAP.put("chuList", "ax");
    	ROUTE_MAP.put("playType", "ay");
    	ROUTE_MAP.put("actionList", "az");
    	ROUTE_MAP.put("currMJNum", "ba");
    	ROUTE_MAP.put("hunPai", "bb");
    	ROUTE_MAP.put("lastAction", "bc");
    	ROUTE_MAP.put("lastActionUserId", "bd");
    	ROUTE_MAP.put("currAction", "be");
    	ROUTE_MAP.put("currActionUserId", "bf");
    	ROUTE_MAP.put("action", "bg");
    	ROUTE_MAP.put("huaList", "bh");
    	ROUTE_MAP.put("zhuangNum", "bi");
    	ROUTE_MAP.put("gangNum", "bj");
    	ROUTE_MAP.put("huScore", "bk");
    	ROUTE_MAP.put("chiType", "bl");
    	ROUTE_MAP.put("huNum", "bm");
    	ROUTE_MAP.put("dianNum", "bn");
    	ROUTE_MAP.put("isWin", "bo");
    	ROUTE_MAP.put("isDian", "bp");
    	ROUTE_MAP.put("winInfo", "bq");
    	ROUTE_MAP.put("dingHunPai", "br");
    	ROUTE_MAP.put("isCreated", "bs");
    	ROUTE_MAP.put("lastFaPai", "bt");
    	ROUTE_MAP.put("toUserId", "bu");
    	ROUTE_MAP.put("startPosition", "bv");
    	
    	ROUTE_MAP.put("x_index", "ca");
    	ROUTE_MAP.put("y_index", "cb");
    	ROUTE_MAP.put("distance", "cc");
    }
    

    public static Map<Integer,int[]> bianmaMap = new ConcurrentHashMap<Integer, int[]>();
    public static int NUM_unknow = -2;//不想让人知道的编码
    public static int NUM_fapai = -1;//请求发牌
    public static int NUM_guo = 0;//过
    public static int NUM_hu = 500;//胡
    public static int NUM_chu = 501;//出牌
    public static int NUM_dui = -3;//怼
    public static int NUM_error = -100;//错误
    
    public static int[] CHI35 = new int[]{1,2,3};
    public static int[] CHI36 = new int[]{2,3,4};
    public static int[] CHI37 = new int[]{3,4,5};
    public static int[] CHI38 = new int[]{4,5,6};
    public static int[] CHI39 = new int[]{5,6,7};
    public static int[] CHI40 = new int[]{6,7,8};
    public static int[] CHI41 = new int[]{7,8,9};
    
    public static int[] CHI42 = new int[]{10,11,12};
    public static int[] CHI43 = new int[]{11,12,13};
    public static int[] CHI44 = new int[]{12,13,14};
    public static int[] CHI45 = new int[]{13,14,15};
    public static int[] CHI46 = new int[]{14,15,16};
    public static int[] CHI47 = new int[]{15,16,17};
    public static int[] CHI48 = new int[]{16,17,18};
    
    public static int[] CHI49 = new int[]{19,20,21};
    public static int[] CHI50 = new int[]{20,21,22};
    public static int[] CHI51 = new int[]{21,22,23};
    public static int[] CHI52 = new int[]{22,23,24};
    public static int[] CHI53 = new int[]{23,24,25};
    public static int[] CHI54 = new int[]{24,25,26};
    public static int[] CHI55 = new int[]{25,26,27};

    public static int[] CHI56 = new int[]{32,33,34};
    static{
    	bianmaMap.put(35, CHI35);
    	bianmaMap.put(36, CHI36);
    	bianmaMap.put(37, CHI37);
    	bianmaMap.put(38, CHI38);
    	bianmaMap.put(39, CHI39);
    	bianmaMap.put(40, CHI40);
    	bianmaMap.put(41, CHI41);
    	bianmaMap.put(42, CHI42);
    	bianmaMap.put(43, CHI43);
    	bianmaMap.put(44, CHI44);
    	bianmaMap.put(45, CHI45);
    	bianmaMap.put(46, CHI46);
    	bianmaMap.put(47, CHI47);
    	bianmaMap.put(48, CHI48);
    	bianmaMap.put(49, CHI49);
    	bianmaMap.put(50, CHI50);
    	bianmaMap.put(51, CHI51);
    	bianmaMap.put(52, CHI52);
    	bianmaMap.put(53, CHI53);
    	bianmaMap.put(54, CHI54);
    	bianmaMap.put(55, CHI55);
    	bianmaMap.put(56, CHI56);
    }
    public static String getChiBianMa(Integer action){
    	String result = "";
    	int[] bianma = bianmaMap.get(action);
    	if (action==ACTION_TYPE_HU) {
			result = "胡";
		}else{
			for(int i=0;i<bianma.length;i++){
	    		if (bianma[i]>9&&bianma[i]<=18) {
	        		result = result.concat((bianma[i]-9)+"").concat(" ");
				}else if(bianma[i]>18&&bianma[i]<=27){
	        		result = result.concat((bianma[i]-18)+"").concat(" ");
				}else if (bianma[i]<=9) {
	        		result = result.concat(bianma[i]+"").concat(" ");
				}else{
	        		result = result.concat(bianma[i]+"").concat(" ");
				}
//				else{
//					result = numToPaiMap.get(bianma[i]+"").concat(" ");
//				}
	    	}
		}
    	return result;
    }
    
    static {
    	for(int i=1;i<=34;i++){
    		bianmaMap.put(i+56, new int[]{i,i,i});
    	}
    }
    
    static {
    	for(int i=1;i<=34;i++){
    		bianmaMap.put(i+90, new int[]{i,i,i,i});
    	}
    	bianmaMap.put(125, new int[]{28,29,30,31});
    	bianmaMap.put(126, new int[]{32,33,34});
    }
    
    
    //请求状态
    public static final int REQ_STATE_FUYI = -1;//敬请期待
    public static final int REQ_STATE_0 = 0;//非法请求
    public static final int REQ_STATE_1 = 1;//正常
    public static final int REQ_STATE_2 = 2;//余额不足
    public static final int REQ_STATE_3 = 3;//已经在其他房间中
    public static final int REQ_STATE_4 = 4;//房间不存在
    public static final int REQ_STATE_5 = 5;//房间人员已满
    public static final int REQ_STATE_6 = 6;//游戏中，不能退出房间
    public static final int REQ_STATE_7 = 7;//有玩家拒绝解散房间
    public static final int REQ_STATE_8 = 8;//玩家不存在（代开模式中，房主踢人用的）
    public static final int REQ_STATE_9 = 9;//接口id不符合，需请求大接口
    public static final int REQ_STATE_10 = 10;//代开房间创建成功
    public static final int REQ_STATE_11 = 11;//已经代开过10个了，不能再代开了
    public static final int REQ_STATE_12 = 12;//房间存在超过24小时解散的提示
    public static final int REQ_STATE_13 = 13;//房间40分钟未开局解散提示
    public static final int REQ_STATE_14 = 14;//ip不一致
    
    public static Map<Integer,String> reqStateMap = new ConcurrentHashMap<Integer, String>();
    static{
    	reqStateMap.put(-1, "敬请期待");
    	reqStateMap.put(0, "非法请求");
    	reqStateMap.put(1, "正常");
    	reqStateMap.put(2, "余额不足");
    	reqStateMap.put(3, "已经在其他房间中");
    	reqStateMap.put(4, "房间不存在");
    	reqStateMap.put(5, "房间人员已满");
    	reqStateMap.put(6, "游戏中，不能退出房间");
    	reqStateMap.put(7, "有玩家拒绝解散房间");
    	reqStateMap.put(8, "玩家不存在");
    	reqStateMap.put(9, "接口id不符合，需请求大接口");
    	reqStateMap.put(10, "代开房间创建成功");
    	reqStateMap.put(11, "已经代开过10个了，不能再代开了");
    	reqStateMap.put(12, "房间存在超过24小时解散的提示");
    	reqStateMap.put(13, "房间40分钟未开局解散提示");
    	reqStateMap.put(14, "ip不一致");
    }
    
    //开房的局数对应消耗的房卡数
    public static final Map<Integer,Integer> moneyMap = new HashMap<>();
    static {
        moneyMap.put(2,4);
        moneyMap.put(4,8);
        moneyMap.put(8,16);
    }
    
  //玩家在线状态 state 
    public static final int PLAYER_LINE_STATE_INLINE = 1;//"inline"
    public static final int PLAYER_LINE_STATE_OUT = 2;//"out"
    
    //玩家进入或退出代开房间
    public static final int PLAYER_EXTRATYPE_ADDROOM = 1;//进入
    public static final int PLAYER_EXTRATYPE_EXITROOM = 2;//退出
    //玩家状态
    public static final int PLAYER_STATE_DATING = 1;//"dating"
    public static final int PLAYER_STATE_IN = 2;//"in"
    public static final int PLAYER_STATE_PREPARED = 3;//"prepared"
    public static final int PLAYER_STATE_GAME = 4;//"game"
    
    //动作类型
    public static final int ACTION_TYPE_FAPAI = -1;
    
    public static final int ACTION_TYPE_CHI = 1;
    public static final int ACTION_TYPE_PENG = 2;
    public static final int ACTION_TYPE_PENGGANG = 3;
    public static final int ACTION_TYPE_DIANGANG = 4;
    public static final int ACTION_TYPE_ANGANG = 5;
    public static final int ACTION_TYPE_CHUPAI = 7;
    public static final int ACTION_TYPE_GUO = 0;
    public static final int ACTION_TYPE_HU = 500;
    
    public static final int ACTION_FAPAI = -1;
    public static final int ACTION_CHUPAI = 501;
    public static final int ACTION_TYPE_DUI = -3;
    
    //请求系统发牌的时候，是否显示发牌按钮  1显示，0不显示；方便停顿测试
    public static boolean shouFaPaiButton = false;
    static{
    	String show = ProjectInfoPropertyUtil.getProperty("shouFaPaiButton","1");
    	if (show.equals("1")) {
    		shouFaPaiButton = true;
		}
    }
    
    
    public static final int ROOM_STATE_CREATED = 1;
    public static final int ROOM_STATE_GAMIING = 2;
    public static final int ROOM_STATE_XJS = 3;
    public static final int ROOM_STATE_YJS = 4;
    
    public static String scoreType1 = "1";
    public static String scoreType2 = "2";
    
    public static Map<String,String> roomStateStr = new ConcurrentHashMap<String, String>();
    static{
    	roomStateStr.put(ROOM_STATE_CREATED+"", "刚创建");
    	roomStateStr.put(ROOM_STATE_GAMIING+"", "游戏中");
    	roomStateStr.put(ROOM_STATE_XJS+"", "小结算");
    	roomStateStr.put(ROOM_STATE_YJS+"", "已解散");
    }
    
    
    public static String getActionStr(Integer action){
    	String str = "";
    	
    	if (action>34&&action<=56) {
			str = "吃";
		}else if (action>56&&action<=90) {
			str = "碰";
		}else if (action>90&&action<=126) {
			str = "杠";
		}else if (action==500) {
			str = "胡";
		}else if (action==0) {
			str = "过";
		}else if (action==-3) {
			str = "怼";
		}else if (action==-1) {
			str = "发牌";
		}else if (action>=127&&action<=134){
			str = "补花";
		}
    	
    	
    	return str;
    }


    public static Map<Integer,String> existType = new ConcurrentHashMap<Integer, String>();
    static{
    	existType.put(1, "玩家退出");
    	existType.put(2, "房间被解散");
    }
    
    public static String split = "和";
    
    public static String buttonId_牌 = "buttonId_牌_";
    public static String buttonId_出的牌 = "buttonId_出的牌_";
    public static String buttonId_创建房间 = "buttonId_创建房间";
    public static String buttonId_加入房间 = "buttonId_加入房间";
    public static String buttonId_俱乐部 = "buttonId_俱乐部";
    public static String buttonId_战绩 = "buttonId_战绩";
    public static String buttonId_代开列表 = "buttonId_代开列表";
    public static String buttonId_代开历史 = "buttonId_代开历史";
    public static String buttonId_添加人员 = "buttonId_添加人员_";
    public static String buttonId_确定 = "buttonId_确定";//创建房间的确定
    public static String buttonId_头像 = "buttonId_头像_";//用户头像
    public static String buttonId_发牌 = "buttonId_发牌_";//发牌按钮
    public static String buttonId_已经执行的行为 = "buttonId_已经执行的行为_";//吃碰杠list的那些数据
    public static String buttonId_已经开过的花牌 = "buttonId_已经开过的画牌_";//开过的画牌

    public static String buttonId_房间规则 = "buttonId_房间规则";//房间规则
    public static String buttonId_开局 = "buttonId_开局_";//房主开房按钮
    public static String buttonId_解散 = "buttonId_解散_";//解散按钮
    
    public static String buttonId_行为 = "buttonId_行为_";//行为按钮
    public static String buttonId_下一局 = "buttonId_下一局";//行为按钮
    
    
    public static String opt_重输 = "opt_重输";
    public static String opt_退格 = "opt_退格";
    public static String opt_确定加入 = "opt_确定加入";
    public static String opt_清理日志 = "opt_清理日志";
    
    public static String redioPrefix_圈数 = "redioPrefix_圈数_";
    public static String redioPrefix_开房模式 = "redioPrefix_开房模式_";
    public static String redioPrefix_玩儿法 = "redioPrefix_玩儿法_";
    public static String roomSnButtonPrefix = "roomSnButtonPrefix_";
    public static String numButtonPrefix = "numButtonPrefix_";
    
    public static String roomType1 = "1";
    public static String roomType2 = "2";
    
    
    private static Map<Integer,Map<String,String>> interfaceStr = new ConcurrentHashMap<Integer, Map<String,String>>();
    
    public static Map<String,String> getSendMap(Integer interfaceId){
    	Map<String,String> send = new ConcurrentHashMap<String, String>();
    	Map<String,String> temp = interfaceStr.get(interfaceId);
    	for(String key:temp.keySet()){
    		send.put(key, temp.get(key));
    	}
    	return send;
    }
    

 // 路由转换
 	/**
 	 * 如:把interfaceId 转换成a 缩短传输的数据.
 	 * 
 	 * @param temp
 	 * @return
 	 */
 	public static JSONObject getNewObj(JSONObject temp) {
 		Iterator<String> iterator = temp.keySet().iterator();
 		JSONObject result = new JSONObject();
 		while (iterator.hasNext()) {
 			String str = iterator.next();
 			Object o = temp.get(str);
 			if (o instanceof List) {
 				result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), getNewList(o));
 			} else if (o instanceof Map) {
 				result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), getNewMap(o));
 			} else {
 				result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), o);
 			}
 		}
 		return result;
 	}

 	public static List getNewList(Object list) {
 		List<Object> temp1 = (List<Object>) list;
 		List<Object> temp = new ArrayList<Object>(temp1);
 		if (temp != null && temp.size() > 0) {
 			for (int i = 0; i < temp.size(); i++) {
 				Object o = temp.get(i);
 				if (o instanceof List) {
 					temp.set(i, getNewList(o));
 				} else if (o instanceof Map) {// 基本上全是这个类型
 					temp.set(i, getNewMap(o));
 				} else {// 默认为String
 					try {
 						JSONObject obj = JSONObject.parseObject(o.toString());
 						temp.set(i, getNewObj(obj));
 					} catch (Exception e) {
 						// e.printStackTrace();
 					}
 				}
 			}
 		}
 		return temp;
 	}

 	public static Map getNewMap(Object map) {
 		Map<String, Object> temp1 = (Map<String, Object>) map;
 		Map<String, Object> temp = new HashMap<String, Object>(temp1);
 		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
 		if (temp != null && temp.size() > 0) {
 			Iterator<String> iterator = temp.keySet().iterator();
 			while (iterator.hasNext()) {
 				String str = String.valueOf(iterator.next());
 				Object o = temp.get(str);
 				if (o instanceof List) {
 					result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), getNewList(o));
 				} else if (o instanceof Map) {
 					result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), getNewMap(o));
 				} else {
 					try {
 						try {
 							JSONObject obj = JSONObject.parseObject(o.toString());
 							result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), getNewObj(obj));
 						} catch (Exception e) {
 							result.put(Cnst.ROUTE_MAP.get(str) == null ? str : Cnst.ROUTE_MAP.get(str), o);
 						}
 					} catch (Exception e) {

 					}
 				}
 			}
 		}
 		return result;
 	}

 	// 转换完成
 	
 	//通过牌的中文显示，返回牌的编码
 	public static String getPaiRealNum(String paiStr){
 		String num = "";
 		for(String n:numToPaiMap.keySet()){
 			if (numToPaiMap.get(n).equals(paiStr)) {
				num = n;
				break;
			}
 		}
 		return num;
 	}
 	
 	public static Map<Integer,String> huInfoStr = new ConcurrentHashMap<Integer, String>();
 	static{
 		huInfoStr.put(1, "点炮");
 		huInfoStr.put(2, "自摸 ");
 		huInfoStr.put(3, "刻牌");
 		huInfoStr.put(4, "杠开");
 		huInfoStr.put(5, "点炮胡");
 		huInfoStr.put(6, "无搭");
 		huInfoStr.put(7, "百搭胡");
 		huInfoStr.put(8, "四混胡");
 		huInfoStr.put(9, "庄家");
 	}
 	
 	
 	
 	
 	
 	//房间规则显示，自定义
 	//说明：只允许有一种玩儿法的，比如如果项目中有唐山玩儿法，有撸麻巧玩儿法，这种切换的，无法实现
 	//只能一种一种测，先定义唐山的下列参数，测试没问题，再定义撸麻巧的下列参数，再测试
 	public static String params_是 = "1";
 	public static String params_否 = "2";
 	public static Integer paramsDataType_是否 = 1;//规定：1是；2否
 	public static Integer paramsDataType_直接取值 = 2;
 	public static Integer paramsDataType_有中文含义 = 3;//需要自己自定义中文含义
 	public static Map<String, String> createRoomParamsStr = new ConcurrentHashMap<String, String>();//参数中文名称集合，都用
 	public static Map<String,Integer> createRoomParamsType = new ConcurrentHashMap<String, Integer>();//参数类型集合，都用
 	public static Map<String,Map<String, String>>  userDefinedStr = new ConcurrentHashMap<String, Map<String,String>>();//有中文含义的集合,查看房间规则用的
 	public static Map<String, List<String>> createRoomParamsSChooseValues = new ConcurrentHashMap<String, List<String>>();//参数的值，创建房间面板用的
 	//注意，以上map的key完全一样，包含完整的创建房间的全部参数、类型、中文名称、值的含义
 	//除了userDefinedStr，如果createRoomParamsType的value为paramsDataType_有中文含义，这里userDefinedStr才会有
 	static {
 		createRoomParamsStr.put("circleNum", "圈数");
 		createRoomParamsStr.put("roomType", "房间类型");
 		createRoomParamsStr.put("scoreType", "计分规则");
 		createRoomParamsStr.put("playType", "玩法规则");
 		createRoomParamsStr.put("chiType", "是否可以吃");
 		
 		createRoomParamsType.put("circleNum", paramsDataType_直接取值);
 		createRoomParamsType.put("roomType", paramsDataType_有中文含义);
 		createRoomParamsType.put("scoreType", paramsDataType_有中文含义);
 		createRoomParamsType.put("playType", paramsDataType_有中文含义);
 		createRoomParamsType.put("chiType", paramsDataType_有中文含义);
 		
 		Map<String, String> userDefinedStrs = new ConcurrentHashMap<String, String>();
 		userDefinedStrs.put("1", "房主模式");
 		userDefinedStrs.put("2", "代开模式");
 		userDefinedStr.put("roomType", userDefinedStrs);
 		
 		userDefinedStrs = new ConcurrentHashMap<String, String>();
 		userDefinedStrs.put("1", "点炮三家付");
 		userDefinedStrs.put("2", "点炮包三家");
 		userDefinedStr.put("scoreType", userDefinedStrs);
 		
 		userDefinedStrs = new ConcurrentHashMap<String, String>();
 		userDefinedStrs.put("1", "出冲大包");
 		userDefinedStrs.put("2", "出冲包三家");
 		userDefinedStrs.put("3", "陪冲");
 		userDefinedStrs.put("4", "不出冲");
 		userDefinedStr.put("scoreType", userDefinedStrs);
 		
 		userDefinedStrs = new ConcurrentHashMap<String, String>();
 		userDefinedStrs.put("1", "有花");
 		userDefinedStrs.put("2", "无花");
 		userDefinedStr.put("playType", userDefinedStrs);
 		
 		userDefinedStrs = new ConcurrentHashMap<String, String>();
 		userDefinedStrs.put("1", "不可吃");
 		userDefinedStrs.put("2", "可以吃");
 		userDefinedStr.put("chiType", userDefinedStrs);
 		
 		List<String> values = new ArrayList<String>();
 		values.add("2");values.add("4");values.add("8");
 		createRoomParamsSChooseValues.put("circleNum", values);
 		values = new ArrayList<String>();
 		values.add("1");values.add("2");
 		createRoomParamsSChooseValues.put("roomType", values);
 		values = new ArrayList<String>();
 		values.add("1");values.add("2");values.add("3");values.add("4");
 		createRoomParamsSChooseValues.put("scoreType", values);
 		values = new ArrayList<String>();
 		values.add("1");values.add("2");
 		createRoomParamsSChooseValues.put("playType", values);
 		values = new ArrayList<String>();
 		values.add("1");values.add("2");
 		createRoomParamsSChooseValues.put("chiType", values);
 		
 	}
 	
 	
 	static{
    	Map<String,String> interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100007");
    	interfaceMap.put("userId", "");
    	//剩下的参数要从创建房间配置的选项中拿
    	for(String key:createRoomParamsType.keySet()){
    		interfaceMap.put(key, "");
    	}
    	interfaceStr.put(100007, interfaceMap);
    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100008");
    	interfaceMap.put("userId", "");
    	interfaceMap.put("roomId", "");
    	interfaceStr.put(100008, interfaceMap);
    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100100");
    	interfaceMap.put("openId", "");
    	interfaceMap.put("cId", Cnst.cid+"");
    	interfaceStr.put(100100, interfaceMap);
    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100102");
    	interfaceMap.put("roomId", "");
    	interfaceStr.put(100102, interfaceMap);
    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100103");
    	interfaceMap.put("roomId", "");
    	interfaceStr.put(100103, interfaceMap);

    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100201");
    	interfaceMap.put("roomId", "");
    	interfaceMap.put("action", "");
    	interfaceMap.put("userId", "");
    	interfaceMap.put("extra", "");
    	interfaceMap.put("toUserId", "");
    	interfaceStr.put(100201, interfaceMap);
    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "100200");
    	interfaceMap.put("roomId", "");
    	interfaceMap.put("userId", "");
    	interfaceStr.put(100200, interfaceMap);
    	
    	interfaceMap = new ConcurrentHashMap<String, String>();
    	interfaceMap.put("interfaceId", "10086");
    	interfaceMap.put("roomId", "");
    	interfaceStr.put(10086, interfaceMap);
    }
 	
 	
 	
 	
}
