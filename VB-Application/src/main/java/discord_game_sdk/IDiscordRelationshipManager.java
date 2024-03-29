package discord_game_sdk;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:441</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class IDiscordRelationshipManager extends Structure {
	/** C type : filter_callback* */
	public IDiscordRelationshipManager.filter_callback filter;
	/** C type : count_callback* */
	public discord_game_sdk.IDiscordStorageManager.count_callback count;
	/** C type : get_callback* */
	public IDiscordRelationshipManager.get_callback get;
	/** C type : get_at_callback* */
	public IDiscordRelationshipManager.get_at_callback get_at;
	/** <i>native declaration : discord_game_sdk.h:442</i> */
	public interface filter_callback_filter_callback extends Callback {
		byte apply(Pointer filter_data, DiscordRelationship relationship);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface filter_callback extends Callback {
		void apply(IDiscordRelationshipManager manager, Pointer filter_data, IDiscordRelationshipManager.filter_callback_filter_callback filter);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface count_callback extends Callback {
		int apply(IDiscordRelationshipManager manager, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_callback extends Callback {
		int apply(IDiscordRelationshipManager manager, long user_id, DiscordRelationship relationship);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_at_callback extends Callback {
		int apply(IDiscordRelationshipManager manager, int index, DiscordRelationship relationship);
	};
	public IDiscordRelationshipManager() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("filter", "count", "get", "get_at");
	}
	/**
	 * @param filter C type : filter_callback*<br>
	 * @param count C type : count_callback*<br>
	 * @param get C type : get_callback*<br>
	 * @param get_at C type : get_at_callback*
	 */
	public IDiscordRelationshipManager(IDiscordRelationshipManager.filter_callback filter, discord_game_sdk.IDiscordStorageManager.count_callback count, IDiscordRelationshipManager.get_callback get, IDiscordRelationshipManager.get_at_callback get_at) {
		super();
		this.filter = filter;
		this.count = count;
		this.get = get;
		this.get_at = get_at;
	}
	public IDiscordRelationshipManager(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends IDiscordRelationshipManager implements Structure.ByReference {
		
	};
	public static class ByValue extends IDiscordRelationshipManager implements Structure.ByValue {
		
	};
}
