package com.thomas.view;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

	public MyPanel() {
		// TODO Auto-generated constructor stub
		this.setLayout(null); // 如果需要用到setLocation,setBounds,就需要设置布局为null
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		// super.paintComponent(g);
		Image image = new ImageIcon("images/bg/majhong_table.png").getImage();
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}

}
