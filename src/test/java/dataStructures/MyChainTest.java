package dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for MyChain class
 * Tests all operations including union, intersection, addRange, and toArray
 */
@DisplayName("MyChain Class Tests")
public class MyChainTest {

    private MyChain myChain;

    @BeforeEach
    void setUp() {
        myChain = new MyChain();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor creates empty chain")
        void defaultConstructorCreatesEmptyChain() {
            MyChain newChain = new MyChain();
            assertTrue(newChain.isEmpty());
            assertEquals(0, newChain.size());
        }

        @Test
        @DisplayName("Constructor with initial capacity creates empty chain")
        void constructorWithCapacityCreatesEmptyChain() {
            MyChain newChain = new MyChain(10);
            assertTrue(newChain.isEmpty());
            assertEquals(0, newChain.size());
        }
    }

    @Nested
    @DisplayName("Basic Operations Tests")
    class BasicOperationsTests {

        @Test
        @DisplayName("New chain is empty")
        void newChainIsEmpty() {
            assertTrue(myChain.isEmpty());
            assertEquals(0, myChain.size());
        }

        @Test
        @DisplayName("Adding element makes chain non-empty")
        void addingElementMakesChainNonEmpty() {
            myChain.add(0, "element");
            assertFalse(myChain.isEmpty());
            assertEquals(1, myChain.size());
        }

        @Test
        @DisplayName("Size increases after adding elements")
        void sizeIncreasesAfterAddingElements() {
            myChain.add(0, "first");
            assertEquals(1, myChain.size());
            
            myChain.add(1, "second");
            assertEquals(2, myChain.size());
            
            myChain.add(0, "third");
            assertEquals(3, myChain.size());
        }
    }

    @Nested
    @DisplayName("Add Operation Tests")
    class AddOperationTests {

        @Test
        @DisplayName("Add at beginning of empty chain")
        void addAtBeginningOfEmptyChain() {
            myChain.add(0, "first");
            assertEquals("first", myChain.get(0));
            assertEquals(1, myChain.size());
        }

        @Test
        @DisplayName("Add at end of chain")
        void addAtEndOfChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            assertEquals("second", myChain.get(1));
            assertEquals(2, myChain.size());
        }

        @Test
        @DisplayName("Add at middle of chain")
        void addAtMiddleOfChain() {
            myChain.add(0, "first");
            myChain.add(1, "third");
            myChain.add(1, "second");
            
            assertEquals("first", myChain.get(0));
            assertEquals("second", myChain.get(1));
            assertEquals("third", myChain.get(2));
            assertEquals(3, myChain.size());
        }

        @Test
        @DisplayName("Add multiple elements in sequence")
        void addMultipleElementsInSequence() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            myChain.add(2, "C");
            
            assertEquals("A", myChain.get(0));
            assertEquals("B", myChain.get(1));
            assertEquals("C", myChain.get(2));
            assertEquals(3, myChain.size());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 1})
        @DisplayName("Add with invalid index throws IndexOutOfBoundsException")
        void addWithInvalidIndexThrowsException(int invalidIndex) {
            myChain.add(0, "first");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myChain.add(invalidIndex, "invalid");
            });
        }
    }

    @Nested
    @DisplayName("Get Operation Tests")
    class GetOperationTests {

        @Test
        @DisplayName("Get element from single element chain")
        void getElementFromSingleElementChain() {
            myChain.add(0, "test");
            assertEquals("test", myChain.get(0));
        }

        @Test
        @DisplayName("Get elements from multi-element chain")
        void getElementsFromMultiElementChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            assertEquals("first", myChain.get(0));
            assertEquals("second", myChain.get(1));
            assertEquals("third", myChain.get(2));
        }

        @Test
        @DisplayName("Get with negative index throws IndexOutOfBoundsException")
        void getWithNegativeIndexThrowsException() {
            myChain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myChain.get(-1);
            });
        }

        @Test
        @DisplayName("Get with index >= size throws IndexOutOfBoundsException")
        void getWithIndexOutOfBoundsThrowsException() {
            myChain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myChain.get(1);
            });
        }
    }

    @Nested
    @DisplayName("IndexOf Operation Tests")
    class IndexOfOperationTests {

        @Test
        @DisplayName("IndexOf in empty chain returns -1")
        void indexOfInEmptyChainReturnsMinusOne() {
            assertEquals(-1, myChain.indexOf("not found"));
        }

        @Test
        @DisplayName("IndexOf existing element returns correct index")
        void indexOfExistingElementReturnsCorrectIndex() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            assertEquals(0, myChain.indexOf("first"));
            assertEquals(1, myChain.indexOf("second"));
            assertEquals(2, myChain.indexOf("third"));
        }

        @Test
        @DisplayName("IndexOf non-existing element returns -1")
        void indexOfNonExistingElementReturnsMinusOne() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            
            assertEquals(-1, myChain.indexOf("not found"));
        }

        @Test
        @DisplayName("IndexOf returns first occurrence")
        void indexOfReturnsFirstOccurrence() {
            myChain.add(0, "duplicate");
            myChain.add(1, "unique");
            myChain.add(2, "duplicate");
            
            assertEquals(0, myChain.indexOf("duplicate"));
        }
    }

    @Nested
    @DisplayName("Remove Operation Tests")
    class RemoveOperationTests {

        @Test
        @DisplayName("Remove from single element chain")
        void removeFromSingleElementChain() {
            myChain.add(0, "only");
            Object removed = myChain.remove(0);
            
            assertEquals("only", removed);
            assertTrue(myChain.isEmpty());
            assertEquals(0, myChain.size());
        }

        @Test
        @DisplayName("Remove first element from multi-element chain")
        void removeFirstElementFromMultiElementChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            Object removed = myChain.remove(0);
            
            assertEquals("first", removed);
            assertEquals(2, myChain.size());
            assertEquals("second", myChain.get(0));
            assertEquals("third", myChain.get(1));
        }

        @Test
        @DisplayName("Remove middle element from multi-element chain")
        void removeMiddleElementFromMultiElementChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            Object removed = myChain.remove(1);
            
            assertEquals("second", removed);
            assertEquals(2, myChain.size());
            assertEquals("first", myChain.get(0));
            assertEquals("third", myChain.get(1));
        }

        @Test
        @DisplayName("Remove last element from multi-element chain")
        void removeLastElementFromMultiElementChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            Object removed = myChain.remove(2);
            
            assertEquals("third", removed);
            assertEquals(2, myChain.size());
            assertEquals("first", myChain.get(0));
            assertEquals("second", myChain.get(1));
        }

        @Test
        @DisplayName("Remove with negative index throws IndexOutOfBoundsException")
        void removeWithNegativeIndexThrowsException() {
            myChain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myChain.remove(-1);
            });
        }

        @Test
        @DisplayName("Remove with index >= size throws IndexOutOfBoundsException")
        void removeWithIndexOutOfBoundsThrowsException() {
            myChain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                myChain.remove(1);
            });
        }
    }

    @Nested
    @DisplayName("ToString Operation Tests")
    class ToStringOperationTests {

        @Test
        @DisplayName("ToString of empty chain")
        void toStringOfEmptyChain() {
            assertEquals("[]", myChain.toString());
        }

        @Test
        @DisplayName("ToString of single element chain")
        void toStringOfSingleElementChain() {
            myChain.add(0, "single");
            assertEquals("[single]", myChain.toString());
        }

        @Test
        @DisplayName("ToString of multi-element chain")
        void toStringOfMultiElementChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            assertEquals("[first, second, third]", myChain.toString());
        }

        @Test
        @DisplayName("ToString with null elements")
        void toStringWithNullElements() {
            myChain.add(0, null);
            myChain.add(1, "not null");
            assertEquals("[null, not null]", myChain.toString());
        }
    }

    @Nested
    @DisplayName("Iterator Tests")
    class IteratorTests {

        @Test
        @DisplayName("Iterator hasNext on empty chain")
        void iteratorHasNextOnEmptyChain() {
            Iterator iterator = myChain.iterator();
            assertFalse(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator hasNext on non-empty chain")
        void iteratorHasNextOnNonEmptyChain() {
            myChain.add(0, "first");
            Iterator iterator = myChain.iterator();
            assertTrue(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator next returns correct elements")
        void iteratorNextReturnsCorrectElements() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            Iterator iterator = myChain.iterator();
            assertEquals("first", iterator.next());
            assertEquals("second", iterator.next());
            assertEquals("third", iterator.next());
            assertFalse(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator next on empty chain throws NoSuchElementException")
        void iteratorNextOnEmptyChainThrowsException() {
            Iterator iterator = myChain.iterator();
            
            assertThrows(NoSuchElementException.class, () -> {
                iterator.next();
            });
        }

        @Test
        @DisplayName("Iterator next beyond last element throws NoSuchElementException")
        void iteratorNextBeyondLastElementThrowsException() {
            myChain.add(0, "only");
            Iterator iterator = myChain.iterator();
            iterator.next();
            
            assertThrows(NoSuchElementException.class, () -> {
                iterator.next();
            });
        }

        @Test
        @DisplayName("Iterator remove throws UnsupportedOperationException")
        void iteratorRemoveThrowsUnsupportedOperationException() {
            myChain.add(0, "test");
            Iterator iterator = myChain.iterator();
            
            assertThrows(UnsupportedOperationException.class, () -> {
                iterator.remove();
            });
        }
    }

    @Nested
    @DisplayName("ToArray Operation Tests")
    class ToArrayOperationTests {

        @Test
        @DisplayName("ToArray on empty chain returns empty array")
        void toArrayOnEmptyChainReturnsEmptyArray() {
            Object[] array = myChain.toArray();
            assertEquals(0, array.length);
        }

        @Test
        @DisplayName("ToArray on single element chain")
        void toArrayOnSingleElementChain() {
            myChain.add(0, "single");
            Object[] array = myChain.toArray();
            
            assertEquals(1, array.length);
            assertEquals("single", array[0]);
        }

        @Test
        @DisplayName("ToArray on multi-element chain")
        void toArrayOnMultiElementChain() {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            Object[] array = myChain.toArray();
            
            assertEquals(3, array.length);
            assertEquals("first", array[0]);
            assertEquals("second", array[1]);
            assertEquals("third", array[2]);
        }

        @Test
        @DisplayName("ToArray with null elements")
        void toArrayWithNullElements() {
            myChain.add(0, "first");
            myChain.add(1, null);
            myChain.add(2, "third");
            
            Object[] array = myChain.toArray();
            
            assertEquals(3, array.length);
            assertEquals("first", array[0]);
            assertNull(array[1]);
            assertEquals("third", array[2]);
        }
    }

    @Nested
    @DisplayName("AddRange Operation Tests")
    class AddRangeOperationTests {

        @Test
        @DisplayName("AddRange to empty chain")
        void addRangeToEmptyChain() {
            Object[] elements = {"A", "B", "C"};
            myChain.addRange(elements);
            
            assertEquals(3, myChain.size());
            assertEquals("A", myChain.get(0));
            assertEquals("B", myChain.get(1));
            assertEquals("C", myChain.get(2));
        }

        @Test
        @DisplayName("AddRange to non-empty chain")
        void addRangeToNonEmptyChain() {
            myChain.add(0, "existing");
            Object[] elements = {"A", "B", "C"};
            myChain.addRange(elements);
            
            assertEquals(4, myChain.size());
            assertEquals("existing", myChain.get(0));
            assertEquals("A", myChain.get(1));
            assertEquals("B", myChain.get(2));
            assertEquals("C", myChain.get(3));
        }

        @Test
        @DisplayName("AddRange with empty array")
        void addRangeWithEmptyArray() {
            myChain.add(0, "existing");
            Object[] elements = {};
            myChain.addRange(elements);
            
            assertEquals(1, myChain.size());
            assertEquals("existing", myChain.get(0));
        }

        @Test
        @DisplayName("AddRange with single element")
        void addRangeWithSingleElement() {
            Object[] elements = {"only"};
            myChain.addRange(elements);
            
            assertEquals(1, myChain.size());
            assertEquals("only", myChain.get(0));
        }
    }

    @Nested
    @DisplayName("Union Operation Tests")
    class UnionOperationTests {

        @Test
        @DisplayName("Union of two empty chains")
        void unionOfTwoEmptyChains() {
            MyChain chain2 = new MyChain();
            MyChain result = myChain.union(chain2);
            
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("Union of empty chain with non-empty chain")
        void unionOfEmptyChainWithNonEmptyChain() {
            MyChain chain2 = new MyChain();
            chain2.add(0, "A");
            chain2.add(1, "B");
            
            MyChain result = myChain.union(chain2);
            
            assertEquals(2, result.size());
            assertEquals("A", result.get(0));
            assertEquals("B", result.get(1));
        }

        @Test
        @DisplayName("Union of non-empty chain with empty chain")
        void unionOfNonEmptyChainWithEmptyChain() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            MyChain chain2 = new MyChain();
            
            MyChain result = myChain.union(chain2);
            
            assertEquals(2, result.size());
            assertEquals("A", result.get(0));
            assertEquals("B", result.get(1));
        }

        @Test
        @DisplayName("Union of two non-empty chains")
        void unionOfTwoNonEmptyChains() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "C");
            chain2.add(1, "D");
            
            MyChain result = myChain.union(chain2);
            
            assertEquals(4, result.size());
            assertEquals("A", result.get(0));
            assertEquals("B", result.get(1));
            assertEquals("C", result.get(2));
            assertEquals("D", result.get(3));
        }

        @Test
        @DisplayName("Union preserves original chains")
        void unionPreservesOriginalChains() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "C");
            chain2.add(1, "D");
            
            MyChain result = myChain.union(chain2);
            
            // Original chains should be unchanged
            assertEquals(2, myChain.size());
            assertEquals("A", myChain.get(0));
            assertEquals("B", myChain.get(1));
            
            assertEquals(2, chain2.size());
            assertEquals("C", chain2.get(0));
            assertEquals("D", chain2.get(1));
        }

        @Test
        @DisplayName("Union with duplicate elements")
        void unionWithDuplicateElements() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "B");
            chain2.add(1, "C");
            
            MyChain result = myChain.union(chain2);
            
            assertEquals(4, result.size());
            assertEquals("A", result.get(0));
            assertEquals("B", result.get(1));
            assertEquals("B", result.get(2)); // Duplicate is included
            assertEquals("C", result.get(3));
        }
    }

    @Nested
    @DisplayName("Intersection Operation Tests")
    class IntersectionOperationTests {

        @Test
        @DisplayName("Intersection of two empty chains")
        void intersectionOfTwoEmptyChains() {
            MyChain chain2 = new MyChain();
            MyChain result = myChain.intersection(chain2);
            
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("Intersection of empty chain with non-empty chain")
        void intersectionOfEmptyChainWithNonEmptyChain() {
            MyChain chain2 = new MyChain();
            chain2.add(0, "A");
            chain2.add(1, "B");
            
            MyChain result = myChain.intersection(chain2);
            
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("Intersection of non-empty chain with empty chain")
        void intersectionOfNonEmptyChainWithEmptyChain() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            MyChain chain2 = new MyChain();
            
            MyChain result = myChain.intersection(chain2);
            
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("Intersection with no common elements")
        void intersectionWithNoCommonElements() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "C");
            chain2.add(1, "D");
            
            MyChain result = myChain.intersection(chain2);
            
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
        }

        @Test
        @DisplayName("Intersection with common elements")
        void intersectionWithCommonElements() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            myChain.add(2, "C");
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "B");
            chain2.add(1, "C");
            chain2.add(2, "D");
            
            MyChain result = myChain.intersection(chain2);
            
            assertEquals(2, result.size());
            assertEquals("B", result.get(0));
            assertEquals("C", result.get(1));
        }

        @Test
        @DisplayName("Intersection removes duplicates")
        void intersectionRemovesDuplicates() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            myChain.add(2, "B"); // Duplicate in first chain
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "B");
            chain2.add(1, "C");
            
            MyChain result = myChain.intersection(chain2);
            
            assertEquals(1, result.size());
            assertEquals("B", result.get(0));
        }

        @Test
        @DisplayName("Intersection preserves original chains")
        void intersectionPreservesOriginalChains() {
            myChain.add(0, "A");
            myChain.add(1, "B");
            
            MyChain chain2 = new MyChain();
            chain2.add(0, "B");
            chain2.add(1, "C");
            
            MyChain result = myChain.intersection(chain2);
            
            // Original chains should be unchanged
            assertEquals(2, myChain.size());
            assertEquals("A", myChain.get(0));
            assertEquals("B", myChain.get(1));
            
            assertEquals(2, chain2.size());
            assertEquals("B", chain2.get(0));
            assertEquals("C", chain2.get(1));
        }
    }

    @Nested
    @DisplayName("Complex Scenario Tests")
    class ComplexScenarioTests {

        @Test
        @DisplayName("Add, remove, and add again maintains correct order")
        void addRemoveAndAddAgainMaintainsCorrectOrder() {
            // Add elements
            myChain.add(0, "A");
            myChain.add(1, "B");
            myChain.add(2, "C");
            assertEquals("[A, B, C]", myChain.toString());
            
            // Remove middle element
            myChain.remove(1);
            assertEquals("[A, C]", myChain.toString());
            
            // Add new element in middle
            myChain.add(1, "D");
            assertEquals("[A, D, C]", myChain.toString());
        }

        @Test
        @DisplayName("Multiple operations maintain chain integrity")
        void multipleOperationsMaintainChainIntegrity() {
            // Build initial chain
            myChain.add(0, "1");
            myChain.add(1, "2");
            myChain.add(2, "3");
            myChain.add(3, "4");
            
            // Remove first and last
            myChain.remove(0);
            myChain.remove(2);
            
            // Add new elements
            myChain.add(0, "0");
            myChain.add(2, "5");
            
            assertEquals("[0, 2, 5]", myChain.toString());
            assertEquals(3, myChain.size());
        }

        @Test
        @DisplayName("Complex union and intersection operations")
        void complexUnionAndIntersectionOperations() {
            // Setup first chain
            myChain.add(0, "A");
            myChain.add(1, "B");
            myChain.add(2, "C");
            
            // Setup second chain
            MyChain chain2 = new MyChain();
            chain2.add(0, "B");
            chain2.add(1, "C");
            chain2.add(2, "D");
            chain2.add(3, "E");
            
            // Test union
            MyChain unionResult = myChain.union(chain2);
            assertEquals(5, unionResult.size());
            assertEquals("A", unionResult.get(0));
            assertEquals("B", unionResult.get(1));
            assertEquals("C", unionResult.get(2));
            assertEquals("D", unionResult.get(3));
            assertEquals("E", unionResult.get(4));
            
            // Test intersection
            MyChain intersectionResult = myChain.intersection(chain2);
            assertEquals(2, intersectionResult.size());
            assertEquals("B", intersectionResult.get(0));
            assertEquals("C", intersectionResult.get(1));
        }

        @Test
        @DisplayName("AddRange followed by union and intersection")
        void addRangeFollowedByUnionAndIntersection() {
            // Add initial elements
            myChain.add(0, "1");
            myChain.add(1, "2");
            
            // Add range
            Object[] range = {"3", "4", "5"};
            myChain.addRange(range);
            
            assertEquals(5, myChain.size());
            assertEquals("[1, 2, 3, 4, 5]", myChain.toString());
            
            // Create second chain
            MyChain chain2 = new MyChain();
            chain2.add(0, "4");
            chain2.add(1, "5");
            chain2.add(2, "6");
            
            // Test operations
            MyChain unionResult = myChain.union(chain2);
            assertEquals(6, unionResult.size());
            
            MyChain intersectionResult = myChain.intersection(chain2);
            assertEquals(2, intersectionResult.size());
            assertEquals("4", intersectionResult.get(0));
            assertEquals("5", intersectionResult.get(1));
        }

        @ParameterizedTest
        @CsvSource({
            "0, 'first'",
            "1, 'second'", 
            "2, 'third'"
        })
        @DisplayName("Parameterized test for get operation")
        void parameterizedGetTest(int index, String expected) {
            myChain.add(0, "first");
            myChain.add(1, "second");
            myChain.add(2, "third");
            
            assertEquals(expected, myChain.get(index));
        }
    }
}
