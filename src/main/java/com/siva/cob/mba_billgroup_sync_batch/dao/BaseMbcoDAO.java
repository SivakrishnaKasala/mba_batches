package com.siva.cob.mba_billgroup_sync_batch.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(
        propagation = Propagation.SUPPORTS,
        readOnly = true
)
public class BaseMbcoDAO extends BaseDAO{
    public BaseMbcoDAO() {
    }
}
