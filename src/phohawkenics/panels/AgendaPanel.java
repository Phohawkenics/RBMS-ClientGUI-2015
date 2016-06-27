package phohawkenics.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import phohawkenics.common.Constants;
import phohawkenics.common.MeetingConstants;
import temp.ClientGUI;

public class AgendaPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientGUI mJFrame;
	
	private JButton btnAccept;
	private JButton btnReject;
	private JButton btnWithdraw;
	private JButton btnAdd;
	private JButton btnRefresh;
	private JButton btnCancel;
	
	private JList<String> mAgendaList;
	
	private JTextArea mEntryDetails;
	private JScrollPane scrollPane;
	
	private boolean mIsRequester = false;
	private String mMeetingStatus = Constants.KEY_EMPTY;
    
    public AgendaPanel (ClientGUI jFrame) {
        super(new GridBagLayout());
        mJFrame = jFrame;
        instantiateComponents();
        addComponents();
    }
    
    /**
     * 
     */
    private void instantiateComponents() {
    	btnAccept = new JButton("Accept");
    	btnAccept.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
            	sendAcceptance();            	
            } 
        });
    	btnReject = new JButton("Reject");
    	btnReject.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
            	sendReject();
            }
        });
    	btnWithdraw = new JButton("Withdraw");
    	btnWithdraw.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
            	sendWithdrawal();
            } 
        });
    	btnAdd = new JButton("Add");
    	btnAdd.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
            	sendAdd();
            } 
        });
    	
    	btnCancel = new JButton("Cancel");
    	btnCancel.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
            	sendCancel();
            } 
        });
    	
    	btnRefresh = new JButton("Refresh");
    	btnRefresh.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
            	Refresh();
            } 
        }); 
    	
    	btnAccept.setEnabled(false);
		btnReject.setEnabled(false);
		btnWithdraw.setEnabled(false);
		btnAdd.setEnabled(false);
		btnCancel.setEnabled(false);
    	
    	mAgendaList = new JList<String>(); //data has type Object[]
    	//mList.setSize(250, 80);
    	mAgendaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	mAgendaList.setLayoutOrientation(JList.VERTICAL);
    	mAgendaList.setVisibleRowCount(-1);
    	JScrollPane listScroller = new JScrollPane(mAgendaList);
    	listScroller.setPreferredSize(new Dimension(60, 100));
    	listScroller.setSize(new Dimension(60, 100));
    	
    	ListSelectionListener listSelectionListener = new ListSelectionListener() {
    	      public void valueChanged(ListSelectionEvent listSelectionEvent) {
    	    	  requestEntryDetails();
    	    	  setButtonStates();
    	      }
    	    };
    	mAgendaList.addListSelectionListener(listSelectionListener);
    	
    	mEntryDetails = new JTextArea(5, 20);
    	mEntryDetails.setEditable(false);
        scrollPane = new JScrollPane(mEntryDetails);
    }
    
    private void addComponents() {
    	GridBagConstraints c = new GridBagConstraints();
    	
    	// Row 1
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridwidth = 1;
    	c.weightx = 1.0/5;
    	add(btnAccept, c);
    	add(btnReject, c);
    	add(btnWithdraw, c);
    	add(btnAdd,c);
    	c.gridwidth = GridBagConstraints.REMAINDER;
    	add(btnRefresh,c);
    	
    	// Row 2
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridwidth = 1;
    	c.weightx = 1.0/5;
    	c.gridwidth = GridBagConstraints.REMAINDER;
    	add(btnCancel, c);
    	
    	// Row 3
    	
    	c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(mAgendaList, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    private String formatAgendaEntryString(String agendaEntry) {
    	String formattedString = Constants.KEY_EMPTY;
    	String entryInfo[] = agendaEntry.split(Constants.SEPERATOR_INPUT);
    	formattedString += MeetingConstants.LBL_DATE + entryInfo[0] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_TIMERANGE + entryInfo[1] + Constants.SEPERATOR_TIME_RANGE + entryInfo[2] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_STATUS + entryInfo[3] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_MEETING_NUMBER + entryInfo[4] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_REQUEST_NUMBER + entryInfo[5];
    	
    	return formattedString;
    }
    
    private void sendAcceptance() {
    	String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    	mJFrame.send(MeetingConstants.MSG_TYPE_ACCEPT + Constants.SEPERATOR_INPUT + temp[temp.length - 4]);
    }
    
    private void sendReject() {
    	String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    	mJFrame.send(MeetingConstants.MSG_TYPE_REJECT + Constants.SEPERATOR_INPUT + temp[temp.length - 4]);
    }
    
    private void sendWithdrawal() {
    	String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    	mJFrame.send(
    			MeetingConstants.MSG_TYPE_WITHDRAW + Constants.SEPERATOR_INPUT 
    			+ mJFrame.getName() + Constants.SEPERATOR_INPUT
    			+ temp[temp.length - 1]
    			
    		);
    }
    
    private void sendAdd() {
    	String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    	mJFrame.send(
    			MeetingConstants.MSG_TYPE_ADD + Constants.SEPERATOR_INPUT 
    			+ mJFrame.getName() + Constants.SEPERATOR_INPUT
    			+ temp[temp.length - 1] 
    		);
    			 
    }
    
    private void sendCancel() {
    	String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    	mJFrame.send(
    			MeetingConstants.MSG_TYPE_CANCEL + Constants.SEPERATOR_INPUT
    			+ temp[temp.length - 1] 
    		);
    			 
    }
    
    private void requestEntryDetails() {
    	if(mAgendaList.getSelectedValue() != null) {
    		String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    		mJFrame.send(MeetingConstants.MSG_TYPE_MEETING_DETAILS + Constants.SEPERATOR_INPUT + temp[temp.length - 1]);
    	}
    }
    
    private void Refresh() {
    	mAgendaList.clearSelection();
    	mEntryDetails.setText("");
    }
    
    private String formatEntryDetailsString(String entryDetails) {
    	String formattedString = Constants.KEY_EMPTY;
    	String entryDetailsInfo[] = entryDetails.split(Constants.SEPERATOR_LINE);
    	String statusString = MeetingConstants.LBL_STATUS + entryDetailsInfo[0];
    	
    	mMeetingStatus = entryDetailsInfo[0];
    	
    	String requestInfo[] = entryDetailsInfo[1].split(Constants.SEPERATOR_DATA_HANDLER);
    	String inviteInfo[] = entryDetailsInfo[2].split(Constants.SEPERATOR_DATA_HANDLER);
    	String attendanceInfo[] = entryDetailsInfo[3].split(Constants.SEPERATOR_DATA_HANDLER);
    	
    	String line0 = Constants.KEY_EMPTY;
    	line0 += MeetingConstants.LBL_TOPIC + requestInfo[8];
    	String line1 = Constants.KEY_EMPTY;
    	line1 += MeetingConstants.LBL_REQUEST_NUMBER + requestInfo[2] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_MEETING_NUMBER + inviteInfo[1];
    	String line2 = Constants.KEY_EMPTY;
    	line2 += MeetingConstants.LBL_DATE + inviteInfo[2] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_TIMERANGE + inviteInfo[3] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_MIN_PARTICIPANTS + requestInfo[5];
    	String line3 = Constants.KEY_EMPTY;
    	line3 += MeetingConstants.LBL_REQUESTER + requestInfo[6] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_PARTICIPANTS + requestInfo[7];
    	String line4 = Constants.KEY_EMPTY;
    	line4 += MeetingConstants.LBL_TOTAL_PARTICIPANTS + attendanceInfo[3] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_TOTAL_ANSWERS + attendanceInfo[4] + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_TOTAL_CONFIRMED + attendanceInfo[5]  + Constants.SEPERATOR_INPUT
    			+ MeetingConstants.LBL_TOTAL_REJECT + attendanceInfo[6] ;
    	
    	formattedString += line0 + Constants.END_LINE
    			+ statusString + Constants.END_LINE
    			+ line1 + Constants.END_LINE
    			+ line2 + Constants.END_LINE
    			+ line3 + Constants.END_LINE
    			+ line4;
    	
    	String name = mJFrame.getName();
    	String requesterName =  requestInfo[6];
    	
    	if (name.equals(requesterName)) {
    		mIsRequester = true;
    		String line5 = "Participants Status: " + attendanceInfo[8];
    		formattedString += Constants.END_LINE + line5;
    	}
    	
    	
    	return formattedString;
    }
    
    private void setButtonStates () {
    	int clientStatusPos = 7;
    	if (mAgendaList.getSelectedValue() != null) {
    		String temp[] = mAgendaList.getSelectedValue().split(Constants.SEPERATOR_INPUT);
    		
	    		if (!mMeetingStatus.equals(MeetingConstants.STATUS_CANCELLED)
	    				&& !mMeetingStatus.equals(MeetingConstants.STATUS_CANCELLED2)) {
		    		if (temp.length > clientStatusPos) {
				    	if (temp[temp.length - clientStatusPos].equals(MeetingConstants.ATTENDANCE_CONFIRM)) {
				    		btnAccept.setEnabled(false);
				    		btnReject.setEnabled(false);
				    		btnWithdraw.setEnabled(true);
				    		btnAdd.setEnabled(false);
				    	} else if (temp[temp.length - clientStatusPos].equals(MeetingConstants.ATTENDANCE_REJECTED)) {
				    		btnAccept.setEnabled(false);
				    		btnReject.setEnabled(false);
				    		btnWithdraw.setEnabled(false);
				    		btnAdd.setEnabled(true);
				    	} else if (temp[temp.length - clientStatusPos].equals(MeetingConstants.ATTENDANCE_WITHDRAWED)) {
				    		btnAccept.setEnabled(false);
				    		btnReject.setEnabled(false);
				    		btnWithdraw.setEnabled(false);
				    		btnAdd.setEnabled(true);
				    	} else if (temp[temp.length - clientStatusPos].equals(MeetingConstants.ATTENDANCE_ADDED)) {
				    		btnAccept.setEnabled(false);
				    		btnReject.setEnabled(false);
				    		btnWithdraw.setEnabled(true);
				    		btnAdd.setEnabled(false);
				    	} else if (temp[temp.length - clientStatusPos].equals(MeetingConstants.ATTENDANCE_NO_REPLY)) {
				    		btnAccept.setEnabled(true);
				    		btnReject.setEnabled(true);
				    		btnWithdraw.setEnabled(false);
				    		btnAdd.setEnabled(false);
				    	} else if (temp[temp.length - clientStatusPos].equals(MeetingConstants.ATTENDANCE_REQUESTER)) {
				    		btnAccept.setEnabled(false);
				    		btnReject.setEnabled(false);
				    		btnWithdraw.setEnabled(false);
				    		btnAdd.setEnabled(false);
				    	}
		    		}
		    		
	    		if (mIsRequester == true && mMeetingStatus.equals(MeetingConstants.STATUS_COMPLETED)) {
	    			btnCancel.setEnabled(true);
	    		} else {
	    			btnCancel.setEnabled(false);
	    		}
	    	} else {
	    		btnAccept.setEnabled(false);
	    		btnReject.setEnabled(false);
	    		btnWithdraw.setEnabled(false);
	    		btnAdd.setEnabled(false);
	    		btnCancel.setEnabled(false);
	    	}
    	} else {
    		btnAccept.setEnabled(false);
    		btnReject.setEnabled(false);
    		btnWithdraw.setEnabled(false);
    		btnAdd.setEnabled(false);
    		btnCancel.setEnabled(false);
    	}
    }
    
    public void setEntryDetails (String entryDetails) {
    	mEntryDetails.setText(formatEntryDetailsString(entryDetails));
    }
    
    public void setAgenda(String[] agenda) {
    	DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (String agendaEntry: agenda) {
            listModel.addElement(formatAgendaEntryString(agendaEntry));
        }
        mAgendaList.setModel(listModel);
        requestEntryDetails();
    }
}
