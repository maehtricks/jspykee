package net.maehtricks.libspykeej;

import java.io.UnsupportedEncodingException;

import net.maehtricks.libspykeej.datatype.DockState;
import net.maehtricks.libspykeej.util.ReverseEnumMap;

/**
 * The Class LoginResponse encapsulates Spykee's login information.
 */
public class LoginResponse {
	
	private static final ReverseEnumMap<DockState> dockStates = new ReverseEnumMap<DockState>(DockState.class);
	private final String name1;
	private final String name2;
	private final String name3;
	private final String version;
	private final DockState dockState;
	private final boolean isSuccess;

	/**
	 * Instantiates a new login response.
	 *
	 * @param name1 the name1
	 * @param name2 the name2
	 * @param name3 the name3
	 * @param version the version
	 * @param dockState the dock state
	 */
	private LoginResponse(String name1, String name2, String name3, String version, DockState dockState) {
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
		this.version = version;
		this.dockState = dockState;
		this.isSuccess = true;
	}
	
	/**
	 * Instantiates a empty login response.
	 */
	public LoginResponse() {
		this.name1 = null;
		this.name2 = null;
		this.name3 = null;
		this.version = null;
		this.dockState = DockState.UNDEFINED;
		this.isSuccess = false;
	}
	
	/**
	 * Generates instance from byte array.
	 *
	 * @param buffer the buffer
	 * @return the login response
	 */
	public static LoginResponse fromByteArray(byte[] buffer) {
		int pos = 1;
		int nameLen = buffer[pos++];
		String name1 = byteArrayToString(buffer, pos, nameLen);
		pos += nameLen;
		
		nameLen = buffer[pos++];
		String name2 = byteArrayToString(buffer, pos, nameLen);
		pos += nameLen;
		
		nameLen = buffer[pos++];
		String name3 = byteArrayToString(buffer, pos, nameLen);
		pos += nameLen;
		
		nameLen = buffer[pos++];
		String version = byteArrayToString(buffer, pos, nameLen);
		pos += nameLen;
		
		DockState dockState = dockStates.get(buffer[pos]);
		
		return new LoginResponse(name1, name2, name3, version, dockState);				
	}
	
	/**
	 * Byte array to string.
	 *
	 * @param buffer the buffer
	 * @param pos the pos
	 * @param nameLen the name len
	 * @return the string
	 */
	private static String byteArrayToString(byte[] buffer, int pos, int nameLen) {
		try {
			return new String(buffer, pos, nameLen, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Gets the name1.
	 *
	 * @return the name1
	 */
	public String getName1() {
		return name1;
	}

	/**
	 * Gets the name2.
	 *
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * Gets the name3.
	 *
	 * @return the name3
	 */
	public String getName3() {
		return name3;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Gets the dock state.
	 *
	 * @return the dock state
	 */
	public DockState getDockState() {
		return dockState;
	}

	/**
	 * Checks if login was successful.
	 *
	 * @return true, if successful
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
}
