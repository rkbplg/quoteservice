/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anz.quoteservice.repository;

import com.anz.quoteservice.domain.XeDeals;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 631713
 */
@Repository
public interface XeDealsRepository extends CrudRepository<XeDeals, Integer>{
    
}
