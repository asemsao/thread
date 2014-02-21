

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {

	/**
	 * @param args
	 */
	public static void Write(String write) {
		StringBuffer content = new StringBuffer();
		content.append(write);
		File file = new File("employee_summary.txt");
		FileWriter fileWriter = null;
		PrintWriter out = null;
		try {
			fileWriter = new FileWriter(file);
			out = new PrintWriter(new BufferedWriter(fileWriter));
			for (int i = 0; i < 1; i++)
				out.println(content.toString());			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
