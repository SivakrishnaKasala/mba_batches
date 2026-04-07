package com.siva.cob.mba_billgroup_sync_batch.dao;


import com.siva.cob.mba_billgroup_sync_batch.dto.SupportObjectDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SupportObjectDAO extends BaseMbcoDAO {

    private static final Logger LOG= LogManager.getLogger(SupportObjectDAO.class);

    @Autowired
    public NamedParameterJdbcTemplate supportObjectTemplate;

    public int insertSupportObject(SupportObjectDTO dto) {


        String INSERT_SQL = """
                INSERT INTO support_object (
                    identifier, address, channel, eAddress_invoicetype,
                    email_confirmation, isLegal, note, version, `when`,
                    pni_account_identifier, start_time_stamp, end_time_stamp,
                    customer_id, billing_account_identifier,
                    billing_account_context, billing_account_scope, bill_group
                )
                VALUES (
                    :identifier, :address, :channel, :invoiceType,
                    :emailConfirmation, :isLegal, :note, :version, :whenValue,
                    :pniAccountIdentifier, :startTS, :endTS, :customerId,
                    :billingAccIdentifier, :billingAccContext,
                    :billingAccScope, :billGroup
                )
                """;


        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("identifier", dto.getIdentifier())
                .addValue("address", dto.getAddress())
                .addValue("channel", dto.getChannel())
                .addValue("invoiceType", dto.geteAddressInvoicetype())
                .addValue("emailConfirmation", dto.getEmailConfirmation())
                .addValue("isLegal", dto.getIsLegal())
                .addValue("note", dto.getNote())
                .addValue("version", dto.getVersion())
                .addValue("whenValue", dto.getWhen())
                .addValue("pniAccountIdentifier", dto.getPniAccountIdentifier())
                .addValue("startTS", dto.getStartTimeStamp())
                .addValue("endTS", dto.getEndTimeStamp())
                .addValue("customerId", dto.getCustomerId())
                .addValue("billingAccIdentifier", dto.getBillingAccountIdentifier())
                .addValue("billingAccContext", dto.getBillingAccountContext())
                .addValue("billingAccScope", dto.getBillingAccountScope())
                .addValue("billGroup", dto.getBillGroup());

        LOG.info("sql query :{}",INSERT_SQL);
        LOG.info("Params :{}",params);
        try {
            LOG.info("Inserting SupportObject: {}", dto.getIdentifier());
            int rows = supportObjectTemplate.update(INSERT_SQL, params);
            LOG.info("Insert successful. Rows affected = {}", rows);
            return rows;
        } catch (Exception e) {
            LOG.error("Failed to insert SupportObject [{}]: {}", dto.getIdentifier(), e.getMessage(), e);
            e.printStackTrace();
            LOG.error("Error message :{}",e.getMessage());
//            return 0;
            throw e;
        }
    }

    public SupportObjectDTO findSupportObject(){


        String query="SELECT * FROM SUPPORT_OBJECT ORDER BY IDENTIFIER DESC LIMIT 1";

        LOG.info("Query used for fetching the SupportObject identifier : {}",query);

        try {
            SupportObjectDTO supportObjectDTO =
                    supportObjectTemplate.queryForObject(
                            query,
                            new MapSqlParameterSource(),
                            new BeanPropertyRowMapper<>(SupportObjectDTO.class)
                    );

            LOG.info("Fetched SupportObject: {}", supportObjectDTO);
            return supportObjectDTO;

        } catch (EmptyResultDataAccessException e) {
            // ✅ No data found case
            LOG.warn("No SupportObject found in database");
            return null;
        } catch (DataAccessException e){
            // ✅ DB related exceptions
            LOG.error("Error occurred while retrieving SupportObject from DB", e);
            throw e; // rethrow (recommended)
        }

    }

    public List<SupportObjectDTO> getSupportObject() {

        List<SupportObjectDTO> result = new ArrayList<>();

        String query = "SELECT * FROM SUPPORT_OBJECT";
        try {
            LOG.info("Query used for fetch the supportObject : [{}]", query);
            result = supportObjectTemplate.query(query, new BeanPropertyRowMapper<>(SupportObjectDTO.class));

            if (!result.isEmpty()) {

                LOG.info("The List of support Object received from the database :{}", result);
            }
            return result;

        } catch (Exception e) {
            LOG.error("Exception occurred while connecting to Database {}", e.getMessage());
            throw e;
        }

    }


}
