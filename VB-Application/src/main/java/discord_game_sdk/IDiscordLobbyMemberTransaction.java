package discord_game_sdk;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:378</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class IDiscordLobbyMemberTransaction extends Structure {
	/** C type : set_metadata_callback* */
	public IDiscordLobbyMemberTransaction.set_metadata_callback set_metadata;
	/** C type : delete_metadata_callback* */
	public IDiscordLobbyMemberTransaction.delete_metadata_callback delete_metadata;
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface set_metadata_callback extends Callback {
		int apply(IDiscordLobbyMemberTransaction lobby_member_transaction, Pointer key, Pointer value);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface delete_metadata_callback extends Callback {
		int apply(IDiscordLobbyMemberTransaction lobby_member_transaction, Pointer key);
	};
	public IDiscordLobbyMemberTransaction() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("set_metadata", "delete_metadata");
	}
	/**
	 * @param set_metadata C type : set_metadata_callback*<br>
	 * @param delete_metadata C type : delete_metadata_callback*
	 */
	public IDiscordLobbyMemberTransaction(IDiscordLobbyMemberTransaction.set_metadata_callback set_metadata, IDiscordLobbyMemberTransaction.delete_metadata_callback delete_metadata) {
		super();
		this.set_metadata = set_metadata;
		this.delete_metadata = delete_metadata;
	}
	public IDiscordLobbyMemberTransaction(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends IDiscordLobbyMemberTransaction implements Structure.ByReference {
		
	};
	public static class ByValue extends IDiscordLobbyMemberTransaction implements Structure.ByValue {
		
	};
}
