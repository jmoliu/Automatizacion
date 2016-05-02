package Testing_Pack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RWfile {
	public static String[] readFile (String source){
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    ArrayList<String> elements = new ArrayList<String>();
		try {
	         archivo = new File (source);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);
	 
	         String linea;
	         while((linea=br.readLine())!=null){
	        	 elements.add(linea);
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }		
		return elements.toArray(new String[0]);
	}

	public static void writeFile (String source, List<String> content){
		try {

			//String content = "This is the content to write into file";

			File file = new File(source);

			// Si no existe, lo creo
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String element : content) {
				bw.write(element);
				bw.write("\n");
			}
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main (String[]args){
		String[] products = {"producto1","producto2"};
		//writeFile ("/Users/joliu/Desktop/allProducts.txt", products);
	}
}