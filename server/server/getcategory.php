<?php
include "connect.php";
$query = "SELECT * FROM category";
$data = mysqli_query($conn,$query);
$mangloaisp = array();
while($row = mysqli_fetch_assoc($data)){
    array_push($mangloaisp,new Loaisp(
        $row['id'],
        $row['category_name'],
        $row['anh']
    ));
}
echo json_encode($mangloaisp);




class Loaisp{
    public function __construct($id, $category_name, $anh) {
        $this->id = $id;
        $this->category_name = $category_name;
        $this->anh = $anh;
    }
}
?>


