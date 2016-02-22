package com.git.cs309.testclient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.git.cs309.mmoserver.packets.LoginPacket;
import com.git.cs309.mmoserver.packets.MessagePacket;
import com.git.cs309.mmoserver.util.StreamUtils;

public class Client {
	//Just a basic client for various server/client interaction testing.
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Client client = new Client();
	}

	private final Socket socket;

	private Client() throws UnknownHostException, IOException, InterruptedException {
		socket = new Socket("proj-309-21.cs.iastate.edu", 43594);
		//while (true) {
		LoginPacket packet = new LoginPacket(null, "Clowbn", "Clown");
		StreamUtils.writeBlockToStream(socket.getOutputStream(), packet.toBytes());
		Thread.sleep(500);
		while (true) {
			StreamUtils.writeBlockToStream(socket.getOutputStream(),
					new MessagePacket(null, (byte) 0, "Lol....").toBytes());
			Thread.sleep(20);
		}
		//}

	}

}
