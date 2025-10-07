#!/bin/bash

echo "=========================================="
echo "Running MyChain Standalone Unit Tests"
echo "=========================================="

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo "Java compiler (javac) is not installed. Please install Java JDK first."
    exit 1
fi

if ! command -v java &> /dev/null; then
    echo "Java runtime (java) is not installed. Please install Java JDK first."
    exit 1
fi

echo "Java found. Compiling and running tests..."
echo

# Compile only the necessary files for MyChain test
echo "1. Compiling MyChain and MyChainTest..."
javac -cp . dataStructures/ChainNode.java dataStructures/LinearList.java dataStructures/Chain.java dataStructures/MyChain.java MyChainTestSimple.java

if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi

echo "Compilation successful!"
echo

# Run the tests
echo "2. Running MyChain unit tests..."
java -cp . MyChainTestSimple

echo
echo "=========================================="
echo "Test completed!"
echo "=========================================="
