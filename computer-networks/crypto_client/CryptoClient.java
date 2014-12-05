//****************************************************************************
// PROJECT :Crypto Client
// CREATED BY: Emily Le
// LAST UPDATED: 12/4/2014
// PROFESSOR: Nic Pantic
//
// DESCRIPTION: 
// This project deserializes a RSA public key attached and uses it to 
// encrypt the AES session key. This encrypted session key is then serialized 
// and sent as a initial handshaking package to the server. Afterwards, 10 IPV4 
// packets with UDP are sent and encrypted with the session key. The server will
// return 0xCAFEBABE if the packets are successfully retrieved. Each package is 
// time and the average RTT time is printed.
//*****************************************************************************

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.net.SocketFactory;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class CryptoClient
{
	// IPV4 global variables
	final int MAX_PACKET_SIZE = 65516;
	byte[] byteMessage;
	byte version = 0b00000100;
	byte TTL = 50;
	byte protocol = 0X11;
	byte Hlen = 0b00000101;
	byte tos = 0b00000000;
	short ident = 0b00000000;
	short flags = 0b0100000000000000;

	// session key global variable
	Key secKey;
	Cipher cipher;

	// UDP global variables
	short udpSourcePort;
	short udpLength;
	short udpChecksum;
	short udpRandomData;
	int udpDestinationPort = 0b0101011011001110;
	int checkSum = 0;
	int length = 0;

	long sourceAddress = (127 << 24) + (0 << 16) + (0 << 8) + 1;
	long destinationAddress = (76 << 24) + (91 << 16) + (123 << 8) + 97;

	// this is the main method
	public static void main(String[] args) throws UnknownHostException,
			IOException, NoSuchAlgorithmException, ClassNotFoundException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			InterruptedException
	{
		CryptoClient art = new CryptoClient();
		art.start();

	}

	// *************************************************************************
	// START:
	// Main driver method that initializes and establishes the connection with
	// the network. It will first make a "handshaking" interaction with the
	// server. If successful, the client will send 10 randomized packets that
	// double in size.
	// *************************************************************************
	private void start() throws UnknownHostException, IOException,
			NoSuchAlgorithmException, ClassNotFoundException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			InterruptedException
	{
		// initializations
		byte[] byteMessage;
		short[] UdpPAckage;
		short[] packageWithData;
		short[] randomData;
		long calculatedWithUdp;

		// time
		long start = 0;
		long finish = 0;
		long average = 0;
		Long[] calculatedTime = new Long[10];

		Random randomUdp = new Random();

		// connecting and writing to server socket
		Socket socket = SocketFactory.getDefault().createSocket("76.91.123.97",
				22222);
		OutputStream out = socket.getOutputStream();
		InputStream in = socket.getInputStream();

		// handshaking process
		byte[] initalHandshakingPackage = makeHandshake();
		// printPacket(shortToByte(initalHandshakingPackage));

		long calculatedChecksum = checksum(initalHandshakingPackage);

		initalHandshakingPackage[11] |= calculatedChecksum;
		initalHandshakingPackage[10] |= (calculatedChecksum >>> 8);

		out.write(initalHandshakingPackage);

		System.out.print("HANDSHAKE SERVER RESPONSE: 0x");
		System.out.print(Integer.toHexString(in.read()).toUpperCase());
		System.out.print(Integer.toHexString(in.read()).toUpperCase());
		System.out.print(Integer.toHexString(in.read()).toUpperCase());
		System.out.print(Integer.toHexString(in.read()).toUpperCase());
		System.out.println();

		// sending 10 packets of data with udp
		for (int i = 0; i < 10; i++)
		{
			randomData = new short[(int) Math.pow(2, i)];
			for (int j = 0; j < randomData.length; j++)
			{
				randomData[j] = (short) randomUdp.nextInt(Short.MAX_VALUE);
			}

			UdpPAckage = buildUdp(randomData);
			packageWithData = buildPackage(UdpPAckage);
			// byte[] bytePackageWithdata = shortToByte(packageWithData);
			calculatedWithUdp = checksum(packageWithData);
			packageWithData[5] |= (short) calculatedWithUdp;

			byteMessage = shortToByte(packageWithData);

			// encrypt packet
			byte[] encryptedPacket = cipher.doFinal(byteMessage);

			// start time
			start = System.currentTimeMillis();
			out.write(encryptedPacket);

			// read from the server 4 times
			System.out.print("Resonse: 0x");
			System.out.print(Integer.toHexString(in.read()).toUpperCase());
			System.out.print(Integer.toHexString(in.read()).toUpperCase());
			System.out.print(Integer.toHexString(in.read()).toUpperCase());
			System.out.print(Integer.toHexString(in.read()).toUpperCase());
			System.out.println();

			// prints out the time it takes for packet to send and ACK
			finish = System.currentTimeMillis();
			calculatedTime[i] = (finish - start);
			System.out.println(calculatedTime[i] + "ms");
		}

		// average RTT Time
		for (int i = 0; i < calculatedTime.length; i++)
		{
			average += calculatedTime[i];
		}

		// final average RTT
		average /= 10;
		System.out.println("Average RTT: " + average + "ms");
	}

	// *************************************************************************
	// SHORT TO BYTE CONVERTER:
	// Will convert a short array to a byte array.
	// *************************************************************************
	private byte[] shortToByte(short[] message)
	{
		byte[] byteMessage = new byte[message.length * 2];
		for (int i = 0, j = 0; i < message.length; i++, j += 2)
		{
			byteMessage[j + 1] |= message[i];
			message[i] >>>= 8;
			byteMessage[j] |= message[i];
		}

		return byteMessage;
	}

	// *************************************************************************
	// BYTE TO SHORT CONVERTER:
	// *************************************************************************
	private short[] byteToShort(byte[] message)
	{
		short[] shortMessage = new short[(message.length + 1) / 2];

		for (int i = 0, j = 0; j < message.length - 1; i++, j += 2)
		{
			shortMessage[i] |= message[j];
			shortMessage[i] <<= 8;
			shortMessage[i] |= message[j + 1];
		}

		return shortMessage;
	}

	// *************************************************************************
	// BUILD PACKAGE (BYTES):
	// This is similar to the other build package method instead this method
	// takes in a byte array instead of a short array
	// *************************************************************************
	private byte[] buildPackage(byte[] data)
	{
		byte[] message = null;
		length = (4 * Hlen) + data.length;
		if (length < MAX_PACKET_SIZE)
		{
			message = new byte[length];
			byte shortoByte = 0;

			// message 0
			message[0] = version;
			message[0] <<= 4;
			message[0] |= Hlen;

			// message 1
			message[1] = 0;

			// message 2 & 3
			message[3] = (byte) length;
			message[2] = (byte) (length >>> 8);

			// message 4 & 5
			message[4] = 0;
			message[5] = 0;

			// message 6 & 7
			message[6] = 0b010;
			message[6] <<= 5;
			message[6] |= 0;
			message[7] = 0;

			// message 8
			message[8] = TTL;

			// message 9
			message[9] = protocol;

			// message 10 & 11
			int tempCheckSum = checkSum;
			message[11] = (byte) tempCheckSum;
			message[10] = (byte) (tempCheckSum >>> 8);

			// message 12, 13, 14, 15
			message[15] = (byte) sourceAddress;
			message[14] = (byte) (sourceAddress >>> 8);
			message[13] = (byte) (sourceAddress >>> 16);
			message[12] = (byte) (sourceAddress >>> 24);

			// message 16, 17, 18, 19
			message[19] = (byte) destinationAddress;
			message[18] = (byte) (destinationAddress >>> 8);
			message[17] = (byte) (destinationAddress >>> 16);
			message[16] = (byte) (destinationAddress >>> 24);

			// message[10]
			for (int i = 0; i < data.length; i++)
			{
				message[20 + i] = data[i];
			}
		}

		return message;
	}

	// *************************************************************************
	// BUILD PACKAGE:
	// Will build the IPV4 packet(data & header) that will also include the UDP
	// data.
	// *************************************************************************
	private short[] buildPackage(short[] data)
	{
		short[] message = null;
		length = (2 * Hlen) + data.length;
		if (length < MAX_PACKET_SIZE)
		{
			message = new short[length];
			short tempShort = 0;

			// message[0]
			message[0] = version;
			message[0] <<= 12;
			tempShort = Hlen;
			tempShort &= 0XFF;
			tempShort <<= 8;
			message[0] |= tempShort;

			// message[1]
			length *= 2; // there are 2 bytes in a short
			message[1] |= length;

			// message[2]
			message[2] = ident;

			// message[3]
			message[3] = flags;

			message[4] |= (TTL & 0XFF);
			message[4] <<= 8;
			message[4] |= (protocol & 0XFF);

			// message[5]
			message[5] |= checkSum;

			// message[7] & message[6]
			message[7] |= sourceAddress;
			// sourceAddress >>>= 16;
			message[6] |= (sourceAddress >>> 16);

			// message[8] & message[9]
			message[9] |= destinationAddress;
			// destinationAddress >>>= 16;
			message[8] |= (destinationAddress >>> 16);

			// message[10]
			for (int i = 0; i < data.length; i++)
			{
				message[10 + i] = data[i];
			}
		}
		return message;
	}

	// *************************************************************************
	// HANDSHAKING:
	// Makes an initial connection(handshaking) with the server. The client will
	// send DEADBEEF as data in an IPV4 packet.
	// *************************************************************************
	private byte[] makeHandshake() throws IOException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			ClassNotFoundException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException,
			InterruptedException
	{
		// reading in RSA public key
		FileInputStream fis = new FileInputStream("public.bin");
		ObjectInputStream ois = new ObjectInputStream(fis);
		RSAPublicKey rsaPublicKey = (RSAPublicKey) ois.readObject();
		Cipher rsaCipher = Cipher.getInstance("RSA");
		rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);

		// create session key
		Cipher cipher = Cipher.getInstance("AES");
		this.cipher = cipher;
		Key secKey = KeyGenerator.getInstance("AES").generateKey();
		this.secKey = secKey;
		cipher.init(Cipher.ENCRYPT_MODE, secKey);

		// serialize key and create short[] cipherText
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(secKey);

		// encryption of session key
		byte[] cipherTextEncrypted = rsaCipher.doFinal(baos.toByteArray());

		/*
		 * short[] cipherText = byteToShort(cipherTextEncrypted); short[]
		 * handshakingPackage = buildPackage(cipherText);
		 */

		byte[] handshakingPackage = buildPackage(cipherTextEncrypted);
		// System.out.println("LENGTH: " + handshakingPackage.length);

		// printPacket(shortToByte(handshakingPackage));
		return handshakingPackage;
	}

	// *************************************************************************
	// BUILD UDP:
	// Builds the UDP protocol that will stored as the data in the ipv4 packet.
	// *************************************************************************
	private short[] buildUdp(short[] data)
	{
		short[] udpMessage = new short[4 + data.length];

		// message 0
		udpMessage[0] = 0b100000000001;

		// message 1
		udpMessage[1] = (short) udpDestinationPort;

		// message 3
		udpMessage[2] = (short) (8 + (2 * data.length));

		udpMessage[3] = 0;

		// message 4
		for (int i = 0; i < data.length; i++)
		{
			udpMessage[4 + i] = data[i];
		}

		long sum = checkSumUDP(udpMessage, udpMessage[2]);
		udpMessage[3] = (short) sum;

		return udpMessage;
	}

	// *************************************************************************
	// UDP CHECKSUM:
	// Will calculate the checksum of all of the parameters in the UDP header
	// along with the source address, destination address, protocol from the
	// IPV4 packet header. It will also count a second length of the UDP. (yes,
	// length will be counted twice).
	// *************************************************************************
	private long checkSumUDP(short[] message, short length)
	{
		long sum = 0;
		short[] newMessage = new short[message.length + 6];

		for (int i = 0; i < message.length; i++)
		{
			newMessage[i] = message[i];
		}

		newMessage[message.length + 1] |= sourceAddress;
		newMessage[message.length] |= (sourceAddress >>> 16);
		newMessage[message.length + 3] |= destinationAddress;
		newMessage[message.length + 2] |= (destinationAddress >>> 16);
		newMessage[message.length + 4] = protocol;
		newMessage[message.length + 5] = length;

		for (int i = 0; i < newMessage.length; i++)
		{
			sum += (newMessage[i] & 0XFFFF);
			if ((sum & 0xFFFF0000) > 0)
			{
				// carry over which takes care of overflow
				sum &= 0xFFFF;
				sum++;
			}
		}

		// does 1's complement
		sum = ~sum;
		sum = sum & 0xFFFF;
		return sum;
	}

	// *************************************************************************
	// IPV4 CHECKSUM:
	// *************************************************************************
	private long checksum(byte[] message)
	{
		short[] newMessage = byteToShort(message);
		long sum = 0;
		for (int i = 0; i < 10; i++)
		{
			sum += (newMessage[i] & 0XFFFF);
			if ((sum & 0xFFFF0000) > 0)
			{
				// carry over which takes care of overflow
				sum &= 0xFFFF;
				sum++;
			}
		}
		// does 1's complement
		sum = ~sum;
		sum = sum & 0xFFFF;
		return sum;
	}

	private long checksum(short[] message)
	{
		long sum = 0;
		for (int i = 0; i < 10; i++)
		{
			sum += (message[i] & 0XFFFF);
			if ((sum & 0xFFFF0000) > 0)
			{
				// carry over which takes care of overflow
				sum &= 0xFFFF;
				sum++;
			}
		}
		// does 1's complement
		sum = ~sum;
		sum = sum & 0xFFFF;
		return sum;
	}

	public static void printPacket(byte[] sendIt)
	{
		System.out.println("SENDING PACKET OF LENGTH " + sendIt.length);
		for (int i = 0; i < sendIt.length; i++)
		{
			String temp = "";
			for (int j = 7; j >= 0; j--)
			{
				temp += ((0b1 << j & sendIt[i]) > 0) ? "1" : "0";
			}
			if (i % 4 == 0)
			{
				System.out.println();
			}
			while (temp.length() < 8)
			{
				temp += ("0" + temp);
			}
			System.out.print(temp.substring(temp.length() - 8) + " ");

		}
		System.out.println();
	}
}
