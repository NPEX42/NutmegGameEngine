package nutmeg.cl.core;

import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.system.MemoryUtil;

import nutmeg.core.Logger;

import static org.lwjgl.opencl.CL22.*;
@SuppressWarnings("unused")
public class ComputeShader {
	private long kernelID, programID;
	private HashMap<String, Integer> memoryBuffers;
	
	public ComputeShader(long kernelID, long programID) {
		super();
		this.kernelID = kernelID;
		this.programID = programID;
	}

	public static ComputeShader Compile(String computeSource, String kernelName) {
		long id, prog;
		IntBuffer errorCode = MemoryUtil.memAllocInt(1);
		prog = clCreateProgramWithSource(OpenCL.GetContext(), computeSource, errorCode);
		if(errorCode.get(0) != CL_SUCCESS) {
			Logger.Throw("NMCL", "Compute Shader", "OpenCL Compile ERROR: "+errorCode.get(0), null);
		}
		
		clBuildProgram(prog, OpenCL.GetDeviceID(), "", null, 0);
		if(errorCode.get(0) != CL_SUCCESS) {
			Logger.Throw("NMCL", "Compute Shader", "OpenCL Build ERROR: "+errorCode.get(0), null);
		}
		
		id = clCreateKernel(prog, kernelName, errorCode);
		if(errorCode.get(0) != CL_SUCCESS) {
			Logger.Throw("NMCL", "Compute Shader", "OpenCL Kernel ERROR: "+errorCode.get(0), null);
		}
		
		return new ComputeShader(id, prog);
		
	}
	
	public void BindArgBuffer(String name, long dataPointer, int index) {
		clSetKernelArg(kernelID, index, dataPointer);
	}
	
}
