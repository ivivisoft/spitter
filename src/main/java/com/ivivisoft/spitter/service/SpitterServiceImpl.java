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

package com.ivivisoft.spitter.service;

import com.ivivisoft.spitter.bean.Spitter;
import com.ivivisoft.spitter.dao.SpitterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class SpitterServiceImpl implements SpitterService {

    @Autowired
    private SpitterDao spitterDao;


    private TransactionTemplate txTemple;

    public void setTxTemple(TransactionTemplate txTemple) {
        this.txTemple = txTemple;
    }

    @Override
    public void saveSpitter(Spitter spitter) {
        spitterDao.saveSpitter(spitter);
    }

    public void saveSpitterWithTransaction(Spitter spitter) {
        txTemple.execute((TransactionCallback<Void>) status -> {
            try {
                spitterDao.saveSpitter(spitter);
            } catch (RuntimeException e) {
                status.setRollbackOnly();
                throw e;
            }
            return null;
        });
    }


}
