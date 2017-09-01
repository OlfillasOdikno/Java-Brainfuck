package de.olfillasodikno.bfinterpreter;

public class BFInterpreter {
	
	public static void main(String[] args) throws Exception {
		if(args.length == 2){
			int size = Integer.valueOf(args[0]);
			String code = args[1];
			Core core = new Core(size);
			core.setCode(code);
			core.run();
		}else {
			System.out.println("Syntax: <size> <code>");
		}
	}
	
}
