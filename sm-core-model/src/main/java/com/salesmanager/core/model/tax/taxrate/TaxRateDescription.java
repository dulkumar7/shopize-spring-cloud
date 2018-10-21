/*
 * Licensed to csti consulting 
 * You may obtain a copy of the License at
 *
 * http://www.csticonsulting.com
 * Copyright (c) 2006-Aug 24, 2010 Consultation CS-TI inc. 
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.salesmanager.core.model.tax.taxrate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;


@Entity
@Table(name = "TAX_RATE_DESCRIPTION" , schema=SchemaConstant.SALESMANAGER_SCHEMA ,uniqueConstraints={
		@UniqueConstraint(columnNames={
				"TAX_RATE_ID",
				"LANGUAGE_ID"
			})
		}
	)
public class TaxRateDescription extends Description {
	private static final long serialVersionUID = -4752794805878361822L;

	@ManyToOne(targetEntity = TaxRate.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "TAX_RATE_ID")
	@JsonBackReference("taxRate")
	private TaxRate taxRate;
	
	public TaxRateDescription() {
	}

	public TaxRate getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(TaxRate taxRate) {
		this.taxRate = taxRate;
	}
}
