# Weathor Report
 




## Features
- Implemented JWT Token Based Authentication 
- Obtain a concise summary of the weather report tailored to a specific location.
- Access a detailed hourly weather report customized for a particular location.
- Implemented Stanford NLP to reduce API calls for handling incorrect specific Location details 





##  Getting Started
  ###  1. Installation

  Clone this repository to your local machine using the following HTTPS link:

  https://github.com/shivasaiparsha/SunbaseFrontEnd/



  


 ### 2. Set up project

 -   Open your favourite code editor and follow these below steps to clone the project:

- click on file in your code editor
- click one new file
- select upload file from version control
- paste the above mentioned url in the given box
- wait for few mintues to set up project in your code editor
- click on run button it will take some time to relaod the project

### 3. Api Testing 
- Once project successfully running on port 8080, you can now fully engage with the application and witness its capabilities in real-time.
 
-  Recommended Post man to test api's 

 #### Request to /welcome endpoint
 
  http://localhost:8080/welcome

![Screenshot (124)](https://github.com/shivasaiparsha/Weather_Report/assets/112009608/2bd8ff99-43a6-4732-b666-52366bc98243)

  --
- The user need to send a request to the /welcome endpoint to receive a username and password.
#### Get JWT Token 
  http://localhost:8080/auth/login
  --
  ![Screenshot (125)](https://github.com/shivasaiparsha/Weather_Report/assets/112009608/cf17222b-cb24-45e2-b2af-596c17d2facd)

-  With the username and password obtained from the /welcome response, the user sends another request to the /auth/login endpoint to authenticate. This endpoint likely requires the username and password in the request body or as parameters.
#### Receive token
- Upon successful authentication, the /auth/login endpoint responds with a token. This token serves as a form of authentication for subsequent requests.

#### Leverage the application
 - To use the application's features, the user need to includes the token received in the previous step as a bearer token in the Authentication header of subsequent requests. This allows the user to access protected resources or perform actions on behalf of the authenticated user.

 #### Location-Specific Weather Report Summary
  http://localhost:8080/api/v1/weather/getWeatherByPlaceName/{ enter Location Name Here}
  --
  ![Screenshot (126)](https://github.com/shivasaiparsha/Weather_Report/assets/112009608/15a08c67-b534-4889-9f79-b8a1e6e4e859)

- Upon sending request to the /api/v1/weather/getWeatherByPlaceName/{name} endpoint, users can retrieve a summary of the weather report for a specific location. This summary offers a concise overview of current weather conditions, encompassing temperature, humidity, precipitation, wind speed, and atmospheric pressure. 

#### Hourly Weather Forecast for Specified Location

http://localhost:8080/api/v1/weather/getHourlyForecastByLocationName/{ enter placeName here }
  --
- Upon sending request to  /getHourlyForecastByLocationName endpoint provides end users with a detailed weather forecast specific to a given location name. This forecast encompasses hourly updates, offering insights into temperature variations, precipitation chances, wind speeds, and atmospheric conditions tailored to the specified location.


- **Note:Prior to accessing this API, users are required to subscribe to a paid subscription plan. The subscription grants access to the full suite of API features and ensures uninterrupted service delivery.** 

 


## Exception Handling
### Enhanced error Handling
- Our API integrates advanced natural language processing (NLP) capabilities powered by Stanford NLP to enhance error handling when users input incorrect location names. By leveraging sophisticated language analysis, the system intelligently detects and identifies erroneous location entries in user queries.

### Reduced API Calls
- Through the utilization of Stanford NLP, erroneous location inputs are intercepted and validated locally, significantly reducing the need for unnecessary API calls. This proactive approach ensures optimal resource utilization and enhances the efficiency of our service.
## Feedback

If you have any feedback, please reach out to us at shivasaiparsha140@gmai.com

