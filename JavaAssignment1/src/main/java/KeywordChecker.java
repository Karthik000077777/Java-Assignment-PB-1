import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class KeywordChecker {
	static Set<String> set = new HashSet<String>();
	static int numOfKey =0;
	static String filePath;
	static Set<String> pdfMatchList = new HashSet(50);
	static Set<String> docxMatchList = new HashSet<String>(50);
	static Set<String> txtMatchList = new HashSet<String>(50);
	static Set<String> csvMatchList = new HashSet<String>(50);
	public static void check(String filePath0) throws IOException {

		filePath = filePath0;
		if(filePath.endsWith("zip")) {
			Unzip.readZipFiles(filePath);
			return;
		}
		else if(filePath.endsWith("pdf")) {
			File pdfFile = new File(filePath);
			messageDisplay();
			readPdf(pdfFile);
			return;
		}
		else if(filePath.endsWith("docx")) {
			File docxFile = new File(filePath);
			messageDisplay();
			readPdf(docxFile);
			return;
		}
		else if(filePath.endsWith("txt")) {
			File txtFile = new File(filePath);
			messageDisplay();
			readPdf(txtFile);
			return;
		}
		else if(filePath.endsWith("csv")) {
			File csvFile = new File(filePath);
			messageDisplay();
			readPdf(csvFile);
			return;
		}
		else {
		File ip = new File(filePath);
		System.out.println("\nFolder Name: "+filePath+File.separator+ip.getName());

// ListFiles returns an array of file pathNames present in the parent directory.
		File[] fp = ip.listFiles();
//		Arrays.sort(fp);
		messageDisplay();

		for (File file : fp) {
			if (file.getName().endsWith("docx")) {// replace with endsWith.
				readDocx(file);
			} 
			else if(file.getName().endsWith("pdf")){
				readPdf(file);
			}
			else if(file.getName().endsWith("txt")){
				readTxt(file);
					}
			else if(file.getName().endsWith("csv")) {
				readCsv(file);
			}
			else if(file.getName().endsWith("zip")) {
				Unzip.readZipFiles(filePath0+file.separator+file.getName());
				return;
			}
			else {
				set.clear();
				pdfMatchList.clear();
				docxMatchList.clear();
				csvMatchList.clear();
				txtMatchList.clear();
				check(filePath0+file.separator+file.getName());
				return;
			}
	
				}
		}
		System.out.println("------------All Files Are Compared------------");
		return;
	}
		
		
	public static void readPdf(File file) {
//		PDDocument.load(), parses a PDF. The given input stream is copied to the memory to enable random access to the pdf. 
		
		PDDocument document = null;
		try {
			document = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PDFTextStripper test = null;
		try {
			test = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String resume = null;
		try {
			resume = test.getText(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String Resume = resume.toLowerCase();
		int count = 0;
		for (String ele : set) {
			if (Resume.contains(ele)) {
				pdfMatchList.add(ele);
				count++;
			}
		}
		System.out.println("File Name: "+file.getName());
		System.out.println("Matched Keywords are: "+pdfMatchList);
		percentage(count, numOfKey);
		try {
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void readDocx(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		XWPFDocument doc = null;
		try {
			doc = new XWPFDocument(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		XWPFWordExtractor ex = new XWPFWordExtractor(doc);
		String resume = ex.getText();
		try {
			ex.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String Resume = resume.toLowerCase();
		int count = 0;
		for (String ele : set) {
			if (Resume.contains(ele)) {
				docxMatchList.add(ele);
				count++;
			}
		}
		System.out.println("File Name: "+file.getName());
		System.out.println("Matched Keywords are: "+docxMatchList);
		percentage(count, numOfKey);
	}
	public static void readTxt(File file) {
		int count = 0;
		try {
			java.io.FileReader fr = new java.io.FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str1; 
			try {
				while((str1 = br.readLine()) != null) {
					for(String ele : set) {
						if(str1.toLowerCase().contains(ele)) {
							txtMatchList.add(ele);
							count++;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(FileNotFoundException ex){
			System.out.print(ex);
		}
				System.out.println("File Name: "+file.getName());
				System.out.println("Matched Keywords are: "+txtMatchList);
				percentage(count, numOfKey);
	}
	public static void readCsv(File file) {
		int count = 0;String line="";
		try {
		java.io.FileReader fr = new java.io.FileReader(file);
		BufferedReader bfr = new BufferedReader(fr);
		while((line = bfr.readLine()) != null) {
			for(String ele1 : set) {
				if(line.toLowerCase().contains(ele1)) {
					csvMatchList.add(ele1);
					count++;
				}
			}
		}
		}catch (Exception e) {
			System.out.print(e);
		}
		System.out.println("File Name: "+file.getName());
		System.out.println("Matched Keywords are: "+csvMatchList);
		percentage(count, numOfKey);
	}
		public static void percentage(int count, int num) {
			if (count == 0) {
				System.out.println("0%");
			} 
			else {
				System.out.println("matched keywords count is " + count);
				double diff = num - count;
				double per = (diff / num) * 100;
				double matched = 100 - per;
				System.out.println(String.format("%.2f", matched)+"%\n");	
			}
		}
		public static void messageDisplay() {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter the number of Keywords required to compare :");
			numOfKey = Integer.parseInt(input.nextLine());
			System.out.println("Enter the keywords :");
			for (int i = 0; i < numOfKey; i++) {
				String keyword = input.nextLine();
				set.add(keyword);
			}
			System.out.println("List of keywords are : ");
			System.out.println(set);
			System.out.println(" ");
			System.out.println("percentages of files containing Keywords are : ");
			System.out.println(" ");
		}
		
}
