package nutmeg.core;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

public class Logger {
	protected static ArrayList<String> errorLog = new ArrayList<String>(), outputLog = new ArrayList<String>(), warnLog = new ArrayList<String>();
	public static void Log(String _sLib, String _sClass, String _sMsg) {
		Date d = new Date(System.currentTimeMillis());
		String time = "["+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"]";
		outputLog.add(time+"["+_sLib+"/"+_sClass+"]: "+_sMsg);
	}
	
	public static void Warn(String _sLib, String _sClass, String _sMsg) {
		Date d = new Date(System.currentTimeMillis());
		String time = "["+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"]";
		warnLog.add(time+"["+_sLib+"/"+_sClass+"]: "+_sMsg);
	}
	
	public static void Error(String _sLib, String _sClass, String _sMsg) {
		Date d = new Date(System.currentTimeMillis());
		String time = "["+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()+"]";
		errorLog.add(time+"["+_sLib+"/"+_sClass+"]: "+_sMsg);
	}
	
	public static void Debug(String _sLib, String _sClass, String _sMsg) {
		if(Nutmeg.GetDebug()) Warn(_sLib, _sClass, _sMsg);
	}
	
	public static void Assert(String _sLib, String _sClass, String _sMsg, boolean _bCondition) {
		if(!_bCondition) { System.err.println("["+_sLib+"/"+_sClass+"]: "+_sMsg); Nutmeg.Close(); }
	}
	
	public static void Throw(String _sLib, String _sClass, String _sMsg, Exception _exException) {
		errorLog.add("["+_sLib+"/"+_sClass+"]: "+_sMsg);
		errorLog.add("==================================");
		errorLog.add(_exException.getMessage());
		errorLog.add("==================================");
		System.exit(0x10);
	}
	
}
