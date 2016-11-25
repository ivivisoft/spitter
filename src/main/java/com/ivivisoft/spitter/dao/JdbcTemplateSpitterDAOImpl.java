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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import java.util.HashMap;
import java.util.Map;

public class JdbcTemplateSpitterDAOImpl extends NamedParameterJdbcDaoSupport implements SpitterDao {

    private String SQL_INSERT_SPITTER = "insert into spitter (username,password,fullname) values(:username,:password,:fullname)";

    private String SQL_UPDATE_SPITTER = "update spitter set username = :username,password=:password,fullname=:fullname where id =:id";

//    private String SQL_QUERY_SPITTER = "select id,username,password,fullname from spitter where id =:id";

    /**
     * 添加spitter
     *
     * @param spitter
     */
    @Override
    public void addSpitter(Spitter spitter) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", spitter.getUsername());
        params.put("password", spitter.getPassword());
        params.put("fullname", spitter.getFullname());
        getJdbcTemplate().update(SQL_INSERT_SPITTER, params);
    }

    /**
     * 保存spitter
     *
     * @param spitter
     */
    @Override
    public void saveSpitter(Spitter spitter) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", spitter.getUsername());
        params.put("password", spitter.getPassword());
        params.put("fullname", spitter.getFullname());
        params.put("id", spitter.getId());
        getJdbcTemplate().update(SQL_UPDATE_SPITTER, params);
    }

    /**
     * 查询spitter
     *
     * @param id
     * @return
     */
    @Override
    public Spitter getSpitterById(long id) {
        return getJdbcTemplate().queryForObject(SQL_QUERY_SPITTER, (rs, rowNum) -> {
            Spitter spitter = new Spitter();
            spitter.setId(rs.getLong(1));
            spitter.setUsername(rs.getString(2));
            spitter.setPassword(rs.getString(3));
            spitter.setFullname(rs.getString(4));
            return spitter;
        }, id);
    }
}
