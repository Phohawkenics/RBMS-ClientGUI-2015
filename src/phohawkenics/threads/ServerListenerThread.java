package phohawkenics.threads;

import java.net.*;
import java.io.*;

import phohawkenics.utils.LogUtil;
import temp.*;

public class ServerListenerThread extends Thread {
	private ClientGUI mGUI;
	private Socket socket = null;
	private Client client = null;
	private DataInputStream streamIn = null;

	public ServerListenerThread(Client _client, Socket _socket, ClientGUI GUI) {
		mGUI = GUI;
		client = _client;
		socket = _socket;
		open();
		start();
	}

	public void open() {
		try {
			streamIn = new DataInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			LogUtil.cout("Error getting input stream: " + ioe);
			client.stop();
		}
	}

	public void close() {
		try {
			if (streamIn != null)
				streamIn.close();
		} catch (IOException ioe) {
			LogUtil.cout("Error closing input stream: " + ioe);
		}
	}

	public void run() {
		while (!isInterrupted()) {
			try {
				client.handle(streamIn.readUTF());
			} catch (IOException ioe) {
				LogUtil.cout("Listening error: " + ioe.getMessage());
				mGUI.disconnectedServer();
				client.stop();
				interrupt();
			}
		}
	}
}