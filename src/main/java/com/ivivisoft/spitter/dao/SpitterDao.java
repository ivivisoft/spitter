/*
 *  Copyright (c) 2016, 张威, ivivisoft@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ivivisoft.spitter.dao;

import com.ivivisoft.spitter.bean.Spitter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SpitterDao {

    DataSource dataSource = null;

    String SQL_INSERT_SPITTER = "insert into spitter (username,password,fullname) values(?,?,?)";

    String SQL_UPDATE_SPITTER = "update spitter set username = ?,password=?,fullname=? where id =?";

    String SQL_QUERY_SPITTER = "select id,username,password,fullname from spitter where id =?";

    /**
     * 添加spitter
     *
     * @param spitter
     */
    default void addSpitter(Spitter spitter) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(SQL_INSERT_SPITTER)) {
            stmt.setString(1, spitter.getUsername());
            stmt.setString(2, spitter.getPassword());
            stmt.setString(3, spitter.getFullname());
            stmt.execute();
        } catch (SQLException e) {

        }
    }

    /**
     * 保存spitter
     *
     * @param spitter
     */
    default void saveSpitter(Spitter spitter) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(SQL_UPDATE_SPITTER)) {
            stmt.setString(1, spitter.getUsername());
            stmt.setString(2, spitter.getPassword());
            stmt.setString(3, spitter.getFullname());
            stmt.setLong(4, spitter.getId());
            stmt.execute();
        } catch (SQLException e) {

        }
    }

    /**
     * 查询spitter
     *
     * @param id
     * @return
     */
    default Spitter getSpitterById(long id) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement stmt = connection.prepareStatement(SQL_QUERY_SPITTER)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            Spitter spitter = null;
            if (rs.next()) {
                spitter = new Spitter();
                spitter.setId(rs.getLong("id"));
                spitter.setUsername(rs.getString("username"));
                spitter.setPassword(rs.getString("password"));
                spitter.setFullname(rs.getString("fullname"));
            }
            return spitter;
        } catch (SQLException e) {

        }
        return null;
    }
}
