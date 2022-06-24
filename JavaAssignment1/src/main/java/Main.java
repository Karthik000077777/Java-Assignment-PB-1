import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		try {
			KeywordChecker.check("C:\\Users\\YKAREDDY\\Desktop\\Resumes");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
