<?php 
include "connect.php";
$user_name = $_POST['username'];
$phonenumber= $_POST['phonenumber'];
$email = $_POST['email'];
if(strlen($user_name)>0 && strlen($email)>0&& strlen($phonenumber)>0){
    $query = "INSERT INTO donhang(id,user_name,phonenumber,email) VALUES (null,'$user_name','$phonenumber','$email')";
    if(mysqli_query($conn,$query)){
            $iddonhang = $conn->insert_id;
            echo $iddonhang;
    }
    else{
        echo"Failed";
    }
}
else{
    echo "Hãy kiểm tra lại kết nối";
}



?>