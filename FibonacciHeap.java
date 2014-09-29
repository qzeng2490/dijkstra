package dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
public final class FibonacciHeap<T> {
	
    public static final class Entry1<T> {
        private int     degreeNode = 0;  
        private boolean childCut = false;

        private Entry1<T> preNode;
        private Entry1<T> nextNode;  
        private Entry1<T> parentNode; 
        private Entry1<T> childNode;

        private T      eleValue; 
          
        int keyValue; 

        public T getValue() {
            return eleValue;
        }

        public void setValue(T value) {
            eleValue = value;
        }

        public int getPriority() {
            return keyValue;
        }
        
        private Entry1(T elem, int priority) {
            nextNode = preNode = this;
            eleValue = elem;
            keyValue = priority;
        }
    }
    private Entry1<T> nodeMin = null;
    private int mSize = 0;
    public Entry1<T> enqueue(T value, int priority) {
        Entry1<T> result = new Entry1<T>(value, priority);
        nodeMin = mergeLists(nodeMin, result);
        ++mSize;
        return result;
    }
    public Entry1<T> min() {
        if (!isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        return nodeMin;
    }
    public boolean isEmpty() {
        return nodeMin == null;
    }
    public int size() {
        return mSize;
    }
    public static <T> FibonacciHeap<T> merge(FibonacciHeap<T> one, FibonacciHeap<T> two) {
    	FibonacciHeap<T> result = new FibonacciHeap<T>();
    	result.nodeMin = mergeLists(one.nodeMin, two.nodeMin);
    	result.mSize = one.mSize + two.mSize;
    	one.mSize = two.mSize = 0;
        one.nodeMin  = null;
        two.nodeMin  = null;

        return result;
    }   
    public Entry1<T> dequeueMin() {
    	if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
    	--mSize;
			
			//set new nodeMin
    	Entry1<T> minElem = nodeMin;
    	if (nodeMin.nextNode == nodeMin) {
            nodeMin = null;
      }else {
            nodeMin.preNode.nextNode = nodeMin.nextNode;
            nodeMin.nextNode.preNode = nodeMin.preNode;
            nodeMin = nodeMin.nextNode;
      }
			// remove children
    	if (minElem.childNode != null) {
    		Entry1<?> nowRun = minElem.childNode;
            do {
            	nowRun.parentNode = null;
                nowRun = nowRun.nextNode;
            } while (nowRun != minElem.childNode);
        }
				//add children to the root list
        nodeMin = mergeLists(nodeMin, minElem.childNode);     
				// minElem is old nodeMin , when nodeMin == null ??
        if (nodeMin == null) return minElem;     
				//Consolidate   
        List<Entry1<T>> treeTable = new ArrayList<Entry1<T>>();
        List<Entry1<T>> toVisit = new ArrayList<Entry1<T>>();
        for (Entry1<T> nowRun = nodeMin; toVisit.isEmpty() || toVisit.get(0) != nowRun; nowRun = nowRun.nextNode)
            toVisit.add(nowRun);
        for (Entry1<T> nowRun: toVisit) {
        		while (true) {
        				while (nowRun.degreeNode >= treeTable.size())
                    treeTable.add(null);
								
        				if (treeTable.get(nowRun.degreeNode) == null) {
                    treeTable.set(nowRun.degreeNode, nowRun);
                    break;
                }
								
        		Entry1<T> other = treeTable.get(nowRun.degreeNode);
                treeTable.set(nowRun.degreeNode, null); // Clear the slot
                Entry1<T> min = (other.keyValue < nowRun.keyValue)? other : nowRun;
                Entry1<T> max = (other.keyValue < nowRun.keyValue)? nowRun  : other;
            
								//remove max from root list
                max.nextNode.preNode = max.preNode;
                max.preNode.nextNode = max.nextNode;

                max.nextNode = max.preNode = max;
                min.childNode = mergeLists(min.childNode, max);
                
                max.parentNode = min;

                max.childCut = false;

                ++min.degreeNode;

                nowRun = min;
            }
        	if (nowRun.keyValue <= nodeMin.keyValue) nodeMin = nowRun;
        }
        return minElem;
    }
    public void decreaseKey(Entry1<T> entry, int newPriority ) {
        if (newPriority <= entry.keyValue)
        	decreaseKeyUnchecked(entry, newPriority);
    }  
    private static <T> Entry1<T> mergeLists(Entry1<T> one, Entry1<T> two) {	
        if (one != null && two == null) { 
            return one;       	
        }
        else if (one == null && two != null) { 
          	return two;      	
        }
        else if (one == null && two == null) { 
            return null;        	
        }       
        else {  
        	Entry1<T> oneNext = one.nextNode;
            one.nextNode = two.nextNode;
            one.nextNode.preNode = one;
            two.nextNode = oneNext;
            two.nextNode.preNode = two;
            return one.keyValue < two.keyValue? one : two;
        }
    } 
    private void decreaseKeyUnchecked(Entry1<T> entry, int priority) {
    	entry.keyValue = priority;
        if (entry.parentNode != null && entry.keyValue <= entry.parentNode.keyValue)
            cutNode(entry);
        if (entry.keyValue <= nodeMin.keyValue)
            nodeMin = entry;
    }
    private void cutNode(Entry1<T> entry) {
    	entry.childCut = false;
    	if (entry.parentNode == null) return;   	
    	if (entry.nextNode != entry) { // Has siblings
       		 entry.nextNode.preNode = entry.preNode;
           	 entry.preNode.nextNode = entry.nextNode;
      	}   	
      	if (entry.parentNode.childNode == entry) {
        	if (entry.nextNode != entry) {
                entry.parentNode.childNode = entry.nextNode;
          	}
        	else {
                entry.parentNode.childNode = null;
          	}
      	}
      	--entry.parentNode.degreeNode;
      	entry.preNode = entry.nextNode = entry;
        nodeMin = mergeLists(nodeMin, entry);
        if (entry.parentNode.childCut)
            cutNode(entry.parentNode);
        else
            entry.parentNode.childCut = true;
        entry.parentNode = null;
    }
}
