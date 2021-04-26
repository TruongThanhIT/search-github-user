# search-github-user
Minimalistic GitHub client, which allows user to search through GitHub users and view their profiles.

![processed (1)](https://user-images.githubusercontent.com/22128728/116030545-f9fe8400-a685-11eb-9b96-fb2b8ccdc368.jpeg) 
![processed (2)](https://user-images.githubusercontent.com/22128728/116030616-24e8d800-a686-11eb-9f13-a3c24409845e.jpeg)

# Getting Started

- Clone or download this repository
- Run and feel free to use this app without any account

# Functionalities
- Input the git user name with a minimum of 1 keyword, it's will automatically searching.
- Render the searched results as a list of user items.
- Handle configuration changes & lifecycle.
- Handle failures.
- Encrypt and decrypt any internal data caching.

# Libraries and Frameworks

- Presentation module
   - Data-binding
   - MVVM
   - Navigation Graph
- Data module
    - SharePreference
-  Network
    - Retrofit: ver 2.7.1 support kotlin coroutines
    - Okhttp3
    - Gson
- Dependence Injection: Hilt
- Kotlin coroutines
- Layout
    - ConstraintLayout

# Base Architecture Diagram (Model-View-ViewModel)

![image](https://user-images.githubusercontent.com/22128728/113498788-5284aa80-953a-11eb-97ee-8edb7f201c15.png)

### View

- Activity, Fragment, Views
- Binding data from ViewModel
- Handle UI logic

### View-Model

- Live Data
- Code logic

### Domain use-case

Define all functions to use-case

- Get data from the repository

### Model - Data layer | Repository

All data needed for the application comes from this

Receive a request to get data. Switch data between remote and local to return a value 

- Local data source: Room for complexly functions in the feature
- Remote data source: web service API
- Share preference
