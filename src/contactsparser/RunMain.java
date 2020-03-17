/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsparser;

import java.util.Scanner;

/**
 *the class {@code RunMain} is the main class containing the main method. it
 * instantiates the method {@code ContactsParser} in this package.
 * ^
 * input requires if someone wishes to use this class is {@code pathToHtmlFile}
 * and output given is {@code pathToVCFFile}
 * @author kelli
 */
public class RunMain
{
    public static void main (String[] args)
    {
        //requesting path to the html file
        String pathToHtmlFile = "/home/kelli/contacts.html";
//        Scanner readInput = new Scanner(System.in);
//        System.out.println("---------------------------------------------"
//                + "-------------");
//        
//        System.out.println("Enter path to html file: ");
//        pathToHtmlFile = readInput.nextLine();
//        
//         System.out.println("---------------------------------------------"
//                + "-------------");
        
        //instantiating object of class {@code ContactsParser} and call its 
        //method. returns String
        ContactsParser parser = new ContactsParser();
        String pathToVCFFile = parser.parseContactsIn(pathToHtmlFile);
        System.out.println(pathToVCFFile);
    }
}
