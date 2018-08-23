# Email Token Microservice CodeSample

## Background
The purpose of this challenge is to demonstrate how you architect, implement, test and organize your software projects. As such, there will be less of an emphasis on algorithms and more on the project as a whole. You can use external libraries but should document their usage.

## Description
You've been tasked with writing an email token microservice. The rules of email tokens are described below.

This project should:
- Listen on port 80
- Accept an email address
- Generate an email token
- Return the generated token in the response

## Email Tokens
You're building an awesome webapp. You regularly send emails to users of this webapp. Some of these emails contain calls to action, prompting the user to click a link and complete some action. To make the process easier for the user, you want to include a token in this link so that the action can be automatically verified without the user having to log in. This service is responsible for generating those tokens.

## Deliverables
1. A git repository containing all the code needed to run the microservice.
2. Instructions for installing and running the microservice and test suite.

Instructions:
1. Import the project into IntelliJ from external sources and run the detected configuration:
   [Main class: com.gonzobeans.emailtoken.Application]
2. Install Postman and try the service:

  a. 
  REQUEST 
  [POST] http://localhost:80/token
    {
	      "emailAddress": "user@example.com",
	      "tokenId": "123",
	      "applicationSecret": "AnyStringYouWant"
    }
   RESPONSE:
    {
        "emailAddress": "dave@teleport.com",
        "usageId": "123",
        "token": "fbc3fe93749a49fd8139eacf1241be8c"
    }
