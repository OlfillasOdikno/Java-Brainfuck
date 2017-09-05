package de.olfillasodikno.bfinterpreter;

import java.io.IOException;

public class Core {

	private final static char[] CMDS = new char[] { '<', '>', '+', '-', '.', ',', '[', ']' };
	private final char[] registers;
	private final char bp;
	private char[] code;
	private int codePtr;
	private int ptr;
	private boolean shouldStop;

	public Core(int size, char bp) {
		registers = new char[size];
		this.bp = bp;
	}

	public void setCode(String code) {
		this.code = code.toCharArray();
		this.codePtr = 0;
	}

	public void parse() throws Exception {
		char c = code[codePtr];
		if (c == bp) {
			printDebug(registers, ptr, code, codePtr);
		} else if (c == CMDS[0]) {
			ptr--;
			if (ptr < 0)
				throw new Exception("Pointer [" + ptr + "] cant be negative.");
		} else if (c == CMDS[1]) {
			ptr++;
			if (ptr > registers.length - 1)
				throw new Exception("Pointer [" + ptr + "] exceeds register size [" + registers.length + "]");
		} else if (c == CMDS[2]) {
			registers[ptr]++;
		} else if (c == CMDS[3]) {
			registers[ptr]--;
		} else if (c == CMDS[4]) {
			System.out.print((char) registers[ptr]);
		} else if (c == CMDS[5]) {
			registers[ptr] = (char) System.in.read();
		} else if (c == CMDS[6]) {
			if (registers[ptr] == 0) {
				int i = 1;
				while (i != 0) {
					codePtr++;
					if (code[codePtr] == CMDS[6]) {
						i++;
					} else if (code[codePtr] == CMDS[7]) {
						i--;
					}
				}
			}
		} else if (c == CMDS[7]) {
			if (registers[ptr] != 0) {
				int i = 1;
				while (i != 0) {
					codePtr--;
					if (code[codePtr] == CMDS[7]) {
						i++;
					} else if (code[codePtr] == CMDS[6]) {
						i--;
					}
				}
			}
		}
	}

	public void run() throws Exception {
		while (!shouldStop && codePtr < code.length) {
			parse();
			codePtr++;
		}
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldStop = shouldRun;
	}
	
	
	private static void printDebug(char[] registers, int ptr, char[] code, int codePtr) throws IOException{
		System.out.println("\n[Debug] hit breakpoint code pointer: "+codePtr);
		System.out.println("[Debug] registers:");
		for(int i = 0; i < registers.length; i++) {
			System.out.println((i==ptr?"[*]":"   ")+"R"+i+": "+(int)registers[i] + (registers[i]>31 && registers[i] < 127 ? (" ("+registers[i]+")") :""));
		}
		System.out.println("------------");
		System.out.println("[Debug] code:");
		String s = "";
		for(int i = 0; i < codePtr; i++) {
			s = s+code[i];
		}
		s = s+" *"+code[codePtr]+"* ";
		for(int i = codePtr+1; i < code.length; i++) {
			s = s+code[i];
		}
		System.out.println(s);
		System.out.println("------------");
		System.out.println("[Debug] Press any key to continue...");
		System.in.read();
	}
}