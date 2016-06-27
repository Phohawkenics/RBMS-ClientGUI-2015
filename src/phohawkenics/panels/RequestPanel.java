package phohawkenics.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import phohawkenics.common.Constants;
import phohawkenics.common.MeetingConstants;
import phohawkenics.utils.LogUtil;
import phohawkenics.validator.RequestValidator;
import temp.ClientGUI;

public class RequestPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientGUI mJFrame;
	private JLabel lblRequest;
	private JLabel lblDate;
	private JLabel lblDateYear;
	private JLabel lblDateMonth;
	private JLabel lblDateDay;
	private JLabel lblTimeStart;
	private JLabel lblTimeEnd;
	private JLabel lblMinParticipants;
	private JLabel lblParticipants;
	private JLabel lblTopic;
	private JLabel lblRegisteredParticipants;
	private JLabel lblRooms;
	
	private JTextField inDateYear;
	private JTextField inDateMonth;
	private JTextField inDateDay;
	private JTextField inTimeStartHour;
	private JTextField inTimeStartMin;
	private JTextField inTimeEndHour;
	private JTextField inTimeEndMin;
	private JTextField inMinParticipants;
	private JTextField inParticipants;
	private JTextField inTopic;
	
	private JButton outSendRequest;
	private JTextArea outCommand;
	private JScrollPane scrollPane;
	
	private JList<String> mParticipantsList;
	private JList<String> mRoomList;
	
	private RequestValidator rValidator = new RequestValidator();
	
    
    public RequestPanel (ClientGUI jFrame) {
        super(new GridBagLayout());
        mJFrame = jFrame;
        instantiateComponents();
        addComponents();
    }
    
    /**
     * 
     */
    private void instantiateComponents() {    	
    	lblRequest= new JLabel("Request a meeting");
    	lblDate= new JLabel("Date: ");
    	lblDateYear= new JLabel("YYYY");
    	lblDateMonth= new JLabel("MM");
    	lblDateDay= new JLabel("DD");
    	lblTimeStart= new JLabel("Start Time: ");
    	lblTimeEnd= new JLabel("End Time: ");
    	lblMinParticipants= new JLabel("Minimum Participants: ");
    	lblParticipants= new JLabel("Participants");;
    	lblTopic= new JLabel("Topic: ");
    	lblRegisteredParticipants = new JLabel("Registered Participants: ");
    	lblRooms = new JLabel("Rooms: ");
    	
    	inDateYear = new JTextField(4);    	
    	inDateMonth = new JTextField(2);
    	inDateDay = new JTextField(2);
    	inTimeStartHour = new JTextField(2);
    	inTimeStartMin = new JTextField(2);
    	inTimeEndHour = new JTextField(2);
    	inTimeEndMin = new JTextField(2);
    	inMinParticipants = new JTextField(3);
    	inParticipants = new JTextField(30);
    	inTopic = new JTextField(30);
    	
    	outSendRequest = new JButton("Send");
    	outSendRequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	outCommand.setText("");
                sendRequest();
            } 
        });
    	
    	outCommand = new JTextArea(5, 20);
    	
    	outCommand.setEditable(false);
        scrollPane = new JScrollPane(outCommand);
        
        
        
        mParticipantsList = new JList<String>(); //data has type Object[]
    	//mList.setSize(250, 80);
    	mParticipantsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	mParticipantsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    	mParticipantsList.setVisibleRowCount(-1);
    	JScrollPane pListScroller = new JScrollPane(mParticipantsList);
    	pListScroller.setPreferredSize(new Dimension(60, 100));
    	pListScroller.setSize(new Dimension(60, 100));
    	
    	ListSelectionListener pListSelectionListener = new ListSelectionListener() {
    	      public void valueChanged(ListSelectionEvent listSelectionEvent) {

    	      }
    	    };
    	mParticipantsList.addListSelectionListener(pListSelectionListener);
    	
    	mRoomList = new JList<String>(); //data has type Object[]
    	//mList.setSize(250, 80);
    	mRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	mRoomList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
    	mRoomList.setVisibleRowCount(-1);
    	JScrollPane listScroller = new JScrollPane(mRoomList);
    	listScroller.setPreferredSize(new Dimension(10, 50));
    	listScroller.setSize(new Dimension(10, 50));
    	
    	ListSelectionListener rListSelectionListener = new ListSelectionListener() {
    	      public void valueChanged(ListSelectionEvent listSelectionEvent) {
    	    	
    	      }
    	};
    	mRoomList.addListSelectionListener(rListSelectionListener);
    }
    
    private void addComponents() {
    	//Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        
        // Row 1
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(lblRequest, c);
        //Row 2
        c.gridwidth = 1;
        add(lblTopic, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(inTopic, c);
        // Row 3
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        // Date
        add(lblDate, c);
        add(lblDateYear, c);
        add(inDateYear, c);
        add(lblDateMonth, c);
        add(inDateMonth, c);
        add(lblDateDay, c);
        add(inDateDay,c);
        
        // Time Range
        JLabel lblTimeSeperator1 = new JLabel("h");
        JLabel lblTimeSeperator2 = new JLabel("h");
        
        add(lblTimeStart, c);
        add(inTimeStartHour, c);
        add(lblTimeSeperator1, c);
        add(inTimeStartMin, c);
        add(lblTimeEnd, c);
        add(inTimeEndHour, c);
        add(lblTimeSeperator2, c);
        add(inTimeEndMin, c);
        
        add(lblMinParticipants, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(inMinParticipants, c);
        
        // Row 4
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblParticipants, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(inParticipants, c);
        
        // Row 5
        
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblRooms, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(lblRegisteredParticipants, c);
        
        // row 6
        
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        add(mRoomList, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(mParticipantsList, c);
        
        //row 7
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(outSendRequest, c);
        
        // Row 8
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    private void sendRequest() {
    	String requestString = MeetingConstants.MSG_TYPE_REQUEST + Constants.SEPERATOR_INPUT;
    	
    	String dateYear = inDateYear.getText();
    	String dateMonth = inDateMonth.getText();
    	String dateDay = inDateDay.getText();
    	
    	String date = dateYear + Constants.SEPERATOR_DATE + dateMonth + Constants.SEPERATOR_DATE + dateDay;
    	
    	String timeStartHour = inTimeStartHour.getText();
    	String timeStartMin = inTimeStartMin.getText();
    	String timeEndHour = inTimeEndHour.getText();
    	String timeEndMin = inTimeEndMin.getText();
    	
    	String startTime = timeStartHour + Constants.SEPERATOR_TIME_H + timeStartMin;
    	String endTime =  timeEndHour + Constants.SEPERATOR_TIME_H + timeEndMin;
    	
    	String minParticipants = inMinParticipants.getText();
    	//String participants = inParticipants.getText();
    	
    	int selectedParticipants[] = mParticipantsList.getSelectedIndices();
    	ListModel<String> participantsList = mParticipantsList.getModel();
    	String participants = Constants.KEY_EMPTY;
    	
    	for(int i = 0; i < selectedParticipants.length; i++) {
    		participants += participantsList.getElementAt(selectedParticipants[i]);
    		if (i != selectedParticipants.length -1) {
    			participants += Constants.SEPERATOR_MULTIPLE;
    		}
    	}
    	
    	String topic = inTopic.getText();
    	
    	if (!mRoomList.isSelectionEmpty()) {
	    	String roomNumber = mRoomList.getSelectedValue();
	    	
	    	boolean flag = true;
	    	String flagMessage = Constants.KEY_EMPTY;
	    	
	    	if (rValidator.isNotValidTopic(topic)) {
	    		flag = false;
    			flagMessage += "Topic must not contain spaces or symbols" + Constants.END_LINE;
	    	}
	    	
	    	if (participants.isEmpty()) {
	    		flag = false;
    			flagMessage += "Please select a participant" + Constants.END_LINE;
	    	}
	    	
	    	if (!rValidator.isValidYear(dateYear) 
	    		|| !rValidator.isValidMonth(dateMonth)
	    		|| !rValidator.isValidDay(dateDay) 
	    		|| !rValidator.isValidHour(timeStartHour)
	    		|| !rValidator.isValidMin(timeStartMin) 
	    		|| !rValidator.isValidHour(timeEndHour)
	    		|| !rValidator.isValidMin(timeEndMin)) {
	    		flag = false;
	    		flagMessage += "Invalid Date and/or Time" + Constants.END_LINE;
	    	} else {
	    		if (rValidator.isDateBeforePresent(date, startTime)) {
	    			flag = false;
	    			flagMessage += "Cannot Schedule before the present date" + Constants.END_LINE;
	    		}
	    		if (!rValidator.startIsLessThanEnd(startTime, endTime)) {
	    			flag = false;
	    			flagMessage += "End Time cannot be before Start Time" + Constants.END_LINE;
	    		}
	    	}
	    	
	    	if (flag) {
		    	requestString += date + Constants.SEPERATOR_INPUT
		    			+ startTime + Constants.SEPERATOR_TIME_RANGE + endTime + Constants.SEPERATOR_INPUT
		    			+ minParticipants + Constants.SEPERATOR_INPUT
		    			+ participants + Constants.SEPERATOR_INPUT
		    			+ topic + Constants.SEPERATOR_INPUT
		    			+ roomNumber;
		    	
		    	mJFrame.send(requestString);
		    	showMessage("Request Successfully sent! \n" + requestString + "\n");
	    	} else {
	    		showMessage(flagMessage);
	    	}
    	} else {
    		showMessage("Please select a room number");
    	}
    }
    
    public void showMessage(String message) {
    	outCommand.setText(outCommand.getText() 
    			+ LogUtil.geCurrentTime() 
    			+ Constants.SEPERATOR_INPUT 
    			+ message );
    }
    
    public void setParticipants(String[] participants) {
    	DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (String participant: participants) {
            listModel.addElement(participant);
        }
        mParticipantsList.setModel(listModel);
    }
    
    public void setRooms(String[] rooms) {
    	DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (String room: rooms) {
            listModel.addElement(room);
        }
        mRoomList.setModel(listModel);
    }
}