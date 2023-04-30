# Multi-Tenant Invoice Management System

In this project, my objective is to build an online Multi-Tenant Invoice Management System. 

## Case Study:
Any company can register with the system and can perform the following actions:

### Add users to the system. 
A user can be an admin or a normal user.
 
### A normal user should be able to do the following:
	1. Update his/her profile.

	2. Create/Manage a one-time invoice.

	3. Create/Manage a recurring invoice. 
	A recurring invoice is generated periodically as set by the user. 
	For eg., The user may create a recurring invoice to be generated on 5th of every month. 
	Once the recurring invoice is created, it is sent automatically by the system to the client.

	4. Create/Manage an Item. Item is the product for which the invoice is generated. 
	For eg., A computer sales dealer create an item “DELL Laptop” and generate an invoice using the item. 
	Item contains the name and price of the product so that the price is autofilled in the Invoice.

	5. Create/Manage a Client. Client is the person/company to which an Invoice is generated and sent. 
	In other words, it is the customer to whom the invoice needs to be billed. 
	General Information about the client needs to be saved; Name, Email, Company, Address, etc.)

	6. Update the status of an invoice as “Paid” for invoices he created.
 
### An admin user should be able to do the following in addition to what a normal user can do:
     
      a. Manage Users in the system.
      b. Upload a “XML” file containing client list that can be imported into the system.
      c. Export the summary of invoices generated in a week/month/quarter/year in an excel format.
      d. Customize the application:
         1. You can upload a company logo for use on the "header" section of the website and invoices.
         2. Set option to:
              -> Automatically send a reminder email to the client when the due date is passed.
              -> Send a thank-you email to the client when the invoice status changes to “Paid ”.
              -> Change the display of dates and currencies in the system.
 
### Dashboard: 

Every user, when logs into the system lands on the dashboard.

The dashboard shows the following information in different widgets. Each widget should be loaded via Ajax.

	1. The upcoming 3 invoices that are due.
	2. The 3 most recently created invoices.
	3. A bar-graph show the total collection received & total amount due on a daily/weekly/monthly basis.
 
### Invoice: 

A user can create an invoice by adding Items to it and associating it with a Client.

Once the Invoice is created, the user clicks on “save and send”, the system generates a PDF of the invoice and sends it as an attachment to the Client. 
Every Invoice has a status associated to it. 

**The status change can happen as follows:**

	1. The initial status of the Invoice is “Draft”. 
	Once the invoice is emailed to the Client, the status of the invoice changes to “Sent”.
	
	2. The Invoice has a due date associated to it. 
	If the invoice status is not changed to “Paid” within the due date, the status changes to “Due”. 
	Based on the administrator’s settings, an email may be sent to the Client reminding of the invoice.
	
	3. The user can change the status of the Invoice to “Paid” at any time. 
	Based on the administrator’s settings, a thank-you mail may be sent to the Client. 
	The user can change the status of the Invoice as “Paid” only for those which he has created.
 
### Search: 
A user can search for an Invoice based on the Invoice Id, Client’s name or an Item.
