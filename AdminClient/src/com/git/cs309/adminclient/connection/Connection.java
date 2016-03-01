package com.git.cs309.adminclient.connection;

import java.io.IOException;
import java.net.Socket;

import com.git.cs309.adminclient.packets.PacketHandler;
import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.packets.Packet;

public class Connection extends AbstractConnection {

	public Connection(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	public void iterationStartBlock() {
		//Don't need anything.
	}

	@Override
	public int maxPacketsPerIteration() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void postRun() {
		//Dont need anything
	}

	@Override
	public void handlePacket(Packet arg0) {
		PacketHandler.getInstance().handlePacket(arg0);
	}
}
