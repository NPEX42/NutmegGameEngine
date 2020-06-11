package nutmeg.core;
import java.io.FileNotFoundException;

import org.joml.*;
public class Nutmeg {
	private static boolean bDebugMode = false;
	private static boolean bClose = false;
	
	public static final int 
	NMGE_POSITION = 0,
	NMGE_TEXCOORD = 1,
	NMGE_COLOR = 2,
	NMGE_TEXID = 3,
	NMGE_VEC3 = 3,
	NMGE_VEC2 = 2,
	NMGE_VEC1 = 1;
	
	public static final Vector3f 
	NMGE_V3_RIGHT    = new Vector3f(1,0,0 ),
	NMGE_V3_UP       = new Vector3f(0,1,0 ),
	NMGE_V3_FORWARD  = new Vector3f(0,0,1 ),
	NMGE_V3_LEFT     = new Vector3f(-1,0,0),
	NMGE_V3_DOWN     = new Vector3f(0,-1,0),
	NMGE_V3_BACKWARD = new Vector3f(0,0,-1);
	
	public static void EnableDebugMode() {
		bDebugMode = true;
	}
	public static void DisableDebugMode() {
		bDebugMode = false;
	}
	
	public static boolean GetDebug() {
		return bDebugMode;
	}
	
	public static boolean GetClose() {
		return bClose;
	}
	public static void Close() {
		bClose = true;
	}
	
	public static String[] GetErrorLog() {
		return Logger.errorLog.toArray(new String[Logger.errorLog.size()]);
	}
	
	public static String[] GetOutputLog() {
		return Logger.outputLog.toArray(new String[Logger.outputLog.size()]);
	}
	
	public static String[] GetWarningLog() {
		return Logger.warnLog.toArray(new String[Logger.warnLog.size()]);
	}
	
	public static void ResetErrorLog  () { Logger.errorLog.clear();  }
	public static void ResetOutputLog () { Logger.outputLog.clear(); }
	public static void ResetWarningLog() { Logger.warnLog.clear();   }
	
}
