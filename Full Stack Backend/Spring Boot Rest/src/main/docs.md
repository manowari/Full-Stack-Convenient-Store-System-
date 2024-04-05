
- passwords are hashed at user creation step 
- some data
- 
-user creation test 
{
"fullName": "John Omondi",
"workClass": 1,
"userName": "johnom",
"email": "johno@example.com",
"password": "securepassword",
"pf": "CS001",
"userRole": "officer"
}

login header 
{

"email": "johno@example.com",
"password": "securepassword",
 
}

 - if any cahllenge create users table using 
CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
pf VARCHAR(50) UNIQUE NOT NULL,
full_name VARCHAR(255) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
user_name VARCHAR(100) UNIQUE,
password VARCHAR(255) NOT NULL,
work_class INT NOT NULL,
user_role VARCHAR(50) NOT NULL DEFAULT 'officer',
created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
