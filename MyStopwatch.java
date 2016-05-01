package com.mylab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class MyStopwatch implements Runnable {
	static long startTime;
	static long lapse;
	static boolean keepRunning = false;
	static char opt;

	public void run() {
		while (true) {			
			while (keepRunning) {
				long milliseconds =  (new Date().getTime() - startTime)+lapse;
				int seconds = (int) (milliseconds / 1000) % 60 ;
				int minutes = (int) ((milliseconds / (1000*60)) % 60);
				int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
				String timer = hours+":"+minutes+":"+seconds+":"+(milliseconds%1000);
				System.out.println(timer);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Someone distrubed my sleep!"+e.getMessage());
				}
			}			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Someone distrubed my sleep!"+e.getMessage());
			}			
		}
	}

	public static void main(String[] args) {
		try {
			MyStopwatch myWatch = new MyStopwatch();
			Thread thread = new Thread(myWatch);
			thread.start();

			//Main thread to wait for user inputs. We can enter options while timer is running.
			InputStreamReader r=new InputStreamReader(System.in);  
			BufferedReader br=new BufferedReader(r);
			System.out.println("Stop Watch \nOptions: \n1. Start \n2. Pause \n3. Reset\n4. Terminate the program");
			System.out.println("Please enter the option seriel number");

			while (true){
				opt = br.readLine().charAt(0);
				switch (opt ) {
				case '1':
					//start timer only when it is not running.
					if(keepRunning != true) {
						System.out.println("Stop Watch started!");
						keepRunning = true;
						start();
					}else{
						System.out.println("Watch is running already! Enter valid input");
					}
					break;
				case '2':
					//pause only when it is running
					if(keepRunning != false) {
						System.out.println("Watch Paused!");
						keepRunning = false;
						pause();
					}else{
						System.out.println("Watch paused already!");
					}
					break;
				case '3':
					System.out.println("Watch cleared");
					keepRunning = false;
					reset();
					break;
				case '4':
					System.out.println("Thanks for your time!!");
					System.exit(0);
				default:
					System.out.println("Invalid input! Timer accepts input ranging from 1 to 4.");
					break;
				}
			}
		}catch(Exception e){
			System.out.println("Oops! Something went wrong!"+e.getMessage());
		}
	}
	//stopwatch start method
	static void start(){
		startTime = new Date().getTime();	
	}
	//pause method
	static void pause(){
		lapse += (new Date().getTime() -startTime);
	}
	//reset method
	static void reset(){
		lapse=startTime=0;
	}

}
