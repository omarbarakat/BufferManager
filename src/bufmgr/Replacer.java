package bufmgr;

/**
 * 
 * @author ahmed
 * replacer is used to get a candidate for replacement in buffer manager
 * using a replacement policy
 * 
 * replacement policies :
 * FIFO, LRU, MRU, love/hate
 * 
 */

public class Replacer {
	
	
	protected Descriptor bufDescr[];
	
	
	protected Replacer() {
		// TODO Auto-generated constructor stub
	}
	
	protected Replacer(Descriptor[] bufDiescr) {
		this.bufDescr = bufDescr ;
	}
	
	/**
	 * factory design pattern
	 * get a replacer object for the specified replacement policy
	 * 
	 * @param bufDescriptors used by replacer to find candidates
	 * @param policy
	 * @return replacer object that supports the specified policy
	 */
	
	public static Replacer getReplacer(Descriptor[] bufDescriptors,String replaceArg) {
		
		Replacer result = null;
		if(replaceArg.equals("LRU"))
			result = new LRU(bufDescriptors);
		else if(replaceArg.equals("MRU"))
			result = new MRU(bufDescriptors);
		else if(replaceArg.equals("FIFO"))
			result = new FIFO(bufDescriptors);
		else if(replaceArg.equals("love/hate"))
			result = new HATE(bufDescriptors);
		else if(replaceArg.equals("Clock"))
			result = new Clock(bufDescriptors);
		else
			throw new IllegalArgumentException("no such policy : "+replaceArg);
		
		result.bufDescr = bufDescriptors;
		return result;
	}
	
	
	
	/**
	 *  return index of candidate for replacement
	 *  if there is no candidate it return -1
	 * @return
	 */
	public int getCandidate() {
		return 0;
	}
	
	/**
	 * update a page if it's pinned or unpinned
	 * @param index
	 */
	public void update(int index, boolean loved) {
		
	}
	
	
}
