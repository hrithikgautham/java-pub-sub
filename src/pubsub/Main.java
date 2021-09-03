package pubsub;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		
		Message message = new Message();
		
		
		(new Thread(new Reader(message))).start();
		(new Thread(new Writer(message))).start();
	}
}

class Message {
	private String message;
	private boolean empty = true;
	
	public synchronized String read() {
		System.out.println("in read methid");
		while(empty) {
			System.out.println("in read while");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		empty = true;
		System.out.println("issueing notifyAll() by read()");
		notifyAll();
		return message;
	}
	
	public synchronized void write(String message) {
		System.out.println("in write methid");
		while(!empty) {
			System.out.println("in write while");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		empty = false;
		this.message = message;
		System.out.println("issueing notifyAll() by write()");
		notifyAll();
	}
}

class Writer implements Runnable {
	private Message message;
	
	Writer(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String arr[] = {"hrithik", "gautham", "tg", "finished"};
		
		for(String i: arr) {
			message.write(i);
		}
		
		System.out.println("All message are written!!!");
	}
}

class Reader implements Runnable {
	Message message;
	
	Reader(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(String i = message.read(); !i.equals("finished"); i = message.read()) {
			System.out.println("message: " + i);
		}
		
		System.out.println("all message are read!!!");
	}
	
}