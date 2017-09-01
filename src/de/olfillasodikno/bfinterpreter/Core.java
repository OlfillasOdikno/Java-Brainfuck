package de.olfillasodikno.bfinterpreter;

import java.io.IOException;

public class Core {

	private char[] code;

	private int codePtr;

	private final char[] registers;

	private int ptr;

	private final static char[] cmds = new char[] {'<','>','+','-','.',',','[',']'};

	public Core(int size) {
		registers = new char[size];
	}

	public void setCode(String code) {
		this.code = code.toCharArray();
		this.codePtr = 0;
	}

	public void parse() throws Exception {
		char c = code[codePtr];
		if (c == cmds[0]) {
			ptr--;
			if (ptr < 0) {
				throw new Exception("Pointer [" + ptr + "] cant be negative.");
			}
		} else if (c == cmds[1]) {
			ptr++;
			if (ptr > registers.length - 1) {
				throw new Exception("Pointer [" + ptr + "] exceeds Register size [" + registers.length + "]");
			}
		} else if (c == cmds[2]) {
			registers[ptr]++;
		} else if (c == cmds[3]) {
			registers[ptr]--;
		} else if (c == cmds[4]) {
			System.out.print((char) registers[ptr]);
		} else if (c == cmds[5]) {
			try {
				registers[ptr] = (char) System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (c == cmds[6]) {
			if (registers[ptr] == 0) {
				int i = 1;
				while (i != 0) {
					codePtr++;
					if (code[codePtr] == '[') {
						i++;
					} else if (code[codePtr] == ']') {
						i--;
					}
				}
			}
		} else if (c == cmds[7]) {
			if (registers[ptr] != 0) {
				int i = 1;
				while (i != 0) {
					codePtr--;
					if (code[codePtr] == ']') {
						i++;
					} else if (code[codePtr] == '[') {
						i--;
					}
				}
			}
		}
	}

	public void run() throws Exception {
		while (codePtr < code.length) {
			parse();
			codePtr++;
		}
	}

}
