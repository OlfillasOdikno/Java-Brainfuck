package de.olfillasodikno.bfinterpreter;

public class BFInterpreter {
	
	public static void main(String[] args) throws Exception {
		if (args.length == 3) {
			int size = Integer.valueOf(args[0]);
			String code = args[1];
			Core core = new Core(size, args[2].toCharArray()[0]);
			core.setCode(code);
			core.run();
		} else {
			System.out.println("Syntax: <size> <code> <break point char>");
		}
	}
}