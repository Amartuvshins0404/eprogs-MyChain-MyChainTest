package dataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Standalone unit tests for MyChain class
 * This version works without Maven - just compile and run with javac/java
 */
public class MyChainTest {

    private static int testsRun = 0;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("MyChain Unit Tests (Standalone Version)");
        System.out.println("==========================================");
        
        MyChainTest test = new MyChainTest();
        test.runAllTests();
        
        System.out.println("\n==========================================");
        System.out.println("Test Summary:");
        System.out.println("Tests Run: " + testsRun);
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + testsFailed);
        System.out.println("Success Rate: " + (testsRun > 0 ? (testsPassed * 100 / testsRun) : 0) + "%");
        System.out.println("==========================================");
        
        if (testsFailed == 0) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.println("❌ Some tests failed!");
            System.exit(1);
        }
    }

    private void runAllTests() {
        testConstructors();
        testBasicOperations();
        testAddOperations();
        testGetOperations();
        testIndexOfOperations();
        testRemoveOperations();
        testToStringOperations();
        testIteratorOperations();
        testToArrayOperations();
        testAddRangeOperations();
        testUnionOperations();
        testIntersectionOperations();
        testComplexScenarios();
    }

    private void assertTrue(boolean condition, String message) {
        testsRun++;
        if (condition) {
            testsPassed++;
            System.out.println("✅ " + message);
        } else {
            testsFailed++;
            System.out.println("❌ " + message);
        }
    }

    private void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    private void assertEquals(Object expected, Object actual, String message) {
        boolean equal = (expected == null) ? (actual == null) : expected.equals(actual);
        if (equal) {
            testsRun++;
            testsPassed++;
            System.out.println("✅ " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
        } else {
            testsRun++;
            testsFailed++;
            System.out.println("❌ " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private void assertThrows(Class<? extends Exception> expectedException, String message) {
        // This is a placeholder - we'll handle exceptions in individual test methods
        testsRun++;
        testsFailed++;
        System.out.println("❌ " + message + " (Exception testing needs to be implemented manually)");
    }

    // ==================== CONSTRUCTOR TESTS ====================
    private void testConstructors() {
        System.out.println("\n--- Constructor Tests ---");
        
        MyChain chain1 = new MyChain();
        assertTrue(chain1.isEmpty(), "Default constructor creates empty chain");
        assertEquals(0, chain1.size(), "Default constructor creates chain with size 0");
        
        MyChain chain2 = new MyChain(10);
        assertTrue(chain2.isEmpty(), "Constructor with capacity creates empty chain");
        assertEquals(0, chain2.size(), "Constructor with capacity creates chain with size 0");
    }

    // ==================== BASIC OPERATIONS TESTS ====================
    private void testBasicOperations() {
        System.out.println("\n--- Basic Operations Tests ---");
        
        MyChain chain = new MyChain();
        assertTrue(chain.isEmpty(), "New chain is empty");
        assertEquals(0, chain.size(), "New chain has size 0");
        
        chain.add(0, "element");
        assertFalse(chain.isEmpty(), "Adding element makes chain non-empty");
        assertEquals(1, chain.size(), "Size increases to 1 after adding element");
        
        chain.add(1, "second");
        assertEquals(2, chain.size(), "Size increases to 2 after adding second element");
    }

    // ==================== ADD OPERATION TESTS ====================
    private void testAddOperations() {
        System.out.println("\n--- Add Operation Tests ---");
        
        MyChain chain = new MyChain();
        
        chain.add(0, "first");
        assertEquals("first", chain.get(0), "Add at beginning of empty chain");
        assertEquals(1, chain.size(), "Size is 1 after adding first element");
        
        chain.add(1, "second");
        assertEquals("second", chain.get(1), "Add at end of chain");
        assertEquals(2, chain.size(), "Size is 2 after adding second element");
        
        chain.add(1, "middle");
        assertEquals("first", chain.get(0), "First element unchanged after middle insert");
        assertEquals("middle", chain.get(1), "Middle element inserted correctly");
        assertEquals("second", chain.get(2), "Second element moved to position 2");
        assertEquals(3, chain.size(), "Size is 3 after middle insert");
        
        // Test invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.add(-1, "invalid");
        }, "Add with negative index throws IndexOutOfBoundsException");
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.add(5, "invalid");
        }, "Add with index > size throws IndexOutOfBoundsException");
    }

    // ==================== GET OPERATION TESTS ====================
    private void testGetOperations() {
        System.out.println("\n--- Get Operation Tests ---");
        
        MyChain chain = new MyChain();
        chain.add(0, "test");
        assertEquals("test", chain.get(0), "Get element from single element chain");
        
        chain.add(1, "second");
        chain.add(2, "third");
        assertEquals("test", chain.get(0), "Get first element");
        assertEquals("second", chain.get(1), "Get second element");
        assertEquals("third", chain.get(2), "Get third element");
        
        // Test invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.get(-1);
        }, "Get with negative index throws IndexOutOfBoundsException");
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            chain.get(3);
        }, "Get with index >= size throws IndexOutOfBoundsException");
    }

    // ==================== INDEXOF OPERATION TESTS ====================
    private void testIndexOfOperations() {
        System.out.println("\n--- IndexOf Operation Tests ---");
        
        MyChain chain = new MyChain();
        assertEquals(-1, chain.indexOf("not found"), "IndexOf in empty chain returns -1");
        
        chain.add(0, "first");
        chain.add(1, "second");
        chain.add(2, "third");
        
        assertEquals(0, chain.indexOf("first"), "IndexOf existing first element");
        assertEquals(1, chain.indexOf("second"), "IndexOf existing second element");
        assertEquals(2, chain.indexOf("third"), "IndexOf existing third element");
        assertEquals(-1, chain.indexOf("not found"), "IndexOf non-existing element returns -1");
        
        chain.add(3, "first"); // Add duplicate
        assertEquals(0, chain.indexOf("first"), "IndexOf returns first occurrence");
    }

    // ==================== REMOVE OPERATION TESTS ====================
    private void testRemoveOperations() {
        System.out.println("\n--- Remove Operation Tests ---");
        
        MyChain chain = new MyChain();
        chain.add(0, "only");
        Object removed = chain.remove(0);
        assertEquals("only", removed, "Remove from single element chain");
        assertTrue(chain.isEmpty(), "Chain is empty after removing only element");
        assertEquals(0, chain.size(), "Size is 0 after removing only element");
        
        // Reset chain
        chain = new MyChain();
        chain.add(0, "first");
        chain.add(1, "second");
        chain.add(2, "third");
        
        removed = chain.remove(0);
        assertEquals("first", removed, "Remove first element");
        assertEquals(2, chain.size(), "Size is 2 after removing first element");
        assertEquals("second", chain.get(0), "Second element moved to position 0");
        assertEquals("third", chain.get(1), "Third element moved to position 1");
        
        removed = chain.remove(1);
        assertEquals("third", removed, "Remove last element");
        assertEquals(1, chain.size(), "Size is 1 after removing last element");
        assertEquals("second", chain.get(0), "Second element remains at position 0");
        
        // Test invalid index
        final MyChain finalChain = chain;
        assertThrows(IndexOutOfBoundsException.class, () -> {
            finalChain.remove(-1);
        }, "Remove with negative index throws IndexOutOfBoundsException");
        
        assertThrows(IndexOutOfBoundsException.class, () -> {
            finalChain.remove(1);
        }, "Remove with index >= size throws IndexOutOfBoundsException");
    }

    // ==================== TOSTRING OPERATION TESTS ====================
    private void testToStringOperations() {
        System.out.println("\n--- ToString Operation Tests ---");
        
        MyChain chain = new MyChain();
        assertEquals("[]", chain.toString(), "ToString of empty chain");
        
        chain.add(0, "single");
        assertEquals("[single]", chain.toString(), "ToString of single element chain");
        
        chain.add(1, "second");
        chain.add(2, "third");
        assertEquals("[single, second, third]", chain.toString(), "ToString of multi-element chain");
        
        MyChain nullChain = new MyChain();
        nullChain.add(0, null);
        nullChain.add(1, "not null");
        assertEquals("[null, not null]", nullChain.toString(), "ToString with null elements");
    }

    // ==================== ITERATOR OPERATION TESTS ====================
    private void testIteratorOperations() {
        System.out.println("\n--- Iterator Operation Tests ---");
        
        MyChain chain = new MyChain();
        Iterator iterator = chain.iterator();
        assertFalse(iterator.hasNext(), "Iterator hasNext on empty chain");
        
        chain.add(0, "first");
        iterator = chain.iterator();
        assertTrue(iterator.hasNext(), "Iterator hasNext on non-empty chain");
        
        chain.add(1, "second");
        chain.add(2, "third");
        iterator = chain.iterator();
        
        assertEquals("first", iterator.next(), "Iterator next returns first element");
        assertEquals("second", iterator.next(), "Iterator next returns second element");
        assertEquals("third", iterator.next(), "Iterator next returns third element");
        assertFalse(iterator.hasNext(), "Iterator hasNext returns false after all elements");
        
        // Test exceptions
        assertThrows(NoSuchElementException.class, () -> {
            chain.iterator().next();
        }, "Iterator next on empty chain throws NoSuchElementException");
        
        MyChain singleChain = new MyChain();
        singleChain.add(0, "only");
        Iterator singleIterator = singleChain.iterator();
        singleIterator.next();
        assertThrows(NoSuchElementException.class, () -> {
            singleIterator.next();
        }, "Iterator next beyond last element throws NoSuchElementException");
        
        assertThrows(UnsupportedOperationException.class, () -> {
            chain.iterator().remove();
        }, "Iterator remove throws UnsupportedOperationException");
    }

    // ==================== TOARRAY OPERATION TESTS ====================
    private void testToArrayOperations() {
        System.out.println("\n--- ToArray Operation Tests ---");
        
        MyChain chain = new MyChain();
        Object[] array = chain.toArray();
        assertEquals(0, array.length, "ToArray on empty chain returns empty array");
        
        chain.add(0, "single");
        array = chain.toArray();
        assertEquals(1, array.length, "ToArray on single element chain returns array of length 1");
        assertEquals("single", array[0], "ToArray returns correct element");
        
        chain.add(1, "second");
        chain.add(2, "third");
        array = chain.toArray();
        assertEquals(3, array.length, "ToArray on multi-element chain returns correct length");
        assertEquals("single", array[0], "ToArray first element correct");
        assertEquals("second", array[1], "ToArray second element correct");
        assertEquals("third", array[2], "ToArray third element correct");
        
        MyChain nullChain = new MyChain();
        nullChain.add(0, "first");
        nullChain.add(1, null);
        nullChain.add(2, "third");
        array = nullChain.toArray();
        assertEquals("first", array[0], "ToArray with null elements - first element correct");
        assertEquals(null, array[1], "ToArray with null elements - null element correct");
        assertEquals("third", array[2], "ToArray with null elements - third element correct");
    }

    // ==================== ADDRANGE OPERATION TESTS ====================
    private void testAddRangeOperations() {
        System.out.println("\n--- AddRange Operation Tests ---");
        
        MyChain chain = new MyChain();
        Object[] elements = {"A", "B", "C"};
        chain.addRange(elements);
        assertEquals(3, chain.size(), "AddRange to empty chain increases size to 3");
        assertEquals("A", chain.get(0), "AddRange first element correct");
        assertEquals("B", chain.get(1), "AddRange second element correct");
        assertEquals("C", chain.get(2), "AddRange third element correct");
        
        chain = new MyChain();
        chain.add(0, "existing");
        chain.addRange(elements);
        assertEquals(4, chain.size(), "AddRange to non-empty chain increases size correctly");
        assertEquals("existing", chain.get(0), "AddRange preserves existing elements");
        assertEquals("A", chain.get(1), "AddRange adds new elements at end");
        assertEquals("B", chain.get(2), "AddRange adds second new element");
        assertEquals("C", chain.get(3), "AddRange adds third new element");
        
        chain = new MyChain();
        chain.add(0, "existing");
        Object[] emptyArray = {};
        chain.addRange(emptyArray);
        assertEquals(1, chain.size(), "AddRange with empty array doesn't change size");
        assertEquals("existing", chain.get(0), "AddRange with empty array preserves existing element");
        
        chain = new MyChain();
        Object[] singleElement = {"only"};
        chain.addRange(singleElement);
        assertEquals(1, chain.size(), "AddRange with single element creates size 1");
        assertEquals("only", chain.get(0), "AddRange with single element adds correctly");
    }

    // ==================== UNION OPERATION TESTS ====================
    private void testUnionOperations() {
        System.out.println("\n--- Union Operation Tests ---");
        
        MyChain chain1 = new MyChain();
        MyChain chain2 = new MyChain();
        MyChain result = chain1.union(chain2);
        assertTrue(result.isEmpty(), "Union of two empty chains is empty");
        assertEquals(0, result.size(), "Union of two empty chains has size 0");
        
        chain2.add(0, "A");
        chain2.add(1, "B");
        result = chain1.union(chain2);
        assertEquals(2, result.size(), "Union of empty with non-empty chain has correct size");
        assertEquals("A", result.get(0), "Union preserves elements from non-empty chain");
        assertEquals("B", result.get(1), "Union preserves second element from non-empty chain");
        
        chain1 = new MyChain();
        chain1.add(0, "A");
        chain1.add(1, "B");
        chain2 = new MyChain();
        result = chain1.union(chain2);
        assertEquals(2, result.size(), "Union of non-empty with empty chain has correct size");
        assertEquals("A", result.get(0), "Union preserves elements from first chain");
        assertEquals("B", result.get(1), "Union preserves second element from first chain");
        
        chain1 = new MyChain();
        chain1.add(0, "A");
        chain1.add(1, "B");
        chain2 = new MyChain();
        chain2.add(0, "C");
        chain2.add(1, "D");
        result = chain1.union(chain2);
        assertEquals(4, result.size(), "Union of two non-empty chains has correct size");
        assertEquals("A", result.get(0), "Union first element from first chain");
        assertEquals("B", result.get(1), "Union second element from first chain");
        assertEquals("C", result.get(2), "Union first element from second chain");
        assertEquals("D", result.get(3), "Union second element from second chain");
        
        // Test that original chains are unchanged
        assertEquals(2, chain1.size(), "Union preserves original first chain size");
        assertEquals("A", chain1.get(0), "Union preserves original first chain elements");
        assertEquals(2, chain2.size(), "Union preserves original second chain size");
        assertEquals("C", chain2.get(0), "Union preserves original second chain elements");
    }

    // ==================== INTERSECTION OPERATION TESTS ====================
    private void testIntersectionOperations() {
        System.out.println("\n--- Intersection Operation Tests ---");
        
        MyChain chain1 = new MyChain();
        MyChain chain2 = new MyChain();
        MyChain result = chain1.intersection(chain2);
        assertTrue(result.isEmpty(), "Intersection of two empty chains is empty");
        assertEquals(0, result.size(), "Intersection of two empty chains has size 0");
        
        chain2.add(0, "A");
        chain2.add(1, "B");
        result = chain1.intersection(chain2);
        assertTrue(result.isEmpty(), "Intersection of empty with non-empty chain is empty");
        assertEquals(0, result.size(), "Intersection of empty with non-empty chain has size 0");
        
        chain1 = new MyChain();
        chain1.add(0, "A");
        chain1.add(1, "B");
        chain2 = new MyChain();
        result = chain1.intersection(chain2);
        assertTrue(result.isEmpty(), "Intersection of non-empty with empty chain is empty");
        assertEquals(0, result.size(), "Intersection of non-empty with empty chain has size 0");
        
        chain1 = new MyChain();
        chain1.add(0, "A");
        chain1.add(1, "B");
        chain2 = new MyChain();
        chain2.add(0, "C");
        chain2.add(1, "D");
        result = chain1.intersection(chain2);
        assertTrue(result.isEmpty(), "Intersection with no common elements is empty");
        assertEquals(0, result.size(), "Intersection with no common elements has size 0");
        
        chain1 = new MyChain();
        chain1.add(0, "A");
        chain1.add(1, "B");
        chain1.add(2, "C");
        chain2 = new MyChain();
        chain2.add(0, "B");
        chain2.add(1, "C");
        chain2.add(2, "D");
        result = chain1.intersection(chain2);
        assertEquals(2, result.size(), "Intersection with common elements has correct size");
        assertEquals("B", result.get(0), "Intersection returns first common element");
        assertEquals("C", result.get(1), "Intersection returns second common element");
        
        // Test duplicate removal
        chain1 = new MyChain();
        chain1.add(0, "A");
        chain1.add(1, "B");
        chain1.add(2, "B"); // Duplicate
        chain2 = new MyChain();
        chain2.add(0, "B");
        chain2.add(1, "C");
        result = chain1.intersection(chain2);
        assertEquals(1, result.size(), "Intersection removes duplicates");
        assertEquals("B", result.get(0), "Intersection returns unique common element");
        
        // Test that original chains are unchanged
        assertEquals(3, chain1.size(), "Intersection preserves original first chain size");
        assertEquals("A", chain1.get(0), "Intersection preserves original first chain elements");
        assertEquals(2, chain2.size(), "Intersection preserves original second chain size");
        assertEquals("B", chain2.get(0), "Intersection preserves original second chain elements");
    }

    // ==================== COMPLEX SCENARIO TESTS ====================
    private void testComplexScenarios() {
        System.out.println("\n--- Complex Scenario Tests ---");
        
        MyChain chain = new MyChain();
        chain.add(0, "A");
        chain.add(1, "B");
        chain.add(2, "C");
        assertEquals("[A, B, C]", chain.toString(), "Initial chain setup");
        
        chain.remove(1);
        assertEquals("[A, C]", chain.toString(), "Remove middle element");
        
        chain.add(1, "D");
        assertEquals("[A, D, C]", chain.toString(), "Add new element in middle");
        
        // Test addRange followed by union and intersection
        MyChain chain2 = new MyChain();
        chain2.add(0, "1");
        chain2.add(1, "2");
        Object[] range = {"3", "4", "5"};
        chain2.addRange(range);
        assertEquals(5, chain2.size(), "AddRange increases size correctly");
        assertEquals("[1, 2, 3, 4, 5]", chain2.toString(), "AddRange creates correct chain");
        
        MyChain chain3 = new MyChain();
        chain3.add(0, "4");
        chain3.add(1, "5");
        chain3.add(2, "6");
        
        MyChain unionResult = chain2.union(chain3);
        assertEquals(6, unionResult.size(), "Complex union has correct size");
        
        MyChain intersectionResult = chain2.intersection(chain3);
        assertEquals(2, intersectionResult.size(), "Complex intersection has correct size");
        assertEquals("4", intersectionResult.get(0), "Complex intersection first element");
        assertEquals("5", intersectionResult.get(1), "Complex intersection second element");
    }
}
