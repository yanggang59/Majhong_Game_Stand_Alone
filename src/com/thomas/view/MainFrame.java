package com.thomas.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.thomas.model.MajhongLabel;
import com.thomas.model.Player;
import com.thomas.view.MyPanel;
import com.thomas.thread.SendThread;
import com.thomas.thread.MusicThread;
import com.thomas.thread.ReceiveThread;
import com.thomas.model.Majhong;
import com.thomas.model.MajhongLabel;
import com.thomas.util.GameUtil;

public class MainFrame extends JFrame {
	public MyPanel myPanel;
	public String uname;
	public Socket socket;

	public SendThread sendThread;
	public ReceiveThread receiveThread;
	public MusicThread musicThread; // 播放音效线程

	public Player currentPlayer; // 存放当前玩家对象

	// 自家的手持麻将列表
	public List<MajhongLabel> myHoldMajhongLabels = new ArrayList<MajhongLabel>();

	// 自家的打出的麻将列表
	public List<MajhongLabel> myDiscardedMajhongLabels = new ArrayList<MajhongLabel>();

	// 自家的碰杠和花牌麻将列表
	public List<MajhongLabel> myOperatedMajhongLabels = new ArrayList<MajhongLabel>();

	// 上一家手持的麻将列表
	public List<MajhongLabel> leftHoldMajhongLabels = new ArrayList<MajhongLabel>();
	// 上一家的打出的麻将列表
	public List<MajhongLabel> leftDiscardedMajhongLabels = new ArrayList<MajhongLabel>();
	// 上一家的碰杠和花牌麻将列表
	public List<MajhongLabel> leftOperatedMajhongLabels = new ArrayList<MajhongLabel>();

	// 下一家手持的麻将列表
	public List<MajhongLabel> rightHoldMajhongLabels = new ArrayList<MajhongLabel>();
	// 下一家的打出的麻将列表
	public List<MajhongLabel> rightDiscardedMajhongLabels = new ArrayList<MajhongLabel>();
	// 下一家的碰杠和花牌麻将列表
	public List<MajhongLabel> rightOperatedMajhongLabels = new ArrayList<MajhongLabel>();

	// 对家的手持麻将列表
	public List<MajhongLabel> oppositeHoldMajhongLabels = new ArrayList<MajhongLabel>();
	// 对家的打出的麻将列表
	public List<MajhongLabel> opppositeDiscardedMajhongLabels = new ArrayList<MajhongLabel>();
	// 对家的碰杠和花牌麻将列表
	public List<MajhongLabel> oppositeOperatedMajhongLabels = new ArrayList<MajhongLabel>();

	public List<MajhongLabel> selectedMajhongLabels = new ArrayList<MajhongLabel>();
	// 庄家图标
	public JLabel dealerIconLabel;

	// 是否有手牌被选中
	public boolean isHoldMajhongSelected;
	public MajhongLabel selectedHoldMajhongLabel = null;

	// 现在是否是轮到我出牌
	public boolean isMyturn;

	public MainFrame(String uname, Socket socket) {
		this.uname = uname;
		this.socket = socket;

		// 设置窗口的属性
		this.setSize(1280, 720);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 添加mypanel
		myPanel = new MyPanel();
		myPanel.setBounds(0, 0, 1280, 720);
		this.add(myPanel);

		// 启动发送消息的线程
		sendThread = new SendThread(socket, uname);
		sendThread.start();

		// 启动接收消息的线程
		receiveThread = new ReceiveThread(socket, this);
		receiveThread.start();

		// String picPath1 = "images/majhong/front_inhand_image_29.png";
		// String valuePath = "images/majhong/w_1.png";
		// //List<MajhongLabel> majhongLabels1 = new ArrayList<MajhongLabel>();
		// for(int i = 0;i<13;i++)
		// {
		// MajhongLabel majhongLabel = new MajhongLabel(i,"wan",picPath1,88,128, false,
		// true);
		// //majhongLabels1.add(majhongLabel);
		// this.myPanel.add(majhongLabel); //添加到面板
		// //每次叠上去都放在最上面
		// this.myPanel.setComponentZOrder(majhongLabel, 0);
		//
		// MajhongLabel valueLabel = new MajhongLabel(i,"wan",valuePath,60,90, false,
		// true);
		// //majhongLabels1.add(majhongLabel);
		// valueLabel.setVisible(false);
		// this.myPanel.add(valueLabel); //添加到面板
		// //每次叠上去都放在最上面
		// this.myPanel.setComponentZOrder(valueLabel, 0);
		//
		// //一张一张的显示出来
		// GameUtil.move(majhongLabel, 40+82*i, 550);
		// GameUtil.move(valueLabel, 55+82*i, 550);
		// valueLabel.setVisible(true);
		// }
		//
		// String picPath2 = "images/majhong/opposite_inhand_29.png";
		// List<MajhongLabel> majhongLabels2 = new ArrayList<MajhongLabel>();
		// for(int i = 0;i<13;i++)
		// {
		// MajhongLabel majhongLabel = new MajhongLabel(i, "wan",picPath2,44,72, false,
		// true);
		// majhongLabels2.add(majhongLabel);
		// this.myPanel.add(majhongLabel); //添加到面板
		// //每次叠上去都放在最上面
		// this.myPanel.setComponentZOrder(majhongLabel, 0);
		// //一张一张的显示出来
		// GameUtil.move(majhongLabel, 300+43*i, 10);
		// }
		//
		// String picPath3 = "images/majhong/left_inhand_29.png";
		// List<MajhongLabel> majhongLabels3 = new ArrayList<MajhongLabel>();
		// for(int i = 0;i<13;i++)
		// {
		// MajhongLabel majhongLabel = new MajhongLabel(i, "wan",picPath3, 26,56,false,
		// true);
		// majhongLabels3.add(majhongLabel);
		// this.myPanel.add(majhongLabel); //添加到面板
		// //每次叠上去都放在最上面
		// this.myPanel.setComponentZOrder(majhongLabel, 0);
		// //一张一张的显示出来
		// GameUtil.move(majhongLabel, 180, 140+20*i);
		// }
		//
		// String picPath4 = "images/majhong/right_inhand_29.png";
		// List<MajhongLabel> majhongLabels4 = new ArrayList<MajhongLabel>();
		// for(int i = 0;i<13;i++)
		// {
		// MajhongLabel majhongLabel = new MajhongLabel(i, "wan", picPath4, 26,56,false,
		// true);
		// majhongLabels3.add(majhongLabel);
		// this.myPanel.add(majhongLabel); //添加到面板
		// //每次叠上去都放在最上面
		// this.myPanel.setComponentZOrder(majhongLabel, 0);
		// //一张一张的显示出来
		// GameUtil.move(majhongLabel, 1000, 140+20*i);
		// }

	}

	public void showAllPlayersInfo(List<Player> players) {
		// 1.显示4个玩家的名字,uname

		// 2.显示庄家信息
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(uname)) {
				currentPlayer = players.get(i);
			}
		}
		showDealerInfo(players);

		// 3.显示当前所有玩家的麻将列表

		List<Majhong> majhongs = currentPlayer.getMajhongs();
		for (int i = 0; i < majhongs.size(); i++) {
			Majhong majhong = majhongs.get(i);
			String majhongName = majhong.getName();
			MajhongLabel majhongLabel = new MajhongLabel(majhongs.get(i).getId(), majhongName, 0,
					"images/majhong/" + majhong.getId() + ".png", 60, 90);
			myHoldMajhongLabels.add(majhongLabel);
		}
		System.out.println("Before Sorting:");
		for (int i = 0; i < myHoldMajhongLabels.size(); i++) {
			System.out.println(myHoldMajhongLabels.get(i).getId());
		}
		// 对麻将牌进行排序
		Collections.sort(myHoldMajhongLabels);
		System.out.println("After Sorting:");
		for (int i = 0; i < myHoldMajhongLabels.size(); i++) {
			System.out.println(myHoldMajhongLabels.get(i).getId());
		}
		showHoldMajhongs(myHoldMajhongLabels, currentPlayer.getId());
		this.repaint();

	}

	private void showHoldMajhongs(List<MajhongLabel> myHoldMajhongLabels, int playerID) {
		if (currentPlayer.getId() == playerID) // 显示自己家的手持牌
		{
			String shellPath = "images/majhong/front_inhand_image_29.png";
			for (int i = 0; i < myHoldMajhongLabels.size(); i++) {
				// 一张一张的显示出来
				GameUtil.move(myHoldMajhongLabels.get(i).getMajhongShellLabel(), 40 + 82 * i, 550);
				GameUtil.move(myHoldMajhongLabels.get(i), 55 + 82 * i, 580);

				this.myPanel.add(myHoldMajhongLabels.get(i)); // 添加到面板
				this.myPanel.add(myHoldMajhongLabels.get(i).getMajhongShellLabel()); // 添加到面板

				// 每次叠上去都放在最上面
				this.myPanel.setComponentZOrder(myHoldMajhongLabels.get(i).getMajhongShellLabel(), 0);
				this.myPanel.setComponentZOrder(myHoldMajhongLabels.get(i), 0);
			}
		}
		this.repaint();
	}

	public void playSoundEffect(String majhongName) {
		musicThread = new MusicThread("audio/" + majhongName + ".wav");
		musicThread.start();
	}

	public void discardMajhong(MajhongLabel majhongLabel) {
		myHoldMajhongLabels.remove(majhongLabel);
		for (int i = 0; i < myHoldMajhongLabels.size(); i++) {
			this.myPanel.remove(myHoldMajhongLabels.get(i));
			this.myPanel.remove(myHoldMajhongLabels.get(i).getMajhongShellLabel());
		}
		Collections.sort(myHoldMajhongLabels);
		myDiscardedMajhongLabels.add(majhongLabel);
		playSoundEffect(majhongLabel.getName());
	}

	private void showDiscardMajhongLabels(List<MajhongLabel> myDiscardedMajhongLabels, int playerID) {
		// TODO Auto-generated method stub
		if (currentPlayer.getId() == playerID) {
			String shellPath = "images/majhong/vertical_discard_image_29.png";
			for (int i = 0; i < myDiscardedMajhongLabels.size(); i++) {
				MajhongLabel majhongLabel = myDiscardedMajhongLabels.get(i);
				majhongLabel.setValueLabelSize(35, 42);
				// 设置外壳标签
				majhongLabel.getMajhongShellLabel().setUpIcon(shellPath, 38, 58);
				// 创建扑克标签
				this.myPanel.add(myDiscardedMajhongLabels.get(i)); // 添加到面板
				this.myPanel.add(myDiscardedMajhongLabels.get(i).getMajhongShellLabel()); // 添加到面板
				// 每次叠上去都放在最上面
				this.myPanel.setComponentZOrder(myDiscardedMajhongLabels.get(i).getMajhongShellLabel(), 0);
				this.myPanel.setComponentZOrder(myDiscardedMajhongLabels.get(i), 0);
				// 一张一张的显示出来
				GameUtil.move(myDiscardedMajhongLabels.get(i).getMajhongShellLabel(), 254 + 38 * i, 395);
				GameUtil.move(myDiscardedMajhongLabels.get(i), 255 + 38 * i, 370);
			}
		}
		this.repaint();
	}

	public void refreshMyPanel() {
		this.repaint();
	}

	private void showDealerInfo(List<Player> players) {
		String dealerIconPath = "images/majhong/dealer.png";
		dealerIconLabel = new JLabel();
		dealerIconLabel.setIcon(new ImageIcon(dealerIconPath));
		dealerIconLabel.setSize(44, 46);
		// 根据玩家ID显示在对应的位置
		int myID = currentPlayer.getId();
		int dealerID = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isDealer()) {
				dealerID = i;
				break;
			}
		}
		// 自己是庄家
		if (dealerID == myID) {
			dealerIconLabel.setLocation(130, 500);
		}
		// 下一家是庄家 0 1 , 1 2 , 2 3 , 3 0
		else if (myID + 1 == dealerID || myID - 3 == currentPlayer.getId()) {
			dealerIconLabel.setLocation(1200, 360);
		}
		// 对家是庄家 0 2, 1 3, 2 0, 3 1
		else if (myID + 2 == dealerID || dealerID + 2 == myID) {
			dealerIconLabel.setLocation(1000, 50);
		} // 上家是庄家
		else {
			dealerIconLabel.setLocation(50, 360);
		}
		this.myPanel.add(dealerIconLabel);
		this.repaint(); // 重绘

	}

	public void addClickEventToMajhongLabel(MajhongLabel majhongLabel) {
		majhongLabel.addMouseListener(new MajhongLabelEvent());
	}

	public void addClickEventToMajhongLabels() {
		for (int i = 0; i < myHoldMajhongLabels.size(); i++) {
			myHoldMajhongLabels.get(i).addMouseListener(new MajhongLabelEvent());
		}
	}

	class MajhongLabelEvent implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			// 如果之前选择过了这张牌,并且轮到我出牌，则打出
			MajhongLabel majhongLabel = (MajhongLabel) arg0.getSource();
			if (majhongLabel.isSelected()) {
				selectedHoldMajhongLabel = null;
				discardMajhong(majhongLabel);
				showHoldMajhongs(myHoldMajhongLabels, currentPlayer.getId());
				showDiscardMajhongLabels(myDiscardedMajhongLabels, currentPlayer.getId());
			}
			// 如果之前没有选择,则先看有没有其他选中的牌
			else {
				// 如果之前没有其他的牌被选中
				if (selectedHoldMajhongLabel == null) {
					// 被选中的牌往上移动一段距离
					majhongLabel.setLocation(majhongLabel.getX(), majhongLabel.getY() - 30);
					majhongLabel.getMajhongShellLabel().setLocation(majhongLabel.getMajhongShellLabel().getX(),
							majhongLabel.getMajhongShellLabel().getY() - 30);

					selectedHoldMajhongLabel = majhongLabel;
					majhongLabel.setSelected(true);
				} else // 之前有其他牌选中
				{
					// 之前被选中的牌返回原位置
					selectedHoldMajhongLabel.setLocation(selectedHoldMajhongLabel.getX(),
							selectedHoldMajhongLabel.getY() + 30);
					selectedHoldMajhongLabel.getMajhongShellLabel().setLocation(
							selectedHoldMajhongLabel.getMajhongShellLabel().getX(),
							selectedHoldMajhongLabel.getMajhongShellLabel().getY() + 30);
					// if(isChiPengMode)
					// {
					// selectedMajhongLabels.add(majhongLabel);
					// }
					// 取消之前被选中的牌
					selectedHoldMajhongLabel.setSelected(false);

					// 被选中的牌往上移动一段距离
					majhongLabel.setLocation(majhongLabel.getX(), majhongLabel.getY() - 30);
					majhongLabel.getMajhongShellLabel().setLocation(majhongLabel.getMajhongShellLabel().getX(),
							majhongLabel.getMajhongShellLabel().getY() - 30);
					selectedHoldMajhongLabel = majhongLabel;
					selectedHoldMajhongLabel.setSelected(true);
					refreshMyPanel();
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
