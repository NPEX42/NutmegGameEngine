package nutmeg.core;

import java.io.*;

public class IO {
	public static String loadString(String filePath) {
		try {
			return loadString(new FileInputStream(filePath), filePath);
		} catch(IOException ioex) {
			System.err.println(ioex.getMessage());
			return null;
		}
	}
	
	public static String[] loadStrings(String filePath) {
		try {
			return loadString(new FileInputStream(filePath), filePath).split("\n");
		} catch(IOException ioex) {
			System.err.println(ioex.getMessage());
			return null;
		}
	}
	
	public static String loadString(InputStream stream, String _sFileName) throws IOException {
		if(stream == null) Logger.Throw("NMC", "IO",  "Unable To Use Stream, Stream is Null!",new Exception());
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder buffer = new StringBuilder();
		while((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
		}
		reader.close();
		tp2 = System.currentTimeMillis();
		Logger.Log("NMC", "IO", "File '"+_sFileName+"' Loaded in "+(tp2 - tp1)+"ms");
		return buffer.toString();
	}	
	
	public static void SaveStrings(String filePath, String[] source) {
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			for(String line : source) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
		} catch(IOException ioex) {
			Logger.Throw("NMC", "IO", "Unable To Create / Write To File '"+filePath+"'", ioex);
		}
		tp2 = System.currentTimeMillis();
		System.err.println("[NMC/IO] File '"+filePath+"' Saved in "+(tp2 - tp1)+"ms");
	}
}
