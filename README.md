<h1>Surveyor</h1>
<h4>A web and android app for creating and distributing surveys</h4>

<h2>Website Appendix / Setup Instructions</h2>

These instructions are here in order to allow you to setup your django installation and development environment on a virtualenv, which essentially isolates a python installation as well as the included packages (django, etc) so that other python installations cannot interfere with this install, and we have a solid base to work on.

NOTE: make sure that once you go through all of these directions, you only interact with the django project from within the virtualenv.  This is explained further down, but just remember: 'workon kinderUpdate'


<h3>----- AMAZON EC2 RUNNING SERVER INSTANCE ----------</h3>

We currently have an amazon ec2 server running the newest version of the site.  

You can access this website by going here: http://ec2-52-11-126-61.us-west-2.compute.amazonaws.com/

Any and all testing is equivalent for this version as well as the django local servers below.  You don't really need to follow these directions, just go to the link above.

In order to login to the site, use the following credentials:

|CREATOR ROLE       | RESPONDENT ROLE   |
|-------------------|-------------------|
|username = create  | username =        |
|password = test    | password =        |


<h3>LINUX INSTRUCTIONS </h3>

1. Install Python v3 from Package manager, as well as python-setuptools and git
	```sudo apt-get install python3-minimal python3-dev```
	```sudo apt-get install python3-setuptools git```
2. ```sudo easy_install3 pip```
3. ```sudo pip3 install virtualenvwrapper```
4.	add the following to your shell's startup file (.bashrc, .zshrc, etc)
    ```
	export WORKON_HOME=$HOME/.virtualenvs
	export PROJECT_HOME=$HOME/directory-you-do-development-in
	source /usr/local/bin/virtualenvwrapper.sh
	```
	replace directory-you-do-development-in with your directory workspace
	
5. Edit the file /usr/local/bin/virtualenvwrapper.sh to the following:

		Change this section:
		```
			if [ “$VIRTUALENVWRAPPER_PYTHON” = “” ]
			then
				VIRTUALENVWRAPPER_PYTHON=”*something*”
			fi
		```
		To this:
		```
			<b>#</b>if [ “$VIRTUALENVWRAPPER_PYTHON” = “” ]
			<b>#</b>then
				VIRTUALENVWRAPPER_PYTHON=<b>”/usr/bin/python3”</b>
			<b>#</b>fi
		```
		
		and run ```. ~/.bashrc```
	
6. ```mkvirtualenv kinderUpdate```

**** Before continuing, you should have (kinderUpdate) at the beginning of your command line ***
**** if you don't, type 'workon kinderUpdate'. to get out of the virtualenv, type 'deactivate' ***

7. ```pip3 install django```
8. navigate to your desired project parent directory (e.g. ~/workspace/)
9. ```git clone https://github.com/kblauer/surveyor.git```
10. ```cd surveyor```
11. To run the local django server, ```python manage.py runserver```
12. Navigate to ```localhost:8000``` in browser


<h3>  WINDOWS INSTRUCTIONS </h3>

1. Uninstall any and all versions of python you currently have installed (sorry :( )
2. Install Python v3 (32-bit) and git
	(make sure to add python to PATH in install options)
	(for git install, choose Use git from Git Bash only, and 
		checkout windows-style, commit unix-style line endings)
3. go here: https://pypi.python.org/pypi/setuptools and install setuptools
	for windows 7, download ez_setup.py and run in the command line ```cd Downloads && python ez_setup.py```

	
4. ```cd \path-to-python3-install\Scripts && easy_install pip```
		!! replace path-to-python3-install with your install location !!
		
5. ```pip install virtualenvwrapper-win```

6. ```mkvirtualenv django17```

**** Before continuing, you should have (django17) at the beginning of your command line ***
**** if you don't, type 'workon django17'. to get out of the virtualenv, type 'deactivate' ***

7. ```cd \```
8. ```pip install django```
9. navigate to your desired project parent directory (e.g. C:\dev\) in windows explorer, 
	then right click and press 'Git Bash Here'
10. in the command line that pops up, enter ```git clone https://github.com/kblauer/surveyor.git```

10. To run the local django server, go back to normal windows command line, make sure you are in the correct virtualenv, and 
	```cd dev-path-here/surveyor && python manage.py runserver```
11. Navigate to ```localhost:8000``` in browser


