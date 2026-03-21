package com.siva.cob.mba_billgroup_sync_batch.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public abstract class BaseDAO {
    public BaseDAO() {
    }


    protected <T> BeanPropertyRowMapper<T> getBeanPropertyRowMapper(final Class responseMappedClass, final boolean isPrimitivesDefaultedForNullValue){
        BeanPropertyRowMapper beanPropertyRowMapper=new BeanPropertyRowMapper(responseMappedClass);
        if (isPrimitivesDefaultedForNullValue){
            beanPropertyRowMapper.setPrimitivesDefaultedForNullValue(isPrimitivesDefaultedForNullValue);
        }

        return beanPropertyRowMapper;
    }

    protected BeanPropertySqlParameterSource getBeanPropertySqlParameterSource(final Object requestMappedClass){
        return new BeanPropertySqlParameterSource(requestMappedClass);
    }


    protected KeyHolder getKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
