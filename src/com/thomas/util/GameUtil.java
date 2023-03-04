package com.thomas.util;

import javax.swing.JLabel;

import com.thomas.model.Majhong;
import com.thomas.model.MajhongLabel;

public class GameUtil {
	public static void move(JLabel jLabel,int x,int y)
	{
		jLabel.setLocation(x,y);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static enum ClientGameState{
		INITIAL,PLAYING,END;
	}
	
	public static String MajhongNames[] = {
			"undefined","1tiao","2tiao","3tiao","4tiao","5tiao","6tiao","7tiao","8tiao","9tiao","undefined",
			"undefined","1tong","2tong","3tong","4tong","5tong","6tong","7tong","8tong","9tong","undefined",
			"undefined","1wan","2wan","3wan","4wan","5wan","6wan","7wan","8wan","9wan","undefined",
			"dong","xi","nan","bei","hongzhong","facai","baiban"
	};

}
