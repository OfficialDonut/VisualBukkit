package discord_game_sdk;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:459</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class IDiscordLobbyManager extends Structure {
	/** C type : get_lobby_create_transaction_callback* */
	public IDiscordLobbyManager.get_lobby_create_transaction_callback get_lobby_create_transaction;
	/** C type : get_lobby_update_transaction_callback* */
	public IDiscordLobbyManager.get_lobby_update_transaction_callback get_lobby_update_transaction;
	/** C type : get_member_update_transaction_callback* */
	public IDiscordLobbyManager.get_member_update_transaction_callback get_member_update_transaction;
	/** C type : create_lobby_callback* */
	public IDiscordLobbyManager.create_lobby_callback create_lobby;
	/** C type : update_lobby_callback* */
	public IDiscordLobbyManager.update_lobby_callback update_lobby;
	/** C type : delete_lobby_callback* */
	public IDiscordLobbyManager.delete_lobby_callback delete_lobby;
	/** C type : connect_lobby_callback* */
	public IDiscordLobbyManager.connect_lobby_callback connect_lobby;
	/** C type : connect_lobby_with_activity_secret_callback* */
	public IDiscordLobbyManager.connect_lobby_with_activity_secret_callback connect_lobby_with_activity_secret;
	/** C type : disconnect_lobby_callback* */
	public IDiscordLobbyManager.disconnect_lobby_callback disconnect_lobby;
	/** C type : get_lobby_callback* */
	public IDiscordLobbyManager.get_lobby_callback get_lobby;
	/** C type : get_lobby_activity_secret_callback* */
	public IDiscordLobbyManager.get_lobby_activity_secret_callback get_lobby_activity_secret;
	/** C type : get_lobby_metadata_value_callback* */
	public IDiscordLobbyManager.get_lobby_metadata_value_callback get_lobby_metadata_value;
	/** C type : get_lobby_metadata_key_callback* */
	public IDiscordLobbyManager.get_lobby_metadata_key_callback get_lobby_metadata_key;
	/** C type : lobby_metadata_count_callback* */
	public IDiscordLobbyManager.lobby_metadata_count_callback lobby_metadata_count;
	/** C type : member_count_callback* */
	public IDiscordLobbyManager.member_count_callback member_count;
	/** C type : get_member_user_id_callback* */
	public IDiscordLobbyManager.get_member_user_id_callback get_member_user_id;
	/** C type : get_member_user_callback* */
	public IDiscordLobbyManager.get_member_user_callback get_member_user;
	/** C type : get_member_metadata_value_callback* */
	public IDiscordLobbyManager.get_member_metadata_value_callback get_member_metadata_value;
	/** C type : get_member_metadata_key_callback* */
	public IDiscordLobbyManager.get_member_metadata_key_callback get_member_metadata_key;
	/** C type : member_metadata_count_callback* */
	public IDiscordLobbyManager.member_metadata_count_callback member_metadata_count;
	/** C type : update_member_callback* */
	public IDiscordLobbyManager.update_member_callback update_member;
	/** C type : send_lobby_message_callback* */
	public IDiscordLobbyManager.send_lobby_message_callback send_lobby_message;
	/** C type : get_search_query_callback* */
	public IDiscordLobbyManager.get_search_query_callback get_search_query;
	/** C type : search_callback* */
	public IDiscordLobbyManager.search_callback search;
	/** C type : lobby_count_callback* */
	public IDiscordLobbyManager.lobby_count_callback lobby_count;
	/** C type : get_lobby_id_callback* */
	public IDiscordLobbyManager.get_lobby_id_callback get_lobby_id;
	/** C type : connect_voice_callback* */
	public IDiscordLobbyManager.connect_voice_callback connect_voice;
	/** C type : disconnect_voice_callback* */
	public IDiscordLobbyManager.disconnect_voice_callback disconnect_voice;
	/** C type : connect_network_callback* */
	public IDiscordLobbyManager.connect_network_callback connect_network;
	/** C type : disconnect_network_callback* */
	public IDiscordLobbyManager.disconnect_network_callback disconnect_network;
	/** C type : flush_network_callback* */
	public IDiscordLobbyManager.flush_network_callback flush_network;
	/** C type : open_network_channel_callback* */
	public IDiscordLobbyManager.open_network_channel_callback open_network_channel;
	/** C type : send_network_message_callback* */
	public IDiscordLobbyManager.send_network_message_callback send_network_message;
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_create_transaction_callback extends Callback {
		int apply(IDiscordLobbyManager manager, PointerByReference transaction);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_update_transaction_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, PointerByReference transaction);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_member_update_transaction_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, long user_id, PointerByReference transaction);
	};
	/** <i>native declaration : discord_game_sdk.h:463</i> */
	public interface create_lobby_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result, DiscordLobby lobby);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface create_lobby_callback extends Callback {
		void apply(IDiscordLobbyManager manager, IDiscordLobbyTransaction transaction, Pointer callback_data, IDiscordLobbyManager.create_lobby_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:464</i> */
	public interface update_lobby_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface update_lobby_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, IDiscordLobbyTransaction transaction, Pointer callback_data, IDiscordLobbyManager.update_lobby_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:465</i> */
	public interface delete_lobby_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface delete_lobby_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, Pointer callback_data, IDiscordLobbyManager.delete_lobby_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:466</i> */
	public interface connect_lobby_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result, DiscordLobby lobby);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface connect_lobby_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, Pointer secret, Pointer callback_data, IDiscordLobbyManager.connect_lobby_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:467</i> */
	public interface connect_lobby_with_activity_secret_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result, DiscordLobby lobby);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface connect_lobby_with_activity_secret_callback extends Callback {
		void apply(IDiscordLobbyManager manager, Pointer activity_secret, Pointer callback_data, IDiscordLobbyManager.connect_lobby_with_activity_secret_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:468</i> */
	public interface disconnect_lobby_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface disconnect_lobby_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, Pointer callback_data, IDiscordLobbyManager.disconnect_lobby_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, DiscordLobby lobby);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_activity_secret_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, Pointer secret);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_metadata_value_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, Pointer key, Pointer value);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_metadata_key_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, int index, Pointer key);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface lobby_metadata_count_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface member_count_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_member_user_id_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, int index, LongByReference user_id);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_member_user_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, long user_id, DiscordUser user);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_member_metadata_value_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, long user_id, Pointer key, Pointer value);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_member_metadata_key_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, long user_id, int index, Pointer key);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface member_metadata_count_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, long user_id, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h:480</i> */
	public interface update_member_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface update_member_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, long user_id, IDiscordLobbyMemberTransaction transaction, Pointer callback_data, IDiscordLobbyManager.update_member_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:481</i> */
	public interface send_lobby_message_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface send_lobby_message_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, Pointer data, int data_length, Pointer callback_data, IDiscordLobbyManager.send_lobby_message_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_search_query_callback extends Callback {
		int apply(IDiscordLobbyManager manager, PointerByReference query);
	};
	/** <i>native declaration : discord_game_sdk.h:483</i> */
	public interface search_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface search_callback extends Callback {
		void apply(IDiscordLobbyManager manager, IDiscordLobbySearchQuery query, Pointer callback_data, IDiscordLobbyManager.search_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface lobby_count_callback extends Callback {
		void apply(IDiscordLobbyManager manager, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_lobby_id_callback extends Callback {
		int apply(IDiscordLobbyManager manager, int index, LongByReference lobby_id);
	};
	/** <i>native declaration : discord_game_sdk.h:486</i> */
	public interface connect_voice_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface connect_voice_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, Pointer callback_data, IDiscordLobbyManager.connect_voice_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h:487</i> */
	public interface disconnect_voice_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface disconnect_voice_callback extends Callback {
		void apply(IDiscordLobbyManager manager, long lobby_id, Pointer callback_data, IDiscordLobbyManager.disconnect_voice_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface connect_network_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface disconnect_network_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface flush_network_callback extends Callback {
		int apply(IDiscordLobbyManager manager);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface open_network_channel_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, byte channel_id, byte reliable);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface send_network_message_callback extends Callback {
		int apply(IDiscordLobbyManager manager, long lobby_id, long user_id, byte channel_id, Pointer data, int data_length);
	};
	public IDiscordLobbyManager() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("get_lobby_create_transaction", "get_lobby_update_transaction", "get_member_update_transaction", "create_lobby", "update_lobby", "delete_lobby", "connect_lobby", "connect_lobby_with_activity_secret", "disconnect_lobby", "get_lobby", "get_lobby_activity_secret", "get_lobby_metadata_value", "get_lobby_metadata_key", "lobby_metadata_count", "member_count", "get_member_user_id", "get_member_user", "get_member_metadata_value", "get_member_metadata_key", "member_metadata_count", "update_member", "send_lobby_message", "get_search_query", "search", "lobby_count", "get_lobby_id", "connect_voice", "disconnect_voice", "connect_network", "disconnect_network", "flush_network", "open_network_channel", "send_network_message");
	}
	public IDiscordLobbyManager(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends IDiscordLobbyManager implements Structure.ByReference {
		
	};
	public static class ByValue extends IDiscordLobbyManager implements Structure.ByValue {
		
	};
}
