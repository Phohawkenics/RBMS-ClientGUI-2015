package phohawkenics.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import phohawkenics.custom.CustomOutputStream;

public class LogPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea outCommand;
	private JScrollPane scrollPane;
	
    
    public LogPanel () {
        super(new GridBagLayout());
        instantiateComponents();
        addComponents();
        setStreams();
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
    
    public void setStreams() {
    	// Set output Stream
        PrintStream printStream = new PrintStream(new CustomOutputStream(outCommand));
        System.setOut(printStream);
        System.setErr(printStream);
    }
 
    public void actionPerformed(ActionEvent evt) {
        outCommand.setCaretPosition(outCommand.getDocument().getLength());
    }
}
