package bufmgr;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HATE extends Replacer{
	
	private ArrayList<Integer> hated;
	private ArrayList<Integer> loved;
	
	public HATE(Descriptor[] bufDescriptors) {
		bufDescr = bufDescriptors;
		hated = new ArrayList<Integer>();
		loved = new ArrayList<Integer>();
		for(int i = 0; i < bufDescr.length; i++)
			hated.add(i);
	}
	
	
	
	@Override
	public int getCandidate() {
		
		if(hated.size() != 0) {
			int j = hated.size();
			for(int i = 0; i < j; i++) {
				if(bufDescr[hated.get(i)].getPin_count() == 0) {
					j = hated.get(i);
					hated.remove(i);
					return j;
				}
			}
		}
		
		else if(loved.size() != 0) {
			int j = loved.size();
			for(int i = loved.size()-1; i >= 0; i--) {
				if(bufDescr[loved.get(i)].getPin_count() == 0) {
					j = loved.get(i);
					loved.remove(i);
					return j;
				}
			}
		}
		
		return -1;
	}
	
	@Override
	public void update(int index, boolean l) {
		if(l) {
			hated.remove(index);
			if(!loved.contains(index))
				loved.add(index);
		}
		else {
			loved.remove(index);
			if(!hated.contains(index))
				hated.add(index);
		}
	}
	
	
}// end of HATE



