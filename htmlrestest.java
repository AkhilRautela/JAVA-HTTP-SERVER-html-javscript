package html;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;
class createsock extends Thread{
	static Socket boss;
	static ServerSocket s;
	public void run() {
	try{
		 s = new ServerSocket(80);
		System.out.println("WAITING");
	    boss= s.accept();
	    System.out.println("CONNECTED");
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	}
}
class fetchdata extends Thread{
	static int c=0;
	public void run() {
		try {
			System.out.println("READING");
			InputStreamReader yo= new InputStreamReader(createsock.boss.getInputStream());
			BufferedReader br =new BufferedReader(yo);
			while(br.ready())
				{
				String s= br.readLine();
				System.out.println(s);
				
				
				if (s.contains("boss.js")) {
					c=1;
					System.out.println("sending script");
				}
				}	
				
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
class giveresponse extends Thread{
	public void run() {
		try {
			System.out.println("writing");
			OutputStreamWriter osw= new OutputStreamWriter(createsock.boss.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
		    if(fetchdata.c==1) {
		    	bw.write("YOUR JAVASCRIPT CODE"); //JAVASCRIPT CODE
		    }
		    else {
			bw.write("YOUR HTML CODE");//HTML CODE USE boss.js NAME FOR JAVASCRIPT FILE
		
		    }
			bw.flush();
			createsock.boss.close();
			createsock.s.close();
			fetchdata.c=0;
			Thread.sleep(1000);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
class htmlrestest {
	
	   public static void main(String args[]) {
		   try {for(;;) {
			   createsock s = new createsock();
			   s.start();
			   s.join();
			   fetchdata fd = new fetchdata();
			   fd.start();
			   fd.join();
			   giveresponse gr = new giveresponse();
			   gr.start();
			   gr.join();
		   }}
		   catch(Exception e) {
			   e.printStackTrace();
		   }
	   }
	


}
