package com.trainticketmanagement;

public class TrainTicketBooking {
String trainName;
String passangerName;
int no_of_passanger;
int mobileNumber;
public void addBooking(String trainName, String passangerName, int no_of_passanger, int mobileNumber) 
{
	this.trainName = trainName;
	this.passangerName = passangerName;
	this.no_of_passanger = no_of_passanger;
	this.mobileNumber = mobileNumber;
}
public void viewBooking(TrainTicketBooking obj)
{
	System.out.print("\n"+obj.trainName+"\t\t"+obj.passangerName+"\t\t"+obj.no_of_passanger+"\t\t\t"+obj.mobileNumber);
}
public void updateBooking(TrainTicketBooking obj,int no_of_pass, int mobile)
{
	obj.no_of_passanger = no_of_pass;
	obj.mobileNumber = mobile;
}
}
