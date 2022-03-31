Final Project Proposal

Group Members: Hannah Maung, Gretel Rajamoney, Cam O’Brien, Payton Olson

High Level Description:
Spotify currently offers an annual event where they publish a  Spotify Wrapped section on their platform which displays various pieces of data pertaining to a given user’s listening habits. Currently, the Wrapped event only happens one time near the end of the year, and only covers listening activity from that past year. 

Our application seeks to improve the accessibility of the Spotify Wrapped feature by allowing users to login with their already existing Spotify account and view similar reports on listening trends. Contrary to how Wrapped works, our application will allow users to view this data at any given point in time, rather than waiting 365 days for their annual listening history analysis.

As mentioned above, the core functionality of our application revolves around displaying current, up to date listening habit reports. The main categories that the application will be focused on include each user’s most listened to songs, artists, and genres, with respect to a specified time frame such as daily, weekly, monthly, and yearly. 

Additional Feature:
We have chosen to include an additional feature that allows users to play snippets of their generated top songs within the application. We will accomplish this by manipulating Android’s Media Controls functionality with the Spotify API’s Playback feature.

Figma Design:
Spotify Wrapped Figma Prototype

Figma Link: 
https://www.figma.com/file/VrIE1Uvq2DVgyFVCXCSlrB/Spotify-Wrapped?node-id=0%3A1

UI Organization & Explanation:
Welcome Screen
The Welcome Screen serves as an introduction to the user of the purposes of the app. It greets the user and offers to generate the user’s top songs through a clickable button. The button is labeled “Generate Your Results”. Clicking this button will take the user to the Login Screen.

Login Screen
The Login Screen takes the user to Spotify’s Single Sign On interface. Here Spotify will authenticate the user’s credentials to be sent back to our application. After successfully providing their Spotify login credentials the user will be taken to the Search Screen.
Search Screen
The Search Screen is the main feature of the app. The very top of the screen includes the username and profile picture of the current user. Two rows of selectable options reside directly underneath the user’s information with a results box underneath. The top row asks the user if they would like to search their top artists, songs, or genres. These options are presented as rounded rectangular selectable buttons that turn from gray to green when selected. The second row of options allows the user to select results from four time periods: today, this week, this month, and this year. The two option rows combine in any fashion to provide the user with the specific results they desire to be outputted to them. Once a combination of the two option rows is selected the results box underneath displays in ascending order (1-5) the user’s top spotify listening information. The results menu will shift in real time, meaning if a user is currently viewing their top songs from this week and decides to select the time period of “This Month”, the results box will change (if there is a difference) to the new results. This screen will also present a share button that takes them to the Share Screen.

Share Screen
The Share Screen allows the user to send their generated Spotify listening results in a variety of ways including Messages, Gmail, and Google Drive.


APIs/SDKs

Spotify Android SDK
Primary Use Case: Authentication

We will be using the Android SDK provided by Spotify to enable support for Single Sign On (SSO) that
allows the user to authenticate using a single button press so long as they are signed in on the official Spotify App on their device.

This will supply us with the authentication code that we need in order to make successful requests via the Web API. 


Spotify Web API
Method 1: Get User's Top Items
This endpoint is responsible for retrieving a list of a user’s top artists OR tracks and will be supplying most of the data for our application’s views. 

Params:
type - String - “artists” OR “tracks”
Determines which category of data we are receiving as a response.

time_range - String - “long_term”, “medium_term”, “short_term” 
Specifies the desired time window for retrieval. 
long=several years, medium=last 6 months, short= ~last 4 weeks

The response data will be propagated to each of the views containing the UI for top songs, artists, and genres.
Input for this request will be collected from the user via the buttons on the results page. 
The application will most likely make a request for the “long_term” list which will include several years worth of data, and then filter it down to the desired time window.
