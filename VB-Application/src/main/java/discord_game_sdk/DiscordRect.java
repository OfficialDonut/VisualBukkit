package discord_game_sdk;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:326</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class DiscordRect extends Structure {
	public int left;
	public int top;
	public int right;
	public int bottom;
	public DiscordRect() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("left", "top", "right", "bottom");
	}
	public DiscordRect(int left, int top, int right, int bottom) {
		super();
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	public DiscordRect(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DiscordRect implements Structure.ByReference {
		
	};
	public static class ByValue extends DiscordRect implements Structure.ByValue {
		
	};
}