package discord_game_sdk;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:339</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class DiscordEntitlement extends Structure {
	/** C type : DiscordSnowflake */
	public long id;
	/**
	 * @see EDiscordEntitlementType<br>
	 * C type : EDiscordEntitlementType
	 */
	public int type;
	/** C type : DiscordSnowflake */
	public long sku_id;
	public DiscordEntitlement() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("id", "type", "sku_id");
	}
	/**
	 * @param id C type : DiscordSnowflake<br>
	 * @param type @see EDiscordEntitlementType<br>
	 * C type : EDiscordEntitlementType<br>
	 * @param sku_id C type : DiscordSnowflake
	 */
	public DiscordEntitlement(long id, int type, long sku_id) {
		super();
		this.id = id;
		this.type = type;
		this.sku_id = sku_id;
	}
	public DiscordEntitlement(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends DiscordEntitlement implements Structure.ByReference {
		
	};
	public static class ByValue extends DiscordEntitlement implements Structure.ByValue {
		
	};
}