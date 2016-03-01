package com.git.cs309.mmoclient.connection;

import java.io.IOException;
import java.net.Socket;

import com.git.cs309.mmoclient.packets.PacketHandler;
import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.packets.Packet;

public class Connection extends AbstractConnection {

	public Connection(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public void iterationStartBlock() {
		// Nothing needed
	}

	@Override
	public int maxPacketsPerIteration() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void postRun() {
		//Nothing needed
	}

	@Override
	public void handlePacket(Packet arg0) {
		PacketHandler.getInstance().handlePacket(arg0);
	}
}
