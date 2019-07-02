package com.fengshuai.system.batch.output.Jdbc.dbtask;

import com.fengshuai.util.character.StringUtil;
import com.fengshuai.util.db.DataBase;
import com.fengshuai.util.log.FsLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ExeSqlDao {
    private Statement statement;
    private String dbpool;
    private Connection conn;
    private FsLogger logger ;

    public ExeSqlDao(String dbpool, FsLogger logger) {
        this.dbpool = dbpool;
        conn = DataBase.getConn(dbpool);
        this.logger=logger;
        try {
            DataBase.setFalseCommit(conn);
            this.statement = conn.createStatement();
            statement.setQueryTimeout(6000);
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public void addSql(String sql) {
        try {
            this.statement.addBatch(sql);
        } catch (SQLException var3) {
            logger.error("err sql:{}",sql);
            logger.error("exception：{}", StringUtil.ExceptionToString(var3));
            var3.printStackTrace();
        }

    }

    public int exeUpdate(String sql) throws SQLException {
        try {
            return this.statement.executeUpdate(sql);
        } catch (SQLException var3) {
            DataBase.rollback(this.dbpool);
            logger.error("err sql:{}",sql);
            logger.error("exception：{}", StringUtil.ExceptionToString(var3));
            throw new SQLException("sql exception："+ StringUtil.ExceptionToString(var3));
        }

    }

    public int[] batchCommit() {
        int[] rows=null;
        try {
            rows=this.statement.executeBatch();
            DataBase.commit(this.dbpool);
        } catch (SQLException var2) {
            DataBase.rollback(this.dbpool);
            logger.error("exception：{}", StringUtil.ExceptionToString(var2));
            var2.printStackTrace();
        }

        return rows;
    }


}
