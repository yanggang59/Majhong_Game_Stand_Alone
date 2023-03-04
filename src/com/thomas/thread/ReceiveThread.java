package com.thomas.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thomas.view.MainFrame;
import com.thomas.model.Player;
import com.thomas.util.GameUtil;
import com.thomas.util.GameUtil.ClientGameState;
import com.thomas.model.Majhong;
import com.thomas.model.MajhongLabel;
import com.thomas.model.Message;

public class ReceiveThread extends Thread {
	private Socket socket;
	private MainFrame mainFrame;
	ClientGameState clientGameState = ClientGameState.INITIAL;

	public ReceiveThread(Socket socket, MainFrame mainFrame) {
		this.socket = socket;
		this.mainFrame = mainFrame;
	}

	public void run() {
		try {
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
			while (true) {
				String jsonString = dataInputStream.readUTF();
				if (clientGameState == ClientGameState.INITIAL) {
					List<Player> players = new ArrayList<Player>();
					System.out.println(jsonString);
					// 解析JSON字符串
					// 将JSON字符串转换为JSON数组
					JSONArray playerJsonArray = JSONArray.parseArray(jsonString);
					for (int i = 0; i < playerJsonArray.size(); i++) {
						// 获得单个JSON对象
						JSONObject playerJsonObject = (JSONObject) playerJsonArray.get(i);
						int id = playerJsonObject.getInteger("id");
						String name = playerJsonObject.getString("name");
						boolean isDealer = playerJsonObject.getBoolean("dealer");

						// 存放扑克列表
						List<Majhong> majhongs = new ArrayList<Majhong>();
						JSONArray majhongJsonArray = playerJsonObject.getJSONArray("majhongs");
						for (int j = 0; j < majhongJsonArray.size(); j++) {
							// 每循环一次获得一个poker对象
							JSONObject pokerJsonObject = (JSONObject) majhongJsonArray.get(j);
							int pokerID = pokerJsonObject.getInteger("id");
							String pokerName = pokerJsonObject.getString("name");
							Majhong majhong = new Majhong(pokerID, pokerName);
							majhongs.add(majhong);
						}
						Player player = new Player(id, name, isDealer, majhongs);
						players.add(player);
					}
					// 显示4个玩家的信息了
					if (players.size() == 4) {
						mainFrame.showAllPlayersInfo(players);
					}
					mainFrame.addClickEventToMajhongLabels();

					Message sendMessage = new Message(0, mainFrame.currentPlayer.getId(), "FirstFapai", null);

					// 通知服务器，这是牌局的开始，服务器将发牌给庄家
					mainFrame.sendThread.setMsg(JSON.toJSONString(sendMessage));

					clientGameState = ClientGameState.PLAYING;
				} else if (clientGameState == ClientGameState.PLAYING) {
					System.out.println("[Info] Client In Playing State");

					JSONObject msgJsonObject = JSON.parseObject(jsonString);
					int typeID = msgJsonObject.getInteger("typeID");
					int playerID = msgJsonObject.getInteger("playerID");
					String content = msgJsonObject.getString("content");
					if (typeID == 0) // 牌局开始，接收服务器发给庄家的第一张牌
					{
						System.out.println("[Client] Message received,typeID is 0");
						System.out.println("[Client] Message received" + jsonString);
						// 若当前玩家是庄家，才解析当前的msg，其他玩家根据playerID增长对应玩家牌数
						if (playerID == mainFrame.currentPlayer.getId()) {
							System.out.println("[Client] Current Dealer");
							JSONArray majhongJsonArray = msgJsonObject.getJSONArray("majhongs");
							JSONObject majhongJsonObject = (JSONObject) majhongJsonArray.get(0);
							int majhongID = majhongJsonObject.getInteger("id");
							String majhongName = majhongJsonObject.getString("name");
							Majhong majhong = new Majhong(majhongID, majhongName);
							MajhongLabel majhongLabel = new MajhongLabel(majhongID, majhongName, 0,
									"images/majhong/" + majhong.getId() + ".png", 60, 90);

							mainFrame.addClickEventToMajhongLabel(majhongLabel);

							// mainFrame.currentPlayer.getMajhongs().add(majhong);
							int myMajhongNum = mainFrame.myHoldMajhongLabels.size();
							MajhongLabel lastMajhongLabel = mainFrame.myHoldMajhongLabels.get(myMajhongNum - 1);
							GameUtil.move(majhongLabel, lastMajhongLabel.getX() + 82 + 30, lastMajhongLabel.getY());
							GameUtil.move(majhongLabel.getMajhongShellLabel(),
									lastMajhongLabel.getMajhongShellLabel().getX() + 82 + 30,
									lastMajhongLabel.getMajhongShellLabel().getY());

							mainFrame.myHoldMajhongLabels.add(majhongLabel);

							mainFrame.myPanel.add(majhongLabel);
							mainFrame.myPanel.add(majhongLabel.getMajhongShellLabel());
							mainFrame.myPanel.setComponentZOrder(majhongLabel.getMajhongShellLabel(), 0);
							mainFrame.myPanel.setComponentZOrder(majhongLabel, 0);

							mainFrame.repaint();
						}

					}

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
