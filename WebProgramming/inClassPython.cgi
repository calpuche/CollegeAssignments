#!/home/calpuche/virtualenv/python3/3.4/bin

import cgi
import cgitb
import classfun



def generate_form(name= '', hours=''):
	print ("<form method='post'> ")
	print(" Enter name: <input type='text' name='name' value='" + name + "' />\n")
	print ("<br  />Hours worked: <input type='text' name='hours' value='" + hours + "' />\n")
	print ("<input type='submit' name='submitHours' />\n")
	print ("</form>\n")
	


# Define main function.
def main():
	myPage.printDocHeading("../css/layout.css", "python example #2 ")
	print ("<h3> Hours worked Example </h3>\n")
	cgitb.enable()
	form = cgi.FieldStorage()
	if form :
		name=''
		hours=''
		name = form.getfirst('name', '')
		hours= form.getfirst('hours', '')
		print("Hours: ",hours, " Name: ", name, "<br />\n")
		if hours == '' or name== '':
			print("<h3> you must enter data for both values </h3>\n")
			generate_form(name, hours)
		else:
			try:
				hours = int(hours)
				print (" Your hours: " , str(hours), "<br />\n")
				if hours > 40:
					print ("Hi " +name + " you worked overtime .. " + str(hours) + " hours")
				else:
					print ("Hi " + name)
					print (" you worked 40 or less hours, no overtime for ")
					print (str(hours) + " hours")
			except:
				hours=''
				print ("Please enter a whole number of hours. <br />\n")
				generate_form(name, hours)
	else:
		print ("<h4> Please enter data below:</h4>\n")
		generate_form()

# Call main function.
main()


print ("</body>\n")
print ("</html>\n")