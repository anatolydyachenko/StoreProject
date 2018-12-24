package service;

import db.DBClient;

import java.sql.Connection;

abstract public class BaseService {
    static Connection conn = DBClient.getConnection();

}
