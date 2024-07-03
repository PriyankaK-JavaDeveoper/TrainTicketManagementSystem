package com.trainticketmanagement;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Passanger {
String mobileNo;
String passangerName;
int trainID;
String trainName;
String source;
String destination;
LocalDate trainDate;
Time arrivalTime;
Time departureTime;
int no_of_passangers,availableSeats;
int costPerSeat,totalCost;


public Passanger()
{
	int choice=0;
	Scanner scan=new Scanner(System.in);
	do
	{
	System.out.print("\n\n1.Book your Train\n2.View your ticket\n3.Cancel your ticket\n4.Exit");
	System.out.print("\nEnter your choice: ");
	choice=scan.nextInt();
	switch(choice)
	{
	case 1:
		try {
			searchTrains();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	break;
	case 2:
		try {
			viewBooking();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	break;
	case 3:
		try {
			cancelBooking();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	break;
	}
	}while(choice!=4);
scan.close();
}
public void searchTrains() throws ClassNotFoundException, SQLException 
{
	Scanner scan1 = new Scanner(System.in);
	System.out.println("Enter below details to book your train");
	System.out.print("Source: ");
	source= scan1.nextLine();
	System.out.print("Destination: ");
	destination= scan1.nextLine();
	System.out.print("Date(yyyy-MM-dd): ");
	String strDate = scan1.next();
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	trainDate = LocalDate.parse(strDate, dateFormatter);
	
	Connection connection = Admin.getConnection();
	Statement statement = connection.createStatement();
	String query = "select * from traindetails where source='"+source+"'and destination='"+destination+"'and date='"+trainDate+"'";
	ResultSet resultSet = statement.executeQuery(query);
	if(resultSet.isBeforeFirst()) 
	{
	System.out.println("Trains Available");
	System.out.println("TRAIN ID\tTRAIN NAME\t\tSOURCE\t\tDESTINATION\tDATE\t\tARRIVAL TIME\tDEPT TIME\tSEATS\tFARE");
	while(resultSet.next())
	{
		System.out.println(
				resultSet.getInt(1)+"\t\t"+
				resultSet.getString(2)+"\t\t"+
				resultSet.getString(3)+"\t"+
				resultSet.getString(4)+"\t\t"+
				resultSet.getDate(5)+"\t\t"+
				resultSet.getTime(6)+"\t"+
				resultSet.getTime(7)+"\t"+
				resultSet.getInt(8)+"\t"+
				resultSet.getInt(9));
	}
	connection.close();	
	System.out.print("\nEnter train ID to book your ticket: ");
	scan1.nextLine();
	trainID = scan1.nextInt();
	bookTicket(trainID,source,destination,trainDate);
	}
	else
	{
		System.out.println("Sorry...No trains available at your search.. Please try for another date or location..!!");
		return;
	}
}

public void bookTicket(int trainNo,String source, String destination, LocalDate trainDate) throws ClassNotFoundException, SQLException
{
	Scanner scan = new Scanner(System.in);
	System.out.println("Please enter below details for booking");
	System.out.print("Mobile No: ");
	mobileNo = scan.nextLine();
	System.out.print("Passanger Name: ");
	passangerName = scan.nextLine();
	System.out.print("No of seats wanted: ");
	no_of_passangers = scan.nextInt();
	
	Connection connection = Admin.getConnection();
	Statement statement = connection.createStatement();
	String query = "select trainname,arrivaltime,departuretime,availableseats,fare from traindetails where trainid="+trainNo;
	ResultSet resultSet = statement.executeQuery(query);
	while(resultSet.next())
	{
		trainName = resultSet.getString(1);
		arrivalTime = resultSet.getTime(2);
		departureTime = resultSet.getTime(3);
		availableSeats = resultSet.getInt(4);
		costPerSeat = resultSet.getInt(5);
	}
	
	if(no_of_passangers<=availableSeats)
	{
		totalCost = no_of_passangers*costPerSeat;
		//System.out.println(mobileNo+"\t"+passangerName+"\t"+trainNo+"\t"+trainName+"\t"+source+"\t"+destination+"\t"+trainDate+"\t"+arrivalTime+"\t"+departureTime+"\t"+no_of_passangers+"\t"+totalCost);
		String insertPassangerQuery = "insert into passangerdetails values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement statement1 = connection.prepareStatement(insertPassangerQuery); 
		statement1.setString(1, mobileNo);
		statement1.setString(2, passangerName);
		statement1.setInt(3, trainNo); 
		statement1.setString(4, trainName);
		statement1.setString(5, source);
		statement1.setString(6, destination);
		statement1.setDate(7, java.sql.Date.valueOf(trainDate));
		statement1.setTime(8, arrivalTime);
		statement1.setTime(9, departureTime);
		statement1.setInt(10, no_of_passangers); 
		statement1.setInt(11, totalCost); 
		int rows = statement1.executeUpdate();
		if(rows!=0)
			System.out.println("Booking Confirmed...Enjoy your journey..!!");
		else
			System.out.println("Booking not confirmed..Pls try again..!!");
	}
	else
	{
		System.out.println("There are only "+availableSeats+" seats available\nPlease try again");
		return;
	}
}

public void viewBooking() throws ClassNotFoundException, SQLException
{
	Scanner scan=new Scanner(System.in);
	System.out.print("Enter mobile number to view ticket: ");
	String mobileNo=scan.next();
	Connection connection = Admin.getConnection();
	Statement statement = connection.createStatement();
	String query = "select * from passangerdetails where mobileno='"+mobileNo+"'";
	ResultSet resultSet = statement.executeQuery(query);
	if(resultSet.isBeforeFirst()) {
	while(resultSet.next())
	{
		System.out.println("YOUR TICKET DETAILS ARE BELOW..!!");
		System.out.print("\nMobile No: "+resultSet.getString(1));
		System.out.print("\nPassanger Name: "+resultSet.getString(2));
		System.out.print("\nTrain ID: "+resultSet.getInt(3));
		System.out.print("\nTrain Name: "+resultSet.getString(4));
		System.out.print("\nSource: "+resultSet.getString(5));
		System.out.print("\nDestination: "+resultSet.getString(6));
		System.out.print("\nJourney Date: "+resultSet.getDate(7));
		System.out.print("\nArrival Time: "+resultSet.getTime(8));
		System.out.print("\nDeparture Time: "+resultSet.getTime(9));		
		System.out.print("\nNo of Passangers: "+resultSet.getInt(10));				
		System.out.print("\nTotal Ticket Cost: "+resultSet.getInt(11));				
	}
	connection.close();	
	}
	else
	{
		System.out.println("No tickets booked for this mobile number");
		return;
	}
}
public void cancelBooking() throws ClassNotFoundException, SQLException
{
	Scanner scan=new Scanner(System.in);
	System.out.print("Enter mobile number to cancel ticket: ");
	String mobileNo=scan.next();
	Connection connection = Admin.getConnection();
	String query = "delete from passangerdetails where mobileno=?";
	PreparedStatement statement = connection.prepareStatement(query); 
	statement.setString(1, mobileNo); 
	int rows = statement.executeUpdate();
	if(rows!=0)
		System.out.println("\nTicket cancelled successfully\n");
	else
		System.out.println("\nTicket not available for this mobile number..!!!");
}
}
