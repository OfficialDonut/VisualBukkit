package discord_game_sdk;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:298</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class DiscordPresence extends Structure {
	/**
	 * @see EDiscordStatus<br>
	 * C type : EDiscordStatus
	 */
	public int status;
	/** C type : DiscordActivity */
	public DiscordActivity activity;
	public DiscordPresence() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("status", "activity");
	}
	/**
	 * @param status @see EDiscordStatus<br>
	 * C type : EDiscordStatus<br>
	 * @param activity C type : DiscordActivity
	 */
	public DiscordPresence(int status, DiscordActivity activity) {
		super();
		this.status = status;
		this.activity = activity;
	}
	public DiscordPresence(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DiscordPresence implements Structure.ByReference {
		
	};
	public static class ByValue extends DiscordPresence implements Structure.ByValue {
		
	};
}
