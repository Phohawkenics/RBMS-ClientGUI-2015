package temp;
/* ClientGUI.java requires no other files. */
 
import java.io.IOException;
import javax.swing.*;

import phohawkenics.common.Constants;
import phohawkenics.panels.*;
import phohawkenics.utils.LogUtil;
 
public class ClientGUI extends JFrame {
	private static final long serialVersionUID = 6328283849369951542L;
	private JTabbedPane jtp;
	private Client mClient = null;
	private String mName = "";
	private LoginPanel mLoginPanel = null;
	private LogPanel mLogPanel = null;
	private AgendaPanel mAgendaPanel = null;
	private RequestPanel mRequestPanel = null;
	private MessagesPanel mMessagesPanel = null;
	
	
    public ClientGUI () {		
    	setTitle("Client");
    	setSize(700, 500);
        jtp = new JTabbedPane();
        getContentPane().add(jtp);
        JPanel jp1 = new ConnectPanel(this);
        jtp.addTab("Connect", jp1);
	}
    
    public void addNewLoginTab(String title, LoginPanel loginPanel, int tabbedPosition) {
    	removeLaterTabbed(tabbedPosition);
    	jtp.addTab(title, loginPanel);
    	jtp.setSelectedIndex(jtp.getTabCount() - 1);
    }
    
    public void addNewLogTab(String title, LogPanel logPanel, int tabbedPosition) {
    	removeLaterTabbed(tabbedPosition);
    	jtp.addTab(title, logPanel);
    	jtp.setSelectedIndex(jtp.getTabCount() - 1);
    }
    
    public void addNewAgendaTab(String title, AgendaPanel agendaPanel, int tabbedPosition) {
    	removeLaterTabbed(tabbedPosition);
    	jtp.addTab(title, agendaPanel);
    }
    
    public void addNewRequestTab(String title, RequestPanel requestPanel, int tabbedPosition) {
    	removeLaterTabbed(tabbedPosition);
    	jtp.addTab(title, requestPanel);
    }
    
    public void addNewMessagesTab(String title, MessagesPanel messsagesPanel, int tabbedPosition) {
    	removeLaterTabbed(tabbedPosition);
    	jtp.addTab(title, messsagesPanel);
    }
    
    public void addNewTab(String title, JPanel nextPanel, int tabbedPosition) {
    	removeLaterTabbed(tabbedPosition);
    	jtp.addTab(title, nextPanel);
    	jtp.setSelectedIndex(jtp.getTabCount() - 1);
    }
    
    public void setClient(Client client) {
    	mClient = client;
    }
    
    public void setName(String name) {
    	mName = name;
    }
    
    public String getName() {
    	return mName;
    }
    
    public Client getClient() {
    	return mClient;
    }
    
    public void removeLaterTabbed(int position) {
    	for (int i = jtp.getTabCount() - 1; i >= position; i--)
    		jtp.removeTabAt(position);
    }
    
    public void send(String msg) {
    	try {
    		LogUtil.cout("Sent: " + msg);
			mClient.streamOut.writeUTF(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void logonValidated() {
    	mLogPanel = new LogPanel();
    	mAgendaPanel = new AgendaPanel(this);
    	mRequestPanel = new RequestPanel(this);
    	mMessagesPanel = new MessagesPanel();
    	
    	addNewLogTab("Logs", mLogPanel, 3);
    	addNewAgendaTab("Agenda", mAgendaPanel, 4);
    	addNewRequestTab("Request", mRequestPanel, 5);
    	addNewMessagesTab("Messages", mMessagesPanel, 6);
    }
    
    public void disconnectedServer() {
    	removeLaterTabbed(1);
    }
    
    public void updateAgenda(String[] agenda) {
    	mAgendaPanel.setAgenda(agenda);
    }
    
    public void updateAgendaEntryDetails(String entryDetails) {
    	mAgendaPanel.setEntryDetails(entryDetails);
    }
    
    public void updateRequestMessageField (String msg) {
    	mRequestPanel.showMessage(msg);
    }
    
    public void updateParticipants(String participants) {
    	mRequestPanel.setParticipants(participants.split(Constants.SEPERATOR_MULTIPLE));
    }
    
    public void updateRooms(String rooms) {
    	mRequestPanel.setRooms(rooms.split(Constants.SEPERATOR_MULTIPLE));
    }
    
    public void updateMessagesField (String msg) {
    	mMessagesPanel.showMessage(msg);
    }
    
    public void updateMessagesField (String[] msgs) {
    	mMessagesPanel.showMessageBank(msgs);
    }
    
    public LoginPanel getLoginPanel () {
    	return mLoginPanel;
    }
    
    public void setLoginPanel (LoginPanel loginPanel) {
    	mLoginPanel = loginPanel;
    }
    
	public static void main(String args[]) {  		
		ClientGUI tp = new ClientGUI();
        tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tp.setVisible(true);
	}
    
}