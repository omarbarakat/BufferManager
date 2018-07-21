package bufmgr;

import java.io.IOException;
import java.util.HashMap;

import chainexception.ChainException;
import diskmgr.DB;
import diskmgr.FileIOException;
import diskmgr.InvalidPageNumberException;
import diskmgr.Page;
import global.GlobalConst;
import global.PageId;

public class BufMgr implements GlobalConst {
	
	 private byte bufpool[][];//simulate the main memory
	 private HashMap<PageId, Integer> map;// has table to get a frame that holds specific page
	 private Descriptor bufDescr[];
	 private Replacer replacer;
	 private DB diskManager;
	 
	   /**
	     * Create the BufMgr object
	     * Allocate pages (frames) for the buffer pool in main memory and
	     * make the buffer manager aware that the replacement policy is
	     * specified by replaceArg (i.e. FIFO, LRU, MRU, love/hate)
	     *
	     * @param numbufs number of buffers in the buffer poolb
	     * @param replaceArg name of the buffer replacement policy
	     */
	 
	public BufMgr(int numBufs, String replaceArg) {
		
		bufpool = new byte[numBufs][MINIBASE_PAGESIZE];
		map = new HashMap<PageId,Integer>();
		bufDescr = new Descriptor[numBufs];
		
		for(int i = 0; i < numBufs; i++)// Instantiate descriptors
			bufDescr[i] = new Descriptor();
		
		replacer = Replacer.getReplacer(bufDescr, replaceArg);
		
		diskManager = new DB();
		
	}

	 /**
     * Pin a page
     * First check if this page is already in the buffer pool.
     * If it is, increment the pin_count and return pointer to this
     * page. If the pin_count was 0 before the call, the page was a
     * replacement candidate, but is no longer a candidate.
     * If the page is not in the pool, choose a frame (from the
     * set of replacement candidates) to hold this page, read the
     * page (using the appropriate method from diskmgr package) and pin it.
     * Also, must write out the old page in chosen frame if it is dirty
     * before reading new page. (You can assume that emptyPage == false for
     * this assignment.)
     *
     * @param pgid page number in the minibase.
     * @param page the pointer point to the page.
     * @param emptyPage true (empty page), false (non­empty page).
	 * @throws IOException 
	 * @throws FileIOException 
	 * @throws InvalidPageNumberException 
     */
	public void pinPage(PageId pgid, Page page, boolean emptyPage, boolean loved) 
			throws InvalidPageNumberException,
			FileIOException,
			IOException {
		
		//check if this page is already in the buffer pool
		Integer index = map.get(pgid);
		if(index != null) {
			bufDescr[index].incrementPinCount();
			replacer.update(index, loved);
			page = new Page(bufpool[index]);
			return;
		}
		
		int candidtate = replacer.getCandidate();
		if(candidtate < 0)// there is no candidate for replacement
			return;
		
		
		// write out the old page in chosen frame if it is dirty
		if(bufDescr[candidtate].isDirtybit()) {
			diskManager.write_page(bufDescr[candidtate].getPagenumber(), page);
		}
		
		// remove the old page from hash table 
		map.remove(bufDescr[candidtate].getPagenumber());
		
		
		// load the page and put it in the frame
		diskManager.read_page(pgid, page);
		bufpool[candidtate] = page.getpage();
		bufDescr[candidtate] = new Descriptor();
		map.put(pgid, candidtate);
		replacer.update(candidtate, loved);
		
	
	}// end of pin page

	
	/**
	 * Unpin a page specified by a pageId. This method should be called with
	 * dirty == true if the client has modified the page. If so, this call
	 * should set the dirty bit for this frame. Further, if pin_count > 0, this
	 * method should decrement it. If pin_count = 0 before this call, throw an
	 * exception to report error. (for testing purposes, we ask you to throw an
	 * exception named PageUnpinnedExcpetion in case of error.)
	 * 
	 * @param pgid
	 *            page number in the minibase
	 * @param dirty
	 *            the dirty bit of the frame.
	 */
	public void unpinPage(PageId pgid, boolean dirty, boolean loved)throws ChainException {
	}

	/**
     * Allocate new page(s).
     * Call DB Object to allocate a run of new pages and* find a frame in the buffer pool for the first page
     * and pin it. (This call allows a client f the Buffer Manager
     * to allocate pages on disk.) If buffer is full, i.e., you
     * can\t find a frame for the first page, ask DB to deallocate
     * all these pages, and return null.
     *
     * @param firstPage the address of the first page.
     * @param howmany total number of allocated new pages.
     *
     * @return the first page id of the new pages. null, if error.
     */
	public PageId newPage(Page firstPage, int howmany) {
		return null;
	}

	/**
	 * This method should be called to delete a page that is on disk. This
	 * routine must call the method in diskmgr package to deallocate the page.
	 * 
	 * @param pgid
	 *            the page number in the database.
	 */
	public void freePage(PageId pgid) throws ChainException {
	}

	/**
	 * Used to flush a particular page of the buffer pool to disk. This method
	 * calls the write_page method of the diskmgr package.
	 * 
	 * @param pgid
	 *            the page number in the database.
	 */
	public void flushPage(PageId pgid) {
	}
/////////////////////////////////////////////////////////////////////
	
	/**
	 * what does the function do ? it's just hear because it's needed in BMTest.
	 * @return
	 */
	public int getNumUnpinnedBuffers() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * this function is just because BMTest calls pinPage with out the love/hate boolean
	 * so in order not to make changes in BMTest this function was created
	 * @param pid
	 * @param pg
	 * @param b
	 * @throws IOException 
	 */
	public void pinPage(PageId pid, Page pg, boolean b) 
			throws ChainException,
			IOException {
		
		pinPage(pid, pg,b,false);
	}
	
	public void flushAllPages(){}
	
	
	
}