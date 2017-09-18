package com.cj.bigVisibleCruiseWeb.service;

import java.util.concurrent.TimeoutException;

import junit.framework.TestCase;

import org.junit.Test;


public class FunctionCallWithTimeoutTest extends TestCase  {
	public boolean passed = false;
	@Override
	protected void setUp() throws Exception {
		passed=false;
	};
	
	
	@Test
	public void testFunctionCallWithTimeoutPassing() throws Exception{
		new FunctionCallWithTimeout(10){
			protected void function(){
				passed=true;
			}
		};
		assertTrue("The 'passed' Variable Should Have Been Altered By The Function Call",passed);
	}
	
	@Test
	public void testFunctionCallWithTimeoutNotPassing(){
		boolean exceptionThrown = false;
		try{
			new FunctionCallWithTimeout(1){
				protected void function(){
					try{
						Thread.sleep(5000);
						passed=true;
					}catch (Exception e){}
					
				}
			};
			assertFalse("The 'passed' Variable Should Not Have Been Altered By The Function Call",passed);
		}catch (TimeoutException e){
			exceptionThrown=true;
		}
		assertTrue("A TimeoutException should have been thrown. ", exceptionThrown);
		
	}
}
