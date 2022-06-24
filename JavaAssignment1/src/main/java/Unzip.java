import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class Unzip{
	public static void readZipFiles(String path) {
//		String path = "C:\\Users\\YKAREDDY\\Desktop\\Resumes\\Resumes.zip";
		File file = new File(path);
		System.out.println("\nZip File Name: "+path+file.separator+file.getName());
		ZipInputStream zin = null;
		String dir = "C:\\Users\\YKAREDDY\\Downloads\\Extract";
		try {
			zin = new ZipInputStream(new FileInputStream(path));
			ZipEntry zipEntry = null;
			while((zipEntry = zin.getNextEntry()) != null) {
				int len;
				byte[] data = new byte[1024];
				FileOutputStream fos = new FileOutputStream(dir+"\\"+zipEntry.getName());
				while((len = zin.read(data)) != -1) {
					fos.write(data,0,len);
				}
				fos.close();
				zin.closeEntry();
			}
			System.out.println("Extraction Completed");
			zin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			KeywordChecker.set.clear();
			KeywordChecker.pdfMatchList.clear();
			KeywordChecker.docxMatchList.clear();
			KeywordChecker.csvMatchList.clear();
			KeywordChecker.txtMatchList.clear();
			KeywordChecker.check(dir);
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}