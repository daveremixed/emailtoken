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

## Instructions
1. Import the project into IntelliJ from external sources and run the detected configuration:
   [Main class: com.gonzobeans.emailtoken.Application]
2. Install Postman and try the service:
```
  REQUEST 
  [POST] http://localhost:80/token
    {
	"emailAddress": "user@example.com",
	"usageId": "welcome-email-v21",
	"applicationSecret": "AnyStringYouWant"
    }
   RESPONSE:
    {
        "emailAddress": "dave@teleport.com",
        "usageId": "welcome-email-v21",
        "token": "fbc3fe93749a49fd8139eacf1241be8c"
    }
```
Now view your token
```
  REQUEST 
  [GET] http://localhost:80/token/{token}
  [HEADER] applicationSecret = "AnyStringYouWant"
    {
	"emailAddress": "user@example.com",
	"usageId": "welcome-email-v21",
	"applicationSecret": "AnyStringYouWant"
    }
   RESPONSE:
    {
        "emailAddress": "user@example.com",
        "usageId": "welcome-email-v21",
        "token": "fbc3fe93749a49fd8139eacf1241be8c"
    }
```
3. Try these tests:
- Invalid Email
- Omit usageId
- Omit appSecret

## Discusion
Application uses SpringBoot, the embeded in memory database w/JPA for persistence.  I added the Application Secret so tokens / redemptions may only be viewed by the creator.  The AppSecret is hashed and stored as a SHA-1 via the Apache Codec library, they are not persisted in plain text.  For security a service like this should only run HTTPS/TLS1.2 - but requirements asked for HTTP.

The tokenID identifies a usage pattern and is defined entirely by the user.  A user may wish to know which tokens have been redeemed and which tokens have not.

[TBD]
When redeeming a token, a new row will be inserted into a second table recording the specific Token, the dateTime and the IpAddress.  A token redemption can be recorded multiple times, and will only insert more rows.  It is the responsibility of the Application in a higher layer to determine how to use that information (whether or not a token can be reused).  To redeem a token use
[PUT] http://localhost:80/token/{token} and pass the token and include the AppSecret in the headers

I did not get a chance to add tests yet.
I did not get a chance to put this in a wrapper yet.
