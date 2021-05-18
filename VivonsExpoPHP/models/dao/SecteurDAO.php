<?php

    class SecteurDAO
    {
        public static function getSecteurs()
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT * FROM secteur ORDER BY UniversId, SecteurLibelle');
                $req->execute();

                $data = $req->fetchAll(PDO::FETCH_ASSOC);
            } catch (Exception $e){
                $data = "";
            }
            return $data;
        }

        public static function getSecteursByUnivers($universLibelle)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT * FROM Secteur NATURAL JOIN Univers WHERE UniversLibelle = :universLibelle ORDER BY UniversId, SecteurLibelle');
                $req->bindValue(':universLibelle', $universLibelle);
                $req->execute();

                $data = $req->fetchAll(PDO::FETCH_ASSOC);
            } catch (Exception $e){
                $data = "";
            }
            return $data;
        }

        public static function getSecteurIdByName($secteurLibelle)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT SecteurId FROM Secteur WHERE SecteurLibelle = :secteurLibelle');
                $req->bindValue(':secteurLibelle', $secteurLibelle);
                $req->execute();

                $data = $req->fetch();
                $data = $data['SecteurId'];
            } catch (Exception $e){
                $data = -1;
            }
            return $data;
        }

    }