/**
 * 
 */
package po;

import java.io.Serializable;

import vo.ChartVO;

/**
 * @author 1
 *
 */
public class MaxMinPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1174675323812930657L;
	private ChartVO maxPO;
	private ChartVO minPO;

	public MaxMinPO(ChartVO MaxPO, ChartVO MinPO) {
		this.maxPO = MaxPO;
		this.minPO = MinPO;
	}

	public ChartVO getMaxPO() {
		return maxPO;
	}

	public void setMaxPO(ChartVO maxPO) {
		this.maxPO = maxPO;
	}

	public ChartVO getMinPO() {
		return minPO;
	}

	public void setMinPO(ChartVO minPO) {
		this.minPO = minPO;
	}
	@Override
	public String toString() {
		return "Max:" + maxPO.toString() + "Min:" + minPO.toString();
	}

}
