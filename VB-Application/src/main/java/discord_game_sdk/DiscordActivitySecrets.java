package discord_game_sdk;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:278</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class DiscordActivitySecrets extends Structure {
	/** C type : char[128] */
	public byte[] match = new byte[128];
	/** C type : char[128] */
	public byte[] join = new byte[128];
	/** C type : char[128] */
	public byte[] spectate = new byte[128];
	public DiscordActivitySecrets() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("match", "join", "spectate");
	}
	/**
	 * @param match C type : char[128]<br>
	 * @param join C type : char[128]<br>
	 * @param spectate C type : char[128]
	 */
	public DiscordActivitySecrets(byte match[], byte join[], byte spectate[]) {
		super();
		if ((match.length != this.match.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.match = match;
		if ((join.length != this.join.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.join = join;
		if ((spectate.length != this.spectate.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.spectate = spectate;
	}
	public DiscordActivitySecrets(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DiscordActivitySecrets implements Structure.ByReference {
		
	};
	public static class ByValue extends DiscordActivitySecrets implements Structure.ByValue {
		
	};
}
