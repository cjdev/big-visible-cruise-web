package com.cj.bigVisibleCruiseWeb.service;

import java.util.concurrent.TimeoutException;

public abstract class FunctionCallWithTimeout {
	private int timeout=-1;
	public FunctionCallWithTimeout(int timeoutInSeconds) throws TimeoutException{
		int secondCounter = 0;
		FunctionThread functionThread = new FunctionThread(this);
		functionThread.start();
		while(!functionThread.isComplete()&&secondCounter<timeoutInSeconds){
			try{
				secondCounter++;
				Thread.sleep(1000);
			}catch(InterruptedException e){
				throw new RuntimeException(e);
			}
		}
		if (!functionThread.isComplete()){
			throw new TimeoutException("FunctionCallWithTimeout Timeout Reached");
		}
		
	}
	protected abstract void function();
}

class FunctionThread extends Thread{
	private FunctionCallWithTimeout functionObject;
	public FunctionThread(FunctionCallWithTimeout functionObject){
		this.functionObject = functionObject;
	}
	public boolean isComplete(){return isComplete;}
	private boolean isComplete=false;
	@Override public void run() {
		isComplete=false;
		functionObject.function();
		isComplete=true;
	}
}
