package discord_game_sdk;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:404</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class IDiscordUserManager extends Structure {
	/** C type : get_current_user_callback* */
	public IDiscordUserManager.get_current_user_callback get_current_user;
	/** C type : get_user_callback* */
	public IDiscordUserManager.get_user_callback get_user;
	/** C type : get_current_user_premium_type_callback* */
	public IDiscordUserManager.get_current_user_premium_type_callback get_current_user_premium_type;
	/** C type : current_user_has_flag_callback* */
	public IDiscordUserManager.current_user_has_flag_callback current_user_has_flag;
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_current_user_callback extends Callback {
		int apply(IDiscordUserManager manager, DiscordUser current_user);
	};
	/** <i>native declaration : discord_game_sdk.h:406</i> */
	public interface get_user_callback_callback_callback extends Callback {
		void apply(Pointer callback_data, int result, DiscordUser user);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_user_callback extends Callback {
		void apply(IDiscordUserManager manager, long user_id, Pointer callback_data, IDiscordUserManager.get_user_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_current_user_premium_type_callback extends Callback {
		int apply(IDiscordUserManager manager, IntByReference premium_type);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface current_user_has_flag_callback extends Callback {
		int apply(IDiscordUserManager manager, int flag, Pointer has_flag);
	};
	public IDiscordUserManager() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("get_current_user", "get_user", "get_current_user_premium_type", "current_user_has_flag");
	}
	/**
	 * @param get_current_user C type : get_current_user_callback*<br>
	 * @param get_user C type : get_user_callback*<br>
	 * @param get_current_user_premium_type C type : get_current_user_premium_type_callback*<br>
	 * @param current_user_has_flag C type : current_user_has_flag_callback*
	 */
	public IDiscordUserManager(IDiscordUserManager.get_current_user_callback get_current_user, IDiscordUserManager.get_user_callback get_user, IDiscordUserManager.get_current_user_premium_type_callback get_current_user_premium_type, IDiscordUserManager.current_user_has_flag_callback current_user_has_flag) {
		super();
		this.get_current_user = get_current_user;
		this.get_user = get_user;
		this.get_current_user_premium_type = get_current_user_premium_type;
		this.current_user_has_flag = current_user_has_flag;
	}
	public IDiscordUserManager(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends IDiscordUserManager implements Structure.ByReference {
		
	};
	public static class ByValue extends IDiscordUserManager implements Structure.ByValue {
		
	};
}
