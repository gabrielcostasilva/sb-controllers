<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello Spring MVC w/ Freemarker</title>
</head>
<body>
    <h1>Hello World ${username!}</h1>

    <form action="/" method="post">
        <input type="text" name="name" placeholder="Enter your name">
        <input type="submit" value="Say Hello">
    </form>
    
</body>
</html>