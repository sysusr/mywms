/*
 * StorageLocationQueryBean.java
 *
 * Created on 14. September 2006, 06:53
 *
 * Copyright (c) 2006-2012 LinogistiX GmbH. All rights reserved.
 *
 *<a href="http://www.linogistix.com/">browse for licence information</a>
 *
 */

package de.linogistix.los.inventory.query;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import de.linogistix.los.inventory.model.LOSStorageRequest;
import de.linogistix.los.inventory.model.LOSStorageRequestState;
import de.linogistix.los.inventory.query.dto.LOSStorageRequestTO;
import de.linogistix.los.query.BODTOConstructorProperty;
import de.linogistix.los.query.BusinessObjectQueryBean;
import de.linogistix.los.query.TemplateQueryWhereToken;

/**
 *
 * @author krane
 */
@Stateless
public class LOSStorageRequestQueryBean extends BusinessObjectQueryBean<LOSStorageRequest> implements LOSStorageRequestQueryRemote{

    @Override
    public String getUniqueNameProp() {
        return "number";
    }

	@Override
	protected String[] getBODTOConstructorProps() {
		return new String[]{};
	}
		
	@Override
	protected List<BODTOConstructorProperty> getBODTOConstructorProperties() {
		List<BODTOConstructorProperty> propList = super.getBODTOConstructorProperties();
		
		propList.add(new BODTOConstructorProperty("id", false));
		propList.add(new BODTOConstructorProperty("version", false));
		propList.add(new BODTOConstructorProperty("number", false));
		propList.add(new BODTOConstructorProperty("unitLoad.labelId", false));
		propList.add(new BODTOConstructorProperty("requestState", false));
		propList.add(new BODTOConstructorProperty("destination.name", null, BODTOConstructorProperty.JoinType.LEFT, "destination"));

		return propList;
	}

	@Override
	public Class<LOSStorageRequestTO> getBODTOClass() {
		return LOSStorageRequestTO.class;
	}

	
    @Override
	protected List<TemplateQueryWhereToken> getAutoCompletionTokens(String value) {
		List<TemplateQueryWhereToken> ret =  new ArrayList<TemplateQueryWhereToken>();
		
		TemplateQueryWhereToken number = new TemplateQueryWhereToken(
				TemplateQueryWhereToken.OPERATOR_LIKE, "number",
				value);
		number.setLogicalOperator(TemplateQueryWhereToken.OPERATOR_OR);
		
		TemplateQueryWhereToken label = new TemplateQueryWhereToken(
				TemplateQueryWhereToken.OPERATOR_LIKE, "unitLoad.labelId",
				value);
		label.setLogicalOperator(TemplateQueryWhereToken.OPERATOR_OR);
		
		TemplateQueryWhereToken destination = new TemplateQueryWhereToken(
				TemplateQueryWhereToken.OPERATOR_LIKE, "destination.name",
				value);
		destination.setLogicalOperator(TemplateQueryWhereToken.OPERATOR_OR);

		
		ret.add(number);
		ret.add(label);
		ret.add(destination);
		
		
		return ret;
	}

    @Override
	protected List<TemplateQueryWhereToken> getFilterTokens(String filterString) {

		List<TemplateQueryWhereToken> ret =  new ArrayList<TemplateQueryWhereToken>();
		TemplateQueryWhereToken token;

		if( "OPEN".equals(filterString) ) {
			token = new TemplateQueryWhereToken(TemplateQueryWhereToken.OPERATOR_EQUAL, "requestState", LOSStorageRequestState.RAW);
			token.setLogicalOperator(TemplateQueryWhereToken.OPERATOR_OR);
			token.setParameterName("stateRaw");
			ret.add(token);
			token = new TemplateQueryWhereToken(TemplateQueryWhereToken.OPERATOR_EQUAL, "requestState", LOSStorageRequestState.PROCESSING);
			token.setLogicalOperator(TemplateQueryWhereToken.OPERATOR_OR);
			token.setParameterName("stateProcessing");
			ret.add(token);
			
		}

		return ret;
	}
    
}
