/**
 * 
 */
package dataservice;

import utility.exception.ExistModel_exception;
import utility.exception.NotFoundModel_exception;

/**
 * @author 1
 *
 */
public interface ModelService {
	public double getValue(String modelId) throws NotFoundModel_exception;

	public boolean insertModel(String modelId, double value) throws ExistModel_exception;

	public boolean updateModel(String modelId, double value) throws NotFoundModel_exception;

	public boolean deleteModel(String modelId) throws NotFoundModel_exception;
}
