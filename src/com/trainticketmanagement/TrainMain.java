package com.trainticketmanagement;

import java.util.*;

public class TrainMain {
public static void main(String[] args) 
{
	List<TrainTicketBooking> list=new ArrayList<TrainTicketBooking>();
	Scanner scan=new Scanner(System.in);
	int choice=0;
	do
	{
	System.out.print("\n\n1.Add Booking\n2.View Booking\n3.Update Booking\n4.Cancel Booking\n5.Exit");
	System.out.print("\nEnter your choice: ");
	choice=scan.nextInt();
	switch(choice)
	{
	case 1:
	System.out.print("\nEnter Train Name: ");
	String trainName=scan.next();
	System.out.print("Enter Passanger Name: ");
	String passangerName=scan.next();
	System.out.print("Enter No of passangers: ");
	int no_of_passanger=scan.nextInt();
	System.out.print("Enter Mobile Number: ");
	int mobileNumber=scan.nextInt();
	TrainTicketBooking obj=new TrainTicketBooking();
	obj.addBooking(trainName, passangerName, no_of_passanger, mobileNumber);
	list.add(obj);
	System.out.println("Booking Details added");
	break;
	case 2:
	System.out.print("\nTrain Name\tPassanger Name\tNo of Passangers\tMobile Number");
	for(int i=0;i<list.size();i++)
	list.get(i).viewBooking(list.get(i));
	break;
	case 3:
	System.out.print("\nEnter Passanger Name to update details: ");
	String name=scan.next();
	int count=0;
	for(int i=0;i<list.size();i++)
	{
		if(name.equals(list.get(i).passangerName))
		{
			System.out.print("Enter No of passangers: ");
			int no_of_pass=scan.nextInt();
			System.out.print("Enter Mobile Number: ");
			int mobile=scan.nextInt();
			list.get(i).updateBooking(list.get(i),no_of_pass,mobile);	
		    count++;
			System.out.println("Passanger details updated");
		    break;
		}
	}
	if(count==0)
	    System.out.println("\nPassanger name does not exist");
	break;
	case 4:
		System.out.print("\nEnter Passanger Name to cancel booking: ");
		String name1=scan.next();
		int count1=0;
		for(int i=0;i<list.size();i++)
		{
			if(name1.equals(list.get(i).passangerName))
			{
				list.remove(list.get(i));
				System.out.println("\nBooking has been cancelled");
				count1++;
				break;
			}
		}
		if(count1==0)
		System.out.println("\nPassanger name does not exist");
	break;
	}
	}while(choice!=5);
scan.close();
}
}
