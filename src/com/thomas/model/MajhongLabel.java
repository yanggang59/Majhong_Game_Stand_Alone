package com.thomas.model;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MajhongLabel extends JLabel implements Comparable {
	private int id;
	private String name;
	private int position; // 判断是哪一家的什么牌
	private String picPath;
	private boolean isSelected; // 是否选中
	private int width;
	private int height;
	private MajhongShellLabel majhongShellLabel;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public MajhongLabel(int id, String name, int position, String picPath, int width, int height) {
		this.id = id;
		this.name = name;
		this.position = position;
		this.picPath = picPath;
		this.width = width;
		this.height = height;
		if (position == 0) // 自己手里的牌
		{
			setValueLabelSize(width, height);
			this.setSize(88, 128);
			this.majhongShellLabel = new MajhongShellLabel("images/majhong/front_inhand_image_29.png", 88, 128);
		}

	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public MajhongShellLabel getMajhongShellLabel() {
		return majhongShellLabel;
	}

	public void setMajhongShellLabel(MajhongShellLabel majhongShellLabel) {
		this.majhongShellLabel = majhongShellLabel;
	}

	public void setValueLabelSize(int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon image = new ImageIcon(picPath);
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));// 可以用下面三句代码来代替
		this.setIcon(image);
		this.setSize(width, height);
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		MajhongLabel majhongLabel = (MajhongLabel) arg0;
		if (this.id > majhongLabel.id)
			return 1;
		else if (this.id < majhongLabel.id)
			return -1;
		else
			return 0;
	}
}
