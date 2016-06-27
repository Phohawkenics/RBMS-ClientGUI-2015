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
import temp.ClientGUI;

public class LoginPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private ClientGUI mJFrame;
	private JLabel lblName;
	private JTextField inName;
	private JLabel lblPassword;
	private JTextField inPassword;
	private JButton btnConnect;
	private JButton btnRegister;
	private JTextArea outCommand;
	private JScrollPane scrollPane;
	
    
    public LoginPanel (ClientGUI jFrame) {
        super(new GridBagLayout());
        mJFrame = jFrame;
        instantiateComponents();
        addComponents();
        setStreams();
    }
    
    public void instantiateComponents() {
    	lblName = new JLabel("Login Name: ");
        lblPassword = new JLabel("Password: ");
        
        inName = new JTextField(20);
        inPassword = new JTextField(20);
        btnConnect = new JButton("Login");
        btnRegister = new JButton("Register");
        outCommand = new JTextArea(5, 20);
        
        inName.setSize(100, 20);
        inPassword.setSize(100, 20);
        btnConnect.setSize(20, 20);
        btnRegister.setSize(20, 20);
        
        btnConnect.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                loginClient();
            } 
        });
        btnRegister.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                registerClient();
            } 
        });
		
        outCommand.setEditable(false);
        scrollPane = new JScrollPane(outCommand);
        
        mJFrame.setLoginPanel(this);
    }
    
    public void addComponents() {
    	//Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        
        // Row 1
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblName, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(inName, c);
        // Row 2
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblPassword, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(inPassword, c);
        // Row 3
        add(btnConnect, c);
        // Row 4
        add(btnRegister, c);
        
        // Row 5
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
    
    private void loginClient() {
    	String name = inName.getText();
    	String password = inPassword.getText();
    	String loginString = "LOGIN" + " " + name + " " + password;
    	
    	if (name.isEmpty() || password.isEmpty() )
    		LogUtil.cout("FAILED - Name or Password cannot be blank");
    	else {
    		mJFrame.setName(name);
    		mJFrame.send(loginString);
    	}
    }
    
    private void registerClient() {
    	String name = inName.getText();
    	String password = inPassword.getText();
    	String loginString = "REGISTER" + " " + name + " " + password;
    	
    	if (name.isEmpty() || password.isEmpty() )
    		LogUtil.cout("FAILED - Name or Password cannot be blank");
    	else { 
    		mJFrame.setName(name);
    		mJFrame.send(loginString);
    	}
    }
    
    public void disableLogin() {
    	mJFrame.setName(inName.getText());
    	inName.setEditable(false);
    	inPassword.setEditable(false);
    }
}
