/**
 * PROJECT: Ipv6 Client
 * CREATED BY: Emily Le
 * LAST UPDATED: 10/29/2014
 * PROFESSOR: Nic Pantic
 *
 * DESCRIPTION: 
 * This is an implementation of a IPV6 protocol. Essentially, 10 packets are created,
 *  and sent to a server for verification. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.net.SocketFactory;

public class Ipv6Client
{
	byte[] byteMessage;
	byte version = 0b00000110; 			// version 6
	byte trafficClass = 0b00000000; 	// don't implement
	byte nextHeader = 0b00010001; 		// set to UDP control
	short hopLimit = 0b00010100; 		// implement set to 20
	int payloadLength = 0; 				// implement (length of data in octets or 16 bits)
	int flowLabel = 0b0000000000000000000000000000000; // don't implement
	final int MAX_PACKET_SIZE = 65516;
	Short[] sourceAddress = new Short[] { 0, 0, 0, 0, 0, -1, 0x7F00, 1 };
	Short[] destinationAddress = new Short[] { 0, 0, 0, 0, 0, -1, 0x4C5B,
			0X7B61 };

	// this is the main method
	public static void main(String[] args) throws UnknownHostException,
			IOException
	{
		Ipv6Client art = new Ipv6Client();
		art.start();

	}

	// called by the main method: connects to server and sends binary message to
	// server
	public void start() throws UnknownHostException, IOException
	{

		// connecting and writing to server socket
		Socket socket = SocketFactory.getDefault().createSocket("76.91.123.97",
				22222);
		PrintStream printStream = new PrintStream(socket.getOutputStream());
		InputStreamReader streamReader = new InputStreamReader(
				socket.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		String incomingMessage;

		// send 10 data packets to the server and will print result
		for (int i = 0; i < 10; i++)
		{
			short[] packageWithData = createData();
			byteMessage = shortToByte(packageWithData);
			
			printStream.write(byteMessage);
			incomingMessage = bufferedReader.readLine();
			System.out.println(incomingMessage);
		}
	}

	// builds the package structure with the necessary information
	private short[] buildPackage(short[] data)
	{
		short[] message = null;
		int length = (20 + data.length);
		
		payloadLength = (short) (2 * (data.length));
		
		if (payloadLength < MAX_PACKET_SIZE)
		{
			message = new short[length];
			short tempShort = 0;

			// message[0]
			message[0] = version;
			message[0] <<= 12;
			message[0] |= trafficClass;

			// message[1]
			message[1] = (short) flowLabel;

			// message[2]
			message[2] = (short) payloadLength;

			// message[3]
			tempShort = nextHeader;
			tempShort <<= 8;
			message[3] = tempShort;
			message[3] |= hopLimit;

			// message[4] through message[11]
			for (int i = 4, j = 0; i < sourceAddress.length + 4; i++, j++)
			{
				message[i] = sourceAddress[j];
			}

			// message[12] through message[19]
			for (int i = 12, j = 0; i < destinationAddress.length + 12; i++, j++)
			{
				message[i] = destinationAddress[j];
			}

			// addition of data
			for (int i = 0; i < data.length; i++)
			{
				message[20 + i] = data[i];
			}
		}
		return message;
	}

	// converts a short array to a byte array
	private byte[] shortToByte(short[] message)
	{
		byteMessage = new byte[message.length * 2];
		for (int i = 0, j = 0; i < message.length; i++, j += 2)
		{
			byteMessage[j + 1] |= message[i];
			message[i] >>>= 8;
			byteMessage[j] |= message[i];
		}

		return byteMessage;
	}

	// create randomly generated data
	private short[] createData()
	{
		Random random = new Random();
		int randomLength = random.nextInt(32748);
		short[] randomData = new short[randomLength];
		for (int i = 10; i < randomData.length; i++)
		{
			randomData[i] = (short) random.nextInt(Short.MAX_VALUE);
		}

		short[] completePackage = buildPackage(randomData);
		return completePackage;
	}
}
