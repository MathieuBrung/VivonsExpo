<?php

    class UniversDAO
    {
        public static function getUnivers()
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT * FROM univers');
                $req->execute();

                $data = $req->fetchAll(PDO::FETCH_ASSOC);
            } catch (Exception $e){
                $data = "";
            }
            return $data;
        }
    }