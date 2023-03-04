package com.thomas.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendThread extends Thread {
	private String msg;

	private Socket socket;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public SendThread(Socket socket) {
		this.socket = socket;
	}

	public SendThread(Socket socket, String msg) {
		this.socket = socket;
		this.msg = msg;
	}

	public SendThread() {

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		DataOutputStream dataOutputStream;
		try {
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			while (true) {
				// 如果消息不为空
				if (msg != null) {
					System.out.println("消息发送中:" + msg);
					// 发送消息
					dataOutputStream.writeUTF(msg);
					// 清空消息
					msg = null;
				}
				try {
					Thread.sleep(50); // 暂停，等新消息进来
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
