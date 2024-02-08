package discord_game_sdk;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : discord_game_sdk.h:582</i><br>
 * This file was autogenerated by <a href="https://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="https://ochafik.com/">Olivier Chafik</a> that <a href="https://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="https://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="https://rococoa.dev.java.net/">Rococoa</a>, or <a href="https://jna.dev.java.net/">JNA</a>.
 */
public class IDiscordStoreManager extends Structure {
	/** C type : fetch_skus_callback* */
	public IDiscordStoreManager.fetch_skus_callback fetch_skus;
	/** C type : count_skus_callback* */
	public IDiscordStoreManager.count_skus_callback count_skus;
	/** C type : get_sku_callback* */
	public IDiscordStoreManager.get_sku_callback get_sku;
	/** C type : get_sku_at_callback* */
	public IDiscordStoreManager.get_sku_at_callback get_sku_at;
	/** C type : fetch_entitlements_callback* */
	public IDiscordStoreManager.fetch_entitlements_callback fetch_entitlements;
	/** C type : count_entitlements_callback* */
	public IDiscordStoreManager.count_entitlements_callback count_entitlements;
	/** C type : get_entitlement_callback* */
	public IDiscordStoreManager.get_entitlement_callback get_entitlement;
	/** C type : get_entitlement_at_callback* */
	public IDiscordStoreManager.get_entitlement_at_callback get_entitlement_at;
	/** C type : has_sku_entitlement_callback* */
	public IDiscordStoreManager.has_sku_entitlement_callback has_sku_entitlement;
	/** C type : start_purchase_callback* */
	public IDiscordStoreManager.start_purchase_callback start_purchase;
	/** <i>native declaration : discord_game_sdk.h:583</i> */
	public interface fetch_skus_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface fetch_skus_callback extends Callback {
		void apply(IDiscordStoreManager manager, Pointer callback_data, IDiscordStoreManager.fetch_skus_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface count_skus_callback extends Callback {
		void apply(IDiscordStoreManager manager, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_sku_callback extends Callback {
		int apply(IDiscordStoreManager manager, long sku_id, DiscordSku sku);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_sku_at_callback extends Callback {
		int apply(IDiscordStoreManager manager, int index, DiscordSku sku);
	};
	/** <i>native declaration : discord_game_sdk.h:587</i> */
	public interface fetch_entitlements_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface fetch_entitlements_callback extends Callback {
		void apply(IDiscordStoreManager manager, Pointer callback_data, IDiscordStoreManager.fetch_entitlements_callback_callback_callback callback);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface count_entitlements_callback extends Callback {
		void apply(IDiscordStoreManager manager, IntByReference count);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_entitlement_callback extends Callback {
		int apply(IDiscordStoreManager manager, long entitlement_id, DiscordEntitlement entitlement);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface get_entitlement_at_callback extends Callback {
		int apply(IDiscordStoreManager manager, int index, DiscordEntitlement entitlement);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface has_sku_entitlement_callback extends Callback {
		int apply(IDiscordStoreManager manager, long sku_id, Pointer has_entitlement);
	};
	/** <i>native declaration : discord_game_sdk.h:592</i> */
	public interface start_purchase_callback_callback_callback extends StdCallCallback {
		void apply(Pointer callback_data, int result);
	};
	/** <i>native declaration : discord_game_sdk.h</i> */
	public interface start_purchase_callback extends Callback {
		void apply(IDiscordStoreManager manager, long sku_id, Pointer callback_data, IDiscordStoreManager.start_purchase_callback_callback_callback callback);
	};
	public IDiscordStoreManager() {
		super();
	}
	protected List<String> getFieldOrder() {
		return Arrays.asList("fetch_skus", "count_skus", "get_sku", "get_sku_at", "fetch_entitlements", "count_entitlements", "get_entitlement", "get_entitlement_at", "has_sku_entitlement", "start_purchase");
	}
	public IDiscordStoreManager(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends IDiscordStoreManager implements Structure.ByReference {
		
	};
	public static class ByValue extends IDiscordStoreManager implements Structure.ByValue {
		
	};
}