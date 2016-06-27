package phohawkenics.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import phohawkenics.common.Constants;
import phohawkenics.utils.LogUtil;

public class MessagesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea outCommand;
	private JScrollPane scrollPane;
	
    
    public MessagesPanel () {
        super(new GridBagLayout());
        instantiateComponents();
        addComponents();
    }
    
    public void instantiateComponents() {
        outCommand = new JTextArea(5, 20);        
        outCommand.setEditable(false);
        scrollPane = new JScrollPane(outCommand);
    }
    
    public void addComponents() {
    	//Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        
        // Row 1
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    public void showMessage(String message) {
    	outCommand.setText(
    			outCommand.getText() + Constants.END_LINE
    			+ LogUtil.geCurrentTime() + Constants.SEPERATOR_INPUT + message);
    }
    
    public synchronized void showMessageBank(String[] messages) {
    	showMessage("Messages you received while offline"
    			+ Constants.END_LINE + "////////////START///////////");
    	for (String message: messages) {
    		showMessage("     " + message);
    	}
    	
    	showMessage("/////////////END////////////");
    }
}
