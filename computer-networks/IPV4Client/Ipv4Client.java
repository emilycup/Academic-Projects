/**
 * PROJECT: Chat Client
 * CREATED BY: Emily Le
 * LAST UPDATED: 10/21/2014
 * PROFESSOR: Nic Pantic
 *
 * DESCRIPTION: 
 * This is an implementation of a IPV4 protocol. Essentially, packets are created,
 *  a checksum is generated, and sent to a server for verification. 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.net.SocketFactory;

public class Ipv4Client {
	final int MAX_PACKET_SIZE = 32767;
	byte[] byteMessage;
	byte version = 0b00000100;
	byte TTL = 0b110010; 
	byte protocol = 0b00000110;
	byte Hlen = 0b00000101;
	byte tos = 0b00000000;
	short ident = 0b00000000;
	short flags = 0b0100000000000000;
	int checkSum = 0;
	int length= 0;
	long sourceAddress = (127 << 24) + (0 << 16) + (0 <<8) + 1;
	long destinationAddress = (76 << 24) + (91 << 16) + (123 << 8) + 97;

	// this is the main method 
	public static void main(String[] args) throws UnknownHostException, IOException {
		Ipv4Client art = new Ipv4Client();
		art.start();

	}
	
	// called by the main method: connects to server and sends binary message to server
	private void start() throws UnknownHostException, IOException{
		
		// connecting and writing to server socket
		Socket socket = SocketFactory.getDefault().createSocket("76.91.123.97", 22222);
		PrintStream printStream = new PrintStream(socket.getOutputStream());
		InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(streamReader);	
		String incomingMessage;
		// send 10 data packets to the server and will print result
		for (int i = 0; i < 10; i++){
			short[] packageWithData = createData();
			long calculatedChecksum = checksum(packageWithData);
			packageWithData[5] |= (short)calculatedChecksum;
			byteMessage = shortToByte(packageWithData);
			printStream.write(byteMessage);
			incomingMessage = bufferedReader.readLine();
			System.out.println(incomingMessage);
		}
	}
	
	// converts a short array to a byte array
	private byte[] shortToByte(short[] message){
		byteMessage = new byte[message.length * 2];
		for (int i = 0, j = 0; i < message.length; i++, j+=2){
			byteMessage[j + 1] |= message[i];
			message[i] >>>= 8;
			byteMessage[j] |= message[i];
		}
		
		return byteMessage;
	}

	
	// builds the package structure with the necessary information
	private short[] buildPackage(short[] data){
		short[] message = null;
		length = (2 * Hlen) + data.length;
		System.out.println(data);
		if (length < MAX_PACKET_SIZE){
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
			
			// message[4]
			tempShort = TTL;
			tempShort <<=8;
			message[4] = tempShort;
			message[4] |= protocol;
			
			//message[5]
			message[5] |= checkSum;
			
			// message[7] & message[6]
			message[7] |= sourceAddress;
			//sourceAddress >>>= 16;
			message[6] |= (sourceAddress >>>16);
			
			// message[8] & message[9]
			message[9] |= destinationAddress;
			//destinationAddress >>>= 16;
			message[8] |= (destinationAddress >>>16);
			
			// message[10] 
			for (int i = 0; i < data.length; i++){
				message[10 + i] = data[i];
			}
		}
		return message;
	}
	
	// create randomly generated data
	private short[] createData(){
		Random random = new Random();
		int randomLength = random.nextInt(16362);
		short[] randomData= new short[randomLength];
		for (int i = 10; i < randomData.length; i++){
			randomData[i] = (short) random.nextInt(Short.MAX_VALUE);
		}
		
		short[] completePackage = buildPackage(randomData);
		return completePackage;
	}
	
	// calculates the checksum 
	private long checksum(short[] message){
		long sum = 0;
		for (int i= 0; i < 10; i++){
			sum += (message[i] & 0XFFFF);
			if ((sum & 0xFFFF0000) > 0){
				//carry over which takes care of overflow
				sum &= 0xFFFF;
				sum++;
			}
		}
		// does 1's complement
		sum = ~sum;
	    sum = sum & 0xFFFF;
	    return sum;
	}
}
