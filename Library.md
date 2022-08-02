# Library

<!--- If we have only one group/collection, then no need for the "ungrouped" heading -->

1. [New Request](#1-new-request)
1. [delete](#2-delete)
    1. [failed](#i-example-request-failed)
    1. [delete](#ii-example-request-delete)
1. [modify](#3-modify)
    1. [modify](#i-example-request-modify)
    1. [couldn't modify](#ii-example-request-couldnt-modify)
1. [add](#4-add)
    1. [add](#i-example-request-add)
    1. [couldn't save](#ii-example-request-couldnt-save)
1. [search](#5-search)
    1. [book available](#i-example-request-book-available)
    1. [book currently not available](#ii-example-request-book-currently-not-available)
    1. [not found](#iii-example-request-not-found)
1. [login](#6-login)
    1. [missing parameter](#i-example-request-missing-parameter)
    1. [wrong username](#ii-example-request-wrong-username)
    1. [wrong password](#iii-example-request-wrong-password)
    1. [correct login](#iv-example-request-correct-login)
1. [register](#7-register)
    1. [register](#i-example-request-register)
    1. [missing field](#ii-example-request-missing-field)
    1. [already taken](#iii-example-request-already-taken)
    1. [password short](#iv-example-request-password-short)

## Endpoints

--------

### 1. New Request

***Endpoint:***

```bash
Method: GET
Type: 
URL: /users
```

### 2. delete

***Endpoint:***

```bash
Method: DELETE
Type: 
URL: books/:bookId
```

***URL variables:***

| Key | Value | Description |
| --- | ------|-------------|
| bookId |  | put bookId here |

***More example Requests/Responses:***

#### I. Example Request: failed

***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| bookId |  | put bookId here |

***Body: None***

#### I. Example Response: failed

```js
{
    "status" : "error",
    "message" : "couldn't delete book please try again later or contact IT"
}
```

***Status Code:*** 500

<br>

#### II. Example Request: delete

***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| bookId |  | put bookId here |

***Body: None***

#### II. Example Response: delete

```js
redirect to /search
```

***Status Code:*** 200

<br>

### 3. modify

***Endpoint:***

```bash
Method: PUT
Type: RAW
URL: books/:bookId
```

***URL variables:***

| Key | Value | Description |
| --- | ------|-------------|
| bookId |  | put the bookId here |

***Body:***

```js        
{
    "isbn":"10DigitIsbn",
    "title": "title",
    "author": "author",
    "year": "releaseYear",
    "availabilty":"available/only for viewing/being restored/",
    "member":"member_id",
    "returnDate":"2022-07-16"
}
```

***More example Requests/Responses:***

#### I. Example Request: modify

***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| bookId |  | put the bookId here |

***Body:***

```js        
{
    "isbn":"10DigitIsbn",
    "title": "title",
    "author": "author",
    "year": "releaseYear",
    "availabilty":"available/only for viewing/being restored/",
    "member":"member_id",
    "returnDate":"2022-07-16"
}
```

#### I. Example Response: modify

```js
redirects to: /search
```

***Status Code:*** 200

<br>

#### II. Example Request: couldn't modify

***Query:***

| Key | Value | Description |
| --- | ------|-------------|
| bookId |  | put the bookId here |

***Body:***

```js        
{
    "isbn":"10DigitIsbn",
    "title": "title",
    "author": "author",
    "year": "releaseYear",
    "availabilty":"available/only for viewing/being restored/",
    "member":"member_id",
    "returnDate":"2022-07-16"
}
```

#### II. Example Response: couldn't modify

```js
{
    "status" : "error",
    "message" : "couldn't save modifications please try again later or contact IT"
}
```

***Status Code:*** 500

<br>

### 4. add

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: books/add
```

***Body:***

```js        
{
    "isbn": "10DigitIsbn",
    "title": "title",
    "author": "author",
    "publisher": "publisher",
    "year": "releaseYear"
}
```

***More example Requests/Responses:***

#### I. Example Request: add

***Body:***

```js        
{
    "isbn": "10DigitIsbn",
    "title": "title",
    "author": "author",
    "publisher": "publisher",
    "year": "releaseYear"
}
```

#### I. Example Response: add

```js
redirect to /add
```

***Status Code:*** 200

<br>

#### II. Example Request: couldn't save

***Body:***

```js        
{
    "isbn": "10DigitIsbn",
    "title": "title",
    "author": "author",
    "publisher": "publisher",
    "year": "releaseYear"
}
```

#### II. Example Response: couldn't save

```js
{
    "status": "error",
    "message": "couldn't add book to database please try again later or contact IT"
}
```

***Status Code:*** 500

<br>

### 5. search

***Endpoint:***

```bash
Method: GET
Type: RAW
URL: /search
```

***Body:***

```js        
{
    "title": "titleString",
    "author": "authorString",
    "year": 1987
}
```

***More example Requests/Responses:***

#### I. Example Request: book available

***Body:***

```js        
{
    "title": "titleString",
    "author": "authorString",
    "year": 1987
}
```

#### I. Example Response: book available

```js
{
    "author":"author",
    "title": "title",
    "publisher": "publisher",
    "releaseYear": 1987,
    "available": 2
}
```

***Status Code:*** 200

<br>

#### II. Example Request: book currently not available

***Body:***

```js        
{
    "title": "titleString",
    "author": "authorString",
    "year": 1987
}
```

#### II. Example Response: book currently not available

```js
{
    "author":"author",
    "title": "title",
    "publisher": "publisher",
    "releaseYear": 1987,
    "available": "2023-05-23"
}
```

***Status Code:*** 0

<br>

#### III. Example Request: not found

***Body:***

```js        
{
    "title": "titleString",
    "author": "authorString",
    "year": 1987
}
```

#### III. Example Response: not found

```js
{
    "status":"error",
    "message" : "Book not found"
}
```

***Status Code:*** 0

<br>

### 6. login

Sending user credentials and receiving a token if authentication succeeded.

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: /login
```

***Body:***

```js        
{
    "username": "user",
    "password": "password"
}
```

***More example Requests/Responses:***

#### I. Example Request: missing parameter

***Body:***

```js        
{

}
```

#### I. Example Response: missing parameter

```js
{
    "status":  "error",
    "message": "<parameter> is required"
}
```

***Status Code:*** 400

<br>

#### II. Example Request: wrong username

***Body:***

```js        
{
    "username": "wrongUser",
    "password": "password"
}
```

#### II. Example Response: wrong username

```js
{
    "status":  "error",
    "message": "wrong username or password"
}
```

***Status Code:*** 401

<br>

#### III. Example Request: wrong password

***Body:***

```js        
{
    "username": "user",
    "password": "wrongPassword"
}
```

#### III. Example Response: wrong password

```js
{
    "status":  "error",
    "message": "wrong username or password"
}
```

***Status Code:*** 401

<br>

#### IV. Example Request: correct login

***Body:***

```js        
{
    "username": "user",
    "password": "password"
}
```

#### IV. Example Response: correct login

```js
{
    "status":  "ok",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE2NTI4ODU0NDUsImV4cCI6MTY4NDQyMTQ0NSwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsInVzZXJJZCI6IjEifQ.LTb_-WTmsoMa8oAhdGQeoSVzXvaOChm8V9IMJ2NMyJE"
}
```

***Status Code:*** 200

<br>

### 7. register

***Endpoint:***

```bash
Method: POST
Type: RAW
URL: /register
```

***Body:***

```js        
{
    "username":"desiredUserName",
    "email": "email",
    "firstName:":"firstName",
    "lastName": "lastName",
    "dateOfBirth":"dateOfBirth",
    "mothersName":"mothersName",
    "address": {
        "county":"county",
        "city":"city",
        "streetAndbuilding": "streetAndbuilding"
    },
    "password":"password"
}
```

***More example Requests/Responses:***

#### I. Example Request: register

***Body:***

```js        
{
    "username": "desiredUserName",
    "email": "email",
    "firstName:": "firstName",
    "lastName": "lastName",
    "dateOfBirth": "dateOfBirth",
    "mothersName": "mothersName",
    "address": {
        "county": "county",
        "city": "city",
        "streetAndbuilding": "streetAndbuilding"
    }
}
```

#### I. Example Response: register

```js
{
    "username": "username"
}
```

***Status Code:*** 200

<br>

#### II. Example Request: missing field

***Body:***

```js        
{
    "username": "desiredUserName",
    "email": "",
    "firstName:": "firstName",
    "lastName": "lastName",
    "dateOfBirth": "dateOfBirth",
    "mothersName": "mothersName",
    "address": {
        "county": "county",
        "city": "city",
        "streetAndbuilding": "streetAndbuilding"
    },
    "password": "password"
}
```

#### II. Example Response: missing field

```js
{
    "status" : "error",
    "message": "<field> is required"
}
```

***Status Code:*** 400

<br>

#### III. Example Request: already taken

***Body:***

```js        
{
    "username": "desiredUserName",
    "email": "email",
    "firstName:": "firstName",
    "lastName": "lastName",
    "dateOfBirth": "dateOfBirth",
    "mothersName": "mothersName",
    "address": {
        "county": "county",
        "city": "city",
        "streetAndbuilding": "streetAndbuilding"
    },
    "password": "password"
}
```

#### III. Example Response: already taken

```js
{
    "status": "error",
    "message" : "Username/email already registered"
}
```

***Status Code:*** 409

<br>

#### IV. Example Request: password short

***Body:***

```js        
{
    "username":"desiredUserName",
    "email": "email",
    "firstName:":"firstName",
    "lastName": "lastName",
    "dateOfBirth":"dateOfBirth",
    "mothersName":"mothersName",
    "address": {
        "county":"county",
        "city":"city",
        "streetAndbuilding": "streetAndbuilding"
    },
    "password": "pass"
}
```

#### IV. Example Response: password short

```js
{
    "status": "error",
    "message": "Password must be 8 characters."
}
```

***Status Code:*** 406

<br>



---
[Back to top](#library)

> Generated at 2022-05-22 17:18:47 by [docgen](https://github.com/thedevsaddam/docgen)
