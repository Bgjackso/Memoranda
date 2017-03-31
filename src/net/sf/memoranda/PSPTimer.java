package net.sf.memoranda;

import javax.swing.JLabel;

import net.sf.memoranda.ui.PSPPanel;

//PSPTimer
//throw directly into net.sf.memoranda package
public class PSPTimer implements Runnable {

  private Thread runThread;
  private boolean running = false;
  private boolean paused = false;
  //private TimeFrame timeFrame;
  private long summedTime = 0;

  /*public Timer(TimeFrame timeFrame) {
      this.timeFrame = timeFrame;
  }*/

  /*
   * When thrown into memoranda, will be creating 
   * instance of JPanel instead of JFrame
   */
  JLabel time = new JLabel();
  public static void main(String[] args) {
      /*TimeFrame t = new TimeFrame();
    //frame creation - panel goes here  
      JFrame f = new JFrame("Timer");
      f.setSize(625,202);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setLocationRelativeTo(null);
      f.getContentPane().add(t);
      f.setVisible(true);*/
  }

  public void startTimer() {
      running = true;
      paused = false;
      //start thread
      runThread = new Thread(this);
      runThread.start();
      //update(0);
  }

  public void pauseTimer() {
      //pauses but is still able to restart
      paused = true;
  }

  public void resetTimer() { //can only be reset if timer is paused
      if (paused){
      	running = false;
          paused = false;
          summedTime = 0;
          //update(0);
      }
  }
  
  public void saveTime(){
  	//TODO
  }

  @Override
  public void run() {
      long startTime = System.currentTimeMillis();
      // keep showing the difference in time until we are either paused or not running anymore
      while(running && !paused) {
          //update(summedTime + (System.currentTimeMillis() - startTime)); //timer causing issues
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
