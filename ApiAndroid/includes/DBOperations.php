<?php

class DBOperations
{
    private $con;

    function __construct()
    {
        require_once dirname(__FILE__) . '/DBConnect.php';
        $db = new DBConnect();
        $this->con = $db->connect();
    }

    function createMarga($marga, $cerita, $namaFile)
    {
        $sttm = $this->con->prepare("INSERT INTO marga(nama,deskripsi,image) VALUES (?,?,?)");
        $sttm->bind_param("sss", $marga, $cerita, $namaFile);
        return $sttm->execute() ? USER_CREATED : USER_FAILURE;
    }
    public function getMarga(){
        $stmt=$this->con->prepare("SELECT * FROM marga");
        $stmt->execute();
        $stmt->bind_result($id,$nama,$deskripsi,$image);
        $margas=array();
        while ($stmt->fetch()){
            $marga=array();
            $marga['id']=$id;
            $marga['nama']=$nama;
            $marga['deskripsi']=$deskripsi;
            $marga['image']=$image;
            array_push($margas,$marga);
        }
        return $margas;
    }
    public function getAllUser()
    {
        $stmt = $this->con->prepare("SELECT * FROM user");
        $stmt->execute();
        $stmt->bind_result($id, $username, $password, $firstname, $lastname, $image);
        $users = array();
        while ($stmt->fetch()) {
            $user = array();
            $user['id'] = $id;
            $user['username'] = $username;
            $user['password'] = $password;
            $user['firstname'] = $firstname;
            $user['lastname'] = $lastname;
            $user['image'] = $image;
            array_push($users, $user);
        }
        return $users;
    }

    public function updateUser($id, $username, $password, $firsname, $lastname)
    {
        $stmt = $this->con->prepare("UPDATE user SET username=?,password=?,first_name=?,last_name=? WHERE id=?");
        $stmt->bind_param("ssssi", $username, $password, $firsname, $lastname, $id);
        if ($stmt->execute()) {
            return true;
        }
        return false;
    }

    public function createUser($username, $password, $firsname, $lastname)
    {
        if (!$this->emailExist($username)) {
            $stmt = $this->con->prepare("INSERT INTO user(username,password,first_name,last_name) VALUES (?,?,?,?)");
            $stmt->bind_param("ssss", $username, $password, $firsname, $lastname);
            if ($stmt->execute()) {
                return USER_CREATED;
            } else {
                return USER_FAILURE;
            }
        }
        return USER_EXIST;
    }

    public function emailExist($username)
    {
        $stmt = $this->con->prepare("SELECT * FROM USER WHERE username=?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }

    public function deleteUser($id)
    {
        $stmt = $this->con->prepare("DELETE FROM user WHERE id=?");
        $stmt->bind_param("i", $id);
        if ($stmt->execute()) {
            return true;
        }
        return false;
    }

    public function loginUser($username, $password)
    {
        if ($this->checkUsername($username)) {
            $has_password = $this->getPassword($username);
            if ($password == $has_password) {
                return USER_AUTHENTICATED;
            } else {
                return PASSWORD_DO_NOT_MATCH;
            }
        } else {
            return USER_NOT_FOUND;
        }
    }
    public function getUserDetail($id){
        $sttm=$this->con->prepare("SELECT * FROM marga WHERE id=?");
        $sttm->bind_param("i",$id);
        $sttm->execute();
        $sttm->bind_result($id,$nama,$deskripsi,$image);
        $sttm->fetch();
        $marga=array();
        $marga['id']=$id;
        $marga['nama']=$nama;
        $marga['deskripsi']=$deskripsi;
        $marga['image']=$image;
        return $marga;
    }
    public function getUser($username)
    {
        $stmt = $this->con->prepare("SELECT id,username,first_name,last_name FROM user WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $stmt->bind_result($id, $username, $first_name, $last_name);
        $stmt->fetch();
        $user = array();
        $user['id'] = $id;
        $user['username'] = $username;
        $user['firstname'] = $first_name;
        $user['lastname'] = $last_name;
        return $user;
    }

    private function getPassword($username)
    {
        $stmt = $this->con->prepare("SELECT password FROM user WHERE username = ?");
        $stmt->bind_param('s', $username);
        $stmt->execute();
        $stmt->bind_result($password);
        $stmt->fetch();
        return $password;
    }

    private function checkUsername($username)
    {
        $sttm = $this->con->prepare("SELECT * FROM user WHERE username = ? ");
        $sttm->bind_param("s", $username);
        $sttm->execute();
        $sttm->store_result();
        return $sttm->num_rows > 0;
    }
}
