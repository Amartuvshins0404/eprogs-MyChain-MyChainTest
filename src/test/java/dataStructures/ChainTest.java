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
 * Comprehensive unit tests for Chain class
 */
@DisplayName("Chain Class Tests")
public class ChainTest {

    private Chain chain;

    @BeforeEach
    void setUp() {
        chain = new Chain();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor creates empty chain")
        void defaultConstructorCreatesEmptyChain() {
            Chain newChain = new Chain();
            assertTrue(newChain.isEmpty());
            assertEquals(0, newChain.size());
        }

        @Test
        @DisplayName("Constructor with initial capacity creates empty chain")
        void constructorWithCapacityCreatesEmptyChain() {
            Chain newChain = new Chain(10);
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
            assertTrue(chain.isEmpty());
            assertEquals(0, chain.size());
        }

        @Test
        @DisplayName("Adding element makes chain non-empty")
        void addingElementMakesChainNonEmpty() {
            chain.add(0, "element");
            assertFalse(chain.isEmpty());
            assertEquals(1, chain.size());
        }

        @Test
        @DisplayName("Size increases after adding elements")
        void sizeIncreasesAfterAddingElements() {
            chain.add(0, "first");
            assertEquals(1, chain.size());
            
            chain.add(1, "second");
            assertEquals(2, chain.size());
            
            chain.add(0, "third");
            assertEquals(3, chain.size());
        }
    }

    @Nested
    @DisplayName("Add Operation Tests")
    class AddOperationTests {

        @Test
        @DisplayName("Add at beginning of empty chain")
        void addAtBeginningOfEmptyChain() {
            chain.add(0, "first");
            assertEquals("first", chain.get(0));
            assertEquals(1, chain.size());
        }

        @Test
        @DisplayName("Add at end of chain")
        void addAtEndOfChain() {
            chain.add(0, "first");
            chain.add(1, "second");
            assertEquals("second", chain.get(1));
            assertEquals(2, chain.size());
        }

        @Test
        @DisplayName("Add at middle of chain")
        void addAtMiddleOfChain() {
            chain.add(0, "first");
            chain.add(1, "third");
            chain.add(1, "second");
            
            assertEquals("first", chain.get(0));
            assertEquals("second", chain.get(1));
            assertEquals("third", chain.get(2));
            assertEquals(3, chain.size());
        }

        @Test
        @DisplayName("Add multiple elements in sequence")
        void addMultipleElementsInSequence() {
            chain.add(0, "A");
            chain.add(1, "B");
            chain.add(2, "C");
            
            assertEquals("A", chain.get(0));
            assertEquals("B", chain.get(1));
            assertEquals("C", chain.get(2));
            assertEquals(3, chain.size());
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 1})
        @DisplayName("Add with invalid index throws IndexOutOfBoundsException")
        void addWithInvalidIndexThrowsException(int invalidIndex) {
            chain.add(0, "first");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                chain.add(invalidIndex, "invalid");
            });
        }

        @Test
        @DisplayName("Add at valid boundary index")
        void addAtValidBoundaryIndex() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            assertEquals(3, chain.size());
            assertEquals("third", chain.get(2));
        }
    }

    @Nested
    @DisplayName("Get Operation Tests")
    class GetOperationTests {

        @Test
        @DisplayName("Get element from single element chain")
        void getElementFromSingleElementChain() {
            chain.add(0, "test");
            assertEquals("test", chain.get(0));
        }

        @Test
        @DisplayName("Get elements from multi-element chain")
        void getElementsFromMultiElementChain() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            assertEquals("first", chain.get(0));
            assertEquals("second", chain.get(1));
            assertEquals("third", chain.get(2));
        }

        @Test
        @DisplayName("Get with negative index throws IndexOutOfBoundsException")
        void getWithNegativeIndexThrowsException() {
            chain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                chain.get(-1);
            });
        }

        @Test
        @DisplayName("Get with index >= size throws IndexOutOfBoundsException")
        void getWithIndexOutOfBoundsThrowsException() {
            chain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                chain.get(1);
            });
        }
    }

    @Nested
    @DisplayName("IndexOf Operation Tests")
    class IndexOfOperationTests {

        @Test
        @DisplayName("IndexOf in empty chain returns -1")
        void indexOfInEmptyChainReturnsMinusOne() {
            assertEquals(-1, chain.indexOf("not found"));
        }

        @Test
        @DisplayName("IndexOf existing element returns correct index")
        void indexOfExistingElementReturnsCorrectIndex() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            assertEquals(0, chain.indexOf("first"));
            assertEquals(1, chain.indexOf("second"));
            assertEquals(2, chain.indexOf("third"));
        }

        @Test
        @DisplayName("IndexOf non-existing element returns -1")
        void indexOfNonExistingElementReturnsMinusOne() {
            chain.add(0, "first");
            chain.add(1, "second");
            
            assertEquals(-1, chain.indexOf("not found"));
        }

        @Test
        @DisplayName("IndexOf returns first occurrence")
        void indexOfReturnsFirstOccurrence() {
            chain.add(0, "duplicate");
            chain.add(1, "unique");
            chain.add(2, "duplicate");
            
            assertEquals(0, chain.indexOf("duplicate"));
        }
    }

    @Nested
    @DisplayName("Remove Operation Tests")
    class RemoveOperationTests {

        @Test
        @DisplayName("Remove from single element chain")
        void removeFromSingleElementChain() {
            chain.add(0, "only");
            Object removed = chain.remove(0);
            
            assertEquals("only", removed);
            assertTrue(chain.isEmpty());
            assertEquals(0, chain.size());
        }

        @Test
        @DisplayName("Remove first element from multi-element chain")
        void removeFirstElementFromMultiElementChain() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            Object removed = chain.remove(0);
            
            assertEquals("first", removed);
            assertEquals(2, chain.size());
            assertEquals("second", chain.get(0));
            assertEquals("third", chain.get(1));
        }

        @Test
        @DisplayName("Remove middle element from multi-element chain")
        void removeMiddleElementFromMultiElementChain() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            Object removed = chain.remove(1);
            
            assertEquals("second", removed);
            assertEquals(2, chain.size());
            assertEquals("first", chain.get(0));
            assertEquals("third", chain.get(1));
        }

        @Test
        @DisplayName("Remove last element from multi-element chain")
        void removeLastElementFromMultiElementChain() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            Object removed = chain.remove(2);
            
            assertEquals("third", removed);
            assertEquals(2, chain.size());
            assertEquals("first", chain.get(0));
            assertEquals("second", chain.get(1));
        }

        @Test
        @DisplayName("Remove with negative index throws IndexOutOfBoundsException")
        void removeWithNegativeIndexThrowsException() {
            chain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                chain.remove(-1);
            });
        }

        @Test
        @DisplayName("Remove with index >= size throws IndexOutOfBoundsException")
        void removeWithIndexOutOfBoundsThrowsException() {
            chain.add(0, "test");
            
            assertThrows(IndexOutOfBoundsException.class, () -> {
                chain.remove(1);
            });
        }
    }

    @Nested
    @DisplayName("ToString Operation Tests")
    class ToStringOperationTests {

        @Test
        @DisplayName("ToString of empty chain")
        void toStringOfEmptyChain() {
            assertEquals("[]", chain.toString());
        }

        @Test
        @DisplayName("ToString of single element chain")
        void toStringOfSingleElementChain() {
            chain.add(0, "single");
            assertEquals("[single]", chain.toString());
        }

        @Test
        @DisplayName("ToString of multi-element chain")
        void toStringOfMultiElementChain() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            assertEquals("[first, second, third]", chain.toString());
        }

        @Test
        @DisplayName("ToString with null elements")
        void toStringWithNullElements() {
            chain.add(0, null);
            chain.add(1, "not null");
            assertEquals("[null, not null]", chain.toString());
        }
    }

    @Nested
    @DisplayName("Iterator Tests")
    class IteratorTests {

        @Test
        @DisplayName("Iterator hasNext on empty chain")
        void iteratorHasNextOnEmptyChain() {
            Iterator iterator = chain.iterator();
            assertFalse(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator hasNext on non-empty chain")
        void iteratorHasNextOnNonEmptyChain() {
            chain.add(0, "first");
            Iterator iterator = chain.iterator();
            assertTrue(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator next returns correct elements")
        void iteratorNextReturnsCorrectElements() {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            Iterator iterator = chain.iterator();
            assertEquals("first", iterator.next());
            assertEquals("second", iterator.next());
            assertEquals("third", iterator.next());
            assertFalse(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator next on empty chain throws NoSuchElementException")
        void iteratorNextOnEmptyChainThrowsException() {
            Iterator iterator = chain.iterator();
            
            assertThrows(NoSuchElementException.class, () -> {
                iterator.next();
            });
        }

        @Test
        @DisplayName("Iterator next beyond last element throws NoSuchElementException")
        void iteratorNextBeyondLastElementThrowsException() {
            chain.add(0, "only");
            Iterator iterator = chain.iterator();
            iterator.next();
            
            assertThrows(NoSuchElementException.class, () -> {
                iterator.next();
            });
        }

        @Test
        @DisplayName("Iterator remove throws UnsupportedOperationException")
        void iteratorRemoveThrowsUnsupportedOperationException() {
            chain.add(0, "test");
            Iterator iterator = chain.iterator();
            
            assertThrows(UnsupportedOperationException.class, () -> {
                iterator.remove();
            });
        }
    }

    @Nested
    @DisplayName("Complex Scenario Tests")
    class ComplexScenarioTests {

        @Test
        @DisplayName("Add, remove, and add again maintains correct order")
        void addRemoveAndAddAgainMaintainsCorrectOrder() {
            // Add elements
            chain.add(0, "A");
            chain.add(1, "B");
            chain.add(2, "C");
            assertEquals("[A, B, C]", chain.toString());
            
            // Remove middle element
            chain.remove(1);
            assertEquals("[A, C]", chain.toString());
            
            // Add new element in middle
            chain.add(1, "D");
            assertEquals("[A, D, C]", chain.toString());
        }

        @Test
        @DisplayName("Multiple operations maintain chain integrity")
        void multipleOperationsMaintainChainIntegrity() {
            // Build initial chain
            chain.add(0, "1");
            chain.add(1, "2");
            chain.add(2, "3");
            chain.add(3, "4");
            
            // Remove first and last
            chain.remove(0);
            chain.remove(2);
            
            // Add new elements
            chain.add(0, "0");
            chain.add(2, "5");
            
            assertEquals("[0, 2, 5]", chain.toString());
            assertEquals(3, chain.size());
        }

        @ParameterizedTest
        @CsvSource({
            "0, 'first'",
            "1, 'second'", 
            "2, 'third'"
        })
        @DisplayName("Parameterized test for get operation")
        void parameterizedGetTest(int index, String expected) {
            chain.add(0, "first");
            chain.add(1, "second");
            chain.add(2, "third");
            
            assertEquals(expected, chain.get(index));
        }
    }
}
