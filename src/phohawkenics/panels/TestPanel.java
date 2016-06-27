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

import phohawkenics.custom.CustomInputStream;
import phohawkenics.custom.CustomOutputStream;
import temp.Client;
import temp.ClientGUI;

public class TestPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private ClientGUI mJFrame;
	private JLabel lblServer;
	private JTextField inServer;
	private JLabel lblPort;
	private JTextField inPort;
	private JButton btnConnect;
	private JLabel lblCommand;
	private JTextField inCommand;
	private JTextArea outCommand;
    
    public TestPanel (ClientGUI JFrame) {
        super(new GridBagLayout());
        
        mJFrame = JFrame;
        
        lblServer = new JLabel("Server Name: ");
        lblPort = new JLabel("Port #: ");
        lblCommand = new JLabel("Command: ");
        
        inServer = new JTextField(20);
        inPort = new JTextField(20);
        btnConnect = new JButton("Connect");
        inCommand = new JTextField(20);
        outCommand = new JTextArea(5, 20);
        
        inServer.setSize(100, 20);
        inPort.setSize(100, 20);
        btnConnect.setSize(20, 20);
        
        btnConnect.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                instatiateClient();
            } 
        });
        
        inCommand.addActionListener(this);        
        outCommand.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outCommand);
 
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
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblCommand, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(inCommand, c);
        // Row 3
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        // Set output Stream
        PrintStream printStream = new PrintStream(new CustomOutputStream(outCommand));
        System.setOut(printStream);
        System.setErr(printStream);
        
        CustomInputStream ts = new CustomInputStream(inCommand);
        //maybe this next line should be done in the TextFieldStreamer ctor
        //but that would cause a "leak a this from the ctor" warning
        inCommand.addActionListener(ts);

        System.setIn(ts);
        
    }
 
    public void actionPerformed(ActionEvent evt) {
        //String text = inCommand.getText();
        //textAreaOutput.append(text);
        
        inCommand.setText(null);
        
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        outCommand.setCaretPosition(outCommand.getDocument().getLength());
    }
    
    private void instatiateClient () {
    	String server =  "localhost";
    	String port = inPort.getText();
    	new Client(mJFrame, server, Integer.valueOf(port));
    }
}
