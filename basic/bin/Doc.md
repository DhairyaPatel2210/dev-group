# **Project's Tech-Stack**

## Front-end
* Angular

## Back-end
* Spring boot (Vesion 3.0.2)
* Java (Version 17)
* Relational Database - MySQL

# **Project's Directory Structure**

> src --> main --> java --> com --> devgroup --> {anyService} --> All packages

## Entities
 
This will contain all the entity related files. These files will help jpa to construct tables into the MySQL database.

## Controller
 
This package will be helpful to maintain all kinds of the controller which will eventually have files that contains multiple endpoints.

## Repository
 
The package repository will have the files which will simply extend the basic JPA Repositories and won't have any kind of implementation written into it.

## Implementation
 
Implementation package is maintained to have all the files which are used for writing logics of each end-points.

# **Entities**

* User (Int id (auto generated), String name, String username, String email, String password(Hashed using BCrypt), String phone)
* Ideas (Int id, String title, String desciption, Int upvoteCounter, Int downvoteCounter)
* Groups (Int id, UserList, IdeaList )

# **Dependencies**

* Spring Web
* Spring Boot Dev Tools
* Spring JPA Data
* MySQL Driver
* Spring Security

# **Relationships**
The user will maintain the list which will have group details in which the user is member, and each group will have the details of ideas shared inside the group and idea will not have any dependencies with them.

User (List(Group)) --> Group (List(Idea)) --> Idea

# API's




