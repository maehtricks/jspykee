package net.maehtricks.libspykeej;

import java.io.UnsupportedEncodingException;

import net.maehtricks.jspykee.datatype.DockState;
import net.maehtricks.libspykeej.util.ReverseEnumMap;

public class LoginResponse {
	private static final ReverseEnumMap<DockState> dockStates = new ReverseEnumMap<DockState>(DockState.class);
	
	private final String name1;
	private final String name2;
	private final String name3;
	private final String version;
	private final DockState dockState;
	private final boolean isSuccess;

	private LoginResponse(String name1, String name2, String name3, String version, DockState dockState) {
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
		this.version = version;
		this.dockState = dockState;
		this.isSuccess = true;
	}
	
	public LoginResponse() {
		this.name1 = null;
		this.name2 = null;
		this.name3 = null;
		this.version = null;
		this.dockState = DockState.UNDEFINED;
		this.isSuccess = false;
	}
	
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
	
	private static String byteArrayToString(byte[] buffer, int pos, int nameLen) {
		try {
			return new String(buffer, pos, nameLen, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}

	public String getName3() {
		return name3;
	}

	public String getVersion() {
		return version;
	}

	public DockState getDockState() {
		return dockState;
	}

	public boolean isSuccess() {
		return isSuccess;
	}
}
