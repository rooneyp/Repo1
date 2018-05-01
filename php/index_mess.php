<html> <head>  <title>PHP Test</title> </head>
 <body>

 <?php

 $dbhost = 'localhost:3036';
 $dbuser = 'guest';
 $dbpass = 'guest123';
 //$conn = mysql_connect($dbhost, $dbuser, $dbpass);

/* Attempt MySQL server connection. Assuming you are running MySQL
server with default setting (user 'root' with no password) */
$link = mysqli_connect("localhost", "root", "");

// Check connection
if($link === false){
    die("ERROR: Could not connect. " . mysqli_connect_error());
}

// Print host information
echo "Connect Successfully. Host info: " . mysqli_get_host_info($link);

 // Close connection
 mysqli_close($link);
?>


<?php echo '<p>Hello World</p>'; ?>


<?php
    $variable = "name";
    $literally = "My $variable will print!";
    print($literally);
?>



<form action = "<?php $_PHP_SELF ?>" method = "GET">
    Name: <input type = "text" name = "name" />
    Age: <input type = "text" name = "age" />
    <input type = "submit" />
</form>

<?php
    if( isset($_GET["name"]) || isset($_GET["age"]) ) {
        echo "Welcome ". $_GET['name']. "<br />";
        echo "You are ". $_GET['age']. " years old.";

        exit();
    }
?>

<?php //phpinfo(); ?>

 </body></html>
