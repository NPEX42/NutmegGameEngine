package nutmeg.cl.core;

import org.lwjgl.opencl.CL;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opencl.CL10.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memUTF8;
 
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCapabilities;
import org.lwjgl.opencl.CLContextCallback;
import org.lwjgl.opencl.CLProgramCallback;

import static org.lwjgl.opencl.CL22.*;
@SuppressWarnings("unused")
public class OpenCL {
	
	  private static CLContextCallback clContextCB;
	  private static long clContext;
	  private static IntBuffer errcode_ret;
	  private static long clKernel;
	  private static long clDevice;
	  private static CLCapabilities deviceCaps;
	  private static long clQueue;
	  private static long clPlatform;
	  private static CLCapabilities clPlatformCapabilities;
	  private static final int size = 100;

	public static void Init() {
	    IntBuffer errcode_ret = BufferUtils.createIntBuffer(1);
	    // Create OpenCL
	    // CL.create();
	    // Get the first available platform
		try (MemoryStack stack = stackPush()) {
	      IntBuffer pi = stack.mallocInt(1);
	      clGetPlatformIDs(null, pi);
	      if (pi.get(0) == 0) {
	        throw new IllegalStateException("No OpenCL platforms found.");
	      }
	 
	      PointerBuffer platformIDs = stack.mallocPointer(pi.get(0));
	      clGetPlatformIDs(platformIDs, (IntBuffer) null);
	 
	      for (int i = 0; i < platformIDs.capacity() && i == 0; i++) {
	        long platform = platformIDs.get(i);
	        clPlatformCapabilities = CL.createPlatformCapabilities(platform);
	        clPlatform = platform;
	      }
	    }
	 
	 
	   clDevice = getDevice(clPlatform, clPlatformCapabilities, CL_DEVICE_TYPE_GPU);
	 
	    // Create the context
	    PointerBuffer ctxProps = BufferUtils.createPointerBuffer(7);
	    ctxProps.put(CL_CONTEXT_PLATFORM).put(clPlatform).put(NULL).flip();
	 
	    clContext = clCreateContext(ctxProps,
	        clDevice, clContextCB = CLContextCallback.create((errinfo, private_info, cb,
	            user_data) -> System.out.printf("cl_context_callback\n\tInfo: %s", memUTF8(errinfo))),
	        NULL, errcode_ret);
	 
	    // create command queue
	    clQueue = clCreateCommandQueue(clContext, clDevice, NULL, errcode_ret);
	}
	
	private static long getDevice(long platform, CLCapabilities platformCaps, int deviceType) {
	    try (MemoryStack stack = stackPush()) {
	      IntBuffer pi = stack.mallocInt(1);
	      clGetDeviceIDs(platform, deviceType, null, pi);
	 
	      PointerBuffer devices = stack.mallocPointer(pi.get(0));
	      clGetDeviceIDs(platform, deviceType, devices, (IntBuffer) null);
	 
	      for (int i = 0; i < devices.capacity(); i++) {
	        long device = devices.get(i);
	 
	        CLCapabilities caps = CL.createDeviceCapabilities(device, platformCaps);
	        if (!(caps.cl_khr_gl_sharing || caps.cl_APPLE_gl_sharing)) {
	          continue;
	        }
	 
	        return device;
	      }
	    }
	 
	    return NULL;
	  }
	
	public static long GetContext() {return clContext;}
	
	public static long GetDeviceID() {return clDevice;}
}
