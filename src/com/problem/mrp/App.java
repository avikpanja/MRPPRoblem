package com.problem.mrp;

import com.problem.mrp.processor.MRPReportProcessor;

public class App {
	
	public static void main(String[] args) {
		System.out.println("******* App.main() excution started *******");
		try {
			if(args.length<2 ) {
				throw new Exception("Invalid no of arguments");
			}
			/* arg[0] is input file name
			   arg[1] is target output file name 
			 */
			MRPReportProcessor processor = new MRPReportProcessor(args[0], args[1]);
			processor.process();
		} catch(Exception e) {
			System.out.println("Exception occurred in main()");
			e.printStackTrace();
		}
		
		System.out.println("******* App.main() excution ended *******");
	}
	
}
