package project.structure.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JavaProjectStructureParser {
	
	public static synchronized void printFileStructure(CompilationUnit compilationUnit){
		
		compilationUnit.accept(new ASTVisitor() { 
			
			   public boolean visit(PackageDeclaration nodeOfPackage) {
				   System.out.println("Package declaration: " + nodeOfPackage.getName());
				   return true;
			   }
				
			   public boolean visit(TypeDeclaration nodeofClass) {
				   System.out.println("Class declaration: " + nodeofClass.getName());
				   return true;
			   }
	            
					
			   public boolean visit(MethodDeclaration nodeOfMethod){
				   System.out.println("Method declaration: '"+nodeOfMethod.getName());
				   return true;
			   }          
		   });
		System.out.println("");
	}
	
	public static CompilationUnit parseSourceCodeStructure(String contentOfFile) {
		
		   ASTParser astParser = ASTParser.newParser(AST.JLS3);
		   astParser.setSource(contentOfFile.toCharArray());
		   astParser.setKind(ASTParser.K_COMPILATION_UNIT);	 
		   final CompilationUnit compilationUnit = (CompilationUnit) astParser.createAST(null);
		   //System.out.println("Parsed");
		   return compilationUnit;
		   
	   }
	
	public static String readFileToString(String filePath) throws IOException {
		
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));	 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}	 
		reader.close();
		System.out.println("Read!");
		return  fileData.toString();	
	}

	public static File[] getListOfAllFiles(){
		//String directoryOfSourceCode = "C:/Users/Ringaile/workspace/Financisto/src/ru/orangesoftware/financisto/activity";			 
		String directoryOfSourceCode = "C:/Users/Ringaile/workspace/pattern/src";
		File root = new File(directoryOfSourceCode);
		File[] filesOfSourceCode = root.listFiles();
		return filesOfSourceCode;
	}
	
	public static void main(String args[]){
		
		File[] filesOfSourceCode = getListOfAllFiles();
		
		for (File file : filesOfSourceCode){
			   if(file.isFile()){
				   final String filePath = file.getAbsolutePath();
				   
				   new Thread(new Runnable(){
						public void run() {
							try {
								String contentOfFile = readFileToString(filePath);
								CompilationUnit compilationUnit = parseSourceCodeStructure(contentOfFile);
								printFileStructure(compilationUnit);
							} catch (IOException e) {
								System.out.println("Error!");
							}	
						}
				   }).start();
				   
			   }
		   }  
	}

}