package com.trainticketmanagement;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;



public class Admin {
	int trainID;
	String trainName;
	String source;
	String destination;
	LocalDate trainDate;
	LocalTime arrivalTime;
	LocalTime departureTime;
	int availableSeats;
	int cost;
	
	public Admin() throws ClassNotFoundException, SQLException
	{
		Scanner scan=new Scanner(System.in);
		boolean res = signin();
		if(res)
		{
		int choice=0;
		do
		{
		System.out.print("\n\n1.Add Train details\n2.View Train details\n3.Update train details\n4.Delete train details\n5.Exit");
		System.out.print("\nEnter your choice: ");
		try
		{
		choice=scan.nextInt();
		}
		catch(InputMismatchException e) {
		System.out.println("Please enter correct input in number only");
		}
		switch(choice)
		{
		case 1:
		addTrainDetails();
		break;
		case 2:
		viewTrainDetails();
		break;
		case 3:
		updateTrainDetails();
		break;
		case 4:
		deleteTrainDetails();
		break;
		}
		}while(choice!=5);
	}
	
	else
	{
		System.out.println("Please enter correct credentials, Try Again");
		return;
	}
}	
	private boolean signin() throws ClassNotFoundException, SQLException {
		String userName, password, dbUser="", dbPassword="";
		boolean res;
		Scanner scan=new Scanner(System.in);
		System.out.println("Please login to proceed");
		System.out.print("Enter user name: ");
		userName = scan.next();
		System.out.print("Enter password: ");
		password = scan.next();
		
		Connection connection = Admin.getConnection();
		Statement statement = connection.createStatement();
		String query = "select * from credentials where username='"+userName+"'";
		ResultSet resultSet = statement.executeQuery(query);
		while(resultSet.next())
		{
			dbUser = resultSet.getString(1);
			dbPassword = resultSet.getString(2);
		}
		if(userName.equals(dbUser)&&password.equals(dbPassword))
			res= true;
		else
			res = false;
		return res;
	}

	private void addTrainDetails() throws ClassNotFoundException, SQLException {
		Scanner scan=new Scanner(System.in);
		System.out.println("Enter below details to add a Train");	
		System.out.print("Train Name: ");
		trainName= scan.nextLine();
		System.out.print("Source: ");
		source= scan.nextLine();
		System.out.print("Destination: ");
		destination= scan.nextLine();
		System.out.print("Date(yyyy-MM-dd): ");
		String strDate = scan.next();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		trainDate = LocalDate.parse(strDate, dateFormatter);
		System.out.print("Arrival Time(HH:mm): ");
		String strArrTime = scan.next();
		DateTimeFormatter arrTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 
		arrivalTime = LocalTime.parse(strArrTime, arrTimeFormatter);
		System.out.print("Departure Time(HH:mm): ");
		String strDepTime = scan.next();
		DateTimeFormatter depTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 
		departureTime = LocalTime.parse(strDepTime, depTimeFormatter);
		System.out.print("Available Seats: ");
		availableSeats = scan.nextInt();
		System.out.print("Cost per seat: ");
		cost = scan.nextInt();
		
		Connection connection = Admin.getConnection();
		String query = "insert into traindetails(trainname,source,destination,date,arrivaltime,departuretime,availableseats,fare) values(?,?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query); 
		statement.setString(1, trainName);
		statement.setString(2,source);
		statement.setString(3,destination);	
		statement.setDate(4, java.sql.Date.valueOf(trainDate)); 
		statement.setTime(5, java.sql.Time.valueOf(arrivalTime));
		statement.setTime(6, java.sql.Time.valueOf(departureTime));
		statement.setInt(7, availableSeats);
		statement.setInt(8, cost); 
		int rows = statement.executeUpdate();
		System.out.println(rows+": Rows Affected "+"\n"+"Train details added to DB successfully\n");
		connection.close();
	}
	
	public void viewTrainDetails() throws ClassNotFoundException, SQLException {
		System.out.println("Trains Listed");
		Connection connection = Admin.getConnection();
		Statement statement = connection.createStatement();
		String query = "select * from traindetails";
		ResultSet resultSet = statement.executeQuery(query);
		System.out.println("TRAIN ID\tTRAIN NAME\t\tSOURCE\t\tDESTINATION\t\tDATE\t\tARRIVAL TIME\tDEPT TIME\tSEATS\tFARE");
		while(resultSet.next())
		{
			System.out.println(
					resultSet.getInt(1)+"\t\t"+
					resultSet.getString(2)+"\t\t"+
					resultSet.getString(3)+"\t\t"+
					resultSet.getString(4)+"\t\t"+
					resultSet.getDate(5)+"\t\t"+
					resultSet.getTime(6)+"\t"+
					resultSet.getTime(7)+"\t"+
					resultSet.getInt(8)+"\t"+
					resultSet.getInt(9));
		}
		connection.close();	
	}
	
	private void updateTrainDetails() throws ClassNotFoundException, SQLException {
		Scanner scan1=new Scanner(System.in);
		boolean trainExist=false;
		System.out.println("Enter below details to update Train");
		System.out.print("Train ID: ");
		trainID=scan1.nextInt();
		scan1.nextLine();
		Connection connection = Admin.getConnection();
		Statement statement = connection.createStatement();
		String query = "select trainid from traindetails";
		ResultSet resultSet = statement.executeQuery(query);
		while(resultSet.next())
		{
			if(resultSet.getInt(1)==trainID)
			{
				trainExist=true;
				//return;
			}
		}
		if(trainExist) {
		System.out.print("Source: ");
		source= scan1.nextLine();
		System.out.print("Destination: ");
		destination= scan1.nextLine();
		System.out.print("Date(yyyy-MM-dd): ");
		String strDate = scan1.next();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		trainDate = LocalDate.parse(strDate, dateFormatter);
		System.out.print("Arrival Time(HH:mm): ");
		String strArrTime = scan1.next();
		DateTimeFormatter arrTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 
		arrivalTime = LocalTime.parse(strArrTime, arrTimeFormatter);
		System.out.print("Departure Time(HH:mm): ");
		String strDepTime = scan1.next();
		DateTimeFormatter depTimeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 
		departureTime = LocalTime.parse(strDepTime, depTimeFormatter);
		System.out.print("Available Seats: ");
		availableSeats = scan1.nextInt();
		System.out.print("Cost per seat: ");
		cost = scan1.nextInt();
		String updateQuery = "update traindetails set source=?,destination=?,date=?,arrivaltime=?,departuretime=?,availableseats=?,fare=? where trainid=?";
		PreparedStatement updateStatement = connection.prepareStatement(updateQuery); 
		updateStatement.setString(1, source);
		updateStatement.setString(2, destination);
		updateStatement.setDate(3, java.sql.Date.valueOf(trainDate));
		updateStatement.setTime(4, java.sql.Time.valueOf(arrivalTime));
		updateStatement.setTime(5, java.sql.Time.valueOf(departureTime));
		updateStatement.setInt(6, availableSeats);
		updateStatement.setInt(7, cost);
		updateStatement.setInt(8, trainID);
		int rows = updateStatement.executeUpdate();
		if(rows!=0)
			System.out.println("Train details updated successfully");
			else
				System.out.println("\nFailed to updated train details");
		}
		else
		{
			System.out.println("Train ID doesnot exist. Please try again...!!");
			return;
		}
	}
	
	private void deleteTrainDetails() throws SQLException, ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter train ID to delete a train: ");
		trainID=scan.nextInt();
		Connection connection = Admin.getConnection();
		String query = "delete from traindetails where trainid=?";
		PreparedStatement statement = connection.prepareStatement(query); 
		statement.setInt(1, trainID); 
		int rows = statement.executeUpdate();
		if(rows!=0)
			System.out.println("\nTrain details deleted successfully\n");
		else
			System.out.println("\nEntered Train ID doesnot exist.. Please check..!!!");
	}
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{	
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/trainsystem", "root", "mysql@8.4");
	}
}
