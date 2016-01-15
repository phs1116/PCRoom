package model;

import java.io.Serializable;
import java.util.Calendar;

import javax.swing.JLabel;

public class ClientUserState extends Thread{
	private String id = "";
	private String seatNum ="";
	private String time = "";
	private String credit = "";
	private Calendar start;
	private Calendar current;
	
	private JLabel creditlb;
	private JLabel timelb;
	
	private boolean run = true;

	private long diffrence;
	
	
	public void setCreditlb(JLabel creditlb) {
		this.creditlb = creditlb;
	}

	public void setTimelb(JLabel timelb) {
		this.timelb = timelb;
	}

	public String getUserId() {
		return id;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public String getTime() {
		return time;
	}

	public String getCredit() {
		return credit;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public static void main(String[] args){
		new ClientUserState("test", "10");
	}

	public ClientUserState(String id, String seatNum){
		this.id = id;
		this.seatNum = seatNum;
		start = Calendar.getInstance();
	}

	@Override
	public void run() {
		while (run) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			current = Calendar.getInstance();
			diffrence = (current.getTimeInMillis() - start.getTimeInMillis())/1000;
			int creditn = (int)diffrence*10;
			long min = diffrence/60;
			long sec = diffrence%60;
			time = min+"분 "+sec+"초";
			timelb.setText(time);
			credit = creditn * 10+"원 ";
		//	System.out.println(time);
		//	System.out.println(credit);
			creditlb.setText(credit);
		}
	}

}
