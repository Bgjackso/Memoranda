package net.sf.memoranda;

import javax.swing.JLabel;

public class PSPTimer implements Runnable {
	private boolean running = false;
	private boolean paused = false;
	private long summedTime = 0;

	JLabel time = new JLabel();

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		while(running && !paused) {
			update(summedTime + (System.currentTimeMillis() - startTime)); //timer causing issues
		}
		if(paused){
			summedTime += System.currentTimeMillis() - startTime;
		}else{
			summedTime = 0;
		}
	}

	public void update(long dTime){
		time.setText(String.valueOf(String.valueOf((dTime/1000)/60) + ":" 
  			+ String.valueOf((dTime/1000)%60) + ":" + String.valueOf((dTime)%1000)));
	}
}
