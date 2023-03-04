package com.thomas.model;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MajhongShellLabel extends JLabel {
	private String picPath;
	private int width;
	private int height;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public MajhongShellLabel(String picPath, int width, int height) {
		this.picPath = picPath;
		this.width = width;
		this.height = height;
		setUpIcon(picPath, width, height);
		this.setSize(88, 128);
	}

	public void setUpIcon(String picPath, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon image = new ImageIcon(picPath);
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));// 可以用下面三句代码来代替
		this.setIcon(image);
		this.setSize(width, height);
	}
}
