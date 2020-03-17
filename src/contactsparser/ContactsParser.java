/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsparser;

import java.io.*;
import java.nio.file.Files;
import static java.nio.file.Files.write;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;



/**
 *i wrote the class {@code ContactsParser} when i lost all my contacts and had no 
 * backup so my only choice was telegram which doesnt support exporting contacts
 * back to my phone. 
 * i exported the contacts from telegram but were saved in a .html file and did 
 * feature anywhere in my contacts list.
 * so i wrote this program so i can parse the contacts from the html file and 
 * store them in a vcf file.
 * inputs required are {@code file_path} 
 * output given is {@code vcffile_path} when terminated successfully
 * 
 * @author kelli
 */
public class ContactsParser
{
    String htmlFile = "";
    String htmlLine;
    Path parentPath;
    Path VCFPath ;
    String VCFfileName = "contacts.vcf";
   
    
    public String parseContactsIn(String pathToHtmlFile) 
    {
       
        Path htmlPath = Paths.get(pathToHtmlFile);
        this.parentPath = htmlPath.getParent();
        System.out.println(String.valueOf(htmlPath.getParent()));
         write_and_ReturnVCF();
        readHtmlFileIn(htmlPath);
        return null;
        
    }

    private void readHtmlFileIn(Path htmlPath) 
    {
        try
        {
          InputStream in = Files.newInputStream(htmlPath);
          BufferedReader readHtml = new BufferedReader(new InputStreamReader(in));
            String line;
                  while ((line = readHtml.readLine())!=null )
                  {
                      this.htmlFile = this.htmlFile.concat(line);
                  }
          
        }catch(IOException x){
            System.out.println("canot find the file \""+htmlPath.getFileName()+"\" ");
            System.out.println("solution:");
            System.out.println("check your file name, if right the file path \"" +htmlPath.getParent()+"\" might be wrong");
        }
        
            Document document = Jsoup.parse(this.htmlFile);
            Elements divNameElements = document.select("div.name.bold");
            Elements divNumberElements = document.getElementsByClass("details_entry details");
            
           
            for (int i = 0; i<divNameElements.size(); i++){
               String contactName = Jsoup.parse(divNameElements.get(i).toString()).text();
               String contactTel = Jsoup.parse(divNumberElements.get(i).toString()).text();
                System.out.println(contactName);
               
                write_and_ReturnVCF( contactName.toUpperCase() , editContactTel(contactTel) );
            }
    }

    private String write_and_ReturnVCF()
    {

        try{
        String path = String.valueOf(this.parentPath);
        path = (path.concat("/").concat(this.VCFfileName));
        this.VCFPath = Paths.get(path);
        OutputStream out = Files.newOutputStream(VCFPath);
        out = Files.newOutputStream(VCFPath);
        return String.valueOf(VCFPath);
        }catch (IOException e){
            System.err.println(e);
        }finally{
           
        }
        return null;
     }
    
     private void write_and_ReturnVCF( String name , String Tel_no )
    {
        String newLine = "\n";
        String line1 = "BEGIN:VCARD";
        String line2 =  "VERSION:2.1";
        String line3 = (("N:;").concat(name)).concat(";;;");
        String line4 = "FN:".concat(name);
        String line5 =  "TEL;CELL:".concat(Tel_no);
        String line6 = "END:VCARD";
        
        try{
        String path = String.valueOf(this.parentPath);
        path = (path.concat("/").concat(this.VCFfileName));
        this.VCFPath = Paths.get(path);
        System.out.println(String.valueOf(VCFPath));
       BufferedWriter out = new BufferedWriter(new FileWriter(path , true));

         
                   
                    out.write(line1);
                   // System.out.print(line1);
                    out.write(newLine);
                   // System.out.print(newLine);
                    
              
                    out.write(line2);
                  //  System.out.print(line2);
                    out.write(newLine);
                  //  System.out.print(newLine);
                
                    out.write(line3);
                   // System.out.print(line3);
                    out.write(newLine);
                   // System.out.print(newLine);
                    
                    out.write(line4);
                    //System.out.print(line4);
                    out.write(newLine);
                   // System.out.print(newLine);
                    
                    out.write(line5);
                 //   System.out.print(line5);
                    out.write(newLine);
                  //  System.out.print(newLine);
                    
                    out.write(line6);
                   // System.out.print(line6);
                    out.write(newLine);
                 //   System.out.print(newLine);
           
            out.close();
        }catch (IOException e){
            System.err.println(e);
        }
     }

    private String editContactTel(String contactTel) {
        // convert format 00254718526212 to 0718 526212
        char tel[]=
       contactTel.toCharArray();
        if (tel.length == 14)
        {
        char tel2[] = new char[10];
        tel2[0]= '0';
        tel2[1]= '7';
        int a = 2;
        for(int i = 6; i< tel.length; i++)
        {
            tel2[a] = tel[i];
            a++;
        }
        System.out.println(String.valueOf(tel2));
        return String.valueOf(tel2);
        }else{
            System.out.println(String.valueOf(contactTel));
        return contactTel;
        }
    }
     
    
    
}
