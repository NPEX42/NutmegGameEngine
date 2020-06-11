package nutmeg.imgui.core;

import java.awt.Color;

import imgui.ImGui;
import imgui.ImGuiIO;
import nutmeg.core.datatypes.FloatPtr;
import nutmeg.core.datatypes.IntPtr;
import nutmeg.glfw.core.Window;
/**
 * @author George Venn
 * @version 1.0.0
 */
public class UI {
	/**
	 * 
	 * @param name - The Title of the UI Group
	 * {@summary Starts a new UI Group, MUST BE PAIRED WITH UI.End()	}
	 */
	public static void Begin(String name) {
		ImGui.begin(name);
	} 
	
	public static void End() {
		ImGui.end();
	}
	
	public static void Render() {
		ImGui.render();
	}
	
	public static void NewFrame() {
		ImGui.newFrame();
	}
	
	public static void Text(String text) {
		ImGui.text(text);
	}
	
	public static void TextColored(Color color, String text) {
		ImGui.textColored(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), text);
	}
	
	public static void SliderFloat1(String text, FloatPtr xAxis, float min, float max) {
		ImGui.sliderFloat(text, xAxis.GetRef(), min, max);
	}
	
	public static void SliderInt1(String text, IntPtr xAxis, int min, int max) {
		ImGui.sliderInt(text, xAxis.GetRef(), min, max);
	}
	
	public static boolean Button(String text) {
		return ImGui.button(text);
	}
	
	public static void Update(Window windowe) {
		ImGuiIO io = ImGui.getIO();
		//io.setDisplaySize(window.getfWidth(), window.getfWidth());

        io.setDeltaTime(1 / 60f);
	}
}
