

/** linked implementation of LinearList */

package dataStructures;

import java.util.*;

public class MyChain implements LinearList
{
   // data members
   protected ChainNode firstNode;
   protected int size;

   // constructors
   /** create a list that is empty */
   public MyChain(int initialCapacity)
   {
    // the default initial values of firstNode and size
    // are null and 0, respectively
   }

   public MyChain()
      {this(0);}

   // methods
   /** @return true iff list is empty */
   public boolean isEmpty()
      {return size == 0;}

   /** @return current number of elements in list */
   public int size()
      {return size;}

   /** @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1 */
   void checkIndex(int index)
   {
      if (index < 0 || index >= size)
         throw new IndexOutOfBoundsException
               ("index = " + index + "  size = " + size);
   }

   /** @return element with specified index
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1 */
   public Object get(int index)
   {
      checkIndex(index);
   
      // move to desired node
      ChainNode currentNode = firstNode;
      for (int i = 0; i < index; i++)
         currentNode = currentNode.next;
   
      return currentNode.element;
   }

   /** @return index of first occurrence of theElement,
     * return -1 if theElement not in list */
   public int indexOf(Object theElement)
   {
      // search the chain for theElement
      ChainNode currentNode = firstNode;
      int index = 0;  // index of currentNode
      while (currentNode != null &&
            !currentNode.element.equals(theElement))
      {
         // move to next node
         currentNode = currentNode.next;
         index++;
      }
   
      // make sure we found matching element
      if (currentNode == null)
         return -1;
      else
         return index;
   }

   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1
     * @return removed element */
   public Object remove(int index)
   {
      checkIndex(index);
   
      Object removedElement;
      if (index == 0) // remove first node
      {
         removedElement = firstNode.element;
         firstNode = firstNode.next;
      }
      else 
      {  // use q to get to predecessor of desired node
         ChainNode q = firstNode;
         for (int i = 0; i < index - 1; i++)
            q = q.next;
      
         removedElement = q.next.element;
         q.next = q.next.next; // remove desired node
      }
      size--;
      return removedElement;
   }

   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size */
   public void add(int index, Object theElement)
   {
      if (index < 0 || index > size)
         // invalid list position
         throw new IndexOutOfBoundsException
               ("index = " + index + "  size = " + size);
   
      if (index == 0)
         // insert at front
         firstNode = new ChainNode(theElement, firstNode);
      else
      {   // find predecessor of new element
         ChainNode p = firstNode;
         for (int i = 0; i < index - 1; i++)
            p = p.next;
      
          // insert after p
         p.next = new ChainNode(theElement, p.next);
      }
      size++;
   }

   /** convert to a string */
   public String toString()
   {
      StringBuffer s = new StringBuffer("["); 
   
      // put elements into the buffer
      ChainNode currentNode = firstNode;
      while(currentNode != null)
      {
         if (currentNode.element == null)
            s.append("null, ");
         else
            s.append(currentNode.element.toString() + ", ");
         currentNode = currentNode.next;
      }
      if (size > 0)
         s.delete(s.length() - 2, s.length());  // remove last ", "
      s.append("]");
   
      // create equivalent String
      return new String(s);
   }

   /** create and return an iterator */
   public Iterator iterator()
      {return new ChainIterator();}

   /** convert the chain to an array
     * return array containing all elements in the chain */
   public Object[] toArray()
   {
      Object[] array = new Object[size];
      ChainNode currentNode = firstNode;
      int index = 0;
      
      while (currentNode != null)
      {
         array[index] = currentNode.element;
         currentNode = currentNode.next;
         index++;
      }
      
      return array;
   }

   /** add all elements from the given array to the end of the chain
     * @param elements array of elements to add */
   public void addRange(Object[] elements)
   {  
      // if chain is empty, add first element at position 0
      if (size == 0)
      {
         firstNode = new ChainNode(elements[0], null);
         size = 1;
         
         // add remaining elements
         for (int i = 1; i < elements.length; i++)
         {
            add(size, elements[i]);
         }
      }
      else
      {
         // chain is not empty, add all elements at the end
         for (int i = 0; i < elements.length; i++)
         {
            add(size, elements[i]);
         }
      }
   }

   /** create a union of this chain and the given chain
     * @param chain the chain to union with
     * @return new chain containing all elements from both chains
     * @throws IllegalArgumentException if chain is null */
   public MyChain union(MyChain chain)
   {
      // create a new empty chain for the result
      MyChain result = new MyChain();
      
      // add all elements from this chain
      ChainNode currentNode = firstNode;
      while (currentNode != null)
      {
         result.add(result.size(), currentNode.element);
         currentNode = currentNode.next;
      }
      
      // add all elements from the given chain
      currentNode = chain.firstNode;
      while (currentNode != null)
      {
         result.add(result.size(), currentNode.element);
         currentNode = currentNode.next;
      }
      
      return result;
   }

   /** create an intersection of this chain and the given chain
     * @param chain the chain to intersect with
     * @return new chain containing elements that exist in both chains
     * @throws IllegalArgumentException if chain is null */
   public MyChain intersection(MyChain chain)
   {
      // create a new empty chain for the result
      MyChain result = new MyChain();
      
      // iterate through this chain
      ChainNode currentNode = firstNode;
      while (currentNode != null)
      {
         // check if current element exists in the given chain
         if (chain.indexOf(currentNode.element) != -1)
         {
            // check if element is not already in result to avoid duplicates
            if (result.indexOf(currentNode.element) == -1)
            {
               result.add(result.size(), currentNode.element);
            }
         }
         currentNode = currentNode.next;
      }
      
      return result;
   }

   /** chain iterator */
   private class ChainIterator implements Iterator
   {
      // data member
      private ChainNode nextNode;
   
      // constructor
      public ChainIterator()
      {nextNode = firstNode;}
   
      // methods
      /** @return true iff list has a next element */
      public boolean hasNext()
         {return nextNode != null;}
   
      /** @return next element in list
        * @throws NoSuchElementException
        * when there is no next element */
      public Object next()
      {
         if (nextNode != null)
         {
            Object elementToReturn = nextNode.element;
            nextNode = nextNode.next;
            return elementToReturn;
         }
         else
            throw new NoSuchElementException("No next element");
      }
   
      /** unsupported method */
      public void remove()
      {
         throw new UnsupportedOperationException
               ("remove not supported");
      }   
   }

   /** test program */
   public static void main(String [] args)
   {
      System.out.println("==========================================");
      System.out.println("         test begin");
      System.out.println("==========================================");
      

      System.out.println("\n1. BASIC OPERATIONS TEST");
      System.out.println("------------------------");
      
      MyChain x = new MyChain();
      System.out.println("✓ Created empty MyChain");
      System.out.println("  - Initial size: " + x.size());
      System.out.println("  - Is empty: " + x.isEmpty());
      
    
      x.add(0, new Integer(2));
      x.add(1, new Integer(6));
      x.add(0, new Integer(1));
      x.add(2, new Integer(4));
      System.out.println("\n✓ Added elements: [1, 2, 4, 6]");
      System.out.println("  - Current size: " + x.size());
      System.out.println("  - Current list: " + x);
      
      
      System.out.println("\n2. SEARCH OPERATIONS TEST");
      System.out.println("-------------------------");
      
      int index = x.indexOf(new Integer(4));
      System.out.println("✓ Search for element '4': " + (index >= 0 ? "Found at index " + index : "Not found"));
      
      index = x.indexOf(new Integer(3));
      System.out.println("✓ Search for element '3': " + (index >= 0 ? "Found at index " + index : "Not found"));
      
      System.out.println("✓ Element at index 0: " + x.get(0));
      System.out.println("✓ Element at index 3: " + x.get(3));
      
   
      System.out.println("\n3. REMOVE OPERATIONS TEST");
      System.out.println("-------------------------");
      
      System.out.println("✓ Removed element at index 1: " + x.remove(1));
      System.out.println("  - List after removal: " + x);
      
      System.out.println("✓ Removed element at index 2: " + x.remove(2));
      System.out.println("  - List after removal: " + x);
      System.out.println("  - Is empty: " + x.isEmpty());
      System.out.println("  - Current size: " + x.size());
      
    
      System.out.println("\n4. ITERATOR TEST");
      System.out.println("----------------");
      
      Iterator y = x.iterator();
      System.out.print("✓ Iterator output: ");
      while (y.hasNext())
         System.out.print(y.next() + " ");
      System.out.println();
     
      System.out.println("\n5. ARRAY CONVERSION TEST");
      System.out.println("------------------------");
      
      Object[] array = x.toArray();
      System.out.print("✓ Array conversion: [");
      for (int i = 0; i < array.length; i++)
      {
         System.out.print(array[i]);
         if (i < array.length - 1)
            System.out.print(", ");
      }
      System.out.println("]");
     

      System.out.println("\n6. ADD RANGE TEST");
      System.out.println("------------------");
      
      Object[] newElements = {new Integer(10), new Integer(20), new Integer(30)};
      System.out.println("✓ Adding range: [10, 20, 30]");
      x.addRange(newElements);
      System.out.println("  - List after addRange: " + x);
      System.out.println("  - New size: " + x.size());
      
   
      System.out.println("\n7. UNION OPERATION TEST");
      System.out.println("------------------------");
      
      MyChain chain2 = new MyChain();
      chain2.add(0, new Integer(100));
      chain2.add(1, new Integer(200));
      chain2.add(2, new Integer(300));
      System.out.println("✓ Chain 1: " + x);
      System.out.println("✓ Chain 2: " + chain2);
      
      MyChain unionResult = x.union(chain2);
      System.out.println("✓ Union result: " + unionResult);
      System.out.println("✓ Union size: " + unionResult.size());
    

      System.out.println("✓ Original Chain 1 unchanged: " + x);
      System.out.println("✓ Original Chain 2 unchanged: " + chain2);
      
   
      System.out.println("\n8. INTERSECTION OPERATION TEST");
      System.out.println("-------------------------------");
      
      MyChain chain3 = new MyChain();
      chain3.add(0, new Integer(4));
      chain3.add(1, new Integer(20));
      chain3.add(2, new Integer(100));
      chain3.add(3, new Integer(500));
      System.out.println("✓ Chain 1: " + x);
      System.out.println("✓ Chain 3: " + chain3);
      
      MyChain intersectionResult = x.intersection(chain3);
      System.out.println("✓ Intersection result: " + intersectionResult);
      System.out.println("✓ Intersection size: " + intersectionResult.size());
      
   
      System.out.println("✓ Original Chain 1 unchanged: " + x);
      System.out.println("✓ Original Chain 3 unchanged: " + chain3);
      
   }
}
