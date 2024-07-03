package com.trainticketmanagement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TrainMain {
public static void main(String[] args) throws ClassNotFoundException, SQLException 
{	
	Scanner scan=new Scanner(System.in);
	int choice=0;
	System.out.println("WELCOME to RAILWAY RESERVATION SYSTEM");
	System.out.println("Press 1 for Admin\nPress 2 for Passanger");
	System.out.print("Enter your choice: ");
	try
	{
	choice=scan.nextInt();
	}
	catch(InputMismatchException e) {
	System.out.println("Please enter correct input in number only");
	return;
	}	
	Admin admin;
	Passanger passanger;
	if(choice==1)
		admin = new Admin();
	if(choice==2)
		passanger = new Passanger();	
	if(choice!=1&&choice!=2)
		System.out.println("Please press 1 or 2 only");
}
}
