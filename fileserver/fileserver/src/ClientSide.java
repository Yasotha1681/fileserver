
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSide {
	public static void main(String[] args) throws Exception
	{
		System.out.println("Client");
		int port = 2500,
			choice; // port number and choice variable for
					// taking user choice for downloading or
					// uploading a file.....
		String ip
			= "localhost"; // Server IP address.....send or
						// receive file from this
						// IP.....IP is used for making
						// connection.....

		while (true) {
			Socket clientSocket = new Socket(
				ip, port); // Socket object used for making
						// a connection to the given IP
						// and Port address.....
			Scanner sc = new Scanner(System.in);

			System.out.println(
				"Enter Your Choice - 1 For Sending File and 2 For Downloading File : ");

			choice = sc.nextInt(); // taking choice from
								// console.....
			sendReq(
				choice,
				clientSocket); // method is used for sending
							// the request to the
							// server.....check the
							// sendFile and receiveFile
							// methods also which are
							// inside sendReq
		}
	}

	public static void sendReq(int choice,
				Socket clientSocket)
		throws Exception
	{
		if (choice == 1) // choice 1 is for sending a file
						// to server.....uploading....
		{
			sendFile(
				clientSocket); // sends a file to
							// server....it takes
							// fileName and fileContent
							// from the console.....and
							// sends it to server.....
		}
		else if (choice
				== 2) // choice 2 is for receiving a file
					// from server....downloading...
		{
			receiveFile(
				clientSocket); // receive a file from server
							// and stores it into a given
							// directory or a folder.....
		}
		else {
			System.out.println("Invalid Choice");
		}
	}

	// used for send the file..........
	public static void sendFile(Socket clientSocket)
	{

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter file Name : ");
		String name = sc.nextLine(); // asking for file name
									// from console.....
		System.out.println("Enter Content : ");
		String content
			= sc.nextLine(); // asking for content of the
							// file....
		Data data = new Data(
			1, name,
			content); // Data class object is used for
					// making the given data into
					// serializable......read more about
					// serializable on internet.....
		ObjectOutputStream
			outputStream; // Using an ObjectOutputStream,
						// you can write both primitive
						// data types and graphs of Java
						// objects to an OutputStream.
		try {
			outputStream = new ObjectOutputStream(
				clientSocket.getOutputStream());
			outputStream.writeObject(
				data); // writeObject(Object obj) method
					// writes the specified object to the
					// ObjectOutputStream.....
			System.out.println("File Sent Successfully!!");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Something Went Wrong");
			e.printStackTrace();
		}
	}

	// used for receiving the file.......
	public static void receiveFile(Socket clientSocket)
		throws Exception
	{
		ObjectOutputStream outputStream
			= new ObjectOutputStream(
				clientSocket.getOutputStream());
		ObjectInputStream inputStream
			= new ObjectInputStream(
				clientSocket.getInputStream());
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter file Name : ");
		String name
			= sc.nextLine(); // asking for file name that
							// user want to download....

		Data data = new Data(
			2, name,
			""); // making data object with given info....
		// sending info
		outputStream.writeObject(data);

		// receiving file
		Data receivedData
			= (Data)inputStream
				.readObject(); // getting data from input
								// stream in the form of
								// Data object.....

		// storing file to the given directory or folder
		// with the given file name......
		File fileToBeDownloaded = new File(
			"C:\\Users\\Administrator\\Documents\\NetBeansProjects\\fileserver\\clinet download files\\"
			+ receivedData.fileName);
		FileOutputStream fileOutputStream
			= new FileOutputStream(fileToBeDownloaded);
		fileOutputStream.write(
			(receivedData.fileContent).getBytes());
	}
}

