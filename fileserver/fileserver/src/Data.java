
import java.io.Serializable;

 public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	public int choice; // choice variable
	public String fileName; // file name variable.....stores
							// file name....
	public String
		fileContent; // file content variable.....stores
					// file content....

	Data(int choice, String fileName,
		String fileContent) // constructor.....
	{
		this.choice = choice;
		this.fileName = fileName;
		this.fileContent = fileContent;
	}
}
