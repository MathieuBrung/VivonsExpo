<?php

    abstract class StandDAO
    {
        static function assignStand($exposantId, $secteurId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('CALL Assign_Stand(:exposantId, :secteurId, @bool)');
            
                $req->bindValue(":exposantId", $exposantId);
                $req->bindValue(":secteurId", $secteurId);
                $req->execute();
                
                $data = $db->query('SELECT @bool AS bool')->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = false;
            }

            return $data;
        }

        static function getStandByExposantId($exposantId)
        {
            try {
                $db = DBConnexion::getConnexion();
                $req = $db->prepare('SELECT StandId, AlleeId, TraveeId, HallId_ALLEE
                                    FROM stand NATURAL JOIN exposant WHERE ExposantId = :exposantId');
            
                $req->bindValue(":exposantId", $exposantId);
                $req->execute();
                
                $data = $req->fetchAll(PDO::FETCH_ASSOC);

            } catch (Exception $e) {
                $data = "";
            }

            return $data;
        }
    }