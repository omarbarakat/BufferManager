package bufmgr;

import javax.tools.Diagnostic;

import global.PageId;

/**
 * 
 * @author ahmed
 *
 * Describes each frame in the main memory
 * 
 */

public class Descriptor {

	private PageId pagenumber;// number of the page 
	private int pin_count;// how many are using this page
	private boolean dirtybit;// if the page is changed then this is true and it needs to saved to disk
	
	
	public Descriptor() {
		pagenumber = null;
		pin_count = 0;
		dirtybit = false;
	}
	
	// getters and sitters
	public PageId getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(PageId pagenumber) {
		this.pagenumber = pagenumber;
	}
	public int getPin_count() {
		return pin_count;
	}
	public void setPin_count(int pin_count) {
		this.pin_count = pin_count;
	}
	public boolean isDirtybit() {
		return dirtybit;
	}
	public void setDirtybit(boolean dirtybit) {
		this.dirtybit = dirtybit;
	}
	
	
	public void incrementPinCount() {
		pin_count++;
	}
	
	public void decremenPinCount() {
		if(pin_count > 0)
			pin_count--;
	}
	
}





