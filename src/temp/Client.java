package temp;
import java.net.*;
import java.util.Scanner;
import java.io.*;

import phohawkenics.common.Constants;
import phohawkenics.common.MeetingConstants;
import phohawkenics.threads.ServerListenerThread;
import phohawkenics.utils.LogUtil;

public class Client implements Runnable {
	private ClientGUI mGUI = null;
	private Socket socket = null;
	private Thread thread = null;
	private DataInputStream console = null;
	public DataOutputStream streamOut = null;
	private ServerListenerThread client = null;
	private boolean hasFailed = false;
	
	public Client(ClientGUI GUI,String serverName, int serverPort) {
		mGUI = GUI;
		LogUtil.cout("Establishing connection. Please wait ...");
		try {
			socket = new Socket(serverName, serverPort);
			LogUtil.cout("Connected: " + socket);
			start();
		} catch (UnknownHostException uhe) {
			LogUtil.cout("Host unknown: " + uhe.getMessage());
			hasFailed = true;
		} catch (IOException ioe) {
			LogUtil.cout("Unexpected exception: " + ioe.getMessage());
			hasFailed = true;
		}
	}
	
	public boolean getFlag() {
		return hasFailed;
	}
	
	public void setFlag() {
		hasFailed = true;
	}
	
	@SuppressWarnings("resource")
	public void run() {
		Scanner s = new Scanner(System.in);
		while (thread != null) {
			try {
				if (s.hasNextLine())
					streamOut.writeUTF(s.nextLine());
				streamOut.flush();
			} catch (IOException ioe) {
				LogUtil.cout("Sending error: " + ioe.getMessage());
				stop();
			}
		}
	}

	@SuppressWarnings("resource")
	public void handle(String msg) {
		@SuppressWarnings("unused")
		Scanner s = new Scanner(System.in);
		LogUtil.cout("Received: " + msg);
		if (msg.equals(".exit")) {
			LogUtil.cout("Good bye. Press RETURN to exit ...");
			stop();
		} else {
			String msgArray[] = msg.split(Constants.SEPERATOR_CLIENT_TRIGGER);
			if (msg.contains("Successful")) {
				mGUI.logonValidated();
				mGUI.getLoginPanel().disableLogin();
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_AGENDA)) {
				if (msgArray.length == 2) {
					mGUI.updateAgenda(msgArray[1].split(Constants.SEPERATOR_DATA_HANDLER));
				}
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_MEETING_DETAILS)) {
				if (msgArray.length == 2) {
					mGUI.updateAgendaEntryDetails(msgArray[1]);
				}
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_REQUEST)) {
				if (msgArray.length == 2) {
					mGUI.updateRequestMessageField(msgArray[1]);
				}
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_NAMES)) {
				if (msgArray.length == 2) {
					mGUI.updateParticipants((msgArray[1]));
				}
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_ROOMS)) {
				if (msgArray.length == 2) {
					mGUI.updateRooms(msgArray[1]);
				}
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_MESSAGE)) {
				if (msgArray.length == 2) {
					mGUI.updateMessagesField(msgArray[1]);
				}
			} else if (msgArray[0].equals(MeetingConstants.MSG_TYPE_MESSAGE_BANK)) {
				if (msgArray.length == 2) {
					mGUI.updateMessagesField(msgArray[1].split(Constants.SEPERATOR_DATA_HANDLER));
				}
			}
		}
	}
	
	public void start() throws IOException {
		console = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null) {
			client = new ServerListenerThread(this, socket, mGUI);
			thread = new Thread(this);
			thread.start();
		}
	}

	public synchronized void stop() {
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
		try {
			if (console != null)
				//console.close();
			if (streamOut != null)
				streamOut.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			LogUtil.cout("Error closing ...");
		}
		client.close();
		client.interrupt();
	}
}