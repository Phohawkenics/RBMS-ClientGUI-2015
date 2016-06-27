package phohawkenics.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import phohawkenics.custom.CustomOutputStream;
import phohawkenics.utils.LogUtil;
import temp.Client;
import temp.ClientGUI;

public class ConnectPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final int tabbedPosition = 1;
	private ClientGUI mJFrame;
	private JLabel lblServer;
	private JTextField inServer;
	private JLabel lblPort;
	private JTextField inPort;
	private JButton btnConnect;
	private JTextArea outCommand;
	private JScrollPane scrollPane;
	
    
    public ConnectPanel (ClientGUI jFrame) {
        super(new GridBagLayout());
        mJFrame = jFrame;
        instantiateComponents();
        addComponents();
    }
    
    public void instantiateComponents() {
    	lblServer = new JLabel("Server Name: ");
        lblPort = new JLabel("Port #: ");
        
        inServer = new JTextField(20);
        inPort = new JTextField(20);
        btnConnect = new JButton("Connect");
        outCommand = new JTextArea(5, 20);
        
        inServer.setSize(100, 20);
        inPort.setSize(100, 20);
        btnConnect.setSize(20, 20);
        
        btnConnect.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
            	if(btnConnect.getText().equals("Connect")) {
            		instatiateClient();
            		btnConnect.setText("Disconnect");
            } else if (btnConnect.getText().equals("Disconnect")) {
            		disconnectClient();
            		btnConnect.setText("Connect");
            	}
            } 
        });    
        outCommand.setEditable(false);
        scrollPane = new JScrollPane(outCommand);
    }
    
    public void addComponents() {
    	//Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        
        // Row 1
        c.gridwidth = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblServer, c);
        add(inServer, c);
        add(lblPort, c);
        add(inPort, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(btnConnect, c);
        // Row 2
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    public void setStreams() {
    	// Set output Stream
        PrintStream printStream = new PrintStream(new CustomOutputStream(outCommand));
        System.setOut(printStream);
        System.setErr(printStream);
    }
 
    public void actionPerformed(ActionEvent evt) {
        outCommand.setCaretPosition(outCommand.getDocument().getLength());
    }
    
    private void instatiateClient () {
    	setStreams();
    	String server =  inServer.getText();
    	String port = inPort.getText();
    	
    	if( server.isEmpty() || port.isEmpty() ) {
    		LogUtil.cout("FAILED - Server and Port cannot be blank");
    	}
    	else {
	    	Client client = new Client(mJFrame ,server, Integer.valueOf(port));
	    	if (!client.getFlag()) {
	    		mJFrame.setClient(client);
	    		LogUtil.cout("SUCCESS");
	    		btnConnect.setText("Disconnect");
	    		inServer.setEditable(false);
	        	inPort.setEditable(false);
	    		mJFrame.addNewLoginTab("Login", new LoginPanel(mJFrame), tabbedPosition);
	    	} else {
	    		btnConnect.setText("Connect");
	    		LogUtil.cout("FAILED");
	    	}
    	}
    }
    
    private void disconnectClient () {
    	mJFrame.disconnectedServer();
    	inServer.setEditable(true);
    	inPort.setEditable(true);
    	Client temp = mJFrame.getClient();
    	if (temp != null) {
    		temp.stop();
    	}
    }
}
