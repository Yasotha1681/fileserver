
import java.io.*;
import java.net.*;

public class ServerSide {
	static int port = 2500; // port address....
	public static void main(String[] args) throws Exception
	{
		System.out.println("Server");
		ServerSocket serverSocketObj = new ServerSocket(
			port); // server serving at port =
				// 2500......ServerSocket starts this
				// program to port = 2500....
		while (true) // infinite loop so that server is
					// always listening to requests.....
		{
			receivingRequest(
				serverSocketObj); // handling requests.....
		}
	}

	// handling requests....downloading and uploading....
	public static void
	receivingRequest(ServerSocket serverSocketObj)
		throws Exception
	{
		Socket serverSideSocket = serverSocketObj.accept();
		ObjectOutputStream outputStream
			= new ObjectOutputStream(
				serverSideSocket
					.getOutputStream()); // output
										// stream....
		ObjectInputStream inputStream
			= new ObjectInputStream(
				serverSideSocket
					.getInputStream()); // input stream....

		Data data = (Data)inputStream.readObject();
		if (data.choice == 1) {
			// Receive file
			receiveFile(
				data, inputStream); // receiving a file.....
		}
		else {
			sendFile(data,
					outputStream); // sending a file....
		}
	}

	// receiving the file from client and store it.....
	public static void
	receiveFile(Data data, ObjectInputStream inputStream)
		throws Exception
	{
		String fileName
			= data.fileName; // getting file name....
		String fileContent
			= data.fileContent; // getting file content....

		File newFile = new File(
			"C:\\Users\\Administrator\\Documents\\NetBeansProjects\\fileserver\\server files\\"
			+ fileName); // file object.....
		FileOutputStream fileOutputStream
			= new FileOutputStream(newFile);

		fileOutputStream.write(
			fileContent
				.getBytes()); // writing the file to the
							// given directory or folder
							// with the given name......
	}

	// sends the file to the client.....
	public static void
	sendFile(Data data, ObjectOutputStream outputStream)
		throws Exception
	{
		File file = new File(
			"C:\\Users\\Administrator\\Documents\\NetBeansProjects\\fileserver\\server files\\"
			+ data.fileName); // getting the file from the
							// server's directory or
							// folder (from server
							// storage)......
		if (file.exists()
			== true) // if given file name is exits in
					// server storage....
		{
			byte[] fileContentInBytes = new byte[(
				int)(file.length())]; // creating an array
									// of bytes to storing
									// the file
									// content....
			FileInputStream fileInpStream
				= new FileInputStream(file);
			fileInpStream.read(
				fileContentInBytes); // reading the file
									// from input stream
									// (input stream
									// between File System
									// of server and this
									// program) and storing
									// it into byte
									// array....

			Data fileToSend = new Data(
				0, data.fileName,
				new String(
					fileContentInBytes)); // creating Data
										// object which
										// contains info
										// about file
										// which have to
										// send....

			outputStream.writeObject(
				fileToSend); // sending object to client
		}
		else {
			System.out.println("File Not Exist");
		}
	}
}
