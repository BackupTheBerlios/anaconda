package fr.umlv.anaconda.tools;

import java.io.*;

public class Right {
	int[] right = new int[9];

	public static native int getRight(String file_path, int right);

	static {
		System.loadLibrary("right");
	}

	public Right(File file) throws IOException {
		if (!file.exists())
			throw new IOException();

		for (int i = 0; i < 9; i++)
			right[i] = getRight(file.getPath(), i);
	}

	public int[] get(){
		return right;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();

		for (int i = 0; i < 9; i++)
			s.append(right[i]);
		return s.toString();
	}
}
