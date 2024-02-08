package discord_game_sdk;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:350</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class DiscordSku extends Structure {
	/** C type : DiscordSnowflake */
	public long id;
	/**
	 * @see EDiscordSkuType<br>
	 * C type : EDiscordSkuType
	 */
	public int type;
	/** C type : char[256] */
	public byte[] name = new byte[256];
	/** C type : DiscordSkuPrice */
	public DiscordSkuPrice price;
	public DiscordSku() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("id", "type", "name", "price");
	}
	/**
	 * @param id C type : DiscordSnowflake<br>
	 * @param type @see EDiscordSkuType<br>
	 * C type : EDiscordSkuType<br>
	 * @param name C type : char[256]<br>
	 * @param price C type : DiscordSkuPrice
	 */
	public DiscordSku(long id, int type, byte name[], DiscordSkuPrice price) {
		super();
		this.id = id;
		this.type = type;
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
		this.price = price;
	}
	public DiscordSku(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DiscordSku implements Structure.ByReference {
		
	};
	public static class ByValue extends DiscordSku implements Structure.ByValue {
		
	};
}
