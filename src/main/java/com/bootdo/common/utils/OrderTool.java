package com.bootdo.common.utils;

import java.util.Random;


/**
 * 描述：生成订单相关的编号
 *
 * @author xyy

 */
public class OrderTool {
	

	/**
	 * 
	 * 功能描述：生成商品识别码 生成规则 商品名称缩写+商品数据库id+时间戳+5位随机数
	 *
	 * @author xyy
	 *
	 */
	public static String getOrderNo(Integer n){
		return getTimestamp()+getRandomNumber(n);
	}


	/**
	 * 得到n位长度的随机数
	 * @param n
	 * @return
	 */
	public static String getRandomNumber(int n) {

		String number = "";
		Random rand = new Random();
		while (true) {
			number =number + rand.nextInt(10);
			if (number.length()>=n)
				break;
		}
		return number+"";
	}

	/**
	 * 获取时间戳
	 * @return
	 */
	private static long getTimestamp(){
        return System.currentTimeMillis();
    }
	
	 
	public static void main(String[] args) {


		
	}
}
