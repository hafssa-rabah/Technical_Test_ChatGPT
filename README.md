Getting Started
This project is a microservices Java application, built using Spring Boot v13, Java 17, Maven, and Tomcat server.

To test the application, follow these steps:

1. Clone the repository onto your local machine using an IDE such as IntelliJ.

2.Run the code.

3.(Use Postman to send a POST request to the endpoint "http://localhost:8080/chatgpt/getanswerandwrite" with the following parameters (for example):

json
Copy code
{
   "question": "What is maven?"
}

4.The result of the request will be written to a file named "output.csv" which is located in the project's source code.
