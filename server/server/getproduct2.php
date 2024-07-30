
<?php
include "connect.php";
$page =$_GET['page'];
$idsp =$_POST['idsanpham'];
$space = 5;
$limit =($page -1) * $space;
$mangsp = array();
$query = "SELECT *FROM product WHERE product_id =$idsp LIMIT $limit,$space";
$data = mysqli_query($conn,$query);
while($row = mysqli_fetch_assoc($data)){
    array_push($mangsp,new Sanpham(
        $row['id'],
        $row['product_id'],
        $row['product_name'],
        $row['description'],
        $row['price'],
        $row['anh']
    ));
}

echo json_encode($mangsp);




class Sanpham{
    public function __construct($id,$product_id,$product_name,$description,$price,$anh) {
        $this->id = $id;
        $this->product_id = $product_id;
        $this->product_name = $product_name;
        $this->description = $description;
        $this->price = $price;
        $this->anh = $anh;
    }
}



?>
