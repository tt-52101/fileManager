*fileManager* 

---

*fileManager* is  an online file manage system. Normal user can upload, download and delete documents. Administer only can get all the users information and other CURD of users.**It's just a practice.**

Techniques:

+ maven+spring boot +mybatis+jquery(ajax and dataTables) 

+ Server Paging by dataTables and PageHelper.

Target:

+ Front-End and Back-End Separation

+ Download and upload files.

+ server paging and simple access control.

---

Prepare:

1. JDK+Mysql+STS+Maven

2. **Create  a test folder in local disk E.** 

E:/test will store your uploaded documents and folder you create when using *fmManager*.

---

How to Start:

1. Run fm.sql to establish database. And modify application.properties based on the database.

2. Use sts import the maven project.And make sure everything ready.

3. Run FileManagerApplication.java. And then go http://localhost:8000/fm/login.html.

4. Login with  roles below:

  + as  an administer( only can get all users' information)  :    username: Adam	   password 123.

  + or go to http://localhost:8000/fm/register.html and register an account  as a normal user and then login. 







